package ua.khpi.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;

public class BPModelsUtil {

	public static String[] getTriplesFileNames(String path) {
		return getFileNames(path, new String[] { ".nt" });
	}

	public static String[] getModelsFileNames(String path) {
		return getFileNames(path, new String[] { ".xpdl" });
	}

	public static void deleteTriplesFiles(String path) {
		String[] names = getTriplesFileNames(path);

		for (String name : names) {
			new File(name).delete();
		}
	}

	private static String[] getFileNames(String path, String[] extensions) {
		String[] names = new File(path).list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String fileName = name.toLowerCase(Locale.getDefault());

				for (int i = 0; i < extensions.length; i++) {
					if (fileName.endsWith(extensions[i])) {
						return true;
					}
				}

				return false;

			}
		});

		return names;
	}
}
