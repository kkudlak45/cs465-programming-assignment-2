package kryzstofkudlak.assignment2.drivers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.services.UserService;

public class LogoutUser implements IDriver {
	
	private static final String COMMAND = "logout";
	
	@Override
	public boolean parse(String cmd) {
		Pattern pattern = Pattern.compile(COMMAND);
		Matcher matcher = pattern.matcher(cmd);
		if (!matcher.matches()) {
			return false;
		}
		
		execute();
		
		return true;
	}
	
	private void execute() {
		if (UserService.getActiveUser() == null) {
			EasyLogger.error("Invalid logout command, no user is active");
			return;
		}
		
		UserService.setActiveUser(null);
		EasyLogger.info("Successful logout");
	}

}
