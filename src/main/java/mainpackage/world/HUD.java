package mainpackage.world;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mainpackage.world.GameWorld.getWorld;

public class HUD {

    private static final Logger LOG = LogManager.getLogger(HUD.class);
    private static int slotSize = 60;

    private static int xPos = 0;
    private static int yPos = 0;
    private static Rectangle select;

    private static PauseTransition pause;

    private static Stage stats;
    private static Stage inventory;

    private static Pane inventoryPane;

    private static VBox statBox;

    private static HBox hBoxStats;

    private static BorderPane invPane;
    private static BorderPane statBp;
    private static BorderPane inventoryBp;
    private static BorderPane infoPane;

    private static Group slots;

    private static final Image invSlot = new Image("file:src/main/resources/designTiles/inventorySlots/Brown_Big_Tile.png", slotSize, slotSize, false, false);
    private static final Image selector = new Image("file:src/main/resources/designTiles/HUD/Selection.png", slotSize, slotSize, false, false);
    private static final Image placeHolder = new Image("file:src/main/resources/designTiles/inventorySlots/Placeholder.png", slotSize, slotSize, false, false);

    private static Text infoText;

    private static final Text hpT = new Text();
    private static Text lvlT = new Text();
    private static Text armT = new Text();
    private static Text strT = new Text();
    private static Text xpT = new Text();
    private static Text coins = new Text();

    private static Sprite[] itemS;

    public Stage stats() {
        stats = new Stage(StageStyle.TRANSPARENT);
        statBox = new VBox();
        statBp = new BorderPane();
        hBoxStats = new HBox();

        hBoxStats.getChildren().add(statBox);
        hBoxStats.setAlignment(Pos.TOP_RIGHT);
        statBp.setPrefSize(1200, 800);
        statBp.setTop(hBoxStats);
        stats.initStyle(StageStyle.UNDECORATED);
        stats.initStyle(StageStyle.TRANSPARENT);
        statBox.setPrefSize(180, 180);
        Scene statScene = new Scene(statBp);
        statScene.setFill(Color.TRANSPARENT);
        stats.setScene(statScene);
        editText(hpT);
        editText(lvlT);
        editText(armT);
        editText(strT);
        editText(xpT);
        editText(coins);
        statBox.getChildren().addAll(hpT, lvlT, armT, strT, xpT, coins);
        updateStats();
        LOG.debug("HUD created");
        return stats;
    }

    public void editText(Text text) {
        text.setFill(Color.WHITE);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
    }

    public static void updateStats() {
        hpT.setText("Health: " + getWorld().playerInstance().getHealth() + "/" + getWorld().playerInstance().getMaxHealth());
        lvlT.setText("Level: " + getWorld().playerInstance().getLevel());
        armT.setText("Armor: " + getWorld().playerInstance().getCurArmor());
        strT.setText("Strength: " + (getWorld().playerInstance().getWeaponStrength() + getWorld().playerInstance().getStrength()));
        xpT.setText("XP: " + getWorld().playerInstance().getCurXP() + "/" + getWorld().playerInstance().getXpUntilNextLvl());
        coins.setText("Coins: " + getWorld().playerInstance().getCurrency());
        LOG.debug("stats updated");
    }



    public Stage inventory() {
        inventory = new Stage(StageStyle.TRANSPARENT);
        inventoryPane = new Pane();
        inventoryBp = new BorderPane();
        infoPane = new BorderPane();
        invPane = new BorderPane();
        select = new Rectangle(60, 60);
        slots = new Group();
        infoText = new Text();
        editText(infoText);

        inventoryPane.setPrefSize(180, 300);
        for (int ww = 0; ww < 3; ww++) {
            for (int hh = 0; hh < 5; hh++) {
                Sprite slot = new Sprite(invSlot, ww * slotSize, hh * slotSize, CollisionType.NONE);
                slots.getChildren().addAll(slot.getImage());
            }
        }
        select.setFill(new ImagePattern(selector));
        inventoryPane.getChildren().addAll(slots, select);
        invPane.setRight(inventoryPane);
        infoPane.setBottom(infoText);
        invPane.setLeft(infoPane);
        inventoryBp.setPrefSize(1200, 800);
        inventoryBp.setBottom(invPane);
        Scene invScene = new Scene(inventoryBp);
        invScene.setFill(Color.TRANSPARENT);
        inventory.setScene(invScene);
        LOG.debug("inventory created");
        return inventory;
    }

