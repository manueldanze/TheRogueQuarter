package mainpackage.world;

import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import mainpackage.characterSystem.CPUCharacter;
import mainpackage.characterSystem.npcCollection.FriendlyCharacterCollection;
import mainpackage.characterSystem.npcCollection.HostileCharacterCollection;
import mainpackage.characterSystem.npcCollection.HostileBossCharacterCollection;

import static mainpackage.world.GameWorld.*;

public final class MapGenerator {

    public static final Image floorTexture = new Image("file:src/main/resources/designTiles/mapTiles/floor_1.png",
            TILE_SIZE, TILE_SIZE,
            false, false);

    public static final Image ladderTexture = new Image("file:src/main/resources/designTiles/mapTiles/floor_ladder.png",
            TILE_SIZE, TILE_SIZE, false, false);

    private static final Image wallTexture = new Image("file:src/main/resources/designTiles/mapTiles/wall_mid.png",
            TILE_SIZE, TILE_SIZE,
            false, false);

    private static final Image chestTexture = new Image("file:src/main/resources/designTiles/other/chest_empty_open_anim_f0.png",
            TILE_SIZE, TILE_SIZE, true, false);

    private static final Image coinTexture = new Image("file:src/main/resources/designTiles/other/gifs/coin_gif.gif",
            TILE_SIZE, TILE_SIZE, true, false);

    private static final Image coinBagTexture = new Image("file:src/main/resources/designTiles/other/gold_bag.png",
            TILE_SIZE, TILE_SIZE, true, false);

    private static final Image skullTexture = new Image("file:src/main/resources/designTiles/other/skull.png",
            TILE_SIZE, TILE_SIZE, true, false);

    public static final Image EMPTY = new Image("file:src/main/resources/designTiles/mapTiles/void.png",
            TILE_SIZE, TILE_SIZE, false, false);

    public static final ColorAdjust shadow = new ColorAdjust(); //hue, saturation, brightness, contrast
    static {
        shadow.setBrightness(-0.6);
        shadow.setSaturation(-0.5);
    }


    private Sprite[][] board = new Sprite[GameWorld.HEIGHT][GameWorld.WIDTH];
    public Sprite[][] getBoard() { return board.clone(); }

    private static int skullCount;
    public static int skullsToCollect() { return skullCount; }


