package mainpackage.itemSystem.itemCollection;

import mainpackage.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public enum ItemCollection {

    //standard weapons
    RUSTY_SWORD("Rusty sword","A rusty old sword\n" + Settings.standardWeaponMultiplier + " damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_rusty_sword.png","standard",1),
    SWORD("Normal sword","An ordinary, average, generic sword. Not very interesting.\n" + Settings.standardWeaponMultiplier + " damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_regular_sword.png","standard",2),
    SPEAR("Rusty spear","A rusty old spear\n" + Settings.standardWeaponMultiplier + " damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_spear.png","standard",1),
    AXE("Rusty axe","Just a normal axe\n+3 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_axe.png","standard",2),
    KNIFE("Small knife","A lil pocket knife\n+1 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_knife.png","standard",1),
    BOW("Old bow","Just a old bow that need arrows to use.\n+0 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_bow.png","standard",1),
    HAMMER("Hammer","A locksmith's favorite.\n+1 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_hammer.png","standard",2),
    MACE("Mace","A small stick with a nubby end..\n"+ Settings.standardWeaponMultiplier +" damage",
            "weapon","file:src/main/resources/designTiles/weapons/weapon_mace.png","standard",1),
    CLEAVER("Steel cleaver","The butcher's most beloved tool.\n+3 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_cleaver.png","standard",2),

    //rare weapons
    BATON("Baton with spikes","Ты колючая как стекловата!\n+4 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_baton_with_spikes.png","rare",2),
    BIG_HAMMER("Big hammer","A big hammer for the big boys in the club.\n+2 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_big_hammer.png","rare",4),
    MACHETE("Machete","Just a typical machete\n+2 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_machete.png","rare",3),
    SAW_SWORD("Saw sword","That sword looks like from a horror movie...\ndeals " + Settings.rareWeaponMultiplier + " damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_saw_sword.png","rare",4),
    STEEL_MACE("Steel Mace","A mace for the barbarian hero\n+3 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_mace.png","standard",3),
    GREEN_MAGIC_STAFF("Green magic staff","A mysterious green magic staff\n+2 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_green_magic_staff.png","standard",4),

    //unique weapons
    KNIGHT_SWORD("Knight sword","Looks rugged, but is heavy and hard-hitting.\n+4 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_knight_sword.png","standard",6),
    GOLD_SWORD("Gold sword","Shiniest kid around.\n+5 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_rusty_sword.png","unique",8),
    BIG_SWORD("Giant Sword","A giant anime-like sword\n"+ Settings.uniqueWeaponMultiplier +" damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_anime_sword.png","unique",6),
    KATANA("Katana","A historical far east asian katana\n+3 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_katana.png","standard",6),
    DUEL_SWORD("Duel sword","Better safe than sorry.\n+3 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_duel_sword.png","standard",8),

    //legendary weapons
    LAVISH_SWORD("Lavish sword","A wasteful interesting sword\n+3 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_lavish_sword.png","unique",10),
    RED_GEM_SWORD("Red gem sword","A powerful mystical sword with a strong gem in it. Deals\n+6 damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_red_gem_sword.png","standard",12),
    RED_MAGIC_STAFF("Red magic staff","A mysterious red magic stick that is better than bacon.. that deals "+Settings.legendaryWeaponMultiplier +" damage",
            "weapon", "file:src/main/resources/designTiles/weapons/weapon_red_magic_staff.png","legendary",12),




    //standart armor
    IRON_ARMOR("Iron Armor", "Some iron plating for your body, good enough for now!\nmaxhealth +2",
            "armor", "file:src/main/resources/designTiles/armor/eisenHarnisch.png","standard",2),
    //rare armor
    GOLD_ARMOR("Gold Armor", "Solid golden armor, perfect for a fight!\nmaxhealth +3",
            "armor","file:src/main/resources/designTiles/armor/goldHarnisch.png","rare",5),
    //unique armor
    DIAMOND_ARMOR("Diamond Armor", "A sturdy, reliable diamond vest, the best!\nmaxhealth +4",
            "armor", "file:src/main/resources/designTiles/armor/diamantHarnisch.png","unique",8),
    ;

    final public String name, description, type, sprite, rarity;
    public int stats, value;

    ItemCollection(String name, String description, String type, String sprite, String rarity, int stats) {

        Logger LOG = LogManager.getLogger(ItemCollection.class);
        IllegalArgumentException e = new IllegalArgumentException("Tried to initialize item with non-existant type " + type);
        IllegalArgumentException f = new IllegalArgumentException("Tried to initialize item with non-existant rarity class " + rarity);

        this.name = name;
        this.description = description;
        this.type = type;
        this.sprite = sprite;
        this.rarity = rarity;
        this.stats = stats;

        if (Objects.equals(type, "weapon")){
            switch (rarity) {
                case "standard" -> {value = Settings.standardValue;this.stats *=  Settings.standardWeaponMultiplier;}
                case "rare" -> {value = Settings.rareValue;this.stats *= Settings.rareWeaponMultiplier;}
                case "unique" -> {value = Settings.uniqueValue;this.stats *= Settings.uniqueWeaponMultiplier;}
                case "legendary" -> {value = Settings.legendaryValue;this.stats *= Settings.legendaryWeaponMultiplier;}
                default -> {
                    LOG.fatal(f);
                    throw f;
                }
            }
        }
        else if (Objects.equals(type, "armor")){
            switch (rarity) {
                case "standard" -> {value = Settings.standardValue;this.stats *= Settings.standardArmorMultiplier;}
                case "rare" -> {value = Settings.rareValue;this.stats *= Settings.rareArmorMultiplier;}
                case "unique" -> {value = Settings.uniqueValue;this.stats *= Settings.uniqueArmorMultiplier;}
                case "legendary" -> {value = Settings.legendaryValue;this.stats *= Settings.legendaryArmorMultiplier;}
                default -> {
                    LOG.fatal(f);
                    throw f;
                }
            }
        }

        else {
            LOG.fatal(e);
            throw e;
        }
    }
}
