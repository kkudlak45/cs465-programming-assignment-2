package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class MakeFile implements IDriver {

	private static final String COMMAND = "mkfile (" + RegexConstants.VALID_NAME + ")";
	
	@Override
	public boolean parse(String cmd) {
		Pattern pattern = Pattern.compile(COMMAND);
		Matcher matcher = pattern.matcher(cmd);
		if (!matcher.matches()) {
			return false;
		}
		
		execute(matcher.group(1));
		
		return true;
	}
	
	public void execute(String filename) {
		if (!UserService.activeUserExists()) {
			EasyLogger.error("Invalid mkfile command, no user logged in");
			return;
		}
		
		if (UniversalConstants.RESTRICTED_FILENAMES.contains(filename)) {
			EasyLogger.error("Invalid mkfile command, (" + filename + ") is a restricted filename");
			return;
		}
		
		if (FileService.fileExists(filename)) {
			EasyLogger.error("Invalid mkfile command, file named (" + filename + ") already exists.");
			return;
		}
		
		FileService.addFile(new File(filename));
		EasyLogger.appendToFile(filename, ""); // should just create the file
		EasyLogger.info("File (" + filename + ") successfully created!");
	}

}
