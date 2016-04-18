package com.lexmark.google.auth;

import java.io.IOException;

public class TestGoogleAuth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GoogleAuthHelper helper = new GoogleAuthHelper();
		System.out.println("helper created");
		
		try {
			String userJSON = helper.getUserInfoJson("4/AFCIG8gqNUDvmdLjkcP516wWriACkgoomcO4HD6vaCM");
			System.out.println(userJSON);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