    public Pane createContent(Map map) {
        skullCount = 0;

        Pane root = new Pane();
        root.setPrefSize(GameWorld.WIDTH * TILE_SIZE, GameWorld.HEIGHT * TILE_SIZE);
        board = new Sprite[map.getTileData().length][map.getTileData()[0].length];
        boolean ladderSpawned = false;

        for (int yy = 0; yy < map.getTileData().length; yy++) {
            for (int xx = 0; xx < map.getTileData()[0].length; xx++) {
                switch (map.getTileData()[yy][xx]) {

                    case 10: if (rand.nextInt(100) >= 19 && !ladderSpawned) {
                        createLadder(map, root, xx, yy);
                        ladderSpawned = true;
                    }
                    else { createFloor(map, root, xx, yy); }
                    break;

                    case 9: CPUCharacter boss = new CPUCharacter(HostileBossCharacterCollection.values()[rand.nextInt(HostileBossCharacterCollection.values().length)],
                            TILE_SIZE * xx, TILE_SIZE * yy);
                        root.getChildren().add(boss.getSprite().getImage());
                        getWorld().aiPlayers.add(boss);
                        getWorld().allSprites.add(boss.getSprite());
                        boss.getSprite().getImage().setVisible(false);
                        createFloor(map, root, xx, yy);
                        break;

                    case 6: if (rand.nextInt(100) >= Math.max(70 - 3 * getWorld().getCompletedMapCount(), 0)) {
                        getWorld().spawnEnemy(HostileCharacterCollection.values()[rand.nextInt(HostileCharacterCollection.values().length)], xx, yy);
                    }
                        createFloor(map, root, xx, yy);
                        break;

                    case 5:
                        if (rand.nextInt(100) >= 29) {
                            Sprite skull = new Sprite(skullTexture, TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.MISSION_COLLECT);
                            getWorld().allSprites.add(skull);
                            root.getChildren().add(skull.getImage());
                            skull.getImage().setVisible(false);
                            skullCount++;
                        }
                        createFloor(map, root, xx, yy);
                        break;

                    case 4:
                        CPUCharacter dealer = new CPUCharacter(FriendlyCharacterCollection.DEALER, TILE_SIZE * xx, TILE_SIZE * yy);
                        root.getChildren().add(dealer.getSprite().getImage());
                        getWorld().aiPlayers.add(dealer);
                        getWorld().allSprites.add(dealer.getSprite());
                        dealer.getSprite().getImage().setVisible(false);
                        createFloor(map, root, xx, yy);
                        break;

                    case 3: if (rand.nextInt(100) >= 29) {
                        if (rand.nextInt(2) == 1) {
                            Sprite coin = new Sprite(coinTexture, TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.COIN);
                            root.getChildren().add(coin.getImage());
                            getWorld().allSprites.add(coin);
                            coin.getImage().setVisible(false);
                        }
                        else {
                            Sprite coinbag = new Sprite(coinBagTexture, TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.COIN_BAG);
                            root.getChildren().add(coinbag.getImage());
                            getWorld().allSprites.add(coinbag);
                            coinbag.getImage().setVisible(false);
                        }
                    }
                        createFloor(map, root, xx, yy);
                        break;

                    case 2: if (rand.nextInt(100) >= 49) {
                        Sprite chest = new Sprite(chestTexture, TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.CHEST);
                        root.getChildren().add(chest.getImage());
                        getWorld().allSprites.add(chest);
                    }

                    case 0:
                        createFloor(map, root, xx, yy);
                        break;

                    default:

                        // Überprüfe, ob die Kachel unten Boden ist
                        if (yy + 1 < map.getTileData().length && (map.getTileData()[yy+1][xx] == 0 || map.getTileData()[yy+1][xx] == 10)) {
                            Sprite wall = new Sprite(wallTexture, TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.TILE);
                            wall.getImage().setCache(true);
                            wall.getImage().setCacheHint(CacheHint.SPEED);
                            root.getChildren().add(wall.getImage());
                            wall.getImage().setEffect(shadow);
                            wall.getImage().setViewOrder(510);
                            board[yy][xx] = wall;

                            Sprite wallTop = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_top_mid.png",
                                    TILE_SIZE, TILE_SIZE,
                                    false, false), TILE_SIZE * xx, TILE_SIZE * yy - TILE_SIZE, CollisionType.TILE);
                            root.getChildren().add(wallTop.getImage());
                            wallTop.getImage().setEffect(shadow);
                            getWorld().allSprites.add(wallTop);

                            if (xx - 1 >= 0 && map.getTileData()[yy+1][xx] == 0 && map.getTileData()[yy+1][xx+1] == 1) {
                                Sprite wallSide = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_mid_right.png",
                                        TILE_SIZE, TILE_SIZE,
                                        false, false), TILE_SIZE * xx + TILE_SIZE, TILE_SIZE * yy, CollisionType.TILE);
                                root.getChildren().add(wallSide.getImage());
                                wallSide.getImage().setEffect(shadow);
                                getWorld().allSprites.add(wallSide);

                                Sprite wallSideCorner = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_top_right.png",
                                        TILE_SIZE, TILE_SIZE,
                                        false, false), TILE_SIZE * xx + TILE_SIZE, TILE_SIZE * yy - TILE_SIZE, CollisionType.NONE);
                                root.getChildren().add(wallSideCorner.getImage());
                                wallSideCorner.getImage().setEffect(shadow);
                                getWorld().allSprites.add(wallSideCorner);
                            }

                            if (xx - 1 >= 0 && map.getTileData()[yy+1][xx] == 0 && map.getTileData()[yy+1][xx-1] == 1) {
                                Sprite wallSide = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_mid_left.png",
                                        TILE_SIZE, TILE_SIZE,
                                        false, false), TILE_SIZE * xx - TILE_SIZE, TILE_SIZE * yy, CollisionType.TILE);
                                root.getChildren().add(wallSide.getImage());
                                wallSide.getImage().setEffect(shadow);
                                getWorld().allSprites.add(wallSide);

                                Sprite wallSideCorner = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_top_left.png",
                                        TILE_SIZE, TILE_SIZE,
                                        false, false), TILE_SIZE * xx - TILE_SIZE, TILE_SIZE * yy - TILE_SIZE, CollisionType.NONE);
                                root.getChildren().add(wallSideCorner.getImage());
                                wallSideCorner.getImage().setEffect(shadow);
                                getWorld().allSprites.add(wallSideCorner);
                            }
                        }

                        // Überprüfe, ob die Kachel oben Boden ist
                        else if (yy - 1 >= 0 && (map.getTileData()[yy-1][xx] == 0 || map.getTileData()[yy-1][xx] == 10)) {
                            Sprite wall = new Sprite(wallTexture, TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.TILE);
                            wall.getImage().setCache(true);
                            wall.getImage().setCacheHint(CacheHint.SPEED);
                            root.getChildren().add(wall.getImage());
                            wall.getImage().setEffect(shadow);
                            wall.getImage().setViewOrder(490);
                            board[yy][xx] = wall;

                            Sprite wallTop = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_top_mid.png",
                                    TILE_SIZE, TILE_SIZE,
                                    false, false), TILE_SIZE * xx, TILE_SIZE * yy - TILE_SIZE, CollisionType.NONE);
                            root.getChildren().add(wallTop.getImage());
                            wallTop.getImage().setEffect(shadow);
                            getWorld().allSprites.add(wallTop);

                            // WANDKAPPEN
                            if (xx + 1 < map.getTileData()[0].length && map.getTileData()[yy-1][xx] == 0 && map.getTileData()[yy-1][xx+1] == 1) {
                                Sprite wallSide = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_front_right.png",
                                        TILE_SIZE, TILE_SIZE,
                                        false, false), TILE_SIZE * xx + TILE_SIZE, TILE_SIZE * yy, CollisionType.NONE);
                                root.getChildren().add(wallSide.getImage());
                                wallSide.getImage().setEffect(shadow);
                                getWorld().allSprites.add(wallSide);
                            }

                            if (xx - 1 >= 0 && map.getTileData()[yy-1][xx] == 0 && map.getTileData()[yy-1][xx-1] == 1) {
                                Sprite wallSide = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_front_left.png",
                                        TILE_SIZE, TILE_SIZE,
                                        false, false), TILE_SIZE * xx - TILE_SIZE, TILE_SIZE * yy, CollisionType.NONE);
                                root.getChildren().add(wallSide.getImage());
                                wallSide.getImage().setEffect(shadow);
                                getWorld().allSprites.add(wallSide);
                            }
                        }

                        else {
                            Sprite empty = new Sprite(EMPTY, TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.NONE);
                            root.getChildren().add(empty.getImage());
                            empty.getImage().setViewOrder(1000);
                            board[yy][xx] = empty;
                        }
                        break;
                }
            }
        }

        if (!ladderSpawned) {
            for (int i = 0; i < map.getTileData()[0].length; i++) {
                for (int j = 0; j < map.getTileData().length; j++) {
                    if (map.getTileData()[i][j] == 10) {
                        createLadder(map, root, i, j);
                        return root;
                    }
                }
            }
        }
        return root;
    }

    private void createFloor(Map map, Pane root, int xx, int yy) {
        Sprite floor = new Sprite(floorTexture,TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.NONE);
        floor.getImage().setCache(true);
        floor.getImage().setCacheHint(CacheHint.SPEED);
        root.getChildren().add(floor.getImage());
        floor.getImage().setEffect(shadow);
        floor.getImage().setViewOrder(900); //Damit der Boden keine anderen Sprites überlagert
        board[yy][xx] = floor;

        // Überprüfe, ob die Kachel links eine Wand ist
        if (map.getTileData()[yy][xx-1] == 1) {
            if (map.getTileData()[yy+1][xx-1] == 1) {
                Sprite wallLeft = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_mid_left.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx - TILE_SIZE, TILE_SIZE * yy, CollisionType.TILE);
                root.getChildren().add(wallLeft.getImage());
                wallLeft.getImage().setEffect(shadow);
                getWorld().allSprites.add(wallLeft);
            }

            else {
                Sprite wallFrontLeft = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_front_left.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx - TILE_SIZE, TILE_SIZE * yy, CollisionType.NONE);
                root.getChildren().add(wallFrontLeft.getImage());
                wallFrontLeft.getImage().setEffect(shadow);
                wallFrontLeft.getImage().setViewOrder(890);
            }
        }

        // Überprüfe, ob die Kachel rechts eine Wand ist
        if (map.getTileData()[yy][xx+1] == 1) {
            if (map.getTileData()[yy+1][xx+1] == 1) {
                Sprite wallRight = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_mid_right.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx + TILE_SIZE, TILE_SIZE * yy, CollisionType.TILE);
                root.getChildren().add(wallRight.getImage());
                wallRight.getImage().setEffect(shadow);
                getWorld().allSprites.add(wallRight);
            }

            else {
                Sprite wallFrontRight = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_front_right.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx + TILE_SIZE, TILE_SIZE * yy, CollisionType.NONE);
                root.getChildren().add(wallFrontRight.getImage());
                wallFrontRight.getImage().setEffect(shadow);
                getWorld().allSprites.add(wallFrontRight);
                wallFrontRight.getImage().setViewOrder(890);
            }
        }
    }

    private void createLadder(Map map, Pane root, int xx, int yy) {
        Sprite ladder = new Sprite(floorTexture,TILE_SIZE * xx, TILE_SIZE * yy, CollisionType.LADDER);
        root.getChildren().add(ladder.getImage());
        ladder.getImage().setEffect(shadow);
        ladder.getImage().setViewOrder(900); //Damit der Boden keine anderen Sprites überlagert
        board[yy][xx] = ladder;

        // Überprüfe, ob die Kachel links eine Wand ist
        if (map.getTileData()[yy][xx-1] == 1) {
            if (map.getTileData()[yy+1][xx-1] == 1) {
                Sprite wallLeft = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_mid_left.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx - TILE_SIZE, TILE_SIZE * yy, CollisionType.TILE);
                root.getChildren().add(wallLeft.getImage());
                wallLeft.getImage().setEffect(shadow);
                getWorld().allSprites.add(wallLeft);
            }

            else {
                Sprite wallFrontLeft = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_front_left.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx - TILE_SIZE, TILE_SIZE * yy, CollisionType.NONE);
                root.getChildren().add(wallFrontLeft.getImage());
                wallFrontLeft.getImage().setEffect(shadow);
                wallFrontLeft.getImage().setViewOrder(890);
            }
        }

        // Überprüfe, ob die Kachel rechts eine Wand ist
        if (map.getTileData()[yy][xx+1] == 1) {
            if (map.getTileData()[yy+1][xx+1] == 1) {
                Sprite wallRight = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_mid_right.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx + TILE_SIZE, TILE_SIZE * yy, CollisionType.TILE);
                root.getChildren().add(wallRight.getImage());
                wallRight.getImage().setEffect(shadow);
                getWorld().allSprites.add(wallRight);
            }

            else {
                Sprite wallFrontRight = new Sprite(new Image("file:src/main/resources/designTiles/mapTiles/wall_side_front_right.png",
                        TILE_SIZE, TILE_SIZE,
                        false, false), TILE_SIZE * xx + TILE_SIZE, TILE_SIZE * yy, CollisionType.NONE);
                root.getChildren().add(wallFrontRight.getImage());
                wallFrontRight.getImage().setEffect(shadow);
                getWorld().allSprites.add(wallFrontRight);
                wallFrontRight.getImage().setViewOrder(890);
            }
        }
    }
}
