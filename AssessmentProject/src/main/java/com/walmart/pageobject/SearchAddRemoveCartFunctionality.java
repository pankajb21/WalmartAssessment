package com.walmart.pageobject;

import com.walmart.utilities.GlobalConstants;
import com.walmart.wrapper.GlobalLibrary;

public class SearchAddRemoveCartFunctionality {

	GlobalLibrary globalLibrary = new GlobalLibrary();
	String url = "http://mobile.walmart.com";

	private final String searchTextBox = "//*[@name='query']";
	private final String submitSearch = "//button[@type='submit']";
	private final String selectedProduct = "//div[@id='tile-container']/div/a/img";
	private final String addToCart = "//button[@id='WMItemAddToCartBtn']";
	private final String checkOutProduct = "//a[@id='PACCheckoutBtn']";
	private final String userIdTextBox = "//input[@id='COAC0WelAccntEmail']";
	private final String passwordTextBox = "//input[@id='COAC0WelAccntPswd']";
	private static String signInButton = "//*[@id='COAC0WelAccntSignInBtn']";
	private final String cartDetails = "//div[@id='spa-layout']/div/div/div/div/h3/span";
	private final String cartIcon = "//div[@id='spa-layout']/div/section/header/div/a[2]/i";
	private final String productDetail = "//img[@alt='Straight Talk Apple iPhone 6 LTE 16GB Prepaid Smartphone']";
	private final String paymentPageVerification = "//div[@id='spa-layout']/div/section/div[3]/div/div/div/section[5]/div/div/ul/li/label";
	private final String reviewYourOrderButton = "//button[@id='COAC3PayReviewOrderBtn']";
	private final String removeLink = "//button[@id='CartRemItemBtn']";
	private final String cartDetailsAfterRemoval = "//div[@id='spa-layout']/div/div/div/div/h3/span";
	private final String checkoutCart = "(//a[contains(text(),'Check Out')])[2]";
	private final String shippingAddressContButton1 = "//button[@id='COAC1ShpOptContBtn']";
	private final String shippingAddressContButton2 = "//button[@id='COAC2ShpAddrContBtn']";

	//Perform a search on home page from a pool of key words given below
	public void searchProduct(){
		globalLibrary.launchApplication(url, GlobalConstants.SAFARI);
		globalLibrary.verifyObjectPresent("Search Text Box Presence", searchTextBox);
		globalLibrary.setText("Enter Search Text", searchTextBox, "iPhone");
		globalLibrary.clickObject("Click for search", submitSearch, false);
	}

	//select the product
	public void selectProduct(){
		globalLibrary.clickObject("Select the product to place in cart", selectedProduct, false);
	}

	// Add the item to cart
	public void addProductToCart(){
		globalLibrary.clickObject("Click Add to Cart", addToCart, false);
		globalLibrary.clickObject("Click to Checkout", checkOutProduct, false);
	}


	//login using existing account which is set up with at least one shipping address	
	public void userSignIn(){
		globalLibrary.setText("Enter user id", userIdTextBox, "pankaj.bhargava21@yahoo.com");
		globalLibrary.setText("Enter Password", passwordTextBox, "walmart");
		globalLibrary.clickObject("Click to Sign In", signInButton, false);
	}

	//Validate only 1 in CART
	public void cartDetailsVerification(){

		globalLibrary.clickObject("Click Cart Icon", cartIcon, false);
		globalLibrary.verifyFieldText("Verify Count in Cart", cartDetails,
				"String", "1 item");
		globalLibrary.verifyObjectPresent("Verify the product in Cart is correct", productDetail);
	}

	//Select Ship to Home as shipping method for your order

	public void shippingDetails(){
		
		globalLibrary.clickObject("Click Check out Button", checkoutCart, false);

		globalLibrary.setText("Enter user id", userIdTextBox, "pankaj.bhargava21@yahoo.com");
		globalLibrary.setText("Enter Password", passwordTextBox, "walmart");
		globalLibrary.clickObject("Click to Sign In", signInButton, false);

		globalLibrary.clickObject("Choose shipping or pickup Button", shippingAddressContButton1, false);
		globalLibrary.clickObject("Choose enter shipping address button", shippingAddressContButton2, false);
	}


	// Validate that you are on Payment details pag
	public void validatePaymentPage(){
		globalLibrary.verifyObjectPresent("Payment  Details Page Object", paymentPageVerification);
		globalLibrary.verifyObjectPresent("Review Your Button verification ", reviewYourOrderButton);

	}

	//Go back to Cart Page, Remove the item from cart and validate cart is empty 
	public void removeProductFromCart(){
		try {
			globalLibrary.clickObject("Click Cart Icon", cartIcon, false);
			globalLibrary.clickObject("Remove the product from Cart", removeLink, false);

			Thread.sleep(2000);
			globalLibrary.verifyFieldText("Verify Count in Cart", cartDetailsAfterRemoval,
					"String", "0 items");
			signOut();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//SignOut

	public void signOut(){
		globalLibrary.openNewUrl("SignoutPage", "https://www.walmart.com/account/logout");
		globalLibrary.closeSelenium();
	}
}
