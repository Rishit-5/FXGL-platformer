package FinalProject;

public class ShopItem {
    private String name;
    private int price;
    private String path;
    private int category;
    //precondition: new ShopItem object created
    //post: will set instance variables equal to parameters
    public ShopItem(String name, int price, String path, int category){
        this.name = name;
        this.price = price;
        this.path = path;
        this.category = category;
    }
    public int getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public String getPath(){
        return path;
    }
    public int getCategory(){
        return category;
    }

}
