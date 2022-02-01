package src.piece;

import java.util.HashSet;
import src.board.*;

abstract public class Piece {
    Board board;
    Cell currentCell;

    public static Piece createNewWithNumber(int pieceNumber) {
        Piece piece = null;
        switch(pieceNumber) {
            case 1:
                piece = new SimplePiece();
                break;
            case 2:
                piece = new DoublePiece();
                break;
            case 3:
                piece = new TriplePiece();
                break;
        }
        return piece;
    }

    public abstract int getNumber();

    public abstract HashSet<Cell> getPossibleMoves(Board board, Cell currentCell);

    public abstract void recurseTroughPossibleMoves(Board board, Cell currentCell, HashSet<Cell> possibleMoves);

    public abstract HashSet<Cell> getPossibleSteps(Board board, Cell currentCell);
}
