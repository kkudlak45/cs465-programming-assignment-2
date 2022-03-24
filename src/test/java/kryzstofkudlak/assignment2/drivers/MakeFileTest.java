package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class MakeFileTest {

	MakeFile unit = new MakeFile();
	User rootUser = new User("root", "1234");
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
		FileService.resetFieldsForTest();
	}  
	
	@Test
	public void successCase() {
		UserService.setActiveUser(this.rootUser);
		
		this.unit.parse("mkfile file");
		
		File result = FileService.getFile("file");
		assertNotNull(result);
		assertTrue(result.isOwnedBy(this.rootUser));
		assertEquals("nil", result.getGroup().getName());
		assertEquals("rw-", result.getOwnerAccess());
		assertEquals("---", result.getGroupAccess());
		assertEquals("---", result.getGlobalAccess());
	}
	
	@Test
	public void whenFilenameIsRestricted_dontAllowAccess() {
		UserService.setActiveUser(this.rootUser);
		
		this.unit.parse("mkfile " + UniversalConstants.ACCOUNTS_FILE);
		this.unit.parse("mkfile " + UniversalConstants.GROUPS_FILE);
		this.unit.parse("mkfile " + UniversalConstants.AUDIT_FILE);
		this.unit.parse("mkfile " + UniversalConstants.FILES_FILE);
		
		assertEquals(0, FileService.getFiles().size());
	}
	
	@Test
	public void whenFileAlreadyExists_thenDontCreateTwo() {
		UserService.setActiveUser(this.rootUser);
		
		this.unit.parse("mkfile file");
		this.unit.parse("mkfile file");
		
		assertEquals(1, FileService.getFiles().size());
	}
	
	@Test
	public void whenNoUserLoggedIn_thenDenyAccess() {
		this.unit.parse("mkfile file");
		
		assertEquals(0, FileService.getFiles().size());
	}
	
}
