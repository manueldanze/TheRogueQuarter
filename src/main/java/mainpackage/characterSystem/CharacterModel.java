package mainpackage.characterSystem;

import mainpackage.itemSystem.Item;
import mainpackage.Settings;
import mainpackage.world.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static mainpackage.world.GameWorld.TILE_SIZE;
import static mainpackage.world.GameWorld.getWorld;

public abstract class CharacterModel {

    private static final Logger LOG = LogManager.getLogger(CharacterModel.class);
    protected static Random rand = new Random();

    protected String description;
    protected int   maxHealth,
                    curHealth,
                    strength,
                    currency,
                    walkSpeed = Settings.walkSpeed;

    protected final ArrayList<Item> inventory = new ArrayList<>();
    public static final FightWindow fightWindow = new FightWindow();
    protected Sprite sprite;
    protected boolean isMovingLeft, isMovingRight, isMovingUp, isMovingDown;



    public CharacterModel() {}
    public CharacterModel(int health, int strength) {
        maxHealth = health;
        curHealth = health;
        this.strength = strength;
    }

    public Sprite getSprite() { return sprite; }
    public int getHealth() { return curHealth; }
    public int getMaxHealth() { return maxHealth; }
    public String getDescription() { return description; }
    public int getStrength() {return strength;}
    public int getCurrency() {return currency;}
    public List<Item> invContents() { return Collections.unmodifiableList(inventory); }

    public void addCoin() {
        LOG.info("Added 1 coin to "+this.getClass());
        currency++;
    }

    public void addCoin(int amount) {
        LOG.info("Added"+amount+"coins to "+this.getClass());
        currency += amount;
        HUD.updateStats();
    }

    public void pay(int amount){
        if(amount<0){
            LOG.error("PlayerCharacter tried to pay negative amount of coins");
            throw new RuntimeException("Tried to pay negative amount of coins");
        }
        LOG.debug("subtracted {} coins", amount);
       currency-=amount;
    }
    public boolean dmgDealRand(int chance){
        boolean result=true;
        int dice;
        dice=rand.nextInt(100 - 1) + 1;
        if (dice > chance){   result=false;   }
        return result;
    }
    public boolean dmgTakeRand(int chance){
        boolean result=true;
        int dice;
        dice=rand.nextInt(100 - 1) + 1;
        if (dice > chance){   result=false;   }
        return result;
    }
    public void takeDamage(int amount) {
        if (dmgTakeRand(100)){
            curHealth -= amount;
            LOG.debug("took {} damage", amount);
        }
        else {
            LOG.debug("enemy missed player");
            fightWindow.setFightText("haha sucker, you missed!");
        }
    }
    public int dealDamage(int strength) {
        if(strength<0){
            throw new RuntimeException("error: tried to deal negativ damage");
        }
        if (!dmgDealRand(Settings.dealDmgChance)){
            LOG.debug("player missed enemy");

            fightWindow.setFightText("damn, you i missed!");
        }//Platzhalter
        return strength;
    }
    public void fight(CPUCharacter other) {
        LOG.info(this+"just fought "+other);
        fightWindow.setFightText(this+"just fought "+other);
    }

    public void simulate() {
        if (isMovingLeft) {
            for (Sprite spr : getWorld().allSprites) {
                if (this.getSprite().equals(spr)) continue;

                fogOfWar(spr);

                if (spr.getImage().getBoundsInParent().contains(this.sprite.getPosition().add(-2 * walkSpeed, 0))) {
                    if (spr.getCollisionType() == CollisionType.TILE) return;
                    else spr.getCollisionType().collide(this, spr);
                }
            }
            moveSprite(-walkSpeed, 0);
        }

        if (isMovingRight) {
            for (Sprite spr : getWorld().allSprites) {
                if (this.getSprite().equals(spr)) continue;

                fogOfWar(spr);

                if (spr.getImage().getBoundsInParent().contains(this.sprite.getPosition().add(walkSpeed, 0))) {
                    if (spr.getCollisionType() == CollisionType.TILE) return;
                    else spr.getCollisionType().collide(this, spr);
                }
            }
            moveSprite(walkSpeed, 0);
        }

        if (isMovingUp) {
            for (Sprite spr : getWorld().allSprites) {
                if (this.getSprite().equals(spr)) continue;

                fogOfWar(spr);

                if (spr.getImage().getBoundsInParent().contains(this.sprite.getPosition().add(0, -walkSpeed))) {
                    if (spr.getCollisionType() == CollisionType.TILE) return;
                    else spr.getCollisionType().collide(this, spr);
                }
            }
            moveSprite(0, -walkSpeed);
        }

        if (isMovingDown) {
            for (Sprite spr : getWorld().allSprites) {
                if (this.getSprite().equals(spr)) continue;

                fogOfWar(spr);

                if (spr.getImage().getBoundsInParent().contains(this.sprite.getPosition().add(0, 2*walkSpeed))) {
                    if (spr.getCollisionType() == CollisionType.TILE) return;
                    else spr.getCollisionType().collide(this, spr);
                }
            }
            moveSprite(0, walkSpeed);
        }

        sprite.progressTime();
    }

    private void fogOfWar(Sprite spr) {

        if (spr.getPosition().distance(getWorld().playerInstance().getSprite().getPosition()) <= Settings.viewDistance) {
            if (spr.getCollisionType() != CollisionType.TILE && spr.getCollisionType() != CollisionType.NONE)
                if (spr.getCollisionType() == CollisionType.LADDER)
                    spr.getImage().setImage(MapGenerator.ladderTexture);
                else spr.getImage().setVisible(true);
            else
                spr.getImage().setEffect(null);
        }
        else {
            if (spr.getCollisionType() != CollisionType.TILE && spr.getCollisionType() != CollisionType.NONE)
                if (spr.getCollisionType() == CollisionType.LADDER)
                    spr.getImage().setImage(MapGenerator.floorTexture);
                else spr.getImage().setVisible(false);
            else
                if (spr.getImage().getEffect() != MapGenerator.shadow) spr.getImage().setEffect(MapGenerator.shadow);
        }
    }

    public void moveSprite(double x, double y) {
        sprite.movePosition(x, y);
        sprite.getImage().setTranslateX(sprite.getPosition().getX()-0.5*TILE_SIZE);
        sprite.getImage().setTranslateY(sprite.getPosition().getY()-0.5*TILE_SIZE);
    }
}


