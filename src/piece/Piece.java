package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;
import src.game.Step;

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

    public abstract ArrayList<ArrayList<Step>> getPossibleMoves(Board board, Cell currentCell);

    public abstract void recurseTroughPossibleMoves(Board board, ArrayList<Step> totalStepList, ArrayList<ArrayList<Step>> possibleMoves);

    public abstract ArrayList<ArrayList<Step>> getPossibleBounces(Board board, Cell currentCell);

    public abstract HashSet<Cell> getPossibleSteps(Board board, Cell currentCell);
}
