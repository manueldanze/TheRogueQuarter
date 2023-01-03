package mainpackage;

public class Settings {

//Allgemein
    public static final int takeDmgChance = 80; //in %
    public static final int dealDmgChance = 80; //in %
    public static final int walkSpeed = 4;
    public static final int viewDistance = 100;
    public static final int hazardDmg = 1;

    public static final boolean devMode = false;



    //PlayerCharacter
    public static final int playerStartLvl = 1;
    public static final int playerStartHealth = 10;
    public static final int playerStartStrength = 1;
    public static final int playerStartCurrency = 10;
    public static final int xpUntilNextLvl = 1000; //Start value
    public static final float xpOnLvlUp = 1.5f; //Multiplier (each lvl needs more xp to reach)
    public static final int strengthOnLvlUp = 1;
    public static final int healthOnLvlUp = 1;
    public static final int xpPerWin = 100;

    //Hostile/Boss
    public static final int hostileStartLvl=1;
    public static final int hostileStartHealth=4;
    public static final int hostileStartStrength=2;
    public static final int hostileStartCurrency =1;
    public static final int hostileLootDropRate = 70; //in %
    public static final int bossStartLvl = 1;
    public static final int bossStartHealth=10;
    public static final int bossStartStrength=3;
    public static final int bossStartCurrency =10;
    public static final float enemyLevelingCoeff = 0.6f;

    //Dealer
    public static final int itemAmountDealer = 3; //Dealer item amount for sale


    //ItemCollection
    public static final int standardValue = 5;
    public static final int rareValue = 15;
    public static final int uniqueValue = 40;
    public static final int legendaryValue = 100;

    public static final int standardWeaponMultiplier = 1;
    public static final int rareWeaponMultiplier = 2;
    public static final int uniqueWeaponMultiplier = 3;
    public static final int legendaryWeaponMultiplier = 4;

    public static final int standardArmorMultiplier = 1;
    public static final int rareArmorMultiplier = 2;
    public static final int uniqueArmorMultiplier = 3;
    public static final int legendaryArmorMultiplier = 4;

    public static final int standardPotionMultiplier = 1;
    public static final int rarePotionMultiplier = 2;
    public static final int uniquePotionMultiplier = 3;
    public static final int legendaryPotionMultiplier = 4;

}
