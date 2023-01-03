package mainpackage.world;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mainpackage.*;
import mainpackage.characterSystem.CPUCharacter;
import mainpackage.characterSystem.PlayerCharacter;
import mainpackage.characterSystem.npcCollection.HostileCharacterCollection;
import mainpackage.itemSystem.Item;
import mainpackage.itemSystem.itemColletion.ItemCollection;
import mainpackage.itemSystem.itemColletion.PotionCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld {

    private GameWorld() {}

    private static final Logger LOG = LogManager.getLogger(GameWorld.class);
    public static final int TILE_SIZE = 20;
    public static final int WIDTH = 60, HEIGHT = 40;
    public final Map FIRST_MAP = Map.values()[rand.nextInt(Map.values().length)];
    private Map currentMap = FIRST_MAP;
    public static final int RES_X = WIDTH * TILE_SIZE;
    public static final int RES_Y = HEIGHT * TILE_SIZE;
    private int mapLvlCount = 1;
    public static final Random rand = new Random();
    private static final MapGenerator mapGen = new MapGenerator();

    private static final GameWorld world = new GameWorld();
    private static GameWorld curWorld = world;
    private PlayerCharacter playerCharacter = new PlayerCharacter(true);
    public final HUD hud = new HUD();
    public final FightWindow fightWin = new FightWindow();
    public final List<CPUCharacter> aiPlayers = new CopyOnWriteArrayList<>();
    public final List<Item> items = new CopyOnWriteArrayList<>();
    public final List<Sprite> allSprites = new CopyOnWriteArrayList<>();

    private Pane field;
    private Stage fight;
    private Scene scene;
    private Stage stage;
    private GameLoop loop;
    private Stage inv;
    private Stage stats;

    public Stage getStage() {
        return stage;
    }

    public PlayerCharacter playerInstance() { return playerCharacter; }

    public int getCompletedMapCount() { return mapLvlCount; }

    public GameLoop getLoop() { return loop; }

    public static GameWorld getWorld() { return curWorld; }

    private static final EventHandler<KeyEvent> spawnEnemyTest = (e) -> {
        if (e.getCode() == KeyCode.R && Settings.devMode) {
            getWorld().spawnEnemy(HostileCharacterCollection.values()[rand.nextInt(HostileCharacterCollection.values().length)]);
            e.consume();
            LOG.debug("spawned Enemy");
        }

        if (e.getCode() == KeyCode.T && Settings.devMode) {
            getWorld().changeLevel();
            e.consume();
        }
    };

    private static final EventHandler<KeyEvent> spawnItemTest = (e) -> {
        if (e.getCode() == KeyCode.I && Settings.devMode) {
            getWorld().spawnItem(ItemCollection.values()[rand.nextInt(ItemCollection.values().length)], rand.nextInt(RES_X-TILE_SIZE), rand.nextInt(RES_Y-TILE_SIZE));
            e.consume();
        }
    };

    public void showFightWindow() {
        fight.show();
    }

    public void closeFightWindow() {
        fight.close();
    }

    private void spawnPlayer() {
        field.getChildren().add(playerCharacter.getSprite().getImage());
        playerCharacter.getSprite().refreshPos();
        LOG.debug("spawned playerCharacter");
    }

    private void loadLevel(Map nextMap) {
        field = mapGen.createContent(nextMap);
        scene = new Scene(field, RES_X, RES_Y);

        for (Sprite[] row : mapGen.getBoard()) {
            for (Sprite cell : row) {
                if (cell.getImage().getImage().equals(MapGenerator.EMPTY))
                    continue;

                allSprites.add(cell);
            }
        }

        spawnPlayer();
        playerCharacter.getSprite().setPos(nextMap.startX, nextMap.startY);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                unload();
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, playerCharacter.controlsPressed);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, playerCharacter.controlsReleased);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, spawnEnemyTest);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, spawnItemTest);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, HUD.selectItem);
        LOG.debug("Loaded level on map "+currentMap.name());
    }

    public void changeLevel() {
        Map nextMap;

        playerCharacter.resetSkulls();
        mapLvlCount++;
        System.out.println(mapLvlCount);
        aiPlayers.clear();
        items.clear();
        allSprites.clear();

        do {
            nextMap = Map.values()[rand.nextInt(Map.values().length)];
        } while (nextMap == currentMap);

        loadLevel(nextMap);
        currentMap = nextMap;
        if(nextMap.equals(Map.LAVENDER_TOWN)){
            Sound.lavenderTownTheme.play();
        }else{
            Sound.lavenderTownTheme.stop();
        }

        Sound.changeLevelSound.get(rand.nextInt( 5-1)+1).play();

        stage.setScene(scene);
        LOG.debug("changed level");

        HUD.switchText("Entered Level " + mapLvlCount+ ". The enemies have gotten fiercer!");
    }

    private void spawnEnemy(HostileCharacterCollection hostile) {

        CPUCharacter enemy = new CPUCharacter(hostile);

        aiPlayers.add(enemy);
        allSprites.add(enemy.getSprite());
        Platform.runLater(() -> field.getChildren().add(enemy.getSprite().getImage()));
        enemy.getSprite().refreshPos();
    }

    public void spawnEnemy(HostileCharacterCollection hostile, int posX, int posY) {

        CPUCharacter enemy = new CPUCharacter(hostile, posX, posY);

        aiPlayers.add(enemy);
        allSprites.add(enemy.getSprite());
        Platform.runLater(() -> field.getChildren().add(enemy.getSprite().getImage()));
        enemy.getSprite().getImage().setVisible(false);
        enemy.getSprite().refreshPos();

        LOG.debug("Enemy spawned at X: "+posX+" Y: "+posY);
    }

    public void killEnemy(CPUCharacter hostile) {
        aiPlayers.remove(hostile);
        allSprites.remove(hostile.getSprite());
        removeSprite(hostile.getSprite());
    }

    public void spawnItem(ItemCollection choice, double posX, double posY) {

        Item item = new Item(choice, posX, posY);
        items.add(item);
        allSprites.add(item.getSprite());
        Platform.runLater(() -> field.getChildren().add(item.getSprite().getImage()));
        item.getSprite().refreshPos();
        LOG.debug("Item spawned at X: "+posX+" Y: "+posY);
    }
    public void spawnItem(PotionCollection choice, double posX, double posY) {
        Item item = new Item(choice, posX, posY);
        items.add(item);
        allSprites.add(item.getSprite());
        Platform.runLater(() -> field.getChildren().add(item.getSprite().getImage()));
        item.getSprite().refreshPos();
        LOG.debug("Potion spawned at X:" +posX+" Y: "+posY);
    }

    public void removeSprite(Sprite spr) {
        Platform.runLater(() -> field.getChildren().remove(spr.getImage()));
        allSprites.remove(spr);
    }


    /**
     * Closes the entire game session
     */
    private void unload() {
        loop.running = false;

        for (Sprite item : allSprites) {
            item.getImage().setCache(false);
        }

        mapLvlCount = 1;
        aiPlayers.clear();
        items.clear();
        allSprites.clear();

        inv.close();
        stats.close();
        stage.close();

        LOG.debug("unloaded level");
        Sound.deathSong.stop();
        Sound.lavenderTownTheme.stop();
        Sound.ambient.stop();
        Sound.themeSong.play();
    }

