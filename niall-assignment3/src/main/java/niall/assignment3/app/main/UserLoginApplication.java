package niall.assignment3.app.main;

import niall.assignment3.app.service.UserService;

public class UserLoginApplication {

	private UserService userService = new UserService();

	// Minimal user login app containing main method, which only calls a in in class
	// method to execute the program.
	public static void main(String[] args) {
		new UserLoginApplication().execute();
	}

	// Execute method to call required methods from
	private void execute() {
		// Existing user data read from "data.txt" stored as String.
		String userDataString = userService.readUserData();
		// Get number of users to pass to user array builder method to define array
		// length.
		int numberOfUsers = userService.getNumberOfUsers();
		// Array of User objects declared by assigning return value of buildUserArray to
		// variable.
		User[] registeredUsers = userService.buildUserArray(userDataString, numberOfUsers);
		// Lines 13-17 could be condensed as below, but it is less readable.
		// *Is this best practice?*
//		User[] registeredUsers = userService.buildUserArray(userService.readUserData(), userService.getNumberOfUsers());
		// Pass User object array to login method to take user input and compare to
		// known user credentials.
		userService.userLogin(registeredUsers);
	}
}

