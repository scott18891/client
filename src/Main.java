import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = new Socket(host, 8080);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeUTF("Message");
        out.flush();
        out.write(-1);
        out.flush();
        out.close();

    }
}
