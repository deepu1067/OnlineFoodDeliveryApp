package sample.extraClasses;

public class Food {
    String id;
    String name;
    boolean available;
    double price;
    String restaurant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public Food(String id, String name, boolean available, double price, String restaurant) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.price = price;
        this.restaurant = restaurant;
    }
}
