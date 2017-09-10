package ua.khpi.ui.task;

import org.apache.jena.rdf.model.Model;

import ua.khpi.model.RepositoryTDB;
import ua.khpi.model.api.IRepository;
import ua.khpi.model.service.ModelJaccardSimilarityService;
import ua.khpi.model.service.ModelLoadingService;
import ua.khpi.model.service.api.IModelLoadingService;
import ua.khpi.model.service.api.IModelSimilarityService;
import ua.khpi.model.service.bean.SimilarModelBean;
import ua.khpi.properties.Properties;
import ua.khpi.ui.task.api.ITask;
import ua.khpi.util.BPModelsUtil;

public class SimilarityTask implements ITask {
	private IModelLoadingService modelLoadingService;
	private IRepository repository;
	private IModelSimilarityService modelSimilarityService;

	private final String pattern = "ErrorModel01_Process_1.nt";

	public SimilarityTask() {
		modelLoadingService = new ModelLoadingService();
		repository = new RepositoryTDB();
		modelSimilarityService = new ModelJaccardSimilarityService(repository);
	}

	@Override
	public void execute() {
		String[] names = BPModelsUtil.getTriplesFileNames(Properties.MODELS_PATH);

		repository.open();

		for (String name : names) {
			Model model = modelLoadingService.loadModel(name);

			repository.store(name, model);
		}

		for (SimilarModelBean bean : modelSimilarityService.defineSimilarModels(pattern)) {
			System.out.printf("%s <-> %s\n", pattern, bean);
		}

		repository.close();
	}
}
