package src.board;

import java.util.ArrayList;

import src.game.Step;

public class GoalCell extends Cell {

    public GoalCell(int line, int column, Board parentBoard) {
        super(line, column, parentBoard);
    }
    
    public ArrayList<Cell> getNeighbouringCells(Board board) {
        ArrayList<Cell> neighbouringCells = new ArrayList<Cell>();
        return neighbouringCells;
    }

    public ArrayList<Step> getNeighbouringSteps() {
        ArrayList<Step> neighbouringSteps = new ArrayList<Step>();
        return neighbouringSteps;
    }
}
