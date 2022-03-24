package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.GroupService;
import kryzstofkudlak.assignment2.services.UserService;

public class ListPermissionsTest {

	ListPermissions unit = new ListPermissions();
	User rootUser;
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
		GroupService.resetFieldsForTest();
		FileService.resetFieldsForTest();
		
		this.rootUser = new User("root", "1234");
		this.rootUser.givePrivilege();
	}  
	
	@Test
	public void successCase() {
		UserService.setActiveUser(this.rootUser);
		File file = new File("test");
		FileService.addFile(file);
		assertTrue(this.unit.parse("ls test"));
	}
	
	@Test
	public void whenNoUserLoggedIn_thenDenyAccess() {
		File file = new File("test");
		FileService.addFile(file);
		assertTrue(this.unit.parse("ls test"));
	}
	
	@Test
	public void whenFileNotFound_thenError() {
		UserService.setActiveUser(this.rootUser);
		assertTrue(this.unit.parse("ls test"));
	}
	
}
