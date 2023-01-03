package mainpackage.world;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mainpackage.world.GameWorld.getWorld;

public class ImageButton extends Parent {

    private static final Logger LOG = LogManager.getLogger(ImageButton.class);

    private static int height = 75;
    private static int width = 158;

    private static final Image START_BUTTON = new Image("file:src/main/resources/screenDesign/buttons/newGameButton.png", width, height, false, false);
    private static final Image PRESSED_START_BUTTON = new Image("file:src/main/resources/screenDesign/buttons/pressedNewGameButton.png", width, height, false, false);

    private static final Image QUIT_BUTTON = new Image("file:src/main/resources/screenDesign/buttons/pressedQuitButton.png", width, height, false, false);
    private static final Image PRESSED_QUIT_BUTTON = new Image("file:src/main/resources/screenDesign/buttons/quitButton.png", width, height, false, false);


    private ImageView iv;

    public ImageButton(String type) {
        if(type.equals("Start")){
            this.iv = new ImageView(START_BUTTON);
            this.getChildren().add(this.iv);
            this.iv.setOnMouseEntered(mouseEvent -> {
                iv.setImage(PRESSED_START_BUTTON);
            });
            this.iv.setOnMouseExited(mouseEvent -> {
                iv.setImage(START_BUTTON);
            });
            this.iv.setOnMousePressed(mouseEvent -> {
                Sound.start.play();
                iv.setImage(PRESSED_START_BUTTON);
                getWorld().display(new Stage(StageStyle.UNDECORATED));
                LOG.info("Game started");
            });
            this.iv.setOnMouseReleased(mouseEvent -> {
                iv.setImage(START_BUTTON);
            });
        }
        if(type.equals("Quit")) {
            this.iv = new ImageView(QUIT_BUTTON);
            this.getChildren().add(this.iv);

            this.iv.setOnMouseEntered(mouseEvent -> {
                iv.setImage(PRESSED_QUIT_BUTTON);
            });
            this.iv.setOnMouseExited(mouseEvent -> {
                iv.setImage(QUIT_BUTTON);
            });
            this.iv.setOnMousePressed(mouseEvent -> {
                iv.setImage(PRESSED_QUIT_BUTTON);
            });
            this.iv.setOnMouseReleased(mouseEvent -> {
                iv.setImage(QUIT_BUTTON);
            });
        }
        LOG.debug("ImageButton Created");
    }
}
