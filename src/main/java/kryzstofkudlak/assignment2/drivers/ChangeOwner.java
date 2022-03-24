package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class ChangeOwner implements IDriver {
	
	private static final String COMMAND = "chown (" + RegexConstants.VALID_NAME + ") (" + RegexConstants.VALID_NAME + ")";
	
	@Override
	public boolean parse(String cmd) {
		Pattern pattern = Pattern.compile(COMMAND);
		Matcher matcher = pattern.matcher(cmd);
		if (!matcher.matches()) {
			return false;
		}
		
		execute(matcher.group(1), matcher.group(2));
		
		return true;
	}
	
	public void execute(String filename, String username) {
		
		if (UniversalConstants.RESTRICTED_FILENAMES.contains(filename)) {
			EasyLogger.error("Invalid chown command, name (" + filename + ") is restricted");
			return;
		}
		
		if (!FileService.fileExists(filename)) {
			EasyLogger.error("Invalid chown command, file does not exist");
			return;
		}
		
		if (!UserService.activeUserIsRoot()) {
			EasyLogger.error("Invalid chown command, active user must be root");
			return;
		}
		
		User newOwner = UserService.getUser(username);
		
		if (newOwner == null) {
			EasyLogger.error("Invalid chown command, user to change to does not exist");
			return;
		}
		
		FileService.getFile(filename).setOwner(newOwner);
		EasyLogger.info("Owner of file (" + filename + ") successfully updated to (" + username + ")");
	}
	
}
