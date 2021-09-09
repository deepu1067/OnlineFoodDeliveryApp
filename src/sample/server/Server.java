package sample.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            Socket socket = serverSocket.accept();

            OutputStreamWriter o = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter writer = new BufferedWriter(o);

            InputStreamReader i = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(i);

            while (true){
                String str = reader.readLine();

                writer.write("You are saying " + str);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
