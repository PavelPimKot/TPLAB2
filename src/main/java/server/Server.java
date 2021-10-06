package server;

import com.google.gson.Gson;
import dto.GuessTheNumberMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                Gson parser = new Gson();
                server = new ServerSocket(4004);
                Socket clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    Scanner consoleReader = new Scanner(System.in);
                    String message;
                    System.out.println("Загадайте число:");
                    int number = consoleReader.nextInt();
                    GuessTheNumberMessage mes = new GuessTheNumberMessage("Игра началась", number, true);
                    out.write(parser.toJson(mes) + "\n");
                    out.flush();
                    while (true) {
                        message = in.readLine();
                        mes = parser.fromJson(message, GuessTheNumberMessage.class);
                        if (number == mes.number) {
                            mes.startEndGameFlag = false;
                            mes.message = "Игра завершена, число угадано";
                            out.write(parser.toJson(mes) + "\n");
                            out.flush();
                            break;
                        } else {
                            if (mes.number > number) {
                                mes.message = "Введенное число меньше чем загаданное";
                            } else {
                                mes.message = "Введенное число больше чем загаданное";
                            }
                            out.write(parser.toJson(mes) + "\n");
                            out.flush();
                        }
                    }
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