//GameWorld creation

    public void display(Stage st) {
        stage = st;

        loop = new GameLoop();
        loop.setDaemon(true);
        playerCharacter = new PlayerCharacter();
        fight = fightWin.fightWin();
        fight.initOwner(stage);
        inv = hud.inventory();
        inv.initOwner(stage);
        stats = hud.stats();
        stats.initOwner(stage);

        loadLevel(Map.values()[rand.nextInt(Map.values().length)]);

        stage.setResizable(false);
        stage.setTitle("The Rogue Quarter");
        stage.setScene(scene);
        stage.show();
        inv.show();
        stats.show();
        stage.requestFocus();
        LOG.info("game started");

        loop.start();
    }


    public static class GameLoop extends Thread {

        private GameLoop() {}


        //Dient dazu, den Thread automatisch abbrechen zu lassen, weil stop() angeblich eine schlechte Idee sein soll.
        private boolean running = true;

        public void continueGame() {
            running = true;
            LOG.info("Game continued");
        }

        // Used for dealer NPC
        public void continueGame(boolean force) {
            if (force) getWorld().loop = new GameLoop();
            running = true;
            getWorld().loop.start();
            LOG.info("Game continued (forced)");
        }

        public void pause() {
            running = false;
            LOG.info("game paused");
        }

        @Override
        public void run() {
            while (running) {

                getWorld().playerCharacter.simulate();

                for (CPUCharacter aiChar : getWorld().aiPlayers) {
                        aiChar.simulate();
                }

                try { Thread.sleep(50); }
                catch (InterruptedException e) {
                    LOG.error("Interruption Error");
                }
            }
            LOG.debug("Game loop running");
        }
    }
}
