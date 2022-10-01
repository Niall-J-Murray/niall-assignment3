package niall.assignment3.app.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import niall.assignment3.app.main.User;

//User Service class to handle majority of data processing.
public class UserService {

	// Instance variable to track number of known users read in from data.txt so
	// User[] length can be set accordingly.
	private int numberOfUsers = 0;

	// Getter without setter, as numberOfUsers is only incremented when data.txt is
	// read in by readUserData().
	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	// Method to read data from file and return as formatted String.
	public String readUserData() {

		// BufferedReader and String declared as null and "empty" (but not null) outside
		// try block, to be updated in try block.
		BufferedReader fileReader = null;
		String userDataString = "";

		try {
			// Name of file to be read passed to new FileReader/BufferedReader, and original
			// BufferedReader updated.
			fileReader = new BufferedReader(new FileReader("src/main/resources/data.txt"));
			// Temp String to store lines as read in and append to original String.
			String fileString;
			// Continue adding string read by file reader until there is no more to read.
			while ((fileString = fileReader.readLine()) != null) {
				// Add comma at end of each line read in to separate concatenated lines.
				// Commas will be used as delimiters to split string later.
				userDataString += fileString + ",";
				// Increment numberOfUsers counter after each line read in.
				numberOfUsers++;
			}
			// Catch block in case of error in reading file.
		} catch (IOException e) {
			System.out.println("Error reading file!");
			e.printStackTrace();
			// finally-try-catch block to close file reader and catch exception if
			// unsuccessful.
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("I/O Exception Error!");
				e.printStackTrace();
			}
		}
		// String containing all data read from file returned to be passed to method
		// below.
		return userDataString;
	}

	// Method to build and return array of User objects from string read in from
	// file.
	public User[] buildUserArray(String userDataString, int numberOfUsers) {

		// Array of strings from string passed as parameter, split using commas as
		// delimiter.
		String[] userArray = userDataString.split(",");
		// New empty array of User objects declared using int parameter passed as
		// length.
		User[] users = new User[numberOfUsers];

		// for loop with two incremental variables.
		// i++ to step through and update each index of users array.
		// j+=3 to add the 3 associated pieces of user data to respective user object in
		// array.
		for (int i = 0, j = 0; i < users.length && j < userArray.length; i++, j += 3) {
			users[i] = new User(userArray[j], userArray[j + 1], userArray[j + 2]);
		}
		// Array of user objects returned to be passed to method below.
		return users;
	}

	// Method to allow user to attempt login.
	// Array of known users passed for comparison.
	public void userLogin(User[] registeredUsers) {

		// Scanner and variables to request and store user input to be checked.
		Scanner scanner = new Scanner(System.in);
		String email;
		String password;
		// To track failed login attempts and exit while loop if login is successful.
		int loginAttempts = 0;
		boolean userExists = false;

		// Continue requesting login info until successful or number of tries exceeded.
		while (!userExists && loginAttempts < 5) {
			// Only printed after first unsuccessful attempt.
			if (loginAttempts > 1) {
				System.out.println("Invalid login, please try again.");
			}
			// Warning of final attempt and consequences.
			if (loginAttempts == 4) {
				System.out.println(
						"Warning, final log-in attempt!\n" + "You will be locked out if log-in is unsucessful.");
			}

			// Request email for username. Convert to lowercase as not case-sensitive.
			System.out.println("Enter your email: ");
			email = scanner.nextLine().toLowerCase();
			// Password is case-sensitive.
			System.out.println("Enter your password (case-sensitive): ");
			password = scanner.nextLine();
			// Increment login attempts after password is entered.
			loginAttempts++;

			// Loop through registeredUsers array to check user input against known user
			// info.
			for (User user : registeredUsers) {
				// If both email(username) and password match a known user, update boolean flag,
				// print confirmation and exit.
				if ((user.getUsername().equals(email)) && (user.getPassword().equals(password))) {
					userExists = true;
					System.out.println("Login Sucessful. \n" + "Welcome " + user.getName() + "!");
				}
			}
			// Print failed login message to user before exiting program.
			if (loginAttempts == 5 && !userExists) {
				System.out.println("Too many failed login attempts, you are now locked out.");
			}
		}
		// Close scanner.
		scanner.close();
	}

}