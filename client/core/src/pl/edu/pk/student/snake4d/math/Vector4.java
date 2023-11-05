package pl.edu.pk.student.snake4d.math;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

public class Vector4 implements Vector<Vector4> {
    public final static Vector4 X = new Vector4(1, 0, 0, 0);
    public final static Vector4 Y = new Vector4(0, 1, 0, 0);
    public final static Vector4 Z = new Vector4(0, 0, 1, 0);
    public final static Vector4 W = new Vector4(0, 0, 0, 1);
    public final static Vector4 Zero = new Vector4(0, 0, 0, 0);
    public float x;
    public float y;
    public float z;
    public float w;


    public Vector4() {
    }

    public Vector4(float x, float y, float z, float w) {
        this.set(x, y, z, w);
    }

    public Vector4(final Vector4 vector) {
        this.set(vector);
    }

    public Vector4(final float[] values) {
        this.set(values[0], values[1], values[2], values[3]);
    }

    @Override
    public Vector4 cpy() {
        return null;
    }

    @Override
    public float len() {
        return 0;
    }

    @Override
    public float len2() {
        return 0;
    }

    @Override
    public Vector4 limit(float limit) {
        return null;
    }

    @Override
    public Vector4 limit2(float limit2) {
        return null;
    }

    @Override
    public Vector4 setLength(float len) {
        return null;
    }

    @Override
    public Vector4 setLength2(float len2) {
        return null;
    }

    @Override
    public Vector4 clamp(float min, float max) {
        return null;
    }

    public Vector4 set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4 set(Vector4 v) {
        return null;
    }

    @Override
    public Vector4 sub(Vector4 v) {
        return null;
    }

    @Override
    public Vector4 nor() {
        return null;
    }

    @Override
    public Vector4 add(Vector4 v) {
        return null;
    }

    @Override
    public float dot(Vector4 v) {
        return 0;
    }

    @Override
    public Vector4 scl(float scalar) {
        return null;
    }

    @Override
    public Vector4 scl(Vector4 v) {
        return null;
    }

    @Override
    public float dst(Vector4 v) {
        return 0;
    }

    @Override
    public float dst2(Vector4 v) {
        return 0;
    }

    @Override
    public Vector4 lerp(Vector4 target, float alpha) {
        return null;
    }

    @Override
    public Vector4 interpolate(Vector4 target, float alpha, Interpolation interpolator) {
        return null;
    }

    @Override
    public Vector4 setToRandomDirection() {
        return null;
    }

    @Override
    public boolean isUnit() {
        return false;
    }

    @Override
    public boolean isUnit(float margin) {
        return false;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean isZero(float margin) {
        return false;
    }

    @Override
    public boolean isOnLine(Vector4 other, float epsilon) {
        return false;
    }

    @Override
    public boolean isOnLine(Vector4 other) {
        return false;
    }

    @Override
    public boolean isCollinear(Vector4 other, float epsilon) {
        return false;
    }

    @Override
    public boolean isCollinear(Vector4 other) {
        return false;
    }

    @Override
    public boolean isCollinearOpposite(Vector4 other, float epsilon) {
        return false;
    }

    @Override
    public boolean isCollinearOpposite(Vector4 other) {
        return false;
    }

    @Override
    public boolean isPerpendicular(Vector4 other) {
        return false;
    }

    @Override
    public boolean isPerpendicular(Vector4 other, float epsilon) {
        return false;
    }

    @Override
    public boolean hasSameDirection(Vector4 other) {
        return false;
    }

    @Override
    public boolean hasOppositeDirection(Vector4 other) {
        return false;
    }

    @Override
    public boolean epsilonEquals(Vector4 other, float epsilon) {
        return false;
    }

    @Override
    public Vector4 mulAdd(Vector4 v, float scalar) {
        return null;
    }

    @Override
    public Vector4 mulAdd(Vector4 v, Vector4 mulVec) {
        return null;
    }

    @Override
    public Vector4 setZero() {
        return null;
    }
}
