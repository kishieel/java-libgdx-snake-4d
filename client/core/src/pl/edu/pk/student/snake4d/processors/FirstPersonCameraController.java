package pl.edu.pk.student.snake4d.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import pl.edu.pk.student.snake4d.interfaces.Updatable;

public class FirstPersonCameraController extends InputAdapter implements Updatable {
    private final PerspectiveCamera cam;
    private final Float speedOfRotation = 360f;

    private FirstPersonCameraController(PerspectiveCamera cam) {
        this.cam = cam;
    }


    @Override
    public void update() {

    }
}
