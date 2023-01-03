package mainpackage.world;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static mainpackage.world.GameWorld.getWorld;

public class FightWindow {

    public FightWindow() {}

    private static final Logger LOG = LogManager.getLogger(FightWindow.class);

    private static Text text;
    private static Text desc;
    private static Stage stage;
    private static Pane root;
    private static BorderPane textPane;
    private static Scene scene;
    private static PauseTransition pause = new PauseTransition(Duration.seconds(2));

    public Stage fightWin() {
        root = new Pane();
        textPane = new BorderPane();
        text = new Text();
        desc = new Text();
        stage = new Stage(StageStyle.UNDECORATED);
        ImageButton btn = new ImageButton("test");
        btn.setTranslateX(0);
        btn.setTranslateY(70);
        root.setPrefSize(1200, 800);
        textPane.setPrefSize(1200, 800);
        BorderPane.setAlignment(desc, Pos.TOP_CENTER);
        textPane.setTop(desc);
        textPane.setCenter(text);
        text.setFill(Color.WHITE);
        desc.setFill(Color.WHITE);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        desc.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        root.getChildren().addAll(textPane);
        scene = new Scene(root);
        scene.setFill(Color.rgb(0, 0, 0, 0.5));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("fight Window");

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        LOG.debug("fight window created");
        return stage;
    }

    public static void reset() {
        root.getChildren().remove(text);
        text.setText("Fight!");
        root.getChildren().add(text);
        pause.setOnFinished(e -> text.setText(""));
        pause.play();
        LOG.debug("fight window reset");
    }

    public void setFightText(String fightText) {
        text.setText(fightText);
        LOG.debug("fight window text reset");
    }
    public void setDesc(String description) {
        desc.setText(description);
        LOG.debug("fight window description changed");
    }

    public void endGame() {
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        Stage end = new Stage();
        ImageView gif = new ImageView(new Image(new File("src/main/resources/endCredits/endCredits.gif").toURI().toString()));
        gif.setFitHeight(700);
        gif.setFitWidth(1200);
        Group rootg = new Group();
        rootg.getChildren().add(gif);
        Scene sceneg = new Scene(rootg, 1200, 800);
        sceneg.setFill(Color.BLACK);
        end.initStyle(StageStyle.UNDECORATED);
        end.initOwner(getWorld().getStage());
        end.setScene(sceneg);
        delay.play();
        end.show();
        sceneg.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                end.close();
            }
        });
    }
}