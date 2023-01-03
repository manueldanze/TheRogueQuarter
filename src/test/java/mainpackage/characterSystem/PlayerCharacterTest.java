package mainpackage.characterSystem;

import mainpackage.itemSystem.itemColletion.PotionCollection;
import mainpackage.Settings;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Order(2)
class PlayerCharacterTest {

    @Test
    void testConsumeBiggerPotionThanNeeded() {
        var player = new PlayerCharacter();
        int amountSmall = PotionCollection.SMALL_HEALTH_POTION.getStats();//is 2
        int amountBig = PotionCollection.BIG_HEALTH_POTION.getStats();//is 4
        player.maxHealth=10;
        player.curHealth=player.maxHealth;

        player.curHealth -= amountSmall;
        player.consume(amountBig);
        assertEquals(player.maxHealth, player.getHealth());
    }

    @Test
    void testOneLevelUpViaXpGain() {
        var player = new PlayerCharacter();
        int playerLvlBeforeXp = player.getLevel();
        player.addXP(Settings.xpUntilNextLvl);
        assertEquals(player.getLevel(), playerLvlBeforeXp + 1);
    }
}