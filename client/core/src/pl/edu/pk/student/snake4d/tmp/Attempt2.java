package pl.edu.pk.student.snake4d.tmp;

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

public class Attempt2 extends ApplicationAdapter {
    public PerspectiveCamera cam;
    public PerspectiveCamera portal;
    public CameraInputController camController;
    public Environment environment;
    public ModelBatch modelBatch;


    public Model plane;
    public ModelInstance planeInstance;
    public Model cube;
    public ModelInstance cubeInstance;

    public FrameBuffer frameBuffer;

    @Override
    public void create() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 20f, 0f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 200f;
        cam.update();

        portal = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        portal.position.set(0f, 40f, 0f);
        portal.lookAt(0f, 0f, 0f);
        portal.near = 1f;
        portal.far = 200f;
        portal.update();


        camController = new CameraInputController(cam);
        camController.setInvertedControls(true);
        Gdx.input.setInputProcessor(camController);

        ModelBuilder modelBuilder = new ModelBuilder();
        plane = modelBuilder.createRect(
                -15f, 0f, 15f,
                15f, 0f, 15f,
                15f, 0f, -15f,
                -15f, 0f, -15f,
                0f, 1f, 0f,
                new Material(TextureAttribute.createDiffuse(new Texture("badlogic.jpg"))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );
        planeInstance = new ModelInstance(plane);

        cube = modelBuilder.createBox(
                1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );
        cubeInstance = new ModelInstance(cube);
        // cubeInstance.transform.setTranslation(0, 20, 0);

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
        portal.update();
        camController.update();

        cubeInstance.transform.setTranslation(cam.position.x, cam.position.y, cam.position.z);

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


        modelBatch.begin(portal);
        modelBatch.render(cubeInstance, environment);
        // modelBatch.render(planeInstance, environment);
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
        plane.dispose();
        cube.dispose();
        frameBuffer.dispose();
    }
}
