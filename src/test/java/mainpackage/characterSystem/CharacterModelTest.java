package mainpackage.characterSystem;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Order(1)
class CharacterModelTest {

    @BeforeAll
    public static void initTest() {
        Platform.startup(() -> {
        });
    }

    @Test
    void testAmountIsPayed() {
        var player = new PlayerCharacter();
        player.pay(10);
        assertEquals(player.currency -= 10, player.getCurrency());
    }

    @Test
    void TestPayNegativAmount() {
        var player = new PlayerCharacter();
        assertThrows(RuntimeException.class, () -> player.pay(-1));
    }

    @Test
    void TestDealNegativDmg() {
        var player = new PlayerCharacter();
        assertThrows(RuntimeException.class, () -> player.dealDamage(-1));
    }
}