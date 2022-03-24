package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.GroupService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class ChangeGroup implements IDriver {

	private static final String COMMAND = "chgrp (" + RegexConstants.VALID_NAME + ") (" + RegexConstants.VALID_NAME + ")";
	
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
	
	public void execute(String filename, String groupname) {
		if (!UserService.activeUserExists()) {
			EasyLogger.error("Invalid chgrp command, no user logged in");
			return;
		}
		
		if (UniversalConstants.RESTRICTED_FILENAMES.contains(filename)) {
			EasyLogger.error("Invalid chgrp command, (" + filename + ") is restricted");
			return;
		}
		
		if (!FileService.fileExists(filename)) {
			EasyLogger.error("Invalid chgrp command, file does not exist");
			return;
		}
		
		File file = FileService.getFile(filename);
		User user = UserService.getActiveUser();
		Group group = GroupService.getGroup(groupname);
		
		if (!file.isOwnedBy(UserService.getActiveUser()) && !UserService.activeUserIsRoot()) {
			EasyLogger.error("Invalid chgrp command, file is not owned by user and user does not have privilege");
			return;
		}
		
		if (groupname.equals("nil")) {
			file.clearGroup(group);
			EasyLogger.info("File's group has successfully been cleared to nil!");
			return;
		}

		if (group == null) {
			EasyLogger.error("Invalid chgrp command, group does not exist");
			return;
		}
		
		if (!user.isInGroup(group) && !user.isPrivileged()) {
			EasyLogger.error("Invalid chgrp command, user is not privileged or in group (" + groupname + ")");
			return;
		}
		
		file.setGroup(group);
		EasyLogger.info("File (" + filename + ") successfully added to group (" + groupname + ")");
	}
	
}
