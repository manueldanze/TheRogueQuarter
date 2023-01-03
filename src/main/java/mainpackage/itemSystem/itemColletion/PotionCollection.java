package mainpackage.itemSystem.itemColletion;

import mainpackage.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum PotionCollection {

    SMALL_HEALTH_POTION("Health Potion", "A health potion, thank do! heals currenthealth +50",
            "health-potion","file:src/main/resources/designTiles/other/flask_red.png","standard",2),
    BIG_HEALTH_POTION("Health Potion", "A giant health potion, thank do! heals currenthealth +200",
            "health-potion", "file:src/main/resources/designTiles/other/flask_big_red.png","rare",2),

    SMALL_POISON_POTION("Poison Potion", "A poison-potion, it says 'drink to die..'",
            "poison-potion", "file:src/main/resources/designTiles/other/flask_green.png","standard",2),
    BIG_POISON_POTION("Poison Potion", "Also known as Kool-Aid",
            "poison-potion", "file:src/main/resources/designTiles/other/flask_big_green.png","rare",2),

    SMALL_MYSTERIOUS_HEALTH_POTION("Mysterious Potion", "A mysterious potion, I should probably not drink it... or should I?",
            "health-potion", "file:src/main/resources/designTiles/other/flask_yellow.png","standard",2),
    BIG_MYSTERIOUS_HEALTH_POTION("Mysterious Potion", "A mysterious potion, I should probably not drink it... or should I?",
            "health-potion", "file:src/main/resources/designTiles/other/flask_big_yellow.png","rare",2),
    SMALL_MYSTERIOUS_POISON_POTION("Mysterious Potion", "A mysterious potion, I should probably not drink it... or should I?",
            "poison-potion", "file:src/main/resources/designTiles/other/flask_yellow.png","standard",2),
    BIG_MYSTERIOUS_POISON_POTION("Mysterious Potion", "A mysterious potion, I should probably not drink it... or should I?",
            "poison-potion", "file:src/main/resources/designTiles/other/flask_big_yellow.png","rare",2),

    ;

    final public String name, description, type, sprite, rarity;
    private int stats;
    private final int value;

    public int getStats() { return stats; }
    public int getValue() { return value; }

    PotionCollection(String name, String description, String type, String sprite, String rarity, int stats) {

        this.name = name;
        this.description = description;
        this.type = type;
        this.sprite = sprite;
        this.rarity = rarity; //--> für Unterscheidung via switch case siehe folgende
        this.stats = stats;

        switch (rarity) {
            case "standard" -> {value = Settings.standardValue;this.stats *= Settings.standardPotionMultiplier;}
            case "rare" -> {value = Settings.rareValue;this.stats *= Settings.rarePotionMultiplier;}
            case "unique" -> {value = Settings.uniqueValue;this.stats *= Settings.uniquePotionMultiplier;}
            case "legendary" -> {value = Settings.legendaryValue;this.stats *= Settings.legendaryPotionMultiplier;}
            default -> {
                IllegalArgumentException e = new IllegalArgumentException("Tried to construct potion enum object with invalid rarity type " + rarity);
                Logger LOG = LogManager.getLogger(PotionCollection.class);
                LOG.fatal(e);
                throw e;
            }
        }
    }
}