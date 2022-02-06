package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;
import src.game.Bounce;
import src.game.Step;

public class SimplePiece extends Piece {
    
    public SimplePiece() {
    }

    public ArrayList<Bounce> getPossibleBounces(Board board, Cell currentCell) {
        ArrayList<Bounce> possibleBounces = new ArrayList<Bounce>();
        ArrayList<Step> possibleFirstSteps = currentCell.getNeighbouringSteps();
        for (Step firstStep : possibleFirstSteps) {
            Bounce possibleBounce = new Bounce();
            possibleBounce.add(firstStep);
            if (possibleBounce.getValid()) {
                possibleBounces.add(possibleBounce);
            }
        }
        return possibleBounces;
    }

    public HashSet<Cell> getPossibleSteps(Board board, Cell currentCell) {
        HashSet<Cell> possibleDestinations = new HashSet<Cell>();
        ArrayList<Cell> neighbouringCells = currentCell.getNeighbouringCells(board);
        for (Cell cell : neighbouringCells) {
            possibleDestinations.add(cell);
        }
        return possibleDestinations;
    }

    public int getNumber(){
        return 1;
    }
}
