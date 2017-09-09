package ua.khpi.model.service.api;

import java.util.List;
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;

import ua.khpi.model.service.bean.SimilarModelBean;

public interface IModelSimilarityService {

	List<SimilarModelBean> defineSimilarModels(String pattern);

	double similarity(Set<RDFNode> a, Set<RDFNode> b);
}
