package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.UserService;

public class LoginTest {

	LoginUser login = new LoginUser();
	private static final String USERNAME = "tom";
	private static final String PASSWORD = "1234";
	private User testUser = new User(USERNAME, PASSWORD);
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
	}
	
	@Test
	public void whenUserExists_andCorrectCredentials_thenLoginUser() {
		UserService.addUser(this.testUser);
		login.parse("login " + USERNAME + " " + PASSWORD);
		assertEquals(USERNAME, UserService.getActiveUser().getName());
	}
	
	@Test
	public void whenUserExists_andWrongPassword_thenDenyAccess() {
		UserService.addUser(this.testUser);
		login.parse("login " + USERNAME + " notthepasssword");
		assertNull(UserService.getActiveUser());
	}
	
	@Test
	public void ifUserAlreadyLoggedIn_thenDenyAccess() {
		User otherUser = new User("activeUser", "password");
		UserService.setActiveUser(otherUser);
		UserService.addUser(this.testUser);
		
		login.parse("login " + USERNAME + " " + PASSWORD);
		assertEquals(otherUser.getName(), UserService.getActiveUser().getName());
	}
	
	@Test
	public void ifUsernameDoesntExist_thenDenyAccess() {
		login.parse("login " + USERNAME + " " + PASSWORD);
		assertNull(UserService.getActiveUser());
	}
	
}
