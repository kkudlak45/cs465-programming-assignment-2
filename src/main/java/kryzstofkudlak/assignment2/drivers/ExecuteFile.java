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

public class ExecuteFile implements IDriver {

	private static final String COMMAND = "execute (" + RegexConstants.VALID_NAME + ")";
	
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
			EasyLogger.error("Invalid execute command, no user logged in");
			return;
		}
		
		if (UniversalConstants.RESTRICTED_FILENAMES.contains(filename)) {
			EasyLogger.error("Invalid execute command, (" + filename + ") is a restricted filename");
			return;
		}
		
		if (!FileService.fileExists(filename)) {
			EasyLogger.error("Invalid execute command, file named (" + filename + ") does not exist");
			return;
		}
		
		File file = FileService.getFile(filename);
		
		if (!file.canExecute(UserService.getActiveUser()) && !UserService.activeUserIsRoot()) {
			EasyLogger.error("Invalid execute command, active user does not have access and is not root");
			return;
		}
		
		EasyLogger.info("File (" + filename + ") successfully executed!");
	}

}
