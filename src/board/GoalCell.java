package src.board;

import java.util.ArrayList;

public class GoalCell extends Cell {

    public GoalCell(int line, int column, Board parentBoard) {
        super(line, column, parentBoard);
    }
    
    public ArrayList<Cell> getNeighbouringCells(Board board) {
        ArrayList<Cell> neighbouringCells = new ArrayList<Cell>();
        return neighbouringCells;
    }
}