    public static final EventHandler<KeyEvent> selectItem = (e) -> {
        if (e.getCode() == KeyCode.UP) {
            Sound.moveCursor.play();
            if (yPos > 0) {
                yPos -= 1;
                select.relocate(xPos * slotSize, yPos * slotSize);
            }
        }
        if (e.getCode() == KeyCode.DOWN) {
            Sound.moveCursor.play();
            if (yPos < 4) {
                yPos += 1;
                select.relocate(xPos * slotSize, yPos * slotSize);
            }
        }
        if (e.getCode() == KeyCode.RIGHT) {
            Sound.moveCursor.play();
            if (xPos < 2) {
                xPos += 1;
                select.relocate(xPos * slotSize, yPos * slotSize);
            }
        }
        if (e.getCode() == KeyCode.LEFT) {
            Sound.moveCursor.play();
            if (xPos > 0) {
                xPos -= 1;
                select.relocate(xPos * slotSize, yPos * slotSize);
            }
        }
        if (e.getCode() == KeyCode.ENTER) {
            if (getWorld().playerInstance().invContents().size() > 0) {
                if (getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getType().equals("health-potion")) {
                    getWorld().playerInstance().consume(getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getStats());
                    switchText("You consumed a " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getName() + ", how good for you!");
                    LOG.debug("consumed a " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getType());
                    getWorld().playerInstance().removeFromInventory(getWorld().playerInstance().invContents().get((yPos * 3) + xPos));
                    Sound.heal.play();
                    Platform.runLater(HUD::fillInventory);
                    return;
                }
                if (getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getType().equals("poison-potion")) {
                    getWorld().playerInstance().takeDamage(getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getStats());
                    switchText("You consumed a " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getName() + ", prepare to die!");
                    LOG.debug("consumed " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getName());
                    getWorld().playerInstance().removeFromInventory(getWorld().playerInstance().invContents().get((yPos * 3) + xPos));
                    Sound.drink.play();
                    Platform.runLater(HUD::fillInventory);
                    if (getWorld().playerInstance().getHealth() <= 0) {
                        getWorld().playerInstance().gameOver();
                    } else {
                        switchText("you feel very sick but at least you're still alive");
                    }
                    return;
                }
        //Weapon und Armor
                if (getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getType().equals("weapon")) {
                    if (getWorld().playerInstance().equipped[0] == null) {
                        getWorld().playerInstance().equipWeapon(getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getStats());
                        getWorld().playerInstance().equipped[0] = getWorld().playerInstance().invContents().get((yPos * 3) + xPos);
                        switchText("You equipped a " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getName());
                        LOG.debug("Equipped " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getName());
                        getWorld().playerInstance().removeFromInventory(getWorld().playerInstance().invContents().get((yPos * 3) + xPos));
                        Sound.equip.play();
                        Platform.runLater(HUD::fillInventory);
                    } else {
                        getWorld().playerInstance().addToInventory(getWorld().playerInstance().equipped[0]);
                        getWorld().playerInstance().equipWeapon(getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getStats());
                        getWorld().playerInstance().equipped[0] = getWorld().playerInstance().invContents().get((yPos * 3) + xPos);
                        getWorld().playerInstance().removeFromInventory(getWorld().playerInstance().invContents().get((yPos * 3) + xPos));
                        Sound.equip.play();
                        Platform.runLater(HUD::fillInventory);
                    }
                    return;
                }
                if (getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getType().equals("armor")) {
                    if (getWorld().playerInstance().equipped[1] == null) {
                        getWorld().playerInstance().equipArmor(getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getStats());
                        getWorld().playerInstance().equipped[1] = getWorld().playerInstance().invContents().get((yPos * 3) + xPos);
                        switchText("You equipped a " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getName());
                        LOG.debug("equipped a " + getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getName());
                        getWorld().playerInstance().removeFromInventory(getWorld().playerInstance().invContents().get((yPos * 3) + xPos));
                        Sound.equip.play();
                        Platform.runLater(HUD::fillInventory);
                    } else {
                        getWorld().playerInstance().addToInventory(getWorld().playerInstance().equipped[1]);
                        getWorld().playerInstance().equipArmor(getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getStats());
                        getWorld().playerInstance().equipped[1] = getWorld().playerInstance().invContents().get((yPos * 3) + xPos);
                        getWorld().playerInstance().removeFromInventory(getWorld().playerInstance().invContents().get((yPos * 3) + xPos));
                        Sound.equip.play();
                        Platform.runLater(HUD::fillInventory);
                    }
                }
            }
        }

        if (e.getCode() == KeyCode.DELETE) {
            if (getWorld().playerInstance().invContents().size() > 0) {
                getWorld().playerInstance().addCoin(Math.round(getWorld().playerInstance().invContents().get((yPos * 3) + xPos).getValue() * 0.75f));
                getWorld().playerInstance().removeFromInventory(getWorld().playerInstance().invContents().get((yPos * 3) + xPos));
                Sound.error.play();
                Platform.runLater(HUD::fillInventory);
            }
        }

        Platform.runLater(HUD::fillInventory);
    };



    public static void resetInv() {
        Sprite placeholder = new Sprite(placeHolder, 0,0, CollisionType.NONE);
        inventoryPane.getChildren().add(placeholder.getImage());
        if (itemS != null) {
            for (int ii = 0; ii < itemS.length; ii++) {
                inventoryPane.getChildren().remove(itemS[ii].getImage());
            }
        }
        LOG.debug("reset Inventory");
    }


    public static void fillInventory() {
        int x = 0;
        int y = 0;
        resetInv();
        itemS = new Sprite[getWorld().playerInstance().invContents().size()];
        for(int i = 0; i < getWorld().playerInstance().invContents().size(); i++){
            switch (i % 3) {
                case 0 -> x = 10;
                case 1 -> x = 70;
                case 2 -> x = 130;
            }
            switch (i / 3) {
                case 0 -> y = 10;
                case 1 -> y = 70;
                case 2 -> y = 130;
                case 3 -> y = 190;
                case 4 -> y = 250;
            }
            itemS[i] = new Sprite(
                    new Image(getWorld().playerInstance().invContents().get(i).getSprite().getImage().getImage().getUrl(),
                    slotSize - 20, slotSize - 20, false, false), x, y, CollisionType.NONE);
            inventoryPane.getChildren().addAll(itemS[i].getImage());
            LOG.debug("refilled Inventory");
        }
    }

    public static void switchText (String text) {
        pause = new PauseTransition(Duration.seconds(6));
        infoText.setText(text);
        pause.setOnFinished(e -> infoText.setText(""));
        pause.play();
        LOG.debug("switched Text");
    }
}
