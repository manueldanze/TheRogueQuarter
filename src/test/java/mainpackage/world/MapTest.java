package mainpackage.world;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;


import static org.junit.jupiter.api.Assertions.*;

@Order(4)
class MapTest {

    @Test
    void testIfAllMapsAreRectangular() {

        for (Map map : EnumSet.allOf(Map.class)) {
            int height = map.getTileData().length;
            int width = map.getTileData()[0].length;
            assertEquals(0, (width - height) % 2);
        }
    }

}