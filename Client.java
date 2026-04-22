import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1"; // локальний сервер
        int port = 151;            // порт сервера

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Connected to server " + host + ":" + port);

            // окремий потік для прийому повідомлень
            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("\n[Server] " + line);
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            }).start();

            // цикл для введення команд
            while (true) {
                System.out.print("> ");
                String command = sc.nextLine();
                out.println(command);

                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
