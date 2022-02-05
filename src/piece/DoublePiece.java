package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;
import src.game.Step;

public class DoublePiece extends Piece {

    public DoublePiece() {
    }

    public int getNumber(){
        return 2;
    }

    public ArrayList<ArrayList<Step>> getPossibleMoves(Board board, Cell currentCell) {
        ArrayList<ArrayList<Step>> possibleMoves = new ArrayList<ArrayList<Step>>();
        return possibleMoves;
    }

    public void recurseTroughPossibleMoves(Board board, ArrayList<Step> totalStepList, ArrayList<ArrayList<Step>> possibleMoves) {

    }

    public ArrayList<ArrayList<Step>> getPossibleBounces(Board board, Cell currentCell) {
        ArrayList<ArrayList<Step>> possibleBounces = new ArrayList<ArrayList<Step>>();
        
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
