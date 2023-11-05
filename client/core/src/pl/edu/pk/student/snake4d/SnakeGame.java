package pl.edu.pk.student.snake4d;

import com.badlogic.gdx.ApplicationAdapter;

public class SnakeGame extends ApplicationAdapter {
    public GameManager gameManager;

    @Override
    public void create() {
        gameManager = new GameManager();
    }

    @Override
    public void render() {
        gameManager.update();
        gameManager.render();
    }

    @Override
    public void dispose() {
        gameManager.dispose();
    }
}
