package ua.khpi.ui.task;

import ua.khpi.model.service.ModelTranslationService;
import ua.khpi.model.service.api.IModelTranslationService;
import ua.khpi.ui.task.api.ITask;

public class TranslationTask implements ITask {
	private IModelTranslationService modelTranslationService;

	public TranslationTask() {
		modelTranslationService = new ModelTranslationService();
	}

	@Override
	public void execute() {
		modelTranslationService.translateModels();
	}
}
