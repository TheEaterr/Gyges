package src.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

import src.board.Cell;

public class Move {
    public static final int startCellSelection = 0;
    public static final int startCellSelected = 1;
    public static final int pieceBouncing = 2;
    public static final int moveOver = 3;
    private boolean isValid = true;
    private ArrayList<Path> possiblePaths;
    private int moveState;
    private Cell startCell;
    private Cell endCell;
    final private Game parentGame;
    
    public Move(Game game) {
        parentGame = game;
        moveState = Move.startCellSelection;
        possiblePaths = new ArrayList<Path>();
    }

    public int getState() {
        return moveState;
    }

    public boolean getValid() {
        return isValid;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public void setState(int moveState) {
        this.moveState = moveState;
    }

    public void setStartCell(Cell startCell, ArrayList<Path> possiblePaths) {
        this.startCell = startCell;
        moveState = Move.startCellSelected;
        this.possiblePaths = possiblePaths;
    }

    public void removeStartCell() {
        startCell = null;
        moveState = Move.startCellSelection;
    }

    public void setBounce(Cell endCell) {
        Predicate<Path> wasPathChosen = (Path path) -> (!wasPathChosen(path, endCell));
        possiblePaths.removeIf(wasPathChosen);
        for (Path path : possiblePaths) {
            path.remove(0);
        }
        moveState = Move.pieceBouncing;
    }

    public HashSet<Bounce> getNextPossibleBounces() {
        HashSet<Bounce> nextPossibleBounces = new HashSet<Bounce>();
        for (Path path : possiblePaths) {
            nextPossibleBounces.add(path.get(0));
        }
        return nextPossibleBounces;
    }

    private boolean wasPathChosen(Path path, Cell endCell) {
        Bounce firstBounce = path.get(0);
        Step lastStep = firstBounce.getLastStep();
        Cell nextCell = lastStep.getEndCell();
        return nextCell == endCell;
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
        moveState = Move.moveOver;
        parentGame.registerMove(this);
    }
}
