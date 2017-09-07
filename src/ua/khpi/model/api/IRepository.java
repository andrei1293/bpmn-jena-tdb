package ua.khpi.model.api;

import java.util.List;

import org.apache.jena.rdf.model.Model;

public interface IRepository {
	void open();

	void close();

	void store(String name, Model model);

	Model get(String name);

	List<Model> getAll();
}
