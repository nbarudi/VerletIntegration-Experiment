package ca.bungo.physics.verletintegrationsym.simulator.types;

import ca.bungo.physics.verletintegrationsym.math.Vector2;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {

    float cellSize;
    private final Map<Point2D, List<VerletIntegration>> grid;

    public Grid(float cellSize){
        this.cellSize = cellSize;
        this.grid = new HashMap<>();
    }

    public void addObject(VerletIntegration object){
        Point2D gridCell = getGridCell(object.currentPosition);
        object.gridPosition = gridCell;
        grid.computeIfAbsent(gridCell, k ->new ArrayList<>()).add(object);
    }

    public void removeObject(VerletIntegration object){
        Point2D gridCell = object.gridPosition;
        if(gridCell == null)
            gridCell = getGridCell(object.currentPosition);
        List<VerletIntegration> cellObjects = grid.get(gridCell);
        if(cellObjects != null){
            cellObjects.remove(object);
            if(cellObjects.isEmpty())
                grid.remove(gridCell);
        }
    }

    public void updateObjectPosition(VerletIntegration object){
        removeObject(object);
        addObject(object);
    }

    public List<Point2D> getAdjacentGrids(Point2D centerCell){
        List<Point2D> adjacent = new ArrayList<>();
        //adjacent.add(centerCell);

        for(int xOff = -1; xOff <= 1; xOff++){
            for(int yOff = -1; yOff <= 1; yOff++){
                int x = (int) (centerCell.getX() + xOff);
                int y = (int) (centerCell.getY() + yOff);
                if(x == centerCell.getX() && y == centerCell.getY()) continue;
                adjacent.add(new Point2D(x,y));
            }
        }

        return adjacent;
    }

    public List<VerletIntegration> getObjectsInCell(Point2D cell){
        return grid.get(cell);
    }

    private Point2D getGridCell(Vector2 position){
        int x = (int) (position.x / cellSize);
        int y = (int) (position.y / cellSize);
        return new Point2D(x,y);
    }

    public Map<Point2D, List<VerletIntegration>> getGrid() { return this.grid; }

}
