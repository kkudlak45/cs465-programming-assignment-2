package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.GroupService;
import kryzstofkudlak.assignment2.services.UserService;

public class GroupAddTest {

	GroupAdd unit = new GroupAdd();
	User rootUser = new User("root", "1234");
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
		GroupService.resetFieldsForTest();
		this.rootUser.givePrivilege();
	}
	
	@Test
	public void whenNoUserLoggedIn_thenDenyAccess() {
		this.unit.parse("groupadd asdf");
		assertEquals(0, GroupService.getGroups().size());
	}
	
	@Test
	public void whenActiveUserNotPrivileged_thenDenyAccess() {
		this.rootUser.revokePrivilege();
		UserService.setActiveUser(this.rootUser);
		this.unit.parse("groupadd asdf");
		assertEquals(0, GroupService.getGroups().size());
	}
	
	@Test
	public void whenGroupAlreadyExists_thenDenyAccess() {
		Group group = new Group("asdf");
		GroupService.addGroup(group);
		UserService.setActiveUser(this.rootUser);
		
		this.unit.parse("groupadd asdf");
		assertEquals(1, GroupService.getGroups().size());
	}
	
	@Test
	public void whenGroupNameIsNil_thenDenyAccess() {
		UserService.setActiveUser(this.rootUser);
		
		this.unit.parse("groupadd nil");
		assertEquals(0, GroupService.getGroups().size());
	}
	
	@Test
	public void whenNoGroups_andRootUserIsActive_thenAcceptCommand() {
		UserService.setActiveUser(this.rootUser);
		this.unit.parse("groupadd asdf");
		assertEquals(1, GroupService.getGroups().size());
		assertEquals("asdf", GroupService.getGroups().get(0).getName());
	}
	
}
