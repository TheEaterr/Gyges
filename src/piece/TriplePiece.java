package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;

public class TriplePiece extends Piece {

    public TriplePiece() {
    }

    public HashSet<Cell> getPossibleMoves(Board board, Cell currentCell) {
        HashSet<Cell> possibleMoves = new HashSet<Cell>();
        
        return possibleMoves;
    }

    public int getNumber(){
        return 3;
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
                    Piece pieceOnCell2 = cell2.getPiece();
                    if (pieceOnCell2 == null) {
                        ArrayList<Cell> thirdNeighbouringCells = cell2.getNeighbouringCells(board);
                        for (Cell cell3 : thirdNeighbouringCells) {
                            if (cell != cell3) {
                                possibleSteps.add(cell3);
                            }    
                        }
                    }
                }
            }
        }
        return possibleSteps;
    }
}
