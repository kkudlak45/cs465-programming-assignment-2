package kryzstofkudlak.assignment2;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import kryzstofkudlak.assignment2.drivers.ChangeGroup;
import kryzstofkudlak.assignment2.drivers.ChangeOwner;
import kryzstofkudlak.assignment2.drivers.ChangePermissions;
import kryzstofkudlak.assignment2.drivers.ExecuteFile;
import kryzstofkudlak.assignment2.drivers.GroupAdd;
import kryzstofkudlak.assignment2.drivers.ListPermissions;
import kryzstofkudlak.assignment2.drivers.LoginUser;
import kryzstofkudlak.assignment2.drivers.LogoutUser;
import kryzstofkudlak.assignment2.drivers.MakeFile;
import kryzstofkudlak.assignment2.drivers.ReadFile;
import kryzstofkudlak.assignment2.drivers.UserAdd;
import kryzstofkudlak.assignment2.drivers.UserGroup;
import kryzstofkudlak.assignment2.drivers.WriteFile;
import kryzstofkudlak.assignment2.logger.EasyLogger;

public class Access {
	
	private static final List<IDriver> DRIVERS = new LinkedList<>();
	
	static { // instantiate drivers
		DRIVERS.add(new ChangeGroup());
		DRIVERS.add(new ChangeOwner());
		DRIVERS.add(new ChangePermissions());
		DRIVERS.add(new ExecuteFile());
		DRIVERS.add(new GroupAdd());
		DRIVERS.add(new ListPermissions());
		DRIVERS.add(new LoginUser());
		DRIVERS.add(new LogoutUser());
		DRIVERS.add(new MakeFile());
		DRIVERS.add(new ReadFile());
		DRIVERS.add(new UserAdd());
		DRIVERS.add(new UserGroup());
		DRIVERS.add(new WriteFile());
	}
	
	private static boolean sendToDrivers(String command) {
		for (IDriver driver : DRIVERS) {
			if (driver.parse(command)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Insufficient arguments supplied, must have 1 argument containing the filename");
		}
		
		String filename = args[0];
		EasyLogger.info("Starting application using file (" + filename + ")");
		
		InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			String cmd = scanner.nextLine();
			EasyLogger.command(cmd);
			if (cmd.strip().equals("end")) {
				break;
			}
			
			if (!sendToDrivers(cmd)) {
				EasyLogger.error("Unrecognized command!");
			}
		}
		
		EasyLogger.info("Terminating execution");
		scanner.close();
		EasyLogger.closeStreams();
		
	}

}
