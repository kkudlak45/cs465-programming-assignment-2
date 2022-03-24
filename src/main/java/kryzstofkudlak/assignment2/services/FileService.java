package kryzstofkudlak.assignment2.services;

import kryzstofkudlak.assignment2.objects.File;

import java.util.LinkedList;
import java.util.List;

public class FileService {
	
	private static List<File> files = new LinkedList<>();
	
	public static void resetFieldsForTest() {
		files = new LinkedList<>();
	}
	
	
	// helpers
	public static boolean fileExists(String filename) {
		for (File file : files) {
			if (file.getName().equals(filename)) {
				return true;
			}
		}
		return false;
	}
	
	public static File getFile(String filename) {
		for (File file : files) {
			if (file.getName().equals(filename)) {
				return file;
			}
		}
		return null;
	}
	
	public static void addFile(File file) {
		files.add(file);
	}
	
	public static List<File> getFiles() {
		return files;
	}
	
}
