package pl.edu.pk.student.snake4d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import pl.edu.pk.student.snake4d.interfaces.Updatable;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public PerspectiveCamera cam;
    public Environment environment;
    public ModelBatch modelBatch;
    private List<Updatable> updatables = new ArrayList<>();
    private List<RenderableProvider> renderables = new ArrayList<>();
    private List<Disposable> disposables = new ArrayList<>();

    ModelInstance modelInstance;

    public GameManager() {
        environment = new GameEnvironment();

        modelBatch = new ModelBatch();
        disposables.add(modelBatch);

        cam = new GameCamera();
        cam.update();

        spawnCubes();
    }

    public void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);

        modelBatch.begin(cam);
        for (RenderableProvider renderable : renderables) {
            modelBatch.render(renderable, environment);
        }
        modelBatch.end();
    }

    public void dispose() {
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }

    private void spawnCubes() {
        Vector3[] positions = new Vector3[] {
                new Vector3(20f, 0f, 0f),
                new Vector3(-20f, 0f, 0f),
                new Vector3(0f, 20f, 0f),
                new Vector3(0f, -20f, 0f),
                new Vector3(0f, 0f, 20f),
                new Vector3(0f, 0f, -20f),
        };

        for (Vector3 position : positions) {
            ModelBuilder modelBuilder = new ModelBuilder();
            Model cube = modelBuilder.createBox(
                    1f, 1f, 1f,
                    new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
            );
            ModelInstance cubeInstance = new ModelInstance(cube);
            cubeInstance.transform.setTranslation(position);

            renderables.add(cubeInstance);
            disposables.add(cube);
        }
    }
}
