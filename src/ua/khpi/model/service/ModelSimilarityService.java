package ua.khpi.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.StmtIterator;

import ua.khpi.model.api.IRepository;
import ua.khpi.model.service.api.IModelSimilarityService;
import ua.khpi.model.service.bean.SimilarModelBean;

public abstract class ModelSimilarityService implements IModelSimilarityService {
	private IRepository repository;

	private static final double Q_COEFF = 1.5;

	public ModelSimilarityService(IRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<SimilarModelBean> defineSimilarModels(String pattern) {
		List<String> names = repository.getAllNames();

		List<SimilarModelBean> similarModels = new ArrayList<SimilarModelBean>();

		Model model = repository.get(pattern);

		for (String name : names) {
			if (!name.equals(pattern)) {
				SimilarModelBean bean = new SimilarModelBean();

				bean.setName(name);
				bean.setSimilarity(calculateSimilarity(model, repository.get(name)));

				similarModels.add(bean);
			}
		}

		classifyModels(similarModels);
		scaleModels(similarModels);

		return similarModels;
	}

	private void classifyModels(List<SimilarModelBean> similarModels) {
		double[] membership = new double[similarModels.size()];
		double membershipSum = 0;

		for (int i = 0; i < similarModels.size(); i++) {
			double distance = 1.0 - similarModels.get(i).getSimilarity();

			if (distance < 10E-3) {
				distance = 10E-3;
			}

			membership[i] = 1.0 / Math.pow(distance, 1.0 / (Q_COEFF - 1.0));

			membershipSum += membership[i];
		}

		for (int i = 0; i < similarModels.size(); i++) {
			similarModels.get(i).setSimilarity(membership[i] / membershipSum);
		}
	}

	private void scaleModels(List<SimilarModelBean> similarModels) {
		double min = 0;
		double max = 0;

		for (int i = 0; i < similarModels.size(); i++) {
			min += similarModels.get(i).getSimilarity();
		}

		min /= similarModels.size();

		for (int i = 0; i < similarModels.size(); i++) {
			max += Math.pow(similarModels.get(i).getSimilarity() - min, 2);
		}

		max /= similarModels.size() - 1;
		max = min + Math.sqrt(max);

		for (int i = 0; i < similarModels.size(); i++) {
			similarModels.get(i)
					.setSimilarity(Math.exp(-Math.exp(-(similarModels.get(i).getSimilarity() - min) / (max - min))));
		}
	}

	private double calculateSimilarity(Model first, Model second) {
		Set<RDFNode> firstSubjects = new HashSet<RDFNode>();
		Set<RDFNode> secondSubjects = new HashSet<RDFNode>();

		for (ResIterator i = first.listSubjects(); i.hasNext();) {
			firstSubjects.add(i.nextResource());
		}

		for (ResIterator i = second.listSubjects(); i.hasNext();) {
			secondSubjects.add(i.nextResource());
		}

		Set<RDFNode> intersection = new HashSet<RDFNode>(firstSubjects);
		intersection.retainAll(secondSubjects);

		double result = 0;

		for (RDFNode subject : intersection) {
			Set<RDFNode> firstProperties = new HashSet<RDFNode>();
			Set<RDFNode> secondProperties = new HashSet<RDFNode>();

			for (StmtIterator i = first.listStatements(subject.asResource(), null, (RDFNode) null); i.hasNext();) {
				firstProperties.add(i.nextStatement().getPredicate());
			}

			for (StmtIterator i = second.listStatements(subject.asResource(), null, (RDFNode) null); i.hasNext();) {
				secondProperties.add(i.nextStatement().getPredicate());
			}

			Set<RDFNode> firstObjects = new HashSet<RDFNode>();
			Set<RDFNode> secondObjects = new HashSet<RDFNode>();

			for (StmtIterator i = first.listStatements(subject.asResource(), null, (RDFNode) null); i.hasNext();) {
				firstObjects.add(i.nextStatement().getObject());
			}

			for (StmtIterator i = second.listStatements(subject.asResource(), null, (RDFNode) null); i.hasNext();) {
				secondObjects.add(i.nextStatement().getObject());
			}

			result += (similarity(firstProperties, secondProperties) + similarity(firstObjects, secondObjects));
		}

		return result == 0 ? 0 : result / (2 * intersection.size());
	}
}
