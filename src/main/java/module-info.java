module SE2StartupProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires org.apache.logging.log4j;

    opens mainpackage;
    opens mainpackage.world;
    opens mainpackage.characterSystem;
    opens mainpackage.characterSystem.npcCollection;
    opens mainpackage.itemSystem;
    opens mainpackage.itemSystem.itemColletion;
}
