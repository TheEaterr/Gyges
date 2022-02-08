package src.piece;

import java.util.ArrayList;
import src.board.*;
import src.game.Bounce;
import src.game.Path;
import src.game.Step;

abstract public class Piece {

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

    public static ArrayList<Path> getPossiblePaths(Board board, Cell currentCell) {
        ArrayList<Path> possiblePaths = new ArrayList<Path>();
        Path possiblePath = new Path();
        recurseTroughPossiblePaths(board, currentCell, possiblePath, possiblePaths);
        return possiblePaths;
    }

    private static void recurseTroughPossiblePaths(Board board, Cell currentCell, Path possiblePath, ArrayList<Path> possiblePaths) {
        ArrayList<Bounce> possibleBounces;
        if (possiblePath.size() > 0) {
            Bounce lastBounce = possiblePath.get(possiblePath.size() - 1);
            Step lastStep = lastBounce.get(lastBounce.size() - 1);
            Cell lastCell = lastStep.getEndCell();
            Piece lastPiece = lastCell.getPiece();
            possibleBounces = lastPiece.getPossibleBounces(board, lastCell);
        }
        else {
            possibleBounces = currentCell.getPieceOnTop().getPossibleBounces(board, currentCell);
        }
        for (Bounce possibleBounce : possibleBounces) {
            Path newPath = new Path();
            newPath.addAll(possiblePath);
            newPath.addLast(possibleBounce);
            if (newPath.getValid()) {
                if (possibleBounce.get(possibleBounce.size() - 1).getEndCell().getPieceOnTop() == null) {
                    possiblePaths.add(newPath);
                }
                else {
                    recurseTroughPossiblePaths(board, currentCell, newPath, possiblePaths);
                }
            }
        }
    }

    public ArrayList<Bounce> getPossibleBounces(Board board, Cell currentCell) {
        int depthNumber = getNumber();
        ArrayList<Bounce> possibleBounces = new ArrayList<Bounce>();
        Bounce possibleBounce = new Bounce();
        recurseTroughPossibleBounces(possibleBounce, possibleBounces, depthNumber, currentCell);
        return possibleBounces;
    }

    private void recurseTroughPossibleBounces(Bounce bounce, ArrayList<Bounce> possibleBounces, int depthNumber, Cell currentCell) {
        if (bounce.size() < depthNumber) {
            ArrayList<Step> possibleSteps;
            if (bounce.size() == 0) {
                possibleSteps = currentCell.getNeighbouringSteps();
            }
            else {
                possibleSteps =  bounce.getLast().getEndCell().getNeighbouringSteps();
            }
            for (Step step : possibleSteps) {
                Piece pieceOnCell = step.getEndCell().getPiece();
                if (pieceOnCell == null || bounce.size() == depthNumber - 1 || step.getEndCell() == currentCell.getParentBoard().selectedCell) {
                    Bounce possibleBounce = new Bounce();
                    possibleBounce.addAll(bounce);
                    possibleBounce.addLast(step);
                    if (possibleBounce.getValid()) {
                        recurseTroughPossibleBounces(possibleBounce, possibleBounces, depthNumber, currentCell);
                    }
                }
            }
        }
        else {
            possibleBounces.add(bounce);
        }
    }
}
