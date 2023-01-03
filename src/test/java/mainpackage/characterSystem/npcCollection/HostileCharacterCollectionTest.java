package mainpackage.characterSystem.npcCollection;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Order(3)
class HostileCharacterCollectionTest {

    @Test
    void testIfHostileTypeIsValid() {

        for (HostileCharacterCollection hostile : EnumSet.allOf(HostileCharacterCollection.class)) {

            assertTrue(List.of("standard", "rare", "unique", "legendary").contains(hostile.type));

        }
    }
}