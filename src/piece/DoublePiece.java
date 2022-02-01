package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;

public class DoublePiece extends Piece {

    public DoublePiece() {
    }

    public HashSet<Cell> getPossibleMoves(Board board, Cell currentCell) {
        HashSet<Cell> possibleMoves = new HashSet<Cell>();
        
        return possibleMoves;
    }

    public int getNumber(){
        return 2;
    }

    public void recurseTroughPossibleMoves(Board board, Cell currentCell, HashSet<Cell> possibleMoves) {

    }

    public HashSet<Cell> getPossibleSteps(Board board, Cell currentCell) {
        HashSet<Cell> possibleSteps = new HashSet<Cell>();
        ArrayList<Cell> neighbouringCells = currentCell.getNeighbouringCells(board);
        for (Cell cell : neighbouringCells) {
            Piece pieceOnCell = cell.getPiece();
            if (pieceOnCell == null) {
                ArrayList<Cell> secondNeighbouringCells = cell.getNeighbouringCells(board);
                for (Cell cell2 : secondNeighbouringCells) {
                    if (cell2 != currentCell) {
                        possibleSteps.add(cell2);
                    }
                }
            }
        }
        return possibleSteps;
    }
}
