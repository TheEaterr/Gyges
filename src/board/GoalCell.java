package src.board;

import java.util.ArrayList;

import src.game.Step;

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
