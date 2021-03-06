package net.bohush.exercises.chapter20;

import java.util.Scanner;

public class Exercise15 {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.print("Enter characters: ");
		String value = input.next();
		System.out.print("Enter char: ");
		char findChar = input.next().charAt(0);
		System.out.print(count(value, findChar));
	}

	public static int count(String str, char a) {
		return count(str, a, str.length() - 1);
	}
	
	public static int count(String str, char a, int high) {
		if (high == 0) {
			if (str.charAt(0) == a) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (str.charAt(high) == a) {
				return 1 + count(str, a, high - 1);
			} else {
				return 0 + count(str, a, high - 1);
			}
		}	
	}

}
