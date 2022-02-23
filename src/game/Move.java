package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

import board.Cell;

public class Move {
    public static final int START_CELL_SELECTION = 0;
    public static final int START_CELL_SELECTED = 1;
    public static final int PIECE_BOUNCING = 2;
    public static final int MOVE_OVER = 3;
    private boolean isValid = true;
    private ArrayList<Path> possiblePaths;
    final private ArrayList<Path> allPossiblePaths;
    final private HashSet<Cell> possibleStartCells;
    private int moveState;
    private Cell startCell;
    private Cell endCell;
    final private Game parentGame;
    
    public Move(Game game, ArrayList<Path> possiblePaths, HashSet<Cell> possibleStartCells) {
        parentGame = game;
        moveState = Move.START_CELL_SELECTION;
        this.allPossiblePaths = possiblePaths;
        this.possibleStartCells = possibleStartCells;
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

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
        moveState = Move.START_CELL_SELECTED;
        possiblePaths = new ArrayList<Path>();
        for (Path path : allPossiblePaths) {
            if (path.getFirst().getFirst().getStartCell() == startCell) {
                possiblePaths.add(path);
            }
        }
    }

    public void removeStartCell() {
        startCell = null;
        moveState = Move.START_CELL_SELECTION;
    }

    public void setBounce(Cell endCell) {
        if (moveState == Move.START_CELL_SELECTED) {
            moveState = Move.PIECE_BOUNCING;
        }
        Predicate<Path> wasPathChosen = (Path path) -> (!wasPathChosen(path, endCell));
        possiblePaths.removeIf(wasPathChosen);
        for (Path path : possiblePaths) {
            path.removeFirst();
        }
    }

    public HashSet<Bounce> getNextPossibleBounces() {
        HashSet<Bounce> nextPossibleBounces = new HashSet<Bounce>();
        for (Path path : possiblePaths) {
            nextPossibleBounces.add(path.getFirst());
        }
        return nextPossibleBounces;
    }

    private boolean wasPathChosen(Path path, Cell endCell) {
        Bounce firstBounce = path.getFirst();
        Step lastStep = firstBounce.getLast();
        Cell nextCell = lastStep.getEndCell();
        return nextCell == endCell;
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
        moveState = Move.MOVE_OVER;
        parentGame.registerMove(this);
    }

    public HashSet<Cell> getPossibleStartCells() {
        return possibleStartCells;
    }
}
