package pl.edu.pk.student.snake4d.tmp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import pl.edu.pk.student.snake4d.processors.ArcballCameraInputController;

public class Attempt1 extends ApplicationAdapter {
    public PerspectiveCamera cam;
    public PerspectiveCamera portal;
    public PerspectiveCamera preview;
    public ArcballCameraInputController camController;
    public Environment environment;
    public ModelBatch modelBatch;


    public Model plane;
    public ModelInstance planeInstance;
    public Model cube;
    public ModelInstance cubeInstance;

    public Model portalCube;
    public ModelInstance portalCubeInstance;
    public FrameBuffer frameBuffer;
    public Texture texture;
    public TextureRegion textureRegion;
    public SpriteBatch spriteBatch;

    @Override
    public void create() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();
        spriteBatch = new SpriteBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 20f, 0f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 100f;
        cam.update();

        portal = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        portal.position.set(100f, 20f, 0f);
        portal.lookAt(100f, 0f, 0f);
        portal.near = 1f;
        portal.far = 100f;
        portal.update();

        preview = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        preview.position.set(100f, 0f, 50f);
        preview.lookAt(100f, 0f, 0f);
        preview.near = 1f;
        preview.far = 100f;
        preview.update();

        camController = new ArcballCameraInputController(cam);
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
                10f, 10f, 10f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );
        cubeInstance = new ModelInstance(cube);
        cubeInstance.transform.trn(100f, 0f, 0f);

        portalCube = modelBuilder.createBox(
                2f, 2f, 2f,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );
        portalCubeInstance = new ModelInstance(portalCube);
        portalCubeInstance.transform.trn(100f, 20f, 0f);

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

        portal.position.set(cam.position.x + 100f, cam.position.y, cam.position.z);
        portal.lookAt(100f, 0f, 0f);
        portal.update();

        portalCubeInstance.transform.setTranslation(cam.position.x + 100f, cam.position.y, cam.position.z);


        frameBuffer.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        modelBatch.begin(portal);
        modelBatch.render(cubeInstance, environment);
        modelBatch.end();
        frameBuffer.end();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(1f, 1f, 1f, 0f);
        planeInstance.materials.get(0).set(TextureAttribute.createDiffuse(frameBuffer.getColorBufferTexture()));

        camController.update();
        modelBatch.begin(preview);
        modelBatch.render(cubeInstance, environment);
        // modelBatch.render(planeInstance, environment);
        modelBatch.render(portalCubeInstance, environment);
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
        portalCube.dispose();
        frameBuffer.dispose();
    }
}
