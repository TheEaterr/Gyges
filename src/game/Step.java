package src.game;

import src.board.Cell;

public class Step {
    final public static int upDirection = 0;
    final public static int downDirection = 1;
    final public static int leftDirection = 2;
    final public static int rightDirection = 3;
    final public static int winDirection = 4;
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

    public Step getReverseStep() {
        int reverseDirection = 0;
        Cell reverseEndCell = null;
        switch(direction) {
            case Step.upDirection:
                reverseDirection = Step.downDirection;
                reverseEndCell = endCell.getParentBoard().getCell(endCell.getLine() - 1, endCell.getColumn());
                break;
            case Step.downDirection:
                reverseDirection = Step.upDirection;
                reverseEndCell = endCell.getParentBoard().getCell(endCell.getLine() + 1, endCell.getColumn());
                break;
            case Step.leftDirection:
                reverseDirection = Step.rightDirection;
                reverseEndCell = endCell.getParentBoard().getCell(endCell.getLine(), endCell.getColumn() + 1);
                break;
            case Step.rightDirection:
                reverseDirection = Step.leftDirection;
                reverseEndCell = endCell.getParentBoard().getCell(endCell.getLine(), endCell.getColumn() - 1);
                break;
        }
        Step reverseStep = new Step(reverseEndCell, reverseDirection);
        return reverseStep;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Step)) {
            return false;
        }
        Step step = (Step) o;
        return step.getEndCell() == this.endCell && step.getDirection() == this.direction;
    }

    @Override
    public String toString() {
        String directionString = null;
        switch(direction) {
            case Step.upDirection:
                directionString = "up";
                break;
            case Step.downDirection:
                directionString = "down";
                break;
            case Step.leftDirection:
                directionString = "left";
                break;
            case Step.rightDirection:
                directionString = "right";
                break;
        }
        return ((Integer) endCell.getLine()).toString() + ((Integer) endCell.getColumn()).toString() + directionString;
    }
}
