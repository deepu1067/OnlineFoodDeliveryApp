package sample.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.net.Socket;

public class ClientHandler extends Thread{
    Socket client;
    BufferedReader reader;
    BufferedWriter writer;

    public ClientHandler(Socket client, BufferedReader reader, BufferedWriter writer) {
        this.client = client;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        while (true){
            try {
                String str = reader.readLine();

                if (str.equals("initializeHomePage")) {
                    writer.write(foodList() + "\n");
                    writer.flush();
                }
                else if(str.contains("getFood")){
                    String ids = reader.readLine();
                    writer.write(addedfood(ids)+"\n");
                    writer.flush();
                }
                else if(str.contains("prices")){
                    String food = reader.readLine();
                    writer.write(priceList(food)+"\n");
                    writer.flush();
                }
                else{
                    System.out.println("[Client] " + str);
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private String foodList(){
        String foods = "";

        try{
            FileReader file = new FileReader("src/sample/server/food.txt");
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
    private String addedfood(String ids){
        String [] list = ids.split("##");
        String sList = "";

        try{
            FileReader file = new FileReader("src/sample/server/food.txt");
            BufferedReader reader = new BufferedReader(file);

            String line;
            while((line=reader.readLine()) != null){
                String [] parts = line.split("#");
                for (String s: list){
                    if(parts[0].equals(s)){
                        sList += parts[1]+"#";
                        break;
                    }
                }
            }

            reader.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return sList;
    }
    private String priceList(String food){
        String price="";

        try{
            FileReader file = new FileReader("src/sample/server/food.txt");
            BufferedReader reader = new BufferedReader(file);

            String line;
            while((line=reader.readLine()) != null){
                String [] parts = line.split("#");
                if(parts[1].equals(food)){
                    price += parts[3];
                    break;
                }
            }

            reader.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return price;
    }

}
