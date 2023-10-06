package ca.bungo.physics.verletintegrationsym.math;

public class Vector2 {

    public float x;
    public float y;

    public Vector2(){
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2(Vector2 vector2){
        this.x = vector2.x;
        this.y = vector2.y;
    }

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }
    public void add(float x, float y) { this.x += x; this.y += y;}

    public static double distance(Vector2 a, Vector2 b) {
        float v0 = b.x - a.x;
        float v1 = b.y - a.y;
        return Math.sqrt(v0*v0 + v1*v1);
    }

    public void normalize() {
        double length = Math.sqrt(x*x + y*y);

        if (length != 0.0) {
            float s = 1.0f / (float)length;
            x = x*s;
            y = y*s;
        }
    }

    public void subtract(Vector2 other){
        this.x -= other.x;
        this.y -= other.y;
    }

    public double length(){
        return Math.sqrt(this.x*this.x + this.y*this.y);
    }
    public double lengthSquared() { return length()*length(); }

    public static Vector2 subtract(Vector2 a, Vector2 b){
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 add(Vector2 a, Vector2 b){
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public Vector2 multiply(float scalar){
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2 divide(float scalar){
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    @Override
    public String toString() {
        return "X: " + this.x + " | Y: " + this.y;
    }

    public void add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
    }

    public static float dotProduct(Vector2 a, Vector2 b){
        return a.x*b.x + a.y+b.y;
    }
}
