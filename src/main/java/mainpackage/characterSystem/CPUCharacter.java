package mainpackage.characterSystem;

import javafx.scene.image.Image;
import mainpackage.itemSystem.Item;
import mainpackage.itemSystem.itemColletion.ItemCollection;
import mainpackage.itemSystem.itemColletion.PotionCollection;
import mainpackage.Settings;
import mainpackage.characterSystem.npcCollection.FriendlyCharacterCollection;
import mainpackage.characterSystem.npcCollection.HostileCharacterCollection;
import mainpackage.characterSystem.npcCollection.HostileBossCharacterCollection;
import mainpackage.world.CollisionType;
import mainpackage.world.Sound;
import mainpackage.world.Sprite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static mainpackage.world.GameWorld.TILE_SIZE;
import static mainpackage.world.GameWorld.getWorld;


public class CPUCharacter extends CharacterModel {

    private static final Logger LOG = LogManager.getLogger(CPUCharacter.class);
    private String name;
    private boolean hostile;
    private int enemyLevel;
    private String type;
    public boolean isHostile() { return hostile; }

    public int getLevel() { return enemyLevel; }
    public String getType() { return type; }

    public void createDealerInventory(int size){
        for (int i=0;i<size;i++){
            inventory.add(new Item(ItemCollection.values()[rand.nextInt(ItemCollection.values().length)]));
            LOG.debug("created inventory for npc with size " + size);
        }
    }

    public void dropInventory() {
        if (rand.nextInt(100 - 1) + 1 < Settings.hostileLootDropRate) {// randomizer für drop chance potion
            getWorld().spawnItem(PotionCollection.values()[rand.nextInt(PotionCollection.values().length)], sprite.getPosition().getX() + rand.nextDouble(),
                    sprite.getPosition().getY() + (rand.nextDouble()));
            LOG.debug("dropped item of normal enemy");
        }
        if(Objects.equals(this.type, "boss")){
            getWorld().spawnItem(ItemCollection.values()[rand.nextInt(ItemCollection.values().length)], sprite.getPosition().getX() + rand.nextDouble(),
                    sprite.getPosition().getY() + (rand.nextDouble()));
            LOG.debug("dropped item of boss enemy");
        }
    }

    private void kill() {
        Sound.fightSong1.pause();
        Sound.fightSong2.pause();
        dropInventory();
        getWorld().playerInstance().currency+=this.currency;
        Sound.victory.play();
        fightWindow.setDesc("\n \nENEMY DEFEATED! \n \n"+this.name+" is dead!");
        fightWindow.setFightText("\nYou earned: " + this.currency + " coins");
        LOG.info("player killed " + this.name);
        LOG.debug("added {} coins to player", this.currency);
        try { Thread.sleep(1000); }
        catch (InterruptedException e) {
            LOG.error("game interrupted");
            throw new RuntimeException(e);
        }
    }
    @Override
    public void takeDamage(int amount) {
        if (dmgTakeRand(Settings.takeDmgChance)) {
            curHealth -= (amount + getWorld().playerInstance().weaponStrength);
            fightWindow.setFightText("You hit enemy");
        }
        else {
            LOG.debug("enemy missed player");
            fightWindow.setFightText("You missed enemy");
        }
        if (curHealth <= 0) { kill(); }
    }

    // Only used in developer mode
    public CPUCharacter(HostileCharacterCollection hostile) {
        super();
        this.hostile=true;
        name = hostile.name;
        description = hostile.description;
        enemyLevel = hostile.level * getWorld().getCompletedMapCount();
        maxHealth = hostile.health * enemyLevel;
        curHealth = hostile.health;
        strength = hostile.strength * enemyLevel;
        sprite = new Sprite(new Image(hostile.sprite,
                1.2*TILE_SIZE, 1.2*TILE_SIZE, true, false), CollisionType.PLAYER);
        type=hostile.type;
        currency = hostile.currency * enemyLevel;
    }

    public CPUCharacter(HostileCharacterCollection hostile, double xpos, double ypos) {
        super();
        this.hostile=true;
        name = hostile.name;
        description = hostile.description;
        enemyLevel = hostile.level * getWorld().getCompletedMapCount();
        maxHealth = (int)(hostile.health * Settings.enemyLevelingCoeff*enemyLevel);
        curHealth = hostile.health;
        strength = (int)(hostile.strength * Settings.enemyLevelingCoeff*enemyLevel);
        sprite = new Sprite(new Image(hostile.sprite,
                TILE_SIZE+5, TILE_SIZE+5, true, false), CollisionType.PLAYER);
        sprite.setPos(xpos * TILE_SIZE, ypos * TILE_SIZE);
        type=hostile.type;
        currency = hostile.currency * enemyLevel;
    }

    public CPUCharacter(HostileBossCharacterCollection hostile, double xpos, double ypos) {
        super();
        this.hostile=true;
        name = hostile.name;
        description = hostile.description;
        enemyLevel = hostile.level * getWorld().getCompletedMapCount();
        maxHealth = (int)(hostile.health * Settings.enemyLevelingCoeff*enemyLevel);
        curHealth = hostile.health;
        strength = (int)(hostile.strength * Settings.enemyLevelingCoeff*enemyLevel);
        sprite = new Sprite(new Image(hostile.sprite,
                TILE_SIZE+40, TILE_SIZE+40, true, false), CollisionType.PLAYER);
        sprite.setPos(xpos, ypos);
        type=hostile.type;
        currency = hostile.currency * enemyLevel;
    }
    public CPUCharacter(FriendlyCharacterCollection friendly) {
        super();
        hostile=false;
        name = friendly.name;
        description = friendly.description;
        sprite = new Sprite(new Image(friendly.sprite,
                TILE_SIZE, TILE_SIZE, true, false), CollisionType.PLAYER);
        createDealerInventory(Settings.itemAmountDealer);
    }

    public CPUCharacter(FriendlyCharacterCollection friendly, double xpos, double ypos){
        super();
        hostile=false;
        name = friendly.name;
        description = friendly.description;
        sprite = new Sprite(new Image(friendly.sprite,
                TILE_SIZE+20, TILE_SIZE+20, true, false), CollisionType.PLAYER);
        sprite.setPos(xpos, ypos);
        createDealerInventory(Settings.itemAmountDealer);
    }
}

