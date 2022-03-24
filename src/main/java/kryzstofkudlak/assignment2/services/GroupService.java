package kryzstofkudlak.assignment2.services;

import java.util.LinkedList;
import java.util.List;

import kryzstofkudlak.assignment2.logger.EasyLogger;
import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class GroupService {
	
	private static List<Group> groups = new LinkedList<>();
	
	public static void resetFieldsForTest() {
		groups = new LinkedList<>();
	}
	
	// public helpers
	
	public static Group getGroup(String groupName) {
		for (Group group : groups) {
			if (group.getName().equals(groupName)) {
				return group;
			}
		}
		return null;
	}
	
	public static void addGroup(Group group) {
		groups.add(group);
	}
	
	public static List<Group> getGroups() {
		return groups;
	}
	
	public static void printAllGroups() {
		for (Group group : groups) {
			EasyLogger.appendToFile(UniversalConstants.GROUPS_FILE, group.getName() + " { ");
			
			for (User user : group.getUsers()) {
				EasyLogger.appendToFile(UniversalConstants.GROUPS_FILE, user.getName() + " ");
			}
			
			EasyLogger.appendToFile(UniversalConstants.GROUPS_FILE, "}\n");
		}
	}

}
