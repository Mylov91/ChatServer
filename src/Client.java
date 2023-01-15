import java.io.*;
import java.net.Socket;
import java.util.Scanner;


class Client implements Runnable {
    Socket socket;
    Scanner in;
    PrintStream out;
    ChatServer server;
    String userName;


    public Client(Socket socket, ChatServer server){
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
    }


    void receive(String userName, String message) {
        out.println(userName + ": " + message);
    }


    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            out.println("Enter user name and push 'ENTER' for send: ");
            userName = in.nextLine();

            out.println("Enter your message and push 'ENTER' for send");
            String input = in.nextLine();

            while (!input.equals("close chat")) {
                server.sendAll(userName, input);
                input = in.nextLine();
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}