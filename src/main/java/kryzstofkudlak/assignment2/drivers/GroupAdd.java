package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.services.GroupService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;

public class GroupAdd implements IDriver {

	private static final String COMMAND = "groupadd (" + RegexConstants.VALID_NAME + ")";
	
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
	
	private void execute(String groupname) {
		if (UserService.getActiveUser() == null || !UserService.getActiveUser().isPrivileged()) { // user is not privileged if nonexistent or not privileged
			EasyLogger.error("Invalid groupadd operation, current active user must be root or have privilege");
			return;
		}
		
		if (groupname.equals("nil")) {
			EasyLogger.error("Invalid groupadd operation, nil is reserved for when no group is associated with a file");
			return;
		}
		
		for (Group group : GroupService.getGroups()) { // make sure this group doesn't already exist
			if (group.getName().equals(groupname)) {
				EasyLogger.error("Invalid groupadd operation, a group named (" + groupname + ") already exists");
				return;
			}
		}
		
		GroupService.addGroup(new Group(groupname));
		EasyLogger.info("Group (" + groupname + ") successfully created!");
	}

}
