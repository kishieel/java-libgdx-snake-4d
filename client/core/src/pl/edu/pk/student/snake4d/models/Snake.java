package pl.edu.pk.student.snake4d.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Snake {
    private ModelInstance modelInstance;

    public Snake(Color color) {
        ModelBuilder modelBuilder = new ModelBuilder();

        Material material = new Material(ColorAttribute.createDiffuse(color));
        material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

        Model model = modelBuilder.createBox(
                1f,
                1f,
                3f,
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );

        modelInstance = new ModelInstance(model);
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }
}
