package sample.extraClasses;

public class Food {
    String id;
    String name;
    boolean available;
    double price;
    String restaurant;

    public Food(String id, String name, boolean available, double price, String restaurant) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.price = price;
        this.restaurant = restaurant;
    }
}
