package ua.khpi.model.service;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import ua.khpi.model.service.api.IModelLoadingService;
import ua.khpi.properties.Properties;

public class ModelLoadingService implements IModelLoadingService {

	@Override
	public Model loadModel(String name) {
		Model model = ModelFactory.createDefaultModel();

		model.read(Properties.MODELS_PATH + "\\" + name, "N-TRIPLES");

		return model;
	}
}
