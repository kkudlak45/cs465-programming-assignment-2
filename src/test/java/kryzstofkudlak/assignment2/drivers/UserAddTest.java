package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.IDriver;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.UserService;

public class UserAddTest {

	IDriver unit = new UserAdd();
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
	}
	
	@Test
	public void whenNoUserExists_andNotAddingRoot_thenDenyAccess() {
		this.unit.parse("useradd tom cs465rules");
		assertEquals(0, UserService.getUsers().size());
	}
	
	@Test
	public void whenNoUserExists_andAddingRoot_thenAllowAccess() {
		this.unit.parse("useradd root cyber465rulz!!!..-~");
		assertEquals(1, UserService.getUsers().size());
		
		User actualUser = UserService.getUser("root");
		assertNotNull(actualUser);
		assertEquals("root", actualUser.getName());
		assertEquals("cyber465rulz!!!..-~", actualUser.getPassword());
		assertTrue(actualUser.isPrivileged());
	}
	
	@Test
	public void whenDuplicateNames_DontCreateSecondUser() {
		this.unit.parse("useradd root asdf");
		this.unit.parse("useradd root hijk");
		
		assertEquals(1, UserService.getUsers().size());
		User actualUser = UserService.getUser("root");
		assertEquals("asdf", actualUser.getPassword());
	}
	
	@Test
	public void whenNotRootUser_thenDenyAccess() {
		User nonRootUser = new User("notroot", "password");
		UserService.setActiveUser(nonRootUser);
		
		this.unit.parse("useradd root asdf");
		assertEquals(0, UserService.getUsers().size());
	}
	
	@Test
	public void whenActiveUserIsRoot_thenAllowAccess() {
		User rootUser = new User("root", "password");
		rootUser.givePrivilege();
		UserService.setActiveUser(rootUser);
		
		this.unit.parse("useradd tom 1234");
		assertEquals(1, UserService.getUsers().size());
		assertEquals("tom", UserService.getUsers().iterator().next().getName());
	}
	
}
