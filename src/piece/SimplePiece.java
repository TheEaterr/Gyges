package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;

public class SimplePiece extends Piece {
    
    public SimplePiece() {
    }

    public HashSet<Cell> getPossibleMoves(Board board, Cell currentCell) {
        HashSet<Cell> possibleMoves = new HashSet<Cell>();
        ArrayList<Cell> neighbouringCells = currentCell.getNeighbouringCells(board);
        for (Cell cell : neighbouringCells) {
            Piece pieceOnCell = cell.getPiece();
            if (pieceOnCell == null) {
                possibleMoves.add(cell);
            }
            else {
                pieceOnCell.recurseTroughPossibleMoves(board, currentCell, possibleMoves);

            }
        }

        return possibleMoves;
    }

    public HashSet<Cell> getPossibleSteps(Board board, Cell currentCell) {
        HashSet<Cell> possibleDestinations = new HashSet<Cell>();
        ArrayList<Cell> neighbouringCells = currentCell.getNeighbouringCells(board);
        for (Cell cell : neighbouringCells) {
            possibleDestinations.add(cell);
        }
        return possibleDestinations;
    }

    public void recurseTroughPossibleMoves(Board board, Cell currentCell, HashSet<Cell> possibleMoves) {
        // rajouter visited pieces, finalement c'est pas si mal
        
    }

    public int getNumber(){
        return 1;
    }
}
