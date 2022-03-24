package kryzstofkudlak.assignment2.utils;

public class RegexConstants {

	private RegexConstants() {}
	
	public static final String VALID_NAME = "[^\\s/:]{1,30}";
	public static final String VALID_PSWD = "[^\\s]{1,30}";
	public static final String VALID_PERM = "[rwx-]{3}";
	
}
