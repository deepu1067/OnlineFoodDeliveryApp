package sample.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("[Server] Waiting for clients");
        ServerSocket serverSocket = new ServerSocket(6000);
        serverSocket.setReuseAddress(true);
        int count = 0;
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                count++;
                System.out.println("Client no-" + count + " connected");

                OutputStreamWriter o = new OutputStreamWriter(socket.getOutputStream());
                BufferedWriter writer = new BufferedWriter(o);

                InputStreamReader i = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(i);

                Thread t1 = new ClientHandler(socket, reader, writer);
                t1.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
