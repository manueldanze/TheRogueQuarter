package mainpackage.itemSystem;

import javafx.scene.image.Image;
import mainpackage.itemSystem.itemColletion.ItemCollection;
import mainpackage.itemSystem.itemColletion.PotionCollection;
import mainpackage.world.CollisionType;
import mainpackage.world.Sprite;

import static mainpackage.world.GameWorld.TILE_SIZE;

public class Item {

    private final String name, description, type;
    private int stats, value;
    private Sprite sprite;


    public String getName() { return name; }
    public String getType() { return type; }
    public int getStats() { return stats; }
    public Sprite getSprite() { return sprite; }
    public String getDescription() {return description;}
    public int getValue() {return value;}

    public Item(ItemCollection item, double posX, double posY) {
        name = item.name;
        description = item.description;
        type = item.type;
        stats = item.stats;
        sprite = new Sprite(new Image(item.sprite,
                TILE_SIZE, TILE_SIZE, true, false), posX, posY, CollisionType.ITEM);
        value = item.value;
    }
    public Item(ItemCollection item){
        name = item.name;
        description = item.description;
        type = item.type;
        stats = item.stats;
        sprite = new Sprite(new Image(item.sprite,
                TILE_SIZE, TILE_SIZE, true, false), CollisionType.ITEM);
        value = item.value;
    }
    public Item(PotionCollection item, double posX, double posY) {
        name = item.name;
        description = item.description;
        type = item.type;
        stats = item.getStats();
        sprite = new Sprite(new Image(item.sprite,
                TILE_SIZE, TILE_SIZE, true, false), posX, posY, CollisionType.ITEM);
        value = item.getValue();
    }
    public Item(PotionCollection item) {
        name = item.name;
        description = item.description;
        type = item.type;
        stats = item.getStats();
        sprite = new Sprite(new Image(item.sprite,
                TILE_SIZE, TILE_SIZE, true, false), CollisionType.ITEM);
        value = item.getValue();
    }
}
