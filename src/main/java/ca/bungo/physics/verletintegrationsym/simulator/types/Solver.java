package ca.bungo.physics.verletintegrationsym.simulator.types;

import ca.bungo.physics.verletintegrationsym.VerletIntegrationApplication;
import ca.bungo.physics.verletintegrationsym.math.Colors;
import ca.bungo.physics.verletintegrationsym.math.Vector2;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;

import java.util.*;

public class Solver {

    List<VerletIntegration> objects = new ArrayList<>();
    Shape border;
    Scene mainScene;

    Timer internalTimer;

    float time = 0;
    float objectSpawnSpeed = 1200.0f;
    float maxAngle = (float) (2.0f*Math.PI);

    private final Grid grid;

    private final Vector2 gravity = new Vector2(0, 1000.0f);

    public Solver(Scene mainScene, Shape border){
        this.grid = new Grid(10);
        this.border = border;
        this.mainScene = mainScene;
        Random seeder = new Random(1984);
        internalTimer = new Timer();
        internalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i = 0; i < 4000; i++){
                    Platform.runLater(() -> {
                        VerletIntegration verletObject = new VerletIntegration(new Vector2(0,0));
                        verletObject.radius = 5;
                        verletObject.color = Colors.getRainbow(time);
                        float angle = (float) (maxAngle * Math.sin(3*time) + Math.PI*0.5f);
                        setObjectVelocity(verletObject, new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).multiply(objectSpawnSpeed));
                        objects.add(verletObject);
                        ((StackPane) mainScene.getRoot()).getChildren().add(verletObject);
                        System.out.println(objects.size());
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 250);
    }

    public void stopTimers(){
        internalTimer.cancel();
        internalTimer.purge();
    }

    public void update(long dt){
        float rate = getStepDt();
        time += rate;
        int subSteps = getSubSteps();
        for(int i = 0; i < subSteps; ++i){
            applyGravity();
            applyConstraints();
            solveCollisions();
            updatePositions(getStepDt());
        }
        updateDrawing();
    }


    void applyGravity(){
        for(VerletIntegration verletIntegration : objects){
            verletIntegration.accelerate(gravity);
        }
    }

    void setObjectVelocity(VerletIntegration object, Vector2 vel){
        object.setVelocity(vel, getStepDt());
    }

    float getRate(){
        return 60.0f;
    }

    float getFrameDt(){
        return 1/getRate();
    }

    int getSubSteps(){
        return 8;
    }

    float getStepDt(){
        return getFrameDt()/getSubSteps();
    }

    void updatePositions(float dt){
        for(VerletIntegration verletIntegration : objects){
            verletIntegration.updatePosition(dt);
            grid.updateObjectPosition(verletIntegration);
        }
    }

    void updateDrawing(){
        for(VerletIntegration verletIntegration : objects){
            verletIntegration.update();
        }
    }

    void applyConstraints(){
        Vector2 centerPosition = new Vector2((float) border.getTranslateX(), (float) border.getTranslateY());
        float radius = 450.0f;
        for(VerletIntegration obj : objects){
            Vector2 v = Vector2.subtract(centerPosition, obj.currentPosition);
            float dist = (float)v.length();
            if(dist > radius - obj.radius){
                Vector2 n = v.divide(dist);
                obj.currentPosition = Vector2.subtract(centerPosition, n.multiply(radius-obj.radius));
            }
        }
    }

    void solveCollisions(){
        for(Map.Entry<Point2D, List<VerletIntegration>> entry : grid.getGrid().entrySet()){
            Point2D cell = entry.getKey();
            List<VerletIntegration> cellObjects = entry.getValue();
            for(int i = 0; i < cellObjects.size(); i++){
                VerletIntegration object1 = cellObjects.get(i);
                for(int j = i+1; j < cellObjects.size(); j++){
                    VerletIntegration object2 = cellObjects.get(j);
                    calculateCollision(object1, object2);
                }

                List<Point2D> adjCells = grid.getAdjacentGrids(cell);
                for(Point2D adjCell : adjCells){
                    List<VerletIntegration> adjCellObjects = grid.getObjectsInCell(adjCell);
                    if(adjCellObjects == null) continue;
                    for(VerletIntegration object2 : adjCellObjects){
                        calculateCollision(object1, object2);
                    }
                }
            }
        }
    }

    void calculateCollision(VerletIntegration object1, VerletIntegration object2){
        float response_coef = 0.75f;
        Vector2 v = Vector2.subtract(object1.currentPosition, object2.currentPosition);
        float dist2 = (float)v.lengthSquared();
        float min_dist = object1.radius + object2.radius;
        if(dist2 < min_dist * min_dist){
            float dist = (float) Math.sqrt(dist2);
            Vector2 n = v.divide(dist);
            float m_r_1 = (float) object1.radius / (object1.radius + object2.radius);
            float m_r_2 = (float) object2.radius / (object1.radius + object2.radius);
            float delta = 0.5f * response_coef * (dist - min_dist);
            object1.currentPosition.subtract(new Vector2(n).multiply(m_r_2 * delta));
            object2.currentPosition.add(new Vector2(n).multiply(m_r_1 * delta));
        }
    }
}
