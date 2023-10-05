# The Rogue Quarter

A simple rogue-like dungeon crawler, made using only JavaFX.
####
- **Description**: 
  This game was developed by 4 students in the 2nd semester of computer science media.
  We took our inspiration from the game „Rogue“, the first game of its kind. The game starts on stage 1. The player has his own stats like hp, armor, xp aswell as strength. The player can explore the map and auto-fight enemies, collect coins to use for purchasing items from merchants, and also fight bigger boss NPCs if the player feels prepared to do so. The coins are found randomly scattered within the world and can also be dropped by enemies. The mission objective is to collect all skulls randomly found on the map in order to unlock the ladder, which acts as the gate to the next level. The fights are carried out in a dedicated window, while the strength of the enemies depends on the map level. To see what actions the player is taking, there is a status text in the bottom of the screen. The player can see his inventory and stats on the right side of the screen.
####
- **Run game**: execute Launcher.main() in an IDE (IntelliJ works fine, you may have to install maven)
####
- **Run tests**: Due to the way the JavaFX toolkit works, running the Junit tests separately will not work. Instead, run the tests all at once with Maven.
`mvn clean compile test`
####
- **Bug-Workaround**: If player sprite gets invisible/stuck -> leftclick into game window
