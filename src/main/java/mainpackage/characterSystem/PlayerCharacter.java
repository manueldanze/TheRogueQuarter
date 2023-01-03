package mainpackage.characterSystem;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mainpackage.itemSystem.Item;
import mainpackage.Settings;
import mainpackage.world.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;
import static mainpackage.world.GameWorld.TILE_SIZE;
import static mainpackage.world.GameWorld.getWorld;

public class PlayerCharacter extends CharacterModel {

    private static final Logger LOG = LogManager.getLogger(PlayerCharacter.class);
    protected int level = Settings.playerStartLvl, exp, xpUntilNextLvl = Settings.xpUntilNextLvl;
    protected int armor, weaponStrength;
    private int collectedSkulls;
    public final Item[] equipped = new Item[2];



    public int getLevel() { return level; }
    public int getCurXP() { return exp; }
    public int getWeaponStrength() {return weaponStrength;}
    public int getCurArmor() {return armor;}
    public int getXpUntilNextLvl() {return xpUntilNextLvl;}
    public int getCollectedSkulls() { return collectedSkulls; }
    public void pickupSkull() {
        collectedSkulls++;
        HUD.switchText("You found a skull! "+(MapGenerator.skullsToCollect() - collectedSkulls)+" skulls left.");
    }
    public void resetSkulls() { collectedSkulls = 0; }

    public void consume(int amount) {
        if (curHealth + amount >= maxHealth) {
            curHealth = maxHealth;
        } else {
            curHealth += amount;
        }
        LOG.debug("added {} health", amount);
        HUD.updateStats();
    }

    public void equipArmor(int amount){
        armor=0;
        armor+=amount;
        HUD.updateStats();
        LOG.debug("gained {} armor", amount);
    }

