package kryzstofkudlak.assignment2.logger;

import org.junit.Test;

public class EasyLoggerTest {

	@Test
	public void attemptToWriteToFile() {
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello world!");
		EasyLogger.appendToFile("asdf.txt", "hello wooooorld!");
		EasyLogger.closeStreams();
	}
	
}
