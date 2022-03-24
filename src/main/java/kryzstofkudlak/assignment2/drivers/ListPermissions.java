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

public class ListPermissions implements IDriver {

	private static final String COMMAND = "ls (" + RegexConstants.VALID_NAME + ")";
	
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
		if (UserService.getActiveUser() == null) { // user is not privileged if nonexistent or not privileged
			EasyLogger.error("Invalid ls operation, no user logged in");
			return;
		}
		
		if (UniversalConstants.RESTRICTED_FILENAMES.contains(filename)) { // i don't think i need this check since these files shouldn't be able to be created in the first place, but will explicitly add to be sure
			EasyLogger.error("Invalid ls command, (" + filename + ") is restricted");
			return;
		}
		
		File file = FileService.getFile(filename);
		
		if (file == null) {
			EasyLogger.error("Invalid ls command, file (" + filename + " doesn't exist");
			return;
		}
		
		EasyLogger.println(file.getName() + ": " + file.getOwner().getName() + " " + file.getGroup().getName() + " "
				+ file.getOwnerAccess() + " " + file.getGroupAccess() + " " + file.getGlobalAccess());
		
	}
	
}
