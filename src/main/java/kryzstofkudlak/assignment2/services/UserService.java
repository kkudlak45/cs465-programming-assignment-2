package kryzstofkudlak.assignment2.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class UserService {
	
	private static User activeUser = null;
	private static List<User> users = new LinkedList<>();
	
	
	public static void resetFieldsForTest() {
		activeUser = null;
		users = new LinkedList<>();
	}
	
	
	// public utility methods
	
	/**
	 * Returns a user object if it exists, null otherwise
	 * 
	 * @param username - name of user to search for
	 * @return
	 */
	public static User getUser(String username) {
		for (User user : users) {
			if (user.getName().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public static List<User> getUsers() {
		return users;
	}
	
	public static User getActiveUser() {
		return activeUser;
	}
	
	public static boolean activeUserExists() {
		return activeUser != null;
	}
	
	public static boolean activeUserIsRoot() {
		return (activeUser != null && activeUser.isPrivileged());
	}
	
	public static void addUser(User user) {
		users.add(user);
	}
	
	public static void setActiveUser(User user) {
		activeUser = user;
	}
	
	public static void printAllUsers() {
		for (User user : users) {
			EasyLogger.appendToFile(UniversalConstants.ACCOUNTS_FILE, user.getName() + ":" + user.getPassword() + ":");
			Set<Group> groups = user.getGroups();
			
			if (groups.size() == 0) {
				EasyLogger.appendToFile(UniversalConstants.ACCOUNTS_FILE, "nil\n");
			}
			else {
				for (Group group : groups) {
					EasyLogger.appendToFile(UniversalConstants.ACCOUNTS_FILE, group.getName() + " ");
				}
				EasyLogger.appendToFile(UniversalConstants.ACCOUNTS_FILE, "\n");
			}
		}
	}
	
}
