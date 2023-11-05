package pl.edu.pk.student.snake4d.tmp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.*;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardRenderer;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Particles extends ApplicationAdapter {
    public AssetManager assets;

    public PerspectiveCamera cam;
    public CameraInputController inputController;
    public ModelBatch modelBatch;
    public Model axesModel;
    public ModelInstance axesInstance;
    public boolean showAxes = true;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public final Color bgColor = new Color(0, 0, 0, 1);
    public static final String DEFAULT_PARTICLE = "pre_particle.png", DEFAULT_SKIN = "uiskin.json";
    Quaternion tmpQuaternion = new Quaternion();
    Matrix4 tmpMatrix = new Matrix4(), tmpMatrix4 = new Matrix4();
    Vector3 tmpVector = new Vector3();
    private class RotationAction extends Action {
        private ParticleController emitter;
        Vector3 axis;
        float angle;

        public RotationAction (ParticleController emitter, Vector3 axis, float angle) {
            this.emitter = emitter;
            this.axis = axis;
            this.angle = angle;
        }

        @Override
        public boolean act (float delta) {
            emitter.getTransform(tmpMatrix);
            tmpQuaternion.set(axis, angle * delta).toMatrix(tmpMatrix4.val);
            tmpMatrix4.mul(tmpMatrix);
            emitter.setTransform(tmpMatrix4);
            return false;
        }
    }

    // Simulation
    Array<ParticleController> emitters;

    // Rendering
    Environment environment;
    BillboardParticleBatch billboardParticleBatch;

    // UI
    Stage ui;
    Label fpsLabel;
    StringBuilder builder;


    @Override
    public void create () {
        if (assets == null) assets = new AssetManager();

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 1000f;
        cam.update();

        createAxes();

        Gdx.input.setInputProcessor(inputController = new CameraInputController(cam));

        emitters = new Array<ParticleController>();
        assets.load(DEFAULT_PARTICLE, Texture.class);
        assets.load(DEFAULT_SKIN, Skin.class);
        loading = true;
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0f, 0f, 0.1f, 1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, 0, -0.5f, -1));
        billboardParticleBatch = new BillboardParticleBatch();
        billboardParticleBatch.setCamera(cam);
        ui = new Stage();
        builder = new StringBuilder();
    }

    final float GRID_MIN = -10f;
    final float GRID_MAX = 10f;
    final float GRID_STEP = 1f;

    private void createAxes () {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("grid", GL20.GL_LINES, VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked, new Material());
        builder.setColor(Color.LIGHT_GRAY);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            builder.line(t, 0, GRID_MIN, t, 0, GRID_MAX);
            builder.line(GRID_MIN, 0, t, GRID_MAX, 0, t);
        }
        builder = modelBuilder.part("axes", GL20.GL_LINES, VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked, new Material());
        builder.setColor(Color.RED);
        builder.line(0, 0, 0, 100, 0, 0);
        builder.setColor(Color.GREEN);
        builder.line(0, 0, 0, 0, 100, 0);
        builder.setColor(Color.BLUE);
        builder.line(0, 0, 0, 0, 0, 100);
        axesModel = modelBuilder.end();
        axesInstance = new ModelInstance(axesModel);
    }

    protected void render (final ModelBatch batch, final Array<ModelInstance> instances) {
        if (emitters.size > 0) {
            // Update
            float delta = Gdx.graphics.getDeltaTime();
            builder.delete(0, builder.length());
            builder.append(Gdx.graphics.getFramesPerSecond());
            fpsLabel.setText(builder);
            ui.act(delta);

            billboardParticleBatch.begin();
            for (ParticleController controller : emitters) {
                controller.update();
                controller.draw();
            }
            billboardParticleBatch.end();
            batch.render(billboardParticleBatch, environment);
        }
        batch.render(instances, environment);
        ui.draw();
    }

    protected boolean loading = false;

    protected void onLoaded () {
        Texture particleTexture = assets.get(DEFAULT_PARTICLE);
        billboardParticleBatch.setTexture(assets.get(DEFAULT_PARTICLE, Texture.class));

        // X
        addEmitter(new float[] {1, 0.12156863f, 0.047058824f}, particleTexture, tmpVector.set(5, 5, 0), Vector3.X, 360);

        // Y
        addEmitter(new float[] {0.12156863f, 1, 0.047058824f}, particleTexture, tmpVector.set(0, 5, -5), Vector3.Y, -360);

        // Z
        addEmitter(new float[] {0.12156863f, 0.047058824f, 1}, particleTexture, tmpVector.set(0, 5, 5), Vector3.Z, -360);

        setupUI();
    }

    private void addEmitter (float[] colors, Texture particleTexture, Vector3 translation, Vector3 actionAxis,
                             float actionRotation) {
        ParticleController controller = createBillboardController(colors, particleTexture);
        controller.init();
        controller.start();
        emitters.add(controller);
        controller.translate(translation);
        ui.addAction(new RotationAction(controller, actionAxis, actionRotation));
    }

    private void setupUI () {
        Skin skin = assets.get(DEFAULT_SKIN);
        Table table = new Table();
        table.setFillParent(true);
        table.top().left().add(new Label("FPS ", skin)).left();
        table.add(fpsLabel = new Label("", skin)).left().expandX().row();
        ui.addActor(table);
    }

    private ParticleController createBillboardController (float[] colors, Texture particleTexture) {
        // Emission
        RegularEmitter emitter = new RegularEmitter();
        emitter.getDuration().setLow(3000);
        emitter.getEmission().setHigh(2900);
        emitter.getLife().setHigh(1000);
        emitter.setMaxParticleCount(3000);

        // Spawn
        PointSpawnShapeValue pointSpawnShapeValue = new PointSpawnShapeValue();
        pointSpawnShapeValue.xOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.xOffsetValue.setActive(true);
        pointSpawnShapeValue.yOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.yOffsetValue.setActive(true);
        pointSpawnShapeValue.zOffsetValue.setLow(0, 1f);
        pointSpawnShapeValue.zOffsetValue.setActive(true);
        SpawnInfluencer spawnSource = new SpawnInfluencer(pointSpawnShapeValue);

        // Scale
        ScaleInfluencer scaleInfluencer = new ScaleInfluencer();
        scaleInfluencer.value.setTimeline(new float[] {0, 1});
        scaleInfluencer.value.setScaling(new float[] {1, 0});
        scaleInfluencer.value.setLow(0);
        scaleInfluencer.value.setHigh(1);

        // Color
        ColorInfluencer.Single colorInfluencer = new ColorInfluencer.Single();
        colorInfluencer.colorValue.setColors(new float[] {colors[0], colors[1], colors[2], 0, 0, 0});
        colorInfluencer.colorValue.setTimeline(new float[] {0, 1});
        colorInfluencer.alphaValue.setHigh(1);
        colorInfluencer.alphaValue.setTimeline(new float[] {0, 0.5f, 0.8f, 1});
        colorInfluencer.alphaValue.setScaling(new float[] {0, 0.15f, 0.5f, 0});

        // Dynamics
        DynamicsInfluencer dynamicsInfluencer = new DynamicsInfluencer();
        DynamicsModifier.BrownianAcceleration modifier = new DynamicsModifier.BrownianAcceleration();
        modifier.strengthValue.setTimeline(new float[] {0, 1});
        modifier.strengthValue.setScaling(new float[] {0, 1});
        modifier.strengthValue.setHigh(80);
        modifier.strengthValue.setLow(1, 5);
        dynamicsInfluencer.velocities.add(modifier);

        return new ParticleController("Billboard Controller", emitter, new BillboardRenderer(billboardParticleBatch),
                                      new RegionInfluencer.Single(particleTexture), spawnSource, scaleInfluencer, colorInfluencer, dynamicsInfluencer);
    }


    public void render (final Array<ModelInstance> instances) {
        modelBatch.begin(cam);
        if (showAxes) modelBatch.render(axesInstance);
        if (instances != null) render(modelBatch, instances);
        modelBatch.end();
    }

    @Override
    public void render () {
        if (loading && assets.update(16)) {
            loading = false;
            onLoaded();
        }

        inputController.update();

        ScreenUtils.clear(bgColor, true);

        render(instances);
    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        assets.dispose();
        assets = null;
        axesModel.dispose();
        axesModel = null;
    }
}

// public class SnakeGame extends ApplicationAdapter {
//     public GameManager gameManager;
//
//     @Override
//     public void create() {
//         gameManager = new GameManager();
//     }
//
//     @Override
//     public void render() {
//         gameManager.update();
//         gameManager.render();
//     }
//
//     @Override
//     public void dispose() {
//         gameManager.dispose();
//     }
// }
