package src.piece;

import java.util.ArrayList;
import java.util.HashSet;
import src.board.*;
import src.game.Step;

public class TriplePiece extends Piece {

    public TriplePiece() {
    }

    public ArrayList<ArrayList<Step>> getPossibleMoves(Board board, Cell currentCell) {
        ArrayList<ArrayList<Step>> possibleMoves = new ArrayList<ArrayList<Step>>();
        ArrayList<ArrayList<Step>> possibleBounces = getPossibleBounces(board, currentCell);
        for (ArrayList<Step> possibleBounce : possibleBounces) {
            recurseTroughPossibleMoves(board, possibleBounce, possibleMoves);
        }
        
        return possibleMoves;
    }

    public int getNumber(){
        return 3;
    }

    public void recurseTroughPossibleMoves(Board board, ArrayList<Step> totalStepList, ArrayList<ArrayList<Step>> possibleMoves) {
        Step lastStep = totalStepList.get(totalStepList.size() - 1);
        Cell lastCell = lastStep.getEndCell();
        ArrayList<ArrayList<Step>> possibleBounces = lastCell.getPiece().getPossibleBounces(board, currentCell);
        for (ArrayList<Step> possibleBounce : possibleBounces) {
            ArrayList<Step> newMoveStepList = new ArrayList<Step>();
            newMoveStepList.addAll(totalStepList);
            for (Step possibleStep : possibleBounce) {
                if (!newMoveStepList.contains(possibleStep)) {
                    newMoveStepList.add(possibleStep);
                }
                else {
                    break;
                }
                if (possibleBounce.get(possibleBounce.size() - 1).getEndCell().getPieceOnTop() == null) {
                    possibleMoves.add(newMoveStepList);
                }
                else {
                    recurseTroughPossibleMoves(board, newMoveStepList, possibleMoves);
                }
            }
        }
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
