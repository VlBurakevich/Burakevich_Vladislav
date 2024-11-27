package org.senla.util;

import lombok.experimental.UtilityClass;

import java.util.Scanner;

@UtilityClass
public class ConsoleUserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
