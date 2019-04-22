package puyuan2019;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author SpencerCJH
 * @date 2019/4/22 15:34
 */
public class DemoServer extends Thread {
    private static String HOST = "10.15.15.109";
    private static int PORT = 8888;
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        DemoServer server = new DemoServer();
        server.start();
        String text = "test content";
        try (Socket client = new Socket(HOST, PORT);
             PrintWriter printWriter = new PrintWriter(client.getOutputStream())) {
            printWriter.println(text);
            printWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            bufferedReader.lines().forEach(System.out::println);
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                requestHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class RequestHandler extends Thread {
    private Socket socket;

    RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream());
             InputStream in = socket.getInputStream()) {
            byte[] bytes = new byte[5];
            out.println(in.read(bytes));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


