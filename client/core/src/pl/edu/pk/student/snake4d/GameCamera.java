package pl.edu.pk.student.snake4d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class GameCamera extends PerspectiveCamera {
    public GameCamera() {
        super(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        position.set(0f, 0f, 0f);
        lookAt(0, 0, 0);
        near = 1f;
        far = 200f;
    }
}
