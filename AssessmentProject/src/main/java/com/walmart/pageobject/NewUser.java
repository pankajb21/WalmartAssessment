package com.walmart.pageobject;

import com.walmart.Tests.assessmentTestFile;
import com.walmart.wrapper.GlobalLibrary;

public class NewUser {
	GlobalLibrary globalLibrary = new GlobalLibrary();
	String url = "http://mobile.walmart.com";
	String browser = "safari";
	
	
	private final String firstName = "//*[@id='firstname']";
	private final String lastName = "//*[@id='lastname']";
	private final String email = "//*[@id='email']";
	private final String password = "//*[@id='password']";
	private final String confirmPassword = "//*[@id='confirm_password']";
	private final String submitButton = "(//button[@type='submit'])[3]";

	
	//@Test
	  public void signInNewUser() {
		
		globalLibrary.launchApplication(url, browser);
		globalLibrary.openNewUrl("Opening Sign in Page", "https://www.walmart.com/account/signup");
		
		globalLibrary.setText("Entering First Name", firstName, "Pankaj");   
		globalLibrary.setText("Entering Last Name", lastName, "Bhargava");
		globalLibrary.setText("Entering Email", email, "pankaj.bhargava21@yahoo.com");
		globalLibrary.setText("Entering password", password, "walmart");
		globalLibrary.setText("Entering confirm password", confirmPassword, "walmart");

		globalLibrary.clickObject("Click Submit Button", submitButton, false);
		
	  }
}
