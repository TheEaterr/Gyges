package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;
import src.game.Bounce;
import src.game.Step;

public class DoublePiece extends Piece {

    public DoublePiece() {
    }

    public int getNumber(){
        return 2;
    }

    public ArrayList<Bounce> getPossibleBounces(Board board, Cell currentCell) {
        ArrayList<Bounce> possibleBounces = new ArrayList<Bounce>();
        ArrayList<Step> possibleFirstSteps = currentCell.getNeighbouringSteps();
        for (Step firstStep : possibleFirstSteps) {
            Piece pieceOnCell = firstStep.getEndCell().getPiece();
            if (pieceOnCell == null) {
                ArrayList<Step> possibleSecondSteps = firstStep.getEndCell().getNeighbouringSteps();
                for (Step secondStep : possibleSecondSteps) {
                    Bounce possibleBounce = new Bounce();
                    possibleBounce.add(firstStep);
                    possibleBounce.add(secondStep);
                    if (possibleBounce.getValid()) {
                        possibleBounces.add(possibleBounce);
                    }
                }
            }
        }
        return possibleBounces;
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
