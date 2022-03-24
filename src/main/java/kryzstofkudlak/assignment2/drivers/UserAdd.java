package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class UserAdd implements IDriver {
	
	private static final String COMMAND = "useradd (" + RegexConstants.VALID_NAME + ") (" + RegexConstants.VALID_PSWD + ")";

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

	private void execute(String username, String password) {
		if (UserService.getActiveUser() == null && !username.equals(UniversalConstants.PRIV_USER_NAME)) { // make sure first useradd is for root
			EasyLogger.error("Invalid useradd operation, current user does not exist, and user being added is not root");
			return;
		}
		
		if (UserService.getActiveUser() != null && !UserService.activeUserIsRoot()) { // make sure that privileged user is running this command
			EasyLogger.error("Invalid useradd operation, current active user is not privileged");
			return;
		}
		
		for (User user : UserService.getUsers()) { // make sure that username is not already taken
			if (user.getName().equals(username)) {
				EasyLogger.error("Invalid useradd operation, username (" + username + ") already exists");
				return;
			}
		}
		
		User newUser = new User(username, password);
		if (username.equals(UniversalConstants.PRIV_USER_NAME)) { newUser.givePrivilege(); }
		UserService.addUser(newUser);
		EasyLogger.info("User (" + username + ") successfully created!");
	}
	
}
