package ca.bungo.physics.verletintegrationsym.simulator.types;

import ca.bungo.physics.verletintegrationsym.math.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VerletIntegration extends Circle {

    public Vector2 currentPosition;
    public Vector2 oldPosition;
    public Vector2 acceleration;
    public Vector2 velocity;
    public Point2D gridPosition;
    int radius;
    Color color;

    public VerletIntegration(){
        super(0,0,10);
        this.setFill(Color.BLUE);
        this.toFront();
        this.currentPosition = new Vector2();
        this.oldPosition = currentPosition;
        this.acceleration = new Vector2();
        this.velocity = new Vector2();
        this.radius = 10;
        this.color = Color.BLUE;
    }

    public VerletIntegration(Vector2 position){
        super(position.x, position.y, 10);
        this.setFill(Color.WHITE);
        this.toFront();
        this.velocity = new Vector2();
        this.currentPosition = new Vector2(position);
        this.oldPosition = new Vector2(position);
        this.acceleration = new Vector2();
        this.radius = 10;
        this.color = Color.WHITE;
    }

    public VerletIntegration(Vector2 position, Vector2 acceleration){
        super(position.x, position.y, 10);
        this.setFill(Color.WHITE);
        this.toFront();
        this.velocity = new Vector2();
        this.currentPosition = position;
        this.oldPosition = currentPosition;
        this.acceleration = acceleration;
        this.radius = 10;
        this.color = Color.WHITE;
    }

    public void updatePosition(float dt){
        Vector2 displacement = Vector2.subtract(currentPosition, oldPosition);

        oldPosition = new Vector2(currentPosition);
        currentPosition = Vector2.add(Vector2.add(currentPosition, displacement), acceleration.multiply(dt*dt));
        acceleration = new Vector2();
    }

    public void accelerate(Vector2 acc){
        acceleration = Vector2.add(acceleration, acc);
    }

    public void setVelocity(Vector2 v, float dt){
        oldPosition = Vector2.subtract(new Vector2(currentPosition), new Vector2(v).multiply(dt));
    }

    public void addVelocity(Vector2 v, float dt){
        oldPosition = Vector2.subtract(oldPosition, new Vector2(v).multiply(dt));
    }

    public void update(){
        this.setFill(color);
        this.setTranslateX(currentPosition.x);
        this.setTranslateY(currentPosition.y);
        this.setRadius(this.radius);
    }

}
