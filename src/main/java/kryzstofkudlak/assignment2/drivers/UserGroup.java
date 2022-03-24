package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.GroupService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;

public class UserGroup implements IDriver {

	private static final String COMMAND = "usergrp (" + RegexConstants.VALID_NAME + ") (" + RegexConstants.VALID_NAME + ")";
	
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
	
	public void execute(String username, String groupname) {
		if (UserService.getActiveUser() == null || !UserService.getActiveUser().isPrivileged()) { // user is not privileged if nonexistent or not privileged
			EasyLogger.error("Invalid groupadd operation, current active user must be root or have privilege");
			return;
		}
		
		User user = UserService.getUser(username);
		Group group = GroupService.getGroup(groupname);
		
		if (user == null) { // if user does not exist
			EasyLogger.error("Invalid usergrp command, user (" + username + ") does not exist");
			return;
		}
		
		if (group == null) { // if group does not exist
			EasyLogger.error("Invalid usergrp command, group (" + groupname + ") does not exist");
			return;
		}
		
		user.addToGroup(group);
		group.addUser(user);
		EasyLogger.info("User (" + user.getName() + ") successfully added to group (" + group.getName() + ")" );
	}
	
}
