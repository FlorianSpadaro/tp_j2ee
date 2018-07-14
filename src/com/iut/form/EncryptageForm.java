package com.iut.form;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class EncryptageForm {
	private static final String ALGO_CHIFFREMENT = "MD5";
	
	public static String encryptPassword(String password)
	{
		String hashPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance(ALGO_CHIFFREMENT);
		    md.update(password.getBytes());
		    byte[] digest = md.digest();
		    hashPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return hashPassword;
	}
}
