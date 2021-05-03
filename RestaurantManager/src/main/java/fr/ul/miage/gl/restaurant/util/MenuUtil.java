package fr.ul.miage.gl.restaurant.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuUtil {

	public static void spacer(int lign) {
		for (int i = 0; i < lign; i++) {
			System.out.println("");
		}
	}

	public static String askForString(String question) {
		String choice = "";

		while (choice.trim().equalsIgnoreCase("") || choice.trim().length() <= 1) {
			try {
				System.out.println(question);
				Scanner input = new Scanner(System.in);
				choice = input.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Une erreur est survenue dans votre entree !");
			}
		}
		return choice;
	}

}