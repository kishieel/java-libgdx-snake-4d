package pl.edu.pk.student.snake4d.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class ArcballCameraInputController extends InputAdapter {
    private final PerspectiveCamera cam;
    private final Float speedOfRotation = 360f;
    private final Float speedOfZoom = 1.0f;
    public Float translateUnits = 10f;
    public Float scrollFactor = -0.1f;
    public Boolean alwaysScroll = true;
    public Float maximalZoomIn = 5f; //15f;
    public Float maximalZoomOut = 25f;
    private Boolean rotateLeftPressed = false;
    private Boolean rotateRightPressed = false;
    private Boolean rotateUpPressed = false;
    private Boolean rotateDownPressed = false;

    public ArcballCameraInputController(PerspectiveCamera cam) {
        this.cam = cam;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) rotateUpPressed = true;
        else if (keycode == Input.Keys.S) rotateDownPressed = true;
        else if (keycode == Input.Keys.A) rotateLeftPressed = true;
        else if (keycode == Input.Keys.D) rotateRightPressed = true;

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.W) rotateUpPressed = false;
        else if (keycode == Input.Keys.S) rotateDownPressed = false;
        else if (keycode == Input.Keys.A) rotateLeftPressed = false;
        else if (keycode == Input.Keys.D) rotateRightPressed = false;

        return super.keyUp(keycode);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return zoom(amountY * scrollFactor * translateUnits);
    }

    public boolean zoom(float amount) {
        if (!alwaysScroll) return false;

        final float distance = cam.position.dst(Vector3.Zero);
        if (amount > 0 && distance <= maximalZoomIn) return false;
        if (amount < 0 && distance >= maximalZoomOut) return false;

        Vector3 tmp = new Vector3().set(cam.direction).scl(amount);
        cam.translate(tmp);
        cam.update();

        return true;
    }

    public void update() {
        if (rotateLeftPressed || rotateRightPressed || rotateUpPressed || rotateDownPressed) {
            final float delta = Gdx.graphics.getDeltaTime();

            Vector3 leftRightAxis = cam.up;
            Vector3 upDownAxis = new Vector3(cam.position).sub(Vector3.Zero).crs(cam.up);

            if (rotateRightPressed) cam.rotateAround(Vector3.Zero, leftRightAxis, speedOfRotation * delta);
            if (rotateLeftPressed) cam.rotateAround(Vector3.Zero, leftRightAxis, -speedOfRotation * delta);
            if (rotateUpPressed) cam.rotateAround(Vector3.Zero, upDownAxis, speedOfRotation * delta);
            if (rotateDownPressed) cam.rotateAround(Vector3.Zero, upDownAxis, -speedOfRotation * delta);
            cam.lookAt(Vector3.Zero);
            cam.update();
        }
    }
}
