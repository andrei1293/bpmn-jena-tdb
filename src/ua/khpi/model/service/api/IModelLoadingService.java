package ua.khpi.model.service.api;

import org.apache.jena.rdf.model.Model;

public interface IModelLoadingService {

	Model loadModel(String name);
}