    public void equipWeapon(int weapon){
        weaponStrength=0;
        weaponStrength+=weapon;
        HUD.updateStats();
        LOG.debug("gained {} strength", weapon);
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public void levelUp() {
        level++;
        xpUntilNextLvl *= Settings.xpOnLvlUp;
        strength += Settings.strengthOnLvlUp;
        maxHealth += Settings.healthOnLvlUp;
        curHealth = maxHealth;
        LOG.info("leveled up");
    }

    public void addXP(int amount) {
        if (exp + amount >= xpUntilNextLvl) {
            int nextXP = exp + amount - xpUntilNextLvl;
            exp = 0;
            levelUp();
            addXP(nextXP);
        } else { exp += amount; }
        HUD.updateStats();
        LOG.debug("added {} xp", amount);
    }

    public void gameOver() {
        Platform.runLater(fightWindow::endGame);
        Sound.fightSong1.pause();
        Sound.fightSong2.pause();
        Sound.lavenderTownTheme.stop();
        Sound.scream.play();
        Sound.deathSong.play();
        fightWindow.setDesc("\n\nYOU DIED...");
        fightWindow.setFightText("GAME OVER!");
        LOG.info("Game Over");
    }

    @Override
    public void takeDamage(int amount) {
        if (dmgTakeRand(Settings.takeDmgChance)){
            armor-=amount;
            if (armor<0){
                curHealth+=armor;
                armor=0;
                LOG.debug("armor lost");
                fightWindow.setFightText("Enemy hits you!");
            }
        }
        else {
            LOG.debug("no damage taken");
            fightWindow.setFightText("Enemy missed you!");
        }
        HUD.updateStats();
    }

    @Override public void fight(CPUCharacter hostile) {

        if (Objects.equals(hostile.getType(), "standard")){
            Sound.fightSong2.play();
        }
        if (Objects.equals(hostile.getType(), "boss")){
            Sound.fightSong1.play();
        }


        LOG.debug("fight started");

        Platform.runLater(getWorld()::showFightWindow);
        freeze();

        boolean playerFirst=true;
        System.out.println(hostile.description);
        fightWindow.setDesc(hostile.description);
        fightWindow.setFightText("");
        try { Thread.sleep(4000); }
        catch (InterruptedException e) {
            LOG.error("game interrupted");
            throw new RuntimeException(e);
        }
        fightWindow.setDesc("\n \nFIGHT..!!!");

        if(hostile.getLevel() == getLevel()){    playerFirst = rand.nextBoolean(); }
        else if (hostile.getLevel() > getLevel()){  playerFirst=false;  }


        if (playerFirst) {
            do {
                if(curHealth > 0) hostile.takeDamage(dealDamage(strength));
                try { getWorld().getLoop().sleep(1250); }
                catch (InterruptedException e) {
                    LOG.error("game interrupted");
                    throw new RuntimeException(e);
                }
                if(hostile.curHealth > 0) takeDamage(hostile.dealDamage(hostile.strength));
                try { getWorld().getLoop().sleep(1250); }
                catch (InterruptedException e) {
                    LOG.error("game interrupted");
                    throw new RuntimeException(e);
                }
            }
            while (hostile.curHealth > 0 && curHealth > 0);
        } else {
            do {
                if(hostile.curHealth>0) takeDamage(hostile.dealDamage(hostile.strength));
                try { getWorld().getLoop().sleep(1250); }
                catch (InterruptedException e) {
                    LOG.error("game interrupted");
                    throw new RuntimeException(e);
                }
                if(curHealth>0) hostile.takeDamage(dealDamage(strength));
                try { getWorld().getLoop().sleep(1250); }
                catch (InterruptedException e) {
                    LOG.error("game interrupted");
                    throw new RuntimeException(e);
                }
            }
            while (hostile.curHealth > 0 && curHealth > 0);
        }

        if (hostile.curHealth <= 0) {
            LOG.debug("fight ended");
            addXP(Settings.xpPerWin * hostile.getLevel());
            getWorld().killEnemy(hostile);
            Platform.runLater(getWorld()::closeFightWindow);
            getWorld().getLoop().continueGame();
        }

        if (curHealth <= 0) {
            gameOver();
        }
    }


    public final EventHandler<KeyEvent> controlsPressed = (e) -> {

        if (e.getCode() == KeyCode.W && !isMovingUp) {
            LOG.debug("player moving up");
            isMovingUp = true;
        }
        if (e.getCode() == KeyCode.A && !isMovingLeft) {
            LOG.debug("player moving left");
            isMovingLeft = true;
        }
        if (e.getCode() == KeyCode.S && !isMovingDown) {
            LOG.debug("player moving down");
            isMovingDown = true;
        }
        if (e.getCode() == KeyCode.D && !isMovingRight) {
            LOG.debug("player moving right");
            isMovingRight = true;
        }
    };

    public final EventHandler<KeyEvent> controlsReleased = (e) -> {

        if (e.getCode() == KeyCode.W) {
            LOG.debug("player stopped moving up");
            isMovingUp = false;
            e.consume();
        }
        if (e.getCode() == KeyCode.A) {
            LOG.debug("player stopped moving left");
            isMovingLeft = false;
            e.consume();
        }
        if (e.getCode() == KeyCode.S) {
            LOG.debug("player stopped moving down");
            isMovingDown = false;
            e.consume();
        }
        if (e.getCode() == KeyCode.D) {
            LOG.debug("player stopped moving right");
            isMovingRight = false;
            e.consume();
        }
    };

    public void freeze() {
        LOG.debug("Game movement frozen");
        isMovingDown = false;
        isMovingLeft = false;
        isMovingRight = false;
        isMovingUp = false;
        getWorld().getLoop().pause();
    }


    public PlayerCharacter() {
        super(Settings.playerStartHealth, Settings.playerStartStrength);
        sprite = new Sprite(new Image("file:src/main/resources/designTiles/player/gifs/player_knight_right.gif",
                TILE_SIZE+5, TILE_SIZE+5, true, false), getWorld().FIRST_MAP.startX, getWorld().FIRST_MAP.startY, CollisionType.PLAYER);
        sprite.getImage().setViewOrder(500);
        currency = Settings.playerStartCurrency;
    }

    public PlayerCharacter(boolean noSpawn) {
        super(Settings.playerStartHealth, Settings.playerStartStrength);
        if (noSpawn) LOG.info("Initialized player without spawn intent");
        sprite = new Sprite(new Image("file:src/main/resources/designTiles/player/gifs/player_elf_right.gif",
                TILE_SIZE, TILE_SIZE, true, false), 0, 0, CollisionType.PLAYER);
        currency = Settings.playerStartCurrency;
    }
}
