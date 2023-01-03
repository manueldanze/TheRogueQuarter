package mainpackage.characterSystem.npcCollection;

import mainpackage.Settings;
import mainpackage.world.OutputTextCollection;

import static mainpackage.world.GameWorld.rand;

public enum HostileCharacterCollection {

    CHORT("Chort", "\nOh no, the devil himself attacks you!\n \nEnemy: " + OutputTextCollection.chortDialogue[rand.nextInt(OutputTextCollection.chortDialogue.length)],
            Settings.hostileStartLvl,Settings.hostileStartHealth,Settings.hostileStartStrength,"file:src/main/resources/designTiles/enemy/gifs/chort_idle_right.gif","standard", Settings.hostileStartCurrency),

    GOBLIN( "Goblin", "\nA stupid fat goblin is charging!\n \nEnemy: " + OutputTextCollection.goblinDialogue[rand.nextInt(OutputTextCollection.goblinDialogue.length)],
            Settings.hostileStartLvl,Settings.hostileStartHealth,Settings.hostileStartStrength,"file:src/main/resources/designTiles/enemy/gifs/goblin_idle_right.gif","standard",Settings.hostileStartCurrency),

    ICE_ZOMBIE("Ice Zombie", "\nA strange cold is emitting from this enemy..\n \nEnemy: " + OutputTextCollection.iceZombieDialogue[rand.nextInt(OutputTextCollection.iceZombieDialogue.length)],
            Settings.hostileStartLvl,Settings.hostileStartHealth,Settings.hostileStartStrength,"file:src/main/resources/designTiles/enemy/gifs/ice_zombie_idle_right.gif","standard",Settings.hostileStartCurrency),

    IMP("Imp", "\nA tiny little red ball... jumps at you!\n \nEnemy: " + OutputTextCollection.impDialogue[rand.nextInt(OutputTextCollection.impDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/imp_idle_right.gif","standard",Settings.hostileStartCurrency),

    MASKED_ORC("Masked Orc", "\nUha! a masked orc attacks!\n \nEnemy: " + OutputTextCollection.maskedOrcDialogue[rand.nextInt(OutputTextCollection.maskedOrcDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/masked_orc_idle_right.png","standard",Settings.hostileStartCurrency),

    MUDDY("Muddy", "\nThe mud infront of your feet is moving..!\n \nEnemy: " + OutputTextCollection.muddyDialogue[rand.nextInt(OutputTextCollection.muddyDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/muddy_idle_right.gif","standard",Settings.hostileStartCurrency),

    NECROMANCER("Necromancer", "\n A mysterios creature is watching you... you cant see its eyes\n \nEnemy: " + OutputTextCollection.necromancerDialogue[rand.nextInt(OutputTextCollection.necromancerDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/necromancer_idle_right.gif","standard",Settings.hostileStartCurrency),

    ORC_SHAMAN("Orc Shaman", "\nA wild looking orc is attacking\n \nEnemy: " + OutputTextCollection.orcShamanDialogue[rand.nextInt(OutputTextCollection.orcShamanDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/orc_shaman_idle_right.gif","standard",Settings.hostileStartCurrency),

    ORC_WARRIOR("Orc Warrior", "\nA orc-warrior charges full speed!!\n \nEnemy: " + OutputTextCollection.orcWarriorDialogue[rand.nextInt(OutputTextCollection.orcWarriorDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/orc_warrior_idle_right.gif","standard",Settings.hostileStartCurrency),

    SKELETON("Skeletor", "\nOh no, even bones are moving inside this dungeon..\n \nEnemy: " + OutputTextCollection.skeletonDialogue[rand.nextInt(OutputTextCollection.skeletonDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/skelet_idle_right.gif","standard",Settings.hostileStartCurrency),

    SWAMPY("Stinkball", "\nStinky green slime reaches out at your feed...\n \nEnemy: " + OutputTextCollection.swampyDialogue[rand.nextInt(OutputTextCollection.swampyDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/swampy_idle_right.gif","standard",Settings.hostileStartCurrency),

    TINY_ZOMBIE("Tiny Zombie", "\nOh, a cute little baby..thing?\n \nEnemy: " + OutputTextCollection.tinyZombieDialogue[rand.nextInt(OutputTextCollection.tinyZombieDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/tiny_zombie_idle_right.gif","standard",Settings.hostileStartCurrency),

    WOGOL("Wogol", "\nWhat even is this???\n \nEnemy: " + OutputTextCollection.wogolDialogue[rand.nextInt(OutputTextCollection.wogolDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/wogol_idle_right.gif","standard",Settings.hostileStartCurrency),

    ZOMBIE("Zombie", "\nA bad smelling zombie moves towards you..\n \nEnemy: " + OutputTextCollection.zombieDialogue[rand.nextInt(OutputTextCollection.zombieDialogue.length)],
            Settings.hostileStartLvl, Settings.hostileStartHealth,Settings.hostileStartStrength, "file:src/main/resources/designTiles/enemy/gifs/zombie_idle_right.gif","standard",Settings.hostileStartCurrency),
    ;

    final public String name, description, type;
    final public int level, health, strength, currency;
    final public String sprite;


    HostileCharacterCollection(String name, String description, int level, int health, int strength, String sprite, String type, int currency) {
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

