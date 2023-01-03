package mainpackage.world;

import static mainpackage.world.GameWorld.TILE_SIZE;

public enum Map {


	MAP02(5, 16, MapCollection.mapPool.map02_60x40),
	LAVENDER_TOWN(20,6, MapCollection.mapPool.lavenderTown),
	TRIAGON(9, 34, MapCollection.mapPool.triagon),
	ROCKY_DUNGEON(9, 8, MapCollection.mapPool.rocky_dungeon),
	SHAPES(5, 14, MapCollection.mapPool.shapes);

	private final byte[][] tileData;
	public final int startX, startY;

	public byte[][] getTileData() { return tileData.clone(); }

	Map(int startX, int startY, byte[][] data) {
		this.startX = startX * TILE_SIZE;
		this.startY = startY * TILE_SIZE;
		tileData = data;
	}
}
