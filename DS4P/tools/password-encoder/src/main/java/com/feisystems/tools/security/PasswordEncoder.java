package com.feisystems.tools.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.StringUtils;

public class PasswordEncoder {

	public static void main(String[] args) {
		try {
			Boolean passwordEntered;
			do {
				BufferedReader buf = new BufferedReader(new InputStreamReader(
						System.in));
				System.out.println("Enter the password in plain text: ");
				String password = buf.readLine();

				passwordEntered = StringUtils.hasText(password);

				if (passwordEntered) {

					String encodedPassword = encodePassword(password);
					System.out.println("The encoded password is: "
							+ encodedPassword);

					System.out.println();
				}
			} while (passwordEntered);

		} catch (IOException ex) {
		}
	}

	public static String encodePassword(String password) {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		return encodedPassword;
	}
}