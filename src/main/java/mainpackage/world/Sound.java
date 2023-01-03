package mainpackage.world;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.util.List;


public class Sound {

//Songs
    public static final MediaPlayer themeSong = new MediaPlayer(new Media(new File("src/main/resources/sounds/theme.mp3").toURI().toString()));
    public static final MediaPlayer deathSong = new MediaPlayer(new Media(new File("src/main/resources/sounds/outro.mp3").toURI().toString()));
    public static final MediaPlayer ambient = new MediaPlayer(new Media(new File("src/main/resources/sounds/ambient1.wav").toURI().toString()));
    public static final MediaPlayer lavenderTownTheme = new MediaPlayer(new Media(new File("src/main/resources/sounds/lavenderTownTheme.mp3").toURI().toString()));

    public static final MediaPlayer fightSong1;
    public static final MediaPlayer fightSong2;
    public static final MediaPlayer fightSong3;

//Effects
    public static final AudioClip scream = new AudioClip(new File("src/main/resources/sounds/misc/Wilhelm Scream.mp3").toURI().toString());
    public static final AudioClip moveCursor = new AudioClip(new File("src/main/resources/sounds/items/moveCurser.mp3").toURI().toString());
    public static final AudioClip drink = new AudioClip(new File("src/main/resources/sounds/items/water2.wav").toURI().toString());
    public static final AudioClip heal = new AudioClip(new File("src/main/resources/sounds/items/health1.wav").toURI().toString());
    public static final AudioClip equip = new AudioClip(new File("src/main/resources/sounds/items/armor1.wav").toURI().toString());
    public static final AudioClip pickup = new AudioClip(new File("src/main/resources/sounds/misc/menu1.wav").toURI().toString());
    public static final AudioClip talk = new AudioClip(new File("src/main/resources/sounds/misc/talk.wav").toURI().toString());
    public static final AudioClip cash = new AudioClip(new File("src/main/resources/sounds/items/cash.mp3").toURI().toString());
    public static final AudioClip error = new AudioClip(new File("src/main/resources/sounds/items/error.mp3").toURI().toString());
    public static final AudioClip click = new AudioClip(new File("src/main/resources/sounds/misc/click.mp3").toURI().toString());
    public static final AudioClip collectible = new AudioClip("file:src/main/resources/sounds/items/am_pkup.wav");
    public static final AudioClip victory = new AudioClip(new File("src/main/resources/sounds/misc/victory.mp3").toURI().toString());
    public static final AudioClip coin = new AudioClip(new File("src/main/resources/sounds/misc/beep.mp3").toURI().toString());
    public static final AudioClip start = new AudioClip(new File("src/main/resources/sounds/misc/start.mp3").toURI().toString());

    public static final List<AudioClip> changeLevelSound = List.of(
            new AudioClip(new File("src/main/resources/sounds/misc/r_tele1.wav").toURI().toString()),
            new AudioClip(new File("src/main/resources/sounds/misc/r_tele2.wav").toURI().toString()),
            new AudioClip(new File("src/main/resources/sounds/misc/r_tele3.wav").toURI().toString()),
            new AudioClip(new File("src/main/resources/sounds/misc/r_tele4.wav").toURI().toString()),
            new AudioClip(new File("src/main/resources/sounds/misc/r_tele5.wav").toURI().toString())
    );


    static {

        themeSong.setVolume(0.5);
        themeSong.setCycleCount(MediaPlayer.INDEFINITE);
        themeSong.setStartTime(Duration.seconds(1.32));

        deathSong.setVolume(0.5);

        ambient.setVolume(1.5);
        ambient.setCycleCount(MediaPlayer.INDEFINITE);

        lavenderTownTheme.setVolume(0.05);
        lavenderTownTheme.setCycleCount(MediaPlayer.INDEFINITE);

        fightSong1 = new MediaPlayer(new Media(new File("src/main/resources/sounds/fight1.mp3").toURI().toString()));
        fightSong1.setCycleCount(MediaPlayer.INDEFINITE);
        fightSong1.setVolume(0.2);

        fightSong2 = new MediaPlayer(new Media(new File("src/main/resources/sounds/fight2.mp3").toURI().toString()));
        fightSong2.setCycleCount(MediaPlayer.INDEFINITE);
        fightSong2.setVolume(1.5);

        fightSong3 = new MediaPlayer(new Media(new File("src/main/resources/sounds/fight3.mp3").toURI().toString()));
        fightSong3.setCycleCount(MediaPlayer.INDEFINITE);
    }
}
