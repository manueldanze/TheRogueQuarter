package mainpackage.characterSystem.npcCollection;

import mainpackage.Settings;

public enum HostileBossCharacterCollection {


    BOSS_DEMON("Demon Boss","\n\nOh my thas a big one..\n\n A BOSS ATTACKS YOU",Settings.bossStartLvl,Settings.bossStartHealth,Settings.bossStartStrength,
            "file:src/main/resources/designTiles/boss/big_demon_idle_anim_f0.png","boss", Settings.bossStartCurrency),

    BOSS_ZOMBIE("Zombie Boss","\n\nA Giant disgusting Zombie \n\n A BOSS ATTACKS YOU",Settings.bossStartLvl,Settings.bossStartHealth,Settings.bossStartStrength,
            "file:src/main/resources/designTiles/boss/big_zombie_idle_anim_f0.png","boss",Settings.bossStartCurrency),

    BOSS_OGRE("Ogre Boss","\n\nA big ogre it looks like from a harry potter movie \n\n A BOSS ATTACKS YOU",Settings.bossStartLvl,Settings.bossStartHealth,Settings.bossStartStrength,
            "file:src/main/resources/designTiles/boss/ogre_idle_anim_f0.png","boss",Settings.bossStartCurrency)
    ;

    final public String name, description, type;
    final public int level, health, strength, currency;
    final public String sprite;


    HostileBossCharacterCollection(String name, String description, int level, int health, int strength, String sprite, String type, int currency) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.health = health;
        this.strength = strength;
        this.sprite = sprite;
        this.type = type;
        this.currency = currency;
    }


}