package com.example;

public class login {
  // Declarations of variables
  private String Name;
  private String Surname;
  private String UserName;
  private String Password;
  private String PhoneNumber;

  // Constructors to initialize the variables
  public login(String Name, String Surname, String UserName,  String Password, String PhoneNumber) {
      this.Name = Name;
      this.Surname = Surname;
      this.UserName = UserName;
      this.Password = Password;
      this.PhoneNumber = PhoneNumber;
  }

  // Getters and Setters for the variables
  public String getName() {
      return Name;
  }

  public void setName(String Name) {
      this.Name = Name;
  }


  public String getSurname() {
      return Surname;
  }

  public void setSurname(String Surname) {
      this.Surname = Surname;
  }

  public String getUserName() {
      return UserName;
  }

  public void setUserName(String UserName) {
      this.UserName = UserName;
  }

  // Method to check if the username contains an underscore and is no more than 5 characters long
  public boolean checkUserNamehasUnderscore(String UserName) {
      return UserName.contains("_") && UserName.length() <= 5;
  }

  public String getPassword() {
      return Password;
  }

  // Setter that sets the password only if it meets complexity requirements
  public boolean setPassword(String Password) {
      if (checkPasswordComplexity(Password)) {
      this.Password = Password;
      return true;
  }
  return false;

  }

  public String getPhoneNumber() {
      return PhoneNumber;
  }

  public void setPhoneNumber(String PhoneNumber) {
      this.PhoneNumber = PhoneNumber;
  }

// Method to check if the password meets complexity requirements
  public boolean checkPasswordComplexity(String Password) {
      if (Password.length() < 8) {
          return false;
      }
      boolean hasUpper = false;
      boolean hasLower = false;
      boolean hasDigit = false;
      boolean hasSpecial = false;

      for (char c : Password.toCharArray()) {
          if (Character.isUpperCase(c)) hasUpper = true;
          else if (Character.isLowerCase(c)) hasLower = true;
          else if (Character.isDigit(c)) hasDigit = true;
          else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
      }
      return hasUpper && hasLower && hasDigit && hasSpecial;
  }

  // Method to check if the phone number is valid (10 digits and starts with country code +27)
  public boolean checkCellNumber(String PhoneNumber) {
      return PhoneNumber.matches("\\+27\\d{9}"); //(OpenAI, 2025)
  }
  
  // Method to return login status message
  public String returnLoginStatus(boolean isValid) {
      if (isValid) {
          return "Welcome " + getName() + " " + getSurname() + " it is great to see you again.";
      } else {
          return "Username or password incorrect, please try again.";
      }
  }

  // Methods to validate username, password, and phone number
  public boolean validateUserName(String inputUserName) {
      return this.UserName.equals(inputUserName);
  }

  public boolean validatePassword(String inputPassword) {
      return this.Password.equals(inputPassword);
  }

  public boolean validatePhoneNumber(String inputPhoneNumber) {
      return this.PhoneNumber.equals(inputPhoneNumber);
  }
  
  // Method to login user by validating username, password, and phone number
  boolean loginUser(String UserName, String Password, String PhoneNumber) {
      return validatePassword(Password) && validateUserName(UserName) && validatePhoneNumber(PhoneNumber);
  }

  // Method to register user by checking username, password, and phone number formats
  public String registerUser(String Name, String Password, String PhoneNumber, String UserName, String Surname) {
      if (!checkUserNamehasUnderscore(UserName)) {
          return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
      } 
      if (!checkPasswordComplexity(Password)) {
          return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
      }
      if (!checkCellNumber(PhoneNumber)) {
          return "Cell number incorrectly formatted or does not contain international code.";
      }
      return "Registration successful!";
  }

}
// References
//OpenAI. (2025) ChatGPT [Generative AI model]. Available at: https://chat.openai.com/
//(Accessed: 19 September 2025).

