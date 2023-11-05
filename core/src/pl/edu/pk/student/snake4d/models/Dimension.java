package pl.edu.pk.student.snake4d.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Dimension {
    private final ModelInstance modelInstance;

    public Dimension(Color color, Vector3 size) {
        ModelBuilder modelBuilder = new ModelBuilder();

        Material material = new Material(ColorAttribute.createDiffuse(color));
        material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

        Model model = modelBuilder.createBox(
                size.x,
                size.y,
                size.z,
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );

        modelInstance = new ModelInstance(model);
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public void update() {
        modelInstance.transform.scale(0.99f, 0.99f, 0.99f);
    }
}
