package sample.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
                else if(str.equals("getFood")){
                    String ids = reader.readLine();
                    writer.write(addedfood(ids)+"\n");
                    writer.flush();
                }
                else if(str.equals("prices")){
                    String food = reader.readLine();
                    writer.write(priceList(food)+"\n");
                    writer.flush();
                }
                else if(str.equals("userAddress")){
                    String user = reader.readLine();
                    writer.write(getAddress(user)+"\n");
                    writer.flush();
                }
                else if(str.equals("adminAdd")){
                    String food = reader.readLine();
                    addNewFood(food);
                }
                else if(str.equals("deleting")){
                    String food = reader.readLine();
                    onDelete(food);
                }
                else if(str.equals("getEmails")){
                    writer.write(getEmail()+"\n");
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

    private String getEmail(){
        String emails = "";
        try{
            FileReader file = new FileReader("src/sample/server/users.txt");
            BufferedReader reader = new BufferedReader(file);

            String line;
            while((line=reader.readLine()) != null){
                String [] parts = line.split("#");
                String email = parts[2]+",";
                emails += email;
            }
            reader.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return emails;
    }
    private void onDelete(String food) throws IOException{
        ArrayList<String> list = new ArrayList<>();

        FileReader file = new FileReader("src/sample/server/food.txt");
        BufferedReader reader = new BufferedReader(file);
        String [] aFood = food.split("#");

        String line;
        while((line=reader.readLine()) != null){
            String [] parts = line.split("#");
            if(!parts[0].equals(aFood[0]))
                list.add(line);
        }
        reader.close();
        System.out.println(list);

        File df = new File("src/sample/server/food.txt");
        df.delete();

        FileWriter fw = new FileWriter("src/sample/server/food.txt", true);
        BufferedWriter w = new BufferedWriter(fw);

        for(String s: list){
            w.write(s);
            w.newLine();
        }
        w.close();
    }
    private void addNewFood(String food) throws IOException {
        FileWriter fw = new FileWriter("src/sample/server/food.txt", true);
        BufferedWriter w = new BufferedWriter(fw);

        w.write(food);
        w.newLine();

        w.close();
    }
    private String getAddress(String user) {
        String ad = "";
        try{
            FileReader file = new FileReader("src/sample/server/users.txt");
            BufferedReader reader = new BufferedReader(file);

            String line;
            while((line=reader.readLine()) != null){
                String [] parts = line.split("#");
                if(user.equals(parts[3])){
                    ad = parts[5];
                }
            }
            reader.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ad;
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
