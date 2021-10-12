package client;

import com.google.gson.Gson;
import dto.GuessTheNumberMessage;
import util.WriteReadHandler;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                Gson parser = new Gson();
                clientSocket = new Socket("localhost", 4004);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String message = WriteReadHandler.read(in);
                GuessTheNumberMessage mes = parser.fromJson(message, GuessTheNumberMessage.class);
                System.out.println(mes.message);
                while (true) {
                    System.out.println("Предположите цифру :");
                    mes.number = WriteReadHandler.readIntFromConsole();
                    WriteReadHandler.write(out, parser.toJson(mes));
                    message = WriteReadHandler.read(in);
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
