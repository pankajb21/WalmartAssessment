package com.walmart.Tests;

import org.testng.annotations.Test;

import com.walmart.pageobject.SearchAddRemoveCartFunctionality;

public class assessmentTestFile {

	@Test
	public void test(){
		SearchAddRemoveCartFunctionality searchAndAddToCartFunctionality = new SearchAddRemoveCartFunctionality();
		//To be called only if want to create a new user
		//		  newUser.signInNewUser();
		searchAndAddToCartFunctionality.searchProduct();
		searchAndAddToCartFunctionality.selectProduct();
		searchAndAddToCartFunctionality.addProductToCart();
		searchAndAddToCartFunctionality.userSignIn();
		searchAndAddToCartFunctionality.cartDetailsVerification();
		searchAndAddToCartFunctionality.shippingDetails();
		searchAndAddToCartFunctionality.validatePaymentPage();
		searchAndAddToCartFunctionality.removeProductFromCart();
	}	
}
