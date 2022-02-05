package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;
import src.game.Step;

public class SimplePiece extends Piece {
    
    public SimplePiece() {
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
