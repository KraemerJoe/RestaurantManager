package fr.ul.miage.gl.restaurant.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuUtil {

	public static void spacer(int lign) {
		for (int i = 0; i < lign; i++) {
			System.out.println("");
		}
	}

	public static int askForYesOrNo(String question) {

		int choice = -1;
		while (choice != 1 && choice != 2) {
			try {
				System.out.println(question);
				System.out.println("[1] Yes");
				System.out.println("[2] No");
				Scanner input = new Scanner(System.in);
				choice = input.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("An error happend with your entry !");
			}
		}
		return choice;
	}
	
	public static int askForPositiveInt(String question) {

		int choice = -1;
		while (choice == -1 && choice <= 0) {
			try {
				System.out.println(question);
				Scanner input = new Scanner(System.in);
				choice = input.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("An error happend with your entry !");
			}
		}
		return choice;
	}

	public static String askForString(String question) {
		String choice = "";

		while (choice.trim().equalsIgnoreCase("") || choice.trim().length() <= 1) {
			try {
				System.out.println(question);
				Scanner input = new Scanner(System.in);
				choice = input.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("An error happend with your entry !");
			}
		}
		return choice;
	}

	public static void line() {
		System.out.println("-----------------------------------------");
	}

}
