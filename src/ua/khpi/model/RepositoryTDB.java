package ua.khpi.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;

import ua.khpi.model.api.IRepository;

public class RepositoryTDB implements IRepository {
	private Dataset dataset;

	@Override
	public void open() {
		dataset = TDBFactory.createDataset();
	}

	@Override
	public void close() {
		dataset.close();
	}

	@Override
	public void store(String name, Model model) {
		dataset.addNamedModel(name, model);
	}

	@Override
	public Model get(String name) {
		return dataset.getNamedModel(name);
	}

	@Override
	public List<Model> getAll() {
		List<Model> models = new ArrayList<Model>();

		for (Iterator<String> i = dataset.listNames(); i.hasNext();) {
			models.add(get(i.next()));
		}

		return models;
	}

	@Override
	public List<String> getAllNames() {
		List<String> names = new ArrayList<String>();

		for (Iterator<String> i = dataset.listNames(); i.hasNext();) {
			names.add(i.next());
		}

		return names;
	}
}
