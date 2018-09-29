package controller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileExplorer {
	public List<File> searchDirectory(File inputFile, boolean containsSubDir, String ext) {
		return searchDirectory0(inputFile, containsSubDir, ext);
	}

	private List<File> searchDirectory0(File inputFile, boolean containsSubDir, String ext) {
		List<File> fileList = new LinkedList<>();

		if (inputFile == null || !inputFile.exists()) {
			return fileList;
		}

		if (inputFile.isDirectory()) {
			if (inputFile.listFiles() == null) {
				return fileList;
			}
			for (File file : inputFile.listFiles()) {
				if (!file.isDirectory() && file.getName().endsWith(ext)) {
					fileList.add(file);
				}
			}
			if (containsSubDir) {
				for (File file : inputFile.listFiles()) {
					if (file.isDirectory()) {
						fileList.addAll(searchDirectory0(file, containsSubDir, ext));
					}
				}
			}
		} else {
			if (inputFile.getName().endsWith(ext)) {
				fileList.add(inputFile);
			}
		}
		return fileList;
	}
}
