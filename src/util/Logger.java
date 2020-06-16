package util;

import model.Driver;
import model.Rider;

public class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printHeader(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public static void printSeparator() {
        String separator = "======================================";
        System.out.println(ANSI_BLUE + separator + ANSI_RESET);
    }

    public static void printImportant(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public static void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static void printQuestion(String message) {
        System.out.print(ANSI_YELLOW + message + ANSI_RESET );
    }

    public static void printIteration(int iteration) {
        System.out.println(ANSI_CYAN + "Iteration-"+ iteration + ANSI_RESET );
    }

    public static void printParticleHeader(int particle) {
        System.out.println(ANSI_PURPLE + "Particle-"+ particle + ANSI_RESET );
    }

    public static void printResult(Driver driver, Rider rider){
        if(rider == null) {
            System.out.println("driver-" + driver.getDriverId() + ANSI_RED + " carry nobody" + ANSI_RESET);
        } else {
            System.out.println("driver-" + driver.getDriverId() + ANSI_GREEN + " carry rider-" + rider.getRiderId() + ANSI_RESET);
        }
    }
}