package sample.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        try {
            System.out.println("[Server] Waiting for clients");
            ServerSocket serverSocket = new ServerSocket(6000);
            Socket socket = serverSocket.accept();
            System.out.println("Connection established");

            OutputStreamWriter o = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter writer = new BufferedWriter(o);

            InputStreamReader i = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(i);

            while (true){
                String str = reader.readLine();

                if(str.equals("initializeHomePage")){
                    writer.write(foodList() + "\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String foodList(){
        String foods = "";

        try{
            FileReader file = new FileReader("food.txt");
            BufferedReader reader = new BufferedReader(file);

            String line;
            while((line=reader.readLine()) != null){
                foods = foods + line + "###";
            }

            reader.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return foods;
    }
}
