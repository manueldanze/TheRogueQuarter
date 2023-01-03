package mainpackage;

import mainpackage.itemSystem.itemColletion.ItemCollection;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Order(5)
class ItemTest {


    @Test
    void testIfItemRarityIsValid() {

        for (ItemCollection item : EnumSet.allOf(ItemCollection.class)) {

            assertTrue(List.of("standard", "rare", "unique", "legendary").contains(item.rarity));

        }
    }
}