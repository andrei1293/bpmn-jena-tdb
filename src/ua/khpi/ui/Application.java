package ua.khpi.ui;

import ua.khpi.ui.task.SimilarityTask;
import ua.khpi.ui.task.TranslationTask;
import ua.khpi.ui.task.api.ITask;

public class Application {

	public static void main(String[] args) {
		ITask[] tasks = { new TranslationTask(), new SimilarityTask() };

		for (ITask task : tasks) {
			task.execute();
		}
	}
}
