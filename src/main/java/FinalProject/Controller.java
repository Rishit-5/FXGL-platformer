package FinalProject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.settings.GameSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller extends GameApplication {
    private int coins=0;
    private int totalCoins = 0;
    private int level = 0;
    private Button button = new Button("Shop");//a button titled shop that can be clicked to show the shop

    private ArrayList<String> inv = new ArrayList<String>();//arraylist containing the owned inventory items
    private Label t = new Label("0 coins");//label indicating coins

    private ArrayList<ShopItem> items = new ArrayList<ShopItem>();//all shop items stored here
    private boolean player1death = false;//verifies if player1 died
    private String player1outfit = "marioblue.png";//out fits player1 with their current skin
    private boolean greenOwned = false;
    private boolean redOwned = false;
    private boolean orangeOwned = false;
    private boolean purpleOwned = false;
    private String player2outfit = "marioblue.png";//outfits player2 with their current skin
    private boolean player2death = false;//verifies player2's death
    private Entity lever1 =  new Entity();//first lever that controls the first door
    private Entity lever2 =  new Entity();//second lever that controls the second door
    private Entity door1 = new Entity();//first door that's affected by the first lever
    private int times = 0;//how many times player cheated in coins
    private int times2 = 0;//how many times player cheated in levels
    private Entity door2 = new Entity();//second door turned on and off by the second lever
    private boolean doubleJump = false;//checks/verifies if the user can constantly jump
    private int doubleCount = 0;
    private boolean lavaWalk = false;//checks if tge players bought the ability to walk on lava
    private boolean teleporter = false;//verifies if user boughtthe teleporter
    private int teleportTimes = 0;//sees how many times user teleported, caps off at 1
    private boolean skip = false;//checks to see if user has already skipped
    private int speed = 150;//sets the current player speed
    private int jumpHeight = 400;//sets the height a player jumped
    private boolean looting = false;//checks for double coins
    private boolean smaller = false;//checkks to see if player size is smaller
    private ArrayList<Boolean> leverstate = new ArrayList<>();//checks if each lever is on or off
    private ArrayList<Entity> doors =  new ArrayList<>();//all the doors that can be added
    private ImageView c = new ImageView("assets/textures/coin.png");//bottom right image view with coin texture
    Button btn2 = new Button("Inventory");//inventory button
    private ArrayList<Entity> enemies =  new ArrayList<>();//arraylist that contains all the enemies
    //prec: Game has been started up and instance fields have been initialized
    //postcondition: size of window and name of window is set
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(21*50);
        settings.setHeight(21*30);
        settings.setTitle("Super Mario Deluxe");
        settings.setVersion("0.1");

    }
    //precondition: user has clicked a category
    //postcondition: the items in the certain category will be shown with an imageview, and a button will be on the right indicating the price
    protected void showCat(ArrayList<Button> btns, int category){
        ArrayList<ShopItem> f = new ArrayList<>();
        for (int j = 0; j < items.size(); j++) {//adds all of a specific category as indicated to the f arraylist, which will be accessed
            if (items.get(j).getCategory()==category){
                f.add(items.get(j));
            }
        }

        ArrayList<ImageView> img = new ArrayList<ImageView>();//arraylist of imageviews
        ArrayList<Button> btn = new ArrayList<>();//arraylist for buy buttons
        int x = 900;
        int y = 200;
        for (int l = 0; l < f.size(); l++) {//adds the new image view 75 below each other and sets them all up
            img.add(new ImageView());
            img.get(l).setFitWidth(50);
            img.get(l).setFitHeight(50);
            img.get(l).setTranslateX(x);
            img.get(l).setTranslateY(y);
            img.get(l).setImage(new Image(f.get(l).getPath()));
            btn.add(new Button(f.get(l).getPrice()+" coins"));
            btn.get(l).setTranslateX(x+75);
            btn.get(l).setTranslateY(y);

            y+=75;
            getGameScene().addUINodes(img.get(l),btn.get(l));
            if (category==0 && l == 1){
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {//sets up the scenario for a green skin
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getName().equalsIgnoreCase("green") && !greenOwned){
                                greenOwned = true;
                                if (totalCoins-items.get(i).getPrice()>=0){//checks that the user can afford it
                                    totalCoins-=items.get(i).getPrice();
                                    t.setText(totalCoins + " coins");
                                    player1.setViewFromTextureWithBBox("mariogreen.png");
                                    player1outfit = "mariogreen.png";
                                    player2.setViewFromTextureWithBBox("mariogreen.png");
                                    player2outfit = "mariogreen.png";
                                }
                            }
                        }
                        if (greenOwned) {//equips the player with the outfit if they own it
                            player1.setViewFromTextureWithBBox("mariogreen.png");
                            player1outfit = "mariogreen.png";
                            player2.setViewFromTextureWithBBox("mariogreen.png");
                            player2outfit = "mariogreen.png";
                        }
                    }
                });
            }
            if (category==0 && l == 0){//red skin
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getName().equalsIgnoreCase("red") && !redOwned){
                                redOwned = true;
                                if (totalCoins-items.get(i).getPrice()>=0){
                                    totalCoins-=items.get(i).getPrice();
                                    t.setText(totalCoins + " coins");
                                    player1.setViewFromTextureWithBBox("mario.png");
                                    player1outfit = "mario.png";
                                    player2.setViewFromTextureWithBBox("mario.png");
                                    player2outfit = "mario.png";
                                }
                            }
                        }
                        if (redOwned) {
                            player1.setViewFromTextureWithBBox("mario.png");
                            player1outfit = "mario.png";
                            player2.setViewFromTextureWithBBox("mario.png");
                            player2outfit = "mario.png";
                        }
                    }
                });
            }
            if (category==0 && l == 2){//orange skin
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getName().equalsIgnoreCase("orange") && !orangeOwned){
                                orangeOwned = true;
                                if (totalCoins-items.get(i).getPrice()>=0){
                                    totalCoins-=items.get(i).getPrice();
                                    t.setText(totalCoins + " coins");
                                    player1.setViewFromTextureWithBBox("marioorange.png");
                                    player1outfit = "marioorange.png";
                                    player2.setViewFromTextureWithBBox("marioorange.png");
                                    player2outfit = "marioorange.png";
                                }
                            }
                        }
                        if (orangeOwned) {
                            player1.setViewFromTextureWithBBox("marioorange.png");
                            player1outfit = "marioorange.png";
                            player2.setViewFromTextureWithBBox("marioorange.png");
                            player2outfit = "marioorange.png";
                        }
                    }
                });
            }
            if (category==0 && l == 3){//purple skin
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getName().equalsIgnoreCase("purple") && !purpleOwned){
                                purpleOwned = true;
                                if (totalCoins-items.get(i).getPrice()>=0){
                                    totalCoins-=items.get(i).getPrice();
                                    t.setText(totalCoins + " coins");
                                    player1.setViewFromTextureWithBBox("mariopurple.png");
                                    player1outfit = "mariopurple.png";
                                    player2.setViewFromTextureWithBBox("mariopurple.png");
                                    player2outfit = "mariopurple.png";
                                }
                            }
                        }
                        if (purpleOwned) {
                            player1.setViewFromTextureWithBBox("mariopurple.png");
                            player1outfit = "mariopurple.png";
                            player2.setViewFromTextureWithBBox("mariopurple.png");
                            player2outfit = "mariopurple.png";
                        }
                    }
                });
            }
            if (category==1 && l == 0) {//category 1(inventory items) and this is for the sword
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (totalCoins-f.get(0).getPrice()>=0 && items.contains(f.get(0))){
                            items.remove(f.get(0));//removes the item from the shop
                            totalCoins-=f.get(0).getPrice();
                            t.setText(totalCoins + " coins");
                            inv.add(f.get(0).getName());//adds the inventory item to the users inventory
                            checkUserHas();
                        }
                    }
                });
            }
            if (category==1 && l == 1) {//the trident of atlantis
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (totalCoins-f.get(1).getPrice()>=0 && items.contains(f.get(1))){
                            items.remove(f.get(1));
                            totalCoins-=f.get(1).getPrice();
                            t.setText(totalCoins + " coins");
                            inv.add(f.get(1).getName());
                            checkUserHas();
                        }
                    }
                });
            }
            if (category==1 && l == 2) {//rosetta stone
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (totalCoins-f.get(2).getPrice()>=0 && items.contains(f.get(2))){
                            items.remove(f.get(2));
                            totalCoins-=f.get(2).getPrice();
                            t.setText(totalCoins + " coins");
                            inv.add(f.get(2).getName());
                            checkUserHas();
                        }
                    }
                });
            }
            if (category==1 && l == 3) {//zeus' lightning
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (totalCoins-f.get(3).getPrice()>=0 && items.contains(f.get(3))){
                            items.remove(f.get(3));
                            totalCoins-=f.get(3).getPrice();
                            t.setText(totalCoins + " coins");
                            inv.add(f.get(3).getName());
                            checkUserHas();
                        }
                    }
                });
            }
            if (category==2 && l == 0) {//category 2 of abilities and infinite jump
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!doubleJump && totalCoins-f.get(0).getPrice()>=0){//verifies that the user didn't already buy the upgrade
                            totalCoins-=f.get(0).getPrice();
                            t.setText(totalCoins + " coins");//updates coins
                            doubleJump = true;
                        }
                    }
                });
            }
            if (category==2 && l == 1) {//walk on lava ability
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!lavaWalk && totalCoins-f.get(1).getPrice()>=0){//verifies that the user didn't already buy the upgrade
                            totalCoins-=f.get(1).getPrice();
                            t.setText(totalCoins + " coins");
                            lavaWalk = true;
                        }
                    }
                });
            }//teleporter
            if (category==2 && l == 2) {
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!teleporter && totalCoins-f.get(2).getPrice()>=0){
                            totalCoins-=f.get(2).getPrice();
                            t.setText(totalCoins + " coins");
                            teleporter = true;
                        }
                    }
                });
            }
            if (category==2 && l == 3) {//level skipper
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!skip && totalCoins-f.get(3).getPrice()>=0){
                            totalCoins-=f.get(3).getPrice();
                            t.setText(totalCoins + " coins");
                            level++;
                            initGame();
                            skip = true;
                        }
                    }
                });
            }
            if (category==3 && l == 0) {//category of upgrades and speed boost
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (speed!=200 && totalCoins-f.get(0).getPrice()>=0){//if the user already has the speed boost, prevents from buying again
                            totalCoins-=f.get(0).getPrice();
                            t.setText(totalCoins + " coins");
                            speed = 200;
                        }
                    }
                });
            }
            if (category==3 && l == 1) {//jump boost
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (jumpHeight!=500 && totalCoins-f.get(1).getPrice()>=0){
                            totalCoins-=f.get(1).getPrice();
                            t.setText(totalCoins + " coins");
                            jumpHeight = 500;
                        }
                    }
                });
            }
            if (category==3 && l == 2) {//double coins
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!looting && totalCoins-f.get(2).getPrice()>=0){
                            totalCoins-=f.get(2).getPrice();
                            t.setText(totalCoins + " coins");
                            looting = true;
                        }
                    }
                });
            }
            if (category==3 && l == 3) {//smaller players
                btn.get(l).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!smaller && totalCoins-f.get(3).getPrice()>=0){
                            totalCoins-=f.get(3).getPrice();
                            t.setText(totalCoins + " coins");
                            smaller = true;
                            player1.setScaleX(.75);
                            player1.setScaleY(.75);
                            player2.setScaleX(.75);
                            player2.setScaleY(.75);
                        }
                    }
                });
            }
        }
        Button back = new Button("Back");
        back.setTranslateX(900);
        back.setTranslateY(100);
        back.setOnAction(new EventHandler<ActionEvent>() {//adds a back button that brings you back to the screen asking for categories
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < img.size(); i++) {
                    getGameScene().removeUINodes(img.get(i),btn.get(i));
                }
                img.clear();//removes the imageviews
                btn.clear();//removes the buttons that buy stuff
                showShop();
                getGameScene().removeUINode(back);
            }
        });
        getGameScene().addUINode(back);
        for (int k = 0; k < btns.size(); k++) {
            getGameScene().removeUINode(btns.get(k));
        }
        btns.clear();
    }
    //prec:user clicked on the shop
    //postc: 4 buttons will be displayed for each category, that can then be clicked on again
    protected void showShop(){
        getGameScene().removeUINode(btn2);
        getGameScene().removeUINode(button);
        ArrayList<Button> btns = new ArrayList<Button>();
        int x = 900;
        int y = 200;
        Button back = new Button("Back");//back button that takes back to the main screen
        back.setTranslateX(900);
        back.setTranslateY(100);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                for (int i = 0; i < btns.size(); i++) {
                    getGameScene().removeUINode(btns.get(i));
                }
                btns.clear();
                createButtons2();
                getGameScene().removeUINode(back);
            }
        });
        getGameScene().addUINode(back);
        for (int i = 0; i < 4; i++) {//adds 4 buttons for all the categories
            btns.add(new Button());
            btns.get(i).setTranslateX(x);
            btns.get(i).setTranslateY(y);
            y+=50;
            if (i==0){
                btns.get(i).setText("Skins");
                btns.get(i).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        showCat(btns,0);//if clicked, runs the method that displays the data in each category
                        getGameScene().removeUINode(back);
                    }
                });
            }
            else if (i==1){
                btns.get(i).setText("Inventory Items");
                btns.get(i).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        showCat(btns,1);
                        getGameScene().removeUINode(back);
                    }
                });

            }
            else if (i==2){
                btns.get(i).setText("Abilities");
                btns.get(i).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        showCat(btns,2);
                        getGameScene().removeUINode(back);
                    }
                });
            }
            else if (i==3){
                btns.get(i).setText("Upgrades");
                btns.get(i).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        showCat(btns,3);
                        getGameScene().removeUINode(back);
                    }
                });
            }
            getGameScene().addUINode(btns.get(i));
        }
    }
    //prec: game has been initialized and initGame has been run
    //postc: a shop will be created from a text file with all the data
    private void makeShop(){
        try {
            FileReader reader = new FileReader("src/main/java/FinalProject/items");
            Scanner in = new Scanner(reader);
            int count = 0;
            while (in.hasNextLine()) {
                String temp = in.nextLine();
                items.add(new ShopItem(temp.substring(0, temp.indexOf(":")), Integer.parseInt(temp.substring(temp.indexOf(":") + 1)),"assets/textures/" + temp.substring(0, temp.indexOf(":")) + ".png" , count/4));
                //adds name before the colon and price is what comes after the colon, category is indicated by what spot it's at
                count++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("SOMETHING HAS GONE HORRIBLY WRONG WE'RE ALL GONNA DIE!");//just kept it cause why not
        }
    }
    //prec: user clicked the inventory button on the main screen
    //postc: the inventory of a player will be displayed with imageviews representing each item
    protected void showInv(){
        getGameScene().removeUINode(t);
        getGameScene().removeUINode(c);
        getGameScene().removeUINode(btn2);
        getGameScene().removeUINode(button);
        ArrayList<ImageView> img = new ArrayList<ImageView>();
        int x = 900;
        int y = 200;
        for (int i = 0; i < inv.size(); i++) {//displays the inventory with image viewsspaced out by 75
            img.add(new ImageView());
            img.get(i).setFitWidth(50);
            img.get(i).setFitHeight(50);
            img.get(i).setTranslateX(x);
            img.get(i).setTranslateY(y);
            img.get(i).setImage(new Image("assets/textures/" + inv.get(i) + ".png"));
            y+=75;
            getGameScene().addUINode(img.get(i));
        }
        Button back = new Button("Back");
        back.setTranslateX(900);
        back.setTranslateY(100);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < img.size(); i++) {
                    getGameScene().removeUINode(img.get(i));
                }
                img.clear();
                createbuttons();
                getGameScene().removeUINode(back);
            }
        });
        getGameScene().addUINode(back);
    }
    //prec: game has been started up and initGame has been run
    //postc: the buttons for the shop and inventory will be set up, this won't require any additional clicking
    protected void createbuttons(){
        button.setTranslateX(900);
        button.setTranslateY(200);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showShop();//will display the categories
            }
        });
        btn2.setTranslateX(900);
        btn2.setTranslateY(300);
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInv();//displays the inventory in imageviews
            }
        });
        getGameScene().addUINode(button);
        getGameScene().addUINode(btn2);

        c.setTranslateX(885);
        c.setTranslateY(585);
        getGameScene().addUINode(c);

        t.setTranslateX(930);
        t.setTranslateY(580);
        t.setStyle("-fx-font-weight: bold;");
        t.setTextFill(Color.GOLD);
        t.setFont(new Font("Anton", 30));
        getGameScene().addUINode(t);//adds the text that's next to the coin imageview
    }
    protected void createButtons2(){
        button.setTranslateX(900);
        button.setTranslateY(200);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showShop();
            }
        });
        btn2.setTranslateX(900);
        btn2.setTranslateY(300);
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInv();
            }
        });
        getGameScene().addUINode(button);
        getGameScene().addUINode(btn2);
    }
    protected void checkUserHas(){
        for (int i = 0; i < inv.size()-1; i++) {
            if (inv.get(inv.size()-1).equalsIgnoreCase(inv.get(i))){
                inv.remove(inv.get(inv.size()-1));
            }
        }
    }
    //prec: game started up
    //post: ations will happen every update in the game
    @Override
    protected void onUpdate(double stuff){
        if (player2.isActive()) {//if player isn't clicking anything, the player won't slide
            if (!getInput().isHeld(KeyCode.L) || !getInput().isHeld(KeyCode.J) || !getInput().isHeld(KeyCode.I)) {
                player2.getComponent(PlayerComponent.class).cometoStop();
            }
        }
        if (player1.isActive()) {
            if (!getInput().isHeld(KeyCode.D) || !getInput().isHeld(KeyCode.A) || !getInput().isHeld(KeyCode.W)) {
                player1.getComponent(PlayerComponent.class).cometoStop();
            }
        }

    }
    //precondition: game has been started
    //postc: main method, game will run this after starting, and references will be made to create each entity that wasn't already created using the json
    @Override
    protected void initGame() {
        if (level ==0) {
            getGameWorld().addEntityFactory(new MarioFactory());//links up with the factory that adds each entity
            makeShop();
            getGameScene().setBackgroundRepeat(new Image("assets/textures/background.png"));
            getGameWorld().setLevelFromMap("mario2.json");
            player1 = getGameWorld().spawn("player",0,0);//spwns the player in a specific location
            player2 = getGameWorld().spawn("player",0,21*4);
            if (smaller){//saves the player size if the user bought the smaller upgrade
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);//outfits are removed when players respawn, so this equips the outfit
            player2.setViewFromTexture(player2outfit);

            createbuttons();
        } else if (level == 1){
            player1.removeFromWorld();//removes them before respawn so there's no overlay
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario3.json");
            player1 = getGameWorld().spawn("player",21*20,21*20);
            player2 = getGameWorld().spawn("player",21*20,21*18);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
        }
        else if (level == 2){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario4.json");
            player1 = getGameWorld().spawn("player",0,21*17);
            player2 = getGameWorld().spawn("player",0,21*20);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
            leverstate.add(false);//adds a lever that is off by default
            lever1= getGameWorld().spawn("lever",600,546-33);
            lever1.setViewFromTexture("offlever.png");//initial image of lever
            door1 = getGameWorld().spawn("door", 21*7,21*11);
        }
        else if (level == 3){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario5.json");
            player1 = getGameWorld().spawn("player",0,21*20);
            player2 = getGameWorld().spawn("player",840-32,21*20);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
            enemies.add(getGameWorld().spawn("enemy",21*8,21*24));//adds an enemy, puts them an arraylist so that they an be accessed later
            enemies.add(getGameWorld().spawn("enemy",21*32,21*24));


        }
        else if (level==4){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario6.json");
            player1 = getGameWorld().spawn("player",0,21*20);
            player2 = getGameWorld().spawn("player",0,21*16);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            getGameWorld().spawn("lockeddoor",21*28,21*22);//adds a locked door that can be accessed with a certain item
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
        }
        else if (level == 5){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario7.json");
            player1 = getGameWorld().spawn("player",21*20,21*20);
            player2 = getGameWorld().spawn("player",21*20,21*18);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
        }
        else if (level == 6){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario8.json");
            player1 = getGameWorld().spawn("player",21*0,21*21);
            player2 = getGameWorld().spawn("player",21*0,21*24);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
        }
        else if (level == 7){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario9.json");
            player1 = getGameWorld().spawn("player",8,463);
            player2 = getGameWorld().spawn("player",9,389);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            getGameWorld().spawn("lockeddoor",21*28,21*2);
            enemies.clear();
            enemies.add(getGameWorld().spawn("enemy", 105,463));
            enemies.add(getGameWorld().spawn("enemy", 292,420));
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
        }
        else if (level == 8){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario10.json");
            player1 = getGameWorld().spawn("player",11,456);
            player2 = getGameWorld().spawn("player",777,487);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
        }
        else if (level == 9){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameWorld().setLevelFromMap("mario11.json");
            player1 = getGameWorld().spawn("player",13,483);
            player2 = getGameWorld().spawn("player",797,485);
            if (smaller){
                player1.setScaleX(.75);
                player1.setScaleY(.75);
                player2.setScaleX(.75);
                player2.setScaleY(.75);
            }
            player1.setViewFromTexture(player1outfit);
            player2.setViewFromTexture(player2outfit);
            leverstate.clear();
            leverstate.add(false);
            leverstate.add(false);
            if (lever1.isActive()) {//if the levers were alrady there from previous levels
                lever1.removeFromWorld();
            }
            if (door1.isActive()){//removes doors from previous levels
                door1.removeFromWorld();
            }
            lever1= getGameWorld().spawn("lever",8,283);;
            lever2 = getGameWorld().spawn("lever",800,283);//
            lever1.setViewFromTexture("offlever.png");
            door1 = getGameWorld().spawn("door", 210,168);
            door2 = getGameWorld().spawn("door", 609,168);
            getGameWorld().spawn("lockeddoor", 210,63);
            getGameWorld().spawn("lockeddoor", 609,63);
            enemies.clear();
            enemies.add(getGameWorld().spawn("enemy", 115,504));
            enemies.add(getGameWorld().spawn("enemy", 683,504));
        }
        else if (level == 10){
            player1.removeFromWorld();
            player2.removeFromWorld();
            getGameScene().removeUINode(t);//removes the UI that dsitracts from the end screen
            getGameScene().removeUINode(c);
            getGameScene().removeUINode(button);
            getGameScene().removeUINode(btn2);
            getGameWorld().setLevelFromMap("mario12.json");
            getGameScene().setBackgroundRepeat(new Image("assets/textures/endtitle.png"));//end screen after the game
        }

    }






    private Entity player1;
    private Entity player2;
    //prec: game has been started up
    //postc: the inputs for the game will be created(directions and what to click to do actions)
    @Override
    protected void initInput() {
        keyCodes();
    }
    private void keyCodes(){
        try{
            getInput().clearAll();
            getInput().addAction(new UserAction("Left") {
                @Override
                protected void onAction() {
                    if (player1.isActive()) {//prevents user from mving when not allowed
                        player1.getComponent(PlayerComponent.class).left(speed);
                    }

                }
            }, KeyCode.A);
            getInput().addAction(new UserAction("Right") {
                @Override
                protected void onAction() {
                    if (player1.isActive()) {
                        if (player1.getX() < 840) {//prevents user from moving past 840(edge)
                            player1.getComponent(PlayerComponent.class).right(speed);
                        }
                    }
                }
            }, KeyCode.D);
            getInput().addAction(new UserAction("Jump") {
                @Override
                protected void onAction() {
                    if (player1.isActive()) {
                        if (doubleJump) {//doesnt disable the double jump
                            player1.getComponent(PlayerComponent.class).jump(jumpHeight);
                        }
                        else{
                            if (player1.getComponent(PlayerComponent.class).getVelocityY()==0) {
                                player1.getComponent(PlayerComponent.class).jump(jumpHeight);
                            }
                        }
                    }
                }
            }, KeyCode.W);
            getInput().addAction(new UserAction("Left2") {
                @Override
                protected void onAction() {
                    if (player2.isActive()) {
                        player2.getComponent(PlayerComponent.class).left(speed);
                    }

                }
            }, KeyCode.J);
            getInput().addAction(new UserAction("Right2") {
                @Override
                protected void onAction() {
                    if (player2.isActive()) {
                        if (player2.getX() < 840) {
                            player2.getComponent(PlayerComponent.class).right(speed);
                        }
                    }
                }
            }, KeyCode.L);
            getInput().addAction(new UserAction("Jump2") {
                @Override
                protected void onAction() {
                    if (player2.isActive()) {
                        if (doubleJump) {
                            player2.getComponent(PlayerComponent.class).jump(jumpHeight);
                        }
                        else{
                            if (player2.getComponent(PlayerComponent.class).getVelocityY()==0) {
                                player2.getComponent(PlayerComponent.class).jump(jumpHeight);
                            }
                        }
                    }
                }
            }, KeyCode.I);
            getInput().addAction(new UserAction("stuff") {//the cheat button to skip to the last level
                @Override
                protected void onAction() {
                    if (times2<1) {
                        if (JOptionPane.showInputDialog("What's the passsword").equalsIgnoreCase("admin_password")) {
                            level = 9;
                            totalCoins += 60;//sets the user up with coins
                            t.setText(totalCoins + " coins");
                            initGame();
                            times2++;
                        }
                    }
                }
            }, KeyCode.DIGIT5);
            getInput().addAction(new UserAction("stuff2") {//the cheat button to skip to the last level
                @Override
                protected void onAction() {
                    if (times<1) {
                        if (JOptionPane.showInputDialog("What's the passsword").equalsIgnoreCase("admin_password")) {
                            totalCoins += 60;//sets the user up with coins
                            t.setText(totalCoins + " coins");
                            times++;
                        }
                    }
                }
            }, KeyCode.DIGIT6);
            getInput().addAction(new UserAction("teleport") {
                @Override
                protected void onAction() {
                    if (teleportTimes == 0 && teleporter) {//verfies that they havent teleported more than once
                        int player = (int) (Math.random() * 2);//randomizes which player gets teleported
                        int x = -30;//default values that arent normal
                        int y = -30;
                        while (x <= 0 || x >= 840-32) {//spams user until they enter a valid value
                            try {
                                x = Integer.parseInt(JOptionPane.showInputDialog("What X value do you want to teleport to(0<x<808)?"));
                            }
                            catch (NumberFormatException ex){

                            }
                        }
                        while (y <= 0 || y >= 630-(32+21*3)) {
                            try {
                                y = Integer.parseInt(JOptionPane.showInputDialog("What Y value do you want to teleport to(0<y<630)?"));
                            }
                            catch(NumberFormatException ex){

                            }
                        }
                        if (player == 0) {//teleports user to a certain location
                            player1.getComponent(PhysicsComponent.class).reposition(new Point2D(x, y));
                        }
                        if (player == 1) {
                            player2.getComponent(PhysicsComponent.class).reposition(new Point2D(x, y));
                        }
                        teleportTimes++;
                    }
                }
            }, KeyCode.T);


        }
        catch (Exception ex){

        }
    }
    //prec: game started up
    //postcondition: onscreen text is displayed
    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100


        getGameScene().addUINode(textPixels); // add to the scene graph
    }
    //pre: a certain collision between two entities occurred
    //post: a handler for the collision is created, often different for each level
    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.COIN) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                //this all checks the collision between the player and coin using fxgl's physics handler
                coin.removeFromWorld();//the action done when colliding with the coin
                coins++;//coins for the level
                totalCoins++;//all the coins the user collected
                if (looting){//adds another coin with looting effect
                    totalCoins++;
                }
                t.setText(totalCoins + " coins");
                checkGameFinished();
            }

        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.ALIEN) {
            @Override
            protected void onCollisionBegin(Entity player, Entity alien) {
                //this all checks the collision between the player and coin using fxgl's physics handler
                if (level==1) {
                    getDisplay().showMessageBox("I need your help! I recently lost my cat somewhere and I can't find her!\nPlease take this key and help me find my cat, I don't know what I would ever do without her!\nThe key can be found in your inventory, but I'm not sure it's enough,\nyou might need a sword to break through.");
                    //adds a message when talking to the alien
                    inv.add("key");
                    checkUserHas();
                    checkGameFinished();
                }
                else if (level==5 && inv.contains("cat")) {
                    getDisplay().showMessageBox("Thank you so much for returning my cat! For that, I will give you 6 coins!");
                    inv.remove("cat");
                    totalCoins+=6;//adds 6 coins for the interaction
                    t.setText(totalCoins + " coins");
                    checkGameFinished();
                }
            }

        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.CAT) {
            @Override
            protected void onCollisionBegin(Entity player, Entity cat) {
                //this all checks the collision between the player and coin using fxgl's physics handler
                if (level==4) {
                    cat.removeFromWorld();
                    inv.add("cat");
                    checkUserHas();
                    checkGameFinished();
                }
            }

        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.LOCKEDDOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity lockeddoor) {
                if (level==4) {//allows user to get through only if they have the key and the sword
                    if (inv.contains("key") && inv.contains("Sword of Ra")){
                        lockeddoor.removeFromWorld();
                        inv.remove("key");
                        inv.remove("Sword of Ra");
                    }
                }
                else if (level==7){
                    if (inv.contains("Stone of Rosetta")){//lets user in if they have the stone of rosetta
                        lockeddoor.removeFromWorld();
                        inv.remove("Stone of Rosetta");
                    }
                }
                else if (level==9){
                    if (inv.contains("Bolt of Zeus")){
                        lockeddoor.removeFromWorld();
                        for (int i = 0; i < inv.size(); i++) {
                            if (inv.get(i).equalsIgnoreCase("Bolt of Zeus")){
                                inv.remove(i);
                                i--;
                            }
                        }
                    }
                    else if (inv.contains("Trident of Atlantis")){
                        lockeddoor.removeFromWorld();
                        for (int i = 0; i < inv.size(); i++) {
                            if (inv.get(i).equalsIgnoreCase("Trident of Atlantis")){
                                inv.remove(i);
                                i--;
                            }
                        }
                    }
                }
            }

        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.LAVA) {
            @Override
            protected void onCollisionBegin(Entity player, Entity lava) {
                if (!lavaWalk) {
                    player.removeFromWorld();
                    if (player.equals(player1)) {
                        player1death = true;
                    } else {
                        player2death = true;
                    }
                    if ((player1death && player2death)) {//resets level if both players died
                        initGame();
                        totalCoins -= coins;
                        coins = 0;
                        t.setText(totalCoins + " coins");
                    }
                }
            }


        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.PORTAL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity portal) {
                if (level == 2) {
                    if (portal.getY()>21*15) {//if portal is in a certain location, teleport to the location of another portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(675,75));
                    }
                    else{
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(675,450));
                    }
                }
                if (level==3){
                    if (portal.getY()<200 && portal.getX()>21*20) {//top right portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(21*33,21*12));
                    }
                    else if (portal.getY()>200 && portal.getX()>21*20){//bottom right portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(576,140));
                    }
                    else if (portal.getY()<200 && portal.getX()<21*20){//top left portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(21*7,21*12));
                    }
                    else{//bottom left portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(281,151));
                    }
                }
                if (level==6){
                    if (portal.getX()>21*20 && portal.getY() >21*14){//bottom right portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(21*2,21*11));
                    }
                    else if (portal.getX()>21*20 && portal.getY() <=21*14 && portal.getY() > 21*8){//middle right portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(21*1,21*6));
                    }
                    else if(portal.getX()>21*20 && portal.getY() < 21*8){//top right portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(31,352));
                    }
                    else if (portal.getX()<21*20 && portal.getY() >21*14){
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(695*1,128));
                    }
                    else if (portal.getX()<21*20 && portal.getY() <=21*14 && portal.getY() > 21*8){//middle right portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(705,342));
                    }
                    else if(portal.getX()<21*20 && portal.getY() < 21*8){//top right portal
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(675,240));
                    }

                }
                else if (level == 7){
                    if (portal.getX()>21*20 && portal.getY() <139){
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(765,288));
                    }
                    else if (portal.getX()>21*20 && portal.getY() > 139 && portal.getY()< 293 ){
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(569,84));
                    }
                    else if (portal.getX()>21*20 && portal.getY()> 439 ){
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(52,41));
                    }
                    else{
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(755,397));
                    }
                }
                else if (level == 9){
                    if (portal.getX() == 336 && portal.getY() == 422){
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(545,300));
                    }
                    else if (portal.getX() == 441 && portal.getY() == 422){
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(250,300));
                    }
                    else if (portal.getX() == 336 && portal.getY() == 282){
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(525,464));
                    }
                    else{
                        player.getComponent(PhysicsComponent.class).reposition(new Point2D(281,464));
                    }
                }
                player1.setViewFromTexture(player1outfit);
                player2.setViewFromTexture(player2outfit);
            }


        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.LEVER) {
            @Override
            protected void onCollisionBegin(Entity player, Entity lever) {
                if (level == 2) {
                    if (leverstate.get(0).equals(false)) {//if the collision happens and the lever is off, it turns the lever on
                        door1.removeFromWorld();//removes the door if the lever is on
                        lever1.setViewFromTextureWithBBox("onlever.png");
                        leverstate.set(0, true);

                    }
                    else{
                        lever1.setViewFromTextureWithBBox("offlever.png");
                        leverstate.set(0,false);
                        door1 = getGameWorld().spawn("door", 21*7,21*11);//respawns the door if lever is turned off
                    }
                }
                else if (level == 9) {
                    if (lever.equals(lever1)){//handling multiple doors on level 10
                        if (leverstate.get(0).equals(false)) {
                            lever1.setViewFromTextureWithBBox("onlever.png");
                            leverstate.set(0, true);
                            door2.removeFromWorld();
                        } else {
                            door2 = getGameWorld().spawn("door", 609, 168);
                            lever1.setViewFromTextureWithBBox("offlever.png");
                            leverstate.set(0, false);
                        }
                    }
                    else{
                        if (leverstate.get(1).equals(false)) {
                            lever2.setViewFromTextureWithBBox("onlever.png");
                            leverstate.set(1, true);
                            door1.removeFromWorld();
                        } else {
                            door1 = getGameWorld().spawn("door", 210, 168);
                            lever2.setViewFromTextureWithBBox("offlever.png");
                            leverstate.set(1, false);

                        }
                    }
                }

            }

        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER,MarioType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).removeFromWorld();
                }
                enemies.clear();
                if (level == 3 ) {//doesnt comletely reset, since fxgl doesnt allow that on physics based collisions
                    enemies.add(getGameWorld().spawn("enemy", 21 * 8, 21 * 24));
                    enemies.add(getGameWorld().spawn("enemy", 21 * 32, 21 * 24));
                }
                else if (level == 7){
                    enemies.add(getGameWorld().spawn("enemy", 105,463));
                    enemies.add(getGameWorld().spawn("enemy", 292,420));
                }
                else{
                    enemies.add(getGameWorld().spawn("enemy", 115,504));
                    enemies.add(getGameWorld().spawn("enemy", 683,504));
                }
                player.removeFromWorld();
                if (player.equals(player1)){
                    player1death = true;
                }
                else {
                    player2death = true;
                }
                if ((player1death && player2death)){
                    if (level!=7) {
                        player1 = getGameWorld().spawn("player", 0, 21 * 20);
                        player2 = getGameWorld().spawn("player", 840 - 32, 21 * 20);
                        if (smaller){
                            player1.setScaleX(.75);
                            player1.setScaleY(.75);
                            player2.setScaleX(.75);
                            player2.setScaleY(.75);
                        }
                    }
                    else {
                        player1 = getGameWorld().spawn("player",8,463);
                        player2 = getGameWorld().spawn("player",9,389);
                        if (smaller){
                            player1.setScaleX(.75);
                            player1.setScaleY(.75);
                            player2.setScaleX(.75);
                            player2.setScaleY(.75);
                        }
                    }
                    player1.setViewFromTexture(player1outfit);
                    player2.setViewFromTexture(player2outfit);

                    t.setText(totalCoins + " coins");
                    player1death = false;
                    player2death = false;
                }
            }


        });
    }
    //prec: some action occurred that might trigger a level to end
    //postcondition: level will end, level coins reset, and a message displayed hinting at the next level
    private void checkGameFinished(){
        if (coins==4 && level==0){//requirement of coins to go to the next level
            getDisplay().showMessageBox("Level Complete! The next level will guide you to an alien in need", () -> {
                coins = 0;//resets coins for the next level
                level++;
                initGame();
            });
        }
        else if (coins==4 && level==1 && inv.contains("key")){
            getDisplay().showMessageBox("Level Complete! On this next level, you'll encounter lava(instant death),\nportals, levers, and doors. Use these to collect coins.", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==6 && level==2){
            getDisplay().showMessageBox("Level Complete! On this level, you must find a way to get past the goombas for coins.", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==4 && level==3){
            getDisplay().showMessageBox("Level Complete! On this next level, you must use your key and a special sword\nto  break down the door and save the cat!", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==3 && level==4 && inv.contains("cat")){
            getDisplay().showMessageBox("Level Complete! On this next level, you must get the cat back to its rightful owner.", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==4 && level==5 && !inv.contains("cat")){
            getDisplay().showMessageBox("Level Complete! On the next level, you must choose the correct path to survive", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==5 && level==6){
            getDisplay().showMessageBox("Level Complete! On the next level, you must find your way around and dodge traps\nto reach your goal, but you might need to decode a wall", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==4 && level==7){
            getDisplay().showMessageBox("Level Complete! On this next level, you need to find coins\nbut this task looks easier than it may be", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==7 && level==8){
            getDisplay().showMessageBox("Level Complete! On this next level, you need to find coins\nbut this task looks easier than it may be", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
        else if (coins==6 && level==9){
            getDisplay().showMessageBox("Mission Complete!\nCongrats on completing the game!", () -> {
                coins = 0;
                level++;
                initGame();
            });
        }
    }
}
