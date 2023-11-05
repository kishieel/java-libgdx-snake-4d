package pl.edu.pk.student.snake4d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;

public class SnakeGame extends ApplicationAdapter {
    public PerspectiveCamera cam;
    public ArcballCameraInputController camController;
    public Environment env1;
    public Environment env2;
    public Environment env3;
    public Model cube1;
    public Model cube2;
    public Model cube3;
    public ModelInstance cubeInstance1;
    public ModelInstance cubeInstance2;
    public ModelInstance cubeInstance3;
    public ModelBatch modelBatch;
    public FrameBuffer frameBuffer;

    @Override
    public void create() {
        env1 = new Environment();
        env1.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env1.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        env2 = new Environment();
        env2.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env2.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        env3 = new Environment();
        env3.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env3.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(2f, 2f, 2f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 200f;
        cam.update();

        camController = new ArcballCameraInputController(cam);
        // camController.setInvertedControls(true);
        Gdx.input.setInputProcessor(camController);

        ModelBuilder modelBuilder = new ModelBuilder();

        cube1 = modelBuilder.createBox(
                1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );
        cubeInstance1 = new ModelInstance(cube1);

        cube2 = modelBuilder.createBox(
                1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(new Color(0, 0, 1, 0.5f))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );
        cubeInstance2 = new ModelInstance(cube2);
        cubeInstance2.transform.setTranslation(-0.25f, 0.5f, -0.25f);

        cube3 = modelBuilder.createBox(
                1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.GOLD)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );
        cubeInstance3 = new ModelInstance(cube3);
        cubeInstance3.transform.setTranslation(0.25f, 0.25f, 0.75f);


        GLFrameBuffer.FrameBufferBuilder frameBufferBuilder = new GLFrameBuffer.FrameBufferBuilder(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        frameBufferBuilder.addBasicColorTextureAttachment(Pixmap.Format.RGB888);
        frameBufferBuilder.addDepthRenderBuffer(GL30.GL_DEPTH_COMPONENT24);
        frameBuffer = frameBufferBuilder.build();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        camController.update();

        // frameBuffer.begin();
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        // Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        // modelBatch.begin(portal);
        // modelBatch.render(cubeInstance, environment);
        // modelBatch.end();
        // frameBuffer.end();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        // planeInstance.materials.get(0).set(TextureAttribute.createDiffuse(frameBuffer.getColorBufferTexture()));


        modelBatch.begin(cam);
        modelBatch.render(cubeInstance1, env1);
        modelBatch.render(cubeInstance2, env1);
        modelBatch.render(cubeInstance3, env1);
        modelBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        cube1.dispose();
        cube2.dispose();
        cube3.dispose();
        frameBuffer.dispose();
    }
}
