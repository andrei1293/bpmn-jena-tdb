package ua.khpi.model.service.api;

import java.util.List;

import ua.khpi.model.service.bean.SimilarModelBean;

public interface IModelSimilarityService {

	List<SimilarModelBean> defineSimilarModels(String pattern);
}
