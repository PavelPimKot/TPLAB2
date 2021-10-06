package client;

import com.google.gson.Gson;
import dto.GuessTheNumberMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                Gson parser = new Gson();
                clientSocket = new Socket("localhost", 4004);
                Scanner consoleReader = new Scanner(System.in);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String message = in.readLine();
                GuessTheNumberMessage mes = parser.fromJson(message, GuessTheNumberMessage.class);
                System.out.println("Игра началась");
                while (true) {
                    System.out.println("Предположите цифру :");
                    mes.number = consoleReader.nextInt();
                    out.write(parser.toJson(mes) + "\n");
                    out.flush();
                    message = in.readLine();
                    mes = parser.fromJson(message, GuessTheNumberMessage.class);
                    if (!mes.startEndGameFlag) {
                        System.out.println("Вы победили угадав число");
                        break;
                    } else {
                        System.out.println(mes.message);
                    }
                }
            } finally {
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
