package ua.khpi.model.service;

import java.io.IOException;

import ua.khpi.model.service.api.IModelTranslationService;
import ua.khpi.properties.Properties;
import ua.khpi.util.BPModelsUtil;

public class ModelTranslationService implements IModelTranslationService {

	@Override
	public void translateModels() {
		String[] names = BPModelsUtil.getModelsFileNames(Properties.MODELS_PATH);

		try {
			for (String name : names) {
				Runtime.getRuntime()
						.exec(Properties.TRANSLATION_TOOL_PATH + " " + Properties.MODELS_PATH + "\\" + name);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
