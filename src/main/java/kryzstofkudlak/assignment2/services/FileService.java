package kryzstofkudlak.assignment2.services;

import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

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
	
	public static void printAllFiles() {
		for (File file : files) {
			EasyLogger.appendToFile(UniversalConstants.FILES_FILE, 
					file.getName() + ": " + file.getOwner().getName() + " " + file.getGroup().getName() + " "
					+ file.getOwnerAccess() + " " + file.getGroupAccess() + " " + file.getGlobalAccess() + "\n");
		}
	}
	
}
