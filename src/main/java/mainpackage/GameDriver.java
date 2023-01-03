package mainpackage;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainpackage.world.ImageButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mainpackage.world.Sound;

public class GameDriver extends Application {

    private static final Logger LOG = LogManager.getLogger(GameDriver.class);

    public GameDriver() {
    }

    @Override
    public void start(Stage primaryStage) {

        Sound.themeSong.play();

        primaryStage.setTitle("The Rogue Quarter");

        ImageButton start = new ImageButton("Start");
        ImageButton quit = new ImageButton("Quit");
        ImageView titleScreen = new ImageView(new Image("file:src/main/resources/screenDesign/screentitle/titleScreen.png", 1200, 600, false, false));

        start.setOnMousePressed(e -> {
            Sound.themeSong.stop();
            Sound.ambient.play();
        });
        quit.setOnMousePressed(mouseEvent -> {
            LOG.info("game ended");
            primaryStage.close();
        });

        Pane background = new Pane();
        BorderPane buttonLayout = new BorderPane();
        VBox buttons = new VBox();
        buttons.getChildren().addAll(start, quit);
        buttonLayout.setPrefSize(1200, 450);
        buttonLayout.setBottom(buttons);
        background.getChildren().addAll(titleScreen, buttonLayout);
        buttons.setAlignment(Pos.CENTER);

        Scene scene = new Scene(background,1200,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
