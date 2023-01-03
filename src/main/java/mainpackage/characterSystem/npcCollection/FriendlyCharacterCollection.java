package mainpackage.characterSystem.npcCollection;

public enum FriendlyCharacterCollection {

        DEALER("Dealer","Hello adventurer, would you like to buy some of my finest wares?","File:src/main/resources/designTiles/player/gifs/dealer_wizzard_idle_right.gif")
    ;

    final public String name, description;
    final public String sprite;

    FriendlyCharacterCollection(String name, String description, String sprite) {
        this.name = name;
        this.description = description;
        this.sprite = sprite;
    }
}
