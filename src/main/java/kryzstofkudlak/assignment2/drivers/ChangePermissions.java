package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class ChangePermissions implements IDriver {
	
	private static final String COMMAND = "chmod (" + RegexConstants.VALID_NAME + ") (" 
			+ RegexConstants.VALID_PERM + ") (" + RegexConstants.VALID_PERM + ") (" + RegexConstants.VALID_PERM + ")";
	
	@Override
	public boolean parse(String cmd) {
		Pattern pattern = Pattern.compile(COMMAND);
		Matcher matcher = pattern.matcher(cmd);
		if (!matcher.matches()) {
			return false;
		}
		
		execute(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
		
		return true;
	}
	
	private void execute(String filename, String ownerPermissions, String groupPermissions, String globalPermissions) {
		if (!UserService.activeUserExists()) {
			EasyLogger.error("Invalid chmod command, no user logged in");
			return;
		}
		
		if (UniversalConstants.RESTRICTED_FILENAMES.contains(filename)) { // i don't think i need this check since these files shouldn't be able to be created in the first place, but will explicitly add to be sure
			EasyLogger.error("Invalid chmod command, (" + filename + ") is restricted");
			return;
		}
		
		File file = FileService.getFile(filename);
		
		if (file == null) {
			EasyLogger.error("Invalid chmod command, file does not exist");
			return;
		}
		
		User activeUser = UserService.getActiveUser();
		if (!activeUser.isPrivileged() && !file.isOwnedBy(activeUser)) { // active user must be owner or root
			EasyLogger.error("Invalid chmod command, user must be privileged or file owner");
			return;
		}
		
		file.setOwnerAccess(ownerPermissions);
		file.setGroupAccess(groupPermissions);
		file.setGlobalAccess(globalPermissions);
		EasyLogger.info("Permissions for file (" + filename + " successfully updated!");
	}
}
