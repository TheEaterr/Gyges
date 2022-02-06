package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;
import src.game.Bounce;
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

    public static ArrayList<ArrayList<Step>> getPossibleMoves(Board board, Cell currentCell) {
        ArrayList<ArrayList<Step>> possibleMoves = new ArrayList<ArrayList<Step>>();
        ArrayList<Bounce> possibleBounces = currentCell.getPieceOnTop().getPossibleBounces(board, currentCell);
        for (Bounce possibleBounce : possibleBounces) {
            Step lastStep = possibleBounce.get(possibleBounce.size() - 1);
            Cell lastCell = lastStep.getEndCell();
            if (lastCell.getPieceOnTop() == null) {
                possibleMoves.add(possibleBounce);
            }
            else {
                recurseTroughPossibleMoves(board, possibleBounce, possibleMoves);
            }
        }
        
        return possibleMoves;
    }

    public static void recurseTroughPossibleMoves(Board board, ArrayList<Step> totalStepList, ArrayList<ArrayList<Step>> possibleMoves) {
        Step lastStep = totalStepList.get(totalStepList.size() - 1);
        Cell lastCell = lastStep.getEndCell();
        Piece lastPiece = lastCell.getPiece();
        ArrayList<Bounce> possibleBounces = lastPiece.getPossibleBounces(board, lastCell);
        for (ArrayList<Step> possibleBounce : possibleBounces) {
            ArrayList<Step> newMoveStepList = new ArrayList<Step>();
            newMoveStepList.addAll(totalStepList);
            boolean moveIsValid = true;
            for (Step possibleStep : possibleBounce) {
                if (!newMoveStepList.contains(possibleStep) && !newMoveStepList.contains(possibleStep.getReverseStep())) {
                    newMoveStepList.add(possibleStep);
                }
                else {
                    moveIsValid = false;
                    break;
                }
            }
            if (moveIsValid) {
                if (possibleBounce.get(possibleBounce.size() - 1).getEndCell().getPieceOnTop() == null) {
                    possibleMoves.add(newMoveStepList);
                }
                else {
                    recurseTroughPossibleMoves(board, newMoveStepList, possibleMoves);
                }
            }
        }
    }

    public abstract ArrayList<Bounce> getPossibleBounces(Board board, Cell currentCell);

    public abstract HashSet<Cell> getPossibleSteps(Board board, Cell currentCell);
}
