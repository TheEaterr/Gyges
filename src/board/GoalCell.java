package board;

import java.util.ArrayList;

import game.Step;

public class GoalCell extends Cell {

    public GoalCell(int line, int column, Board parentBoard) {
        super(line, column, parentBoard);
    }

    @Override
    public ArrayList<Step> getNeighbouringSteps() {
        ArrayList<Step> neighbouringSteps = new ArrayList<Step>();
        return neighbouringSteps;
    }
}
