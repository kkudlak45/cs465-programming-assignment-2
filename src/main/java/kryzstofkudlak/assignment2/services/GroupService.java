package kryzstofkudlak.assignment2.services;

import java.util.LinkedList;
import java.util.List;

import kryzstofkudlak.assignment2.objects.Group;

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

}
