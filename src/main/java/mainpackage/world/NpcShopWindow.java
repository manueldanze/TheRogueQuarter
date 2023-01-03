package mainpackage.world;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainpackage.characterSystem.CPUCharacter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mainpackage.world.GameWorld.getWorld;
import static mainpackage.world.HUD.*;


public class NpcShopWindow{

    private static final Logger LOG = LogManager.getLogger(NpcShopWindow.class);

    private static Stage shop;
    private static HBox inShop;
    private static VBox layout;
    private static VBox slots;
    private static VBox descriptionSlots;
    private static VBox descriptions;
    private static VBox items;
    private static BorderPane button;
    private static Pane slotsWithItems;
    private static Pane shopWithSelect;
    private static Pane slotsWithDescription;
    private static BorderPane[] descriptionTextSlot;
    private static Scene shopScene;
    private static Text closeText;
    private static Text[] description;
    private static Text[] price;
    private static Rectangle select;

    private static int slotSize = 60;
    private static int selectorPos;

    private static Image slot = new Image("file:src/main/resources/designTiles/inventorySlots/Brown_Big_Tile.png", slotSize, slotSize, false, false);
    private static Image slotDesc = new Image("file:src/main/resources/designTiles/inventorySlots/Brown_Big_Tile.png", 400, slotSize, false, false);
    private static Image close = new Image("file:src/main/resources/designTiles/inventorySlots/Brown_Big_Tile.png", 460, 40, false, false);
    private static final Image selector = new Image("file:src/main/resources/designTiles/HUD/Selection.png", slotSize, slotSize, false, false);


    public static void createShopWindow(CPUCharacter npc) {
        selectorPos = 0;
        shop = new Stage();
        slots = new VBox();
        descriptionSlots = new VBox();
        layout = new VBox();
        items = new VBox();
        descriptions = new VBox();
        inShop = new HBox();
        closeText = new Text();
        description = new Text[npc.invContents().size()];
        price = new Text[npc.invContents().size()];
        slotsWithItems = new Pane();
        slotsWithDescription = new Pane();
        shopWithSelect = new Pane();
        descriptionTextSlot = new BorderPane[npc.invContents().size()];
        button = new BorderPane();
        select = new Rectangle(slotSize, slotSize);
        select.setFill(new ImagePattern(selector));
        closeText.setText("Press Backspace to close Shop");
        closeText.setFill(Color.WHITE);
        closeText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        slots.setPrefSize(slotSize, slotSize * 3);

        for (int ii = 0; ii < 3; ii++) {
            Sprite slotSprite = new Sprite(slot, 0, 0, CollisionType.NONE);
            slots.getChildren().add(slotSprite.getImage());
        }
        for (int ii = 0; ii < 3; ii++) {
            Sprite slotSprite = new Sprite(slotDesc, 0, 0, CollisionType.NONE);
            descriptionSlots.getChildren().add(slotSprite.getImage());
        }
        for (int jj = 0; jj < 3; jj++) {
            Sprite item = new Sprite(
                    new Image(npc.invContents().get(jj).getSprite().getImage().getImage().getUrl(),
                            slotSize, slotSize, false, false), 0, 0, CollisionType.NONE);
            items.getChildren().add(item.getImage());
        }
        for (int kk = 0; kk < 3; kk++) {
            description[kk] = new Text();
            price[kk] = new Text();
            descriptionTextSlot[kk] = new BorderPane();
            descriptionTextSlot[kk].setPrefSize(400, slotSize);
            description[kk].setText(npc.invContents().get(kk).getDescription());
            description[kk].setFill(Color.WHITE);
            description[kk].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
            price[kk].setText("Price: " + npc.invContents().get(kk).getValue());
            price[kk].setFill(Color.WHITE);
            price[kk].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
            price[kk].setTranslateY(-2);
            price[kk].setTranslateX(10);
            descriptionTextSlot[kk].setCenter(description[kk]);
            descriptionTextSlot[kk].setBottom(price[kk]);
            descriptions.getChildren().add(descriptionTextSlot[kk]);
        }
        slotsWithItems.getChildren().addAll(slots, items);
        slotsWithDescription.getChildren().addAll(descriptionSlots, descriptions);
        button.getChildren().add(new ImageView(close));
        button.setCenter(closeText);
        inShop.getChildren().addAll(slotsWithItems, slotsWithDescription);
        layout.getChildren().addAll(inShop, button);
        shopWithSelect.getChildren().addAll(layout, select);
        shopScene = new Scene(shopWithSelect);
        shop.initStyle(StageStyle.UNDECORATED);
        shop.setScene(shopScene);
        shop.initOwner(getWorld().getStage());

        shopScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE) {
                Sound.talk.play();
                shop.close();
                getWorld().getLoop().continueGame(true);
            }
            if (e.getCode() == KeyCode.UP && selectorPos > 0) {
                Sound.moveCursor.play();
                selectorPos -= 1;
                select.relocate(0, selectorPos * slotSize);
            }
            if (e.getCode() == KeyCode.DOWN && selectorPos < 2) {
                Sound.moveCursor.play();
                selectorPos += 1;
                select.relocate(0, selectorPos * slotSize);
            }
            if (e.getCode() == KeyCode.ENTER) {
                if (getWorld().playerInstance().getCurrency()>=npc.invContents().get(selectorPos).getValue()){
                    Sound.cash.play();
                    getWorld().playerInstance().addToInventory(npc.invContents().get(selectorPos));
                    getWorld().playerInstance().pay(npc.invContents().get(selectorPos).getValue());
                    updateStats();
                }else{
                    Sound.error.play();
                    switchText("Insufficient funds!");
                }
                fillInventory();

            }
        });
        getWorld().playerInstance().freeze();
        LOG.debug("shop window created");
        shop.show();
    }
}
