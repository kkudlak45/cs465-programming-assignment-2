package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.GroupService;
import kryzstofkudlak.assignment2.services.UserService;

public class UserGroupTest {

	UserGroup unit = new UserGroup();
	User rootUser = new User("root", "1234");
	Group testGroup = new Group("testGroup");
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
		GroupService.resetFieldsForTest();
		this.rootUser.givePrivilege();
	}  
	
	@Test
	public void successCase() {
		UserService.addUser(this.rootUser);
		UserService.setActiveUser(this.rootUser);
		GroupService.addGroup(this.testGroup);
		
		this.unit.parse("usergrp root testGroup");
		assertTrue(this.rootUser.isInGroup(this.testGroup));
		assertTrue(this.testGroup.countainsUser(this.rootUser));
	}
	
	@Test
	public void whenNoUserLoggedIn_thenDenyAccess() {
		UserService.addUser(this.rootUser);
		GroupService.addGroup(this.testGroup);
		
		this.unit.parse("usergrp root testGroup");
		assertFalse(this.rootUser.isInGroup(this.testGroup));
		assertFalse(this.testGroup.countainsUser(this.rootUser));
	}
	
	@Test
	public void whenLoggedInUser_hasNoPrivilege_thenDenyAccess() {
		this.rootUser.revokePrivilege();
		UserService.setActiveUser(this.rootUser);
		UserService.addUser(this.rootUser);
		GroupService.addGroup(this.testGroup);
		
		this.unit.parse("usergrp root testGroup");
		assertFalse(this.rootUser.isInGroup(this.testGroup));
		assertFalse(this.testGroup.countainsUser(this.rootUser));
	}
	
	@Test
	public void whenUserDoesntExist_thenDenyAccess() {
		UserService.setActiveUser(this.rootUser);
		UserService.addUser(this.rootUser);
		GroupService.addGroup(this.testGroup);
		
		this.unit.parse("usergrp GROOT testGroup");
		assertFalse(this.rootUser.isInGroup(this.testGroup));
		assertFalse(this.testGroup.countainsUser(this.rootUser));
	}
	
	@Test
	public void whenGroupDoesntExist_thenDenyAccess() {
		UserService.setActiveUser(this.rootUser);
		UserService.addUser(this.rootUser);
		GroupService.addGroup(this.testGroup);
		
		this.unit.parse("usergrp root testGROOP");
		assertFalse(this.rootUser.isInGroup(this.testGroup));
		assertFalse(this.testGroup.countainsUser(this.rootUser));
	}
	
}
