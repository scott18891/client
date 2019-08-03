import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main  {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String message = null;
        InetAddress host = InetAddress.getLocalHost();
        ServerSocket serverSocket = new ServerSocket(4222);
        Runnable runnable =
                () -> {
                    try {
                        System.out.println("Listening on port 4222");
                        displayMessage(serverSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
        Thread thread = new Thread(runnable);
        thread.start();

        while (null == message || !message.equals("disconnect")) {
            message = scan.nextLine();
            sendMessage(host, message);
        }
        scan.close();
    }

    public static void sendMessage(InetAddress host, String message) throws IOException {
        Socket socket = new Socket(host, 4222);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeUTF(message);
        out.flush();
        out.write(-1);
        out.flush();
        out.close();
    }

    public static void displayMessage(ServerSocket serverSocket) throws IOException {
        boolean disconnect = false;
        while (!disconnect) {
            ObjectInputStream ois = new ObjectInputStream(serverSocket.accept().getInputStream());
            String sentMessage = ois.readUTF();
            System.out.println("Message: " + sentMessage);
            if(sentMessage.equals("disconnect")){
                disconnect = true;
            }
        }
    }
}
