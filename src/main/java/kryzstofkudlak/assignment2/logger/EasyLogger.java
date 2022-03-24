package kryzstofkudlak.assignment2.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class EasyLogger {
	
	private EasyLogger() {}
	
	private static final Map<String, OutputStream> OUT_STREAMS = new HashMap<>();
	public static final String PREFIX = "outfiles/";

	public static void info(String message) {
		String formattedMsg = "[INFO] " + message;
		System.out.println(formattedMsg); 
		appendToFile(UniversalConstants.AUDIT_FILE, formattedMsg + "\n");
	}
	
	public static void error(String message) {
		String formattedMsg = "[ERROR] " + message;
		System.out.println(formattedMsg);
		appendToFile(UniversalConstants.AUDIT_FILE, formattedMsg + "\n");
	}
	
	public static void command(String message) {
		String formattedMsg = "[CMD] " + message;
		System.out.println("[CMD] " + message);
		appendToFile(UniversalConstants.AUDIT_FILE, formattedMsg + "\n");
	}
	
	public static void println(String message) {
		System.out.println(message);
		appendToFile(UniversalConstants.AUDIT_FILE, message + "\n");
	}
	
	public static void appendToFile(String filename, String text) {
		try {
			File outFile = new File(PREFIX + filename);
			if (!outFile.exists()) {
				System.out.println("Creating file");
				outFile.createNewFile();
			}
			
			OutputStream output;
			if (!OUT_STREAMS.containsKey(filename)) {
				output = new FileOutputStream(outFile, false);
				OUT_STREAMS.put(filename, output);
			}
			else {
				output = OUT_STREAMS.get(filename);
			}
			
			output.write((text).getBytes());
		}
		catch (IOException e) {
			error("Exception trying to write to file");
		}
	}
	
	public static void closeStreams() {
		OUT_STREAMS.forEach((key, value) -> {
			try {
				value.close();
			} catch (IOException e) {
				error("Exception trying to close output streams");
			}
		});
	}
	
}
