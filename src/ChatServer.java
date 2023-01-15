import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import static java.lang.System.out;


public class ChatServer {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;


    ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234);
    }


    void sendAll(String userName, String message) {
        for (Client client : clients) {
            client.receive(userName + ": ", message);
        }
    }


    public void run() {
        while (true) {
            out.println("Waiting...");
            try {
                Socket socket = serverSocket.accept();
                out.println("Client connected!");
                clients.add(new Client(socket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}

