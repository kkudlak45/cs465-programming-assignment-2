package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.RegexConstants;

public class LoginUser implements IDriver {
	
	private static final String COMMAND = "login (" + RegexConstants.VALID_NAME + ") (" + RegexConstants.VALID_PSWD + ")";
	
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
		if (UserService.getActiveUser() != null) {
			EasyLogger.error("Invalid login command, there is already a user logged in");
			return;
		}
		
		for (User user : UserService.getUsers()) {
			if (user.getName().equals(username)) {
				if (!user.getPassword().equals(password)) {
					EasyLogger.error("Invalid password for user (" + username + "), login unsuccessful");
					return;
				}
				UserService.setActiveUser(user); 
				EasyLogger.info("Login successful, current active user is now (" + username + ")");
				return;
			}
		}
		
		EasyLogger.error("Invalid username, user (" + username + ") not found");	
	}
}
