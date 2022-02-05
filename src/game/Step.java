package src.game;

import src.board.Cell;

public class Step {
    final public static int upDirection = 0;
    final public static int downDirection = 1;
    final public static int leftDirection = 2;
    final public static int rightDirection = 3;
    final private Cell endCell;
    final private int direction;

    public Step(Cell endCell, int direction) {
        this.endCell = endCell;
        this.direction = direction;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Step)) {
            return false;
        }
        Step step = (Step) o;
        return step.getEndCell() == this.endCell && step.getDirection() == this.direction;
    }
}
