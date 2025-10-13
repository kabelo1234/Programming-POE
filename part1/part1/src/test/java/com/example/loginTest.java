package com.example;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
public class loginTest {
  
    @Test
    public void testUsernameCorrectlyFormatted() {
        login user = new login("kyle", "Doe", "kyl_1", "Ch&sec@ke99!", "+27838968976");

        assertTrue(user.checkUserNamehasUnderscore("kyl_1"));
        String expected = "Registration successful!";
        assertEquals(expected, user.registerUser("kyle", "Ch&sec@ke99!", "+27838968976", "kyl_1", "Doe"));
    }

@Test
public void testUsernameIncorrectlyFormatted() {
    login user = new login("kyle", "Doe", "kyle!!!!!!!", "Ch&sec@ke99!", "+27838968976");

    assertFalse(user.checkUserNamehasUnderscore("kyle!!!!!!!"));
    String expected = "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
    assertEquals(expected, user.registerUser("kyle", "Ch&sec@ke99!", "+27838968976", "kyle!!!!!!!", "Doe"));
}

@Test
public void testPasswordMeetsComplexity() {
    login user = new login("kyle", "Doe", "kyl_1", "Ch&sec@ke99!", "+27838968976");

    assertTrue(user.checkPasswordComplexity("Ch&sec@ke99!"));
    assertEquals("Registration successful!", user.registerUser("kyle", "Ch&sec@ke99!", "+27838968976", "kyl_1", "Doe"));
}

@Test
public void testPasswordDoesNotMeetComplexity() {
    login user = new login("John", "Doe", "kyl_1", "password", "+27838968976");

    assertFalse(user.checkPasswordComplexity("password"));
    String expected = "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
    assertEquals(expected, user.registerUser("kyle", "password", "+27838968976", "kyl_1", "Doe"));
}

@Test
public void testCellNumberCorrectlyFormatted() {
    login user = new login("kyle", "Doe", "kyl_1", "Ch&sec@ke99!", "+27838968976");

    assertTrue(user.checkCellNumber("+27838968976"));
    assertEquals("Registration successful!", user.registerUser("John", "Ch&sec@ke99!", "+27838968976", "kyl_1", "Doe"));
}

@Test
public void testCellNumberIncorrectlyFormatted() {
    login user = new login("kyle", "Doe", "kyl_1", "Ch&sec@ke99!", "08966553");

    assertFalse(user.checkCellNumber("08966553"));
    String expected = "Cell number incorrectly formatted or does not contain international code.";
    assertEquals(expected, user.registerUser("kyle", "Ch&sec@ke99!", "08966553", "kyl_1", "Doe"));
}

@Test
public void testLoginSuccessful() {
    login user = new login("kyle", "Doe", "kyl_1", "Ch&sec@ke99!", "+27838968976");
    boolean result = user.loginUser("kyl_1", "Ch&sec@ke99!", "+27838968976");

    assertTrue(result);
    assertEquals("Welcome kyle Doe it is great to see you again.", user.returnLoginStatus(result));
}

@Test
public void testLoginFailed() {
    login user = new login("kyle", "Doe", "kyl_1", "Ch&sec@ke99!", "+27838968976");
    boolean result = user.loginUser("kyl_1", "wrongPass", "+27838968976");

    assertFalse(result);
    assertEquals("Username or password incorrect, please try again.", user.returnLoginStatus(result));
}
}

