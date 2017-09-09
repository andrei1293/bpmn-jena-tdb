package ua.khpi.model.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;

import ua.khpi.model.api.IRepository;

public class ModelJaccardSimilarityService extends ModelSimilarityService {

	public ModelJaccardSimilarityService(IRepository repository) {
		super(repository);
	}

	@Override
	public double similarity(Set<RDFNode> a, Set<RDFNode> b) {
		Set<RDFNode> union = new HashSet<RDFNode>();
		union.addAll(a);
		union.addAll(b);

		Set<RDFNode> intersection = new HashSet<RDFNode>(a);
		intersection.retainAll(b);

		return ((double) intersection.size()) / ((double) union.size());
	}
}
