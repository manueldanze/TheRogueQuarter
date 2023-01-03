package mainpackage.world;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static mainpackage.world.GameWorld.TILE_SIZE;
import static mainpackage.world.GameWorld.rand;

public class Sprite {

    private ImageView image;
    private Point2D position;
    CollisionType collisions;
    private int timeSinceSpawn = 0;

    public ImageView getImage() { return image; }
    public Point2D getPosition() { return position; }


    public void movePosition(double x, double y) {
        position = position.add(x, y);
        refreshPos();
    }
    public int timeSinceSpawn() {
        return timeSinceSpawn;
    }

    public CollisionType getCollisionType() { return collisions; }

    //Nützlich als Zeiteinheit für z.B. Animationen
    public void progressTime() {
        timeSinceSpawn++;
    }

    public boolean collidesWith(Sprite other) {
        boolean result = false;

        return result;
    }

    public void refreshPos() {
        image.setTranslateX(position.getX()-0.5*TILE_SIZE);     // 0.5*TILE_SIZE bewirkt nur, dass der Point2D zentriert im Bild ist
        image.setTranslateY(position.getY()-0.5*TILE_SIZE);
    }

    public void setPos(double x, double y) {
        position = new Point2D(x, y);
        image.setTranslateX(position.getX()-0.5*TILE_SIZE);
        image.setTranslateY(position.getY()-0.5*TILE_SIZE);
    }


    public Sprite(Image img, CollisionType coll) {
        image = new ImageView(img);
        position = new Point2D(rand.nextInt(GameWorld.RES_X - TILE_SIZE), rand.nextInt(GameWorld.RES_Y - TILE_SIZE));
        image.setTranslateX(position.getX());
        image.setTranslateY(position.getY());
        collisions = coll;
    }

    public Sprite(Image img, double posX, double posY, CollisionType coll) {
        image = new ImageView(img);
        collisions = coll;
        position = new Point2D(posX, posY);
        image.setTranslateX(position.getX());
        image.setTranslateY(position.getY());
    }
}
