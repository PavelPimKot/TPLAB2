package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public interface WriteReadHandler {

    static void write(BufferedWriter out, String message) throws IOException {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            System.out.println("Соединение больше не действительно");
            throw e;
        }
    }

    static String read(BufferedReader in) throws IOException {
        String message;
        try {
            message = in.readLine();
            return message;
        } catch (IOException e) {
            System.out.println("Соединение больше не действительно");
            throw e;
        }
    }

    static int readIntFromConsole() {
        Scanner consoleReader = new Scanner(System.in);
        int returnValue;
        while (true) {
            try {
                returnValue = consoleReader.nextInt();
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Введите число корректно");
                consoleReader.next();
            }
        }
        return returnValue;
    }
}
