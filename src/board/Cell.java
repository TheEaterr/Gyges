package board;

import java.util.ArrayList;

import frontend.CellFrontEndHandler;
import frontend.FrontEndFactory;
import game.Game;
import game.Move;
import game.Step;
import piece.*;

public class Cell {
    final private int line;
    final private int column;
    private Piece pieceOnCell;
    private Piece pieceOvertopCell;
    final private CellFrontEndHandler cellFrontEndHandler;
    final private Board parentBoard;

    public Cell(int line, int column, Board parentBoard) {
        this.line = line;
        this.column = column;
        this.parentBoard = parentBoard;

        FrontEndFactory frontEndFactory = parentBoard.getFrontEndFactory();
        cellFrontEndHandler = frontEndFactory.createCellFrontEndHandler();
        cellFrontEndHandler.setCell(this);
    }

    public CellFrontEndHandler getCellFrontEndHandler() {
        return cellFrontEndHandler;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public Piece getPieceOvertop() {
        return pieceOvertopCell;
    }

    public Piece getPiece() {
        return pieceOnCell;
    }

    public Piece getPieceOnTop() {
        if (this.pieceOvertopCell != null) {
            return pieceOvertopCell;
        }
        else {
            return pieceOnCell;
        }
    }

    private void setPiece(Piece piece) {
        pieceOnCell = piece;
        cellFrontEndHandler.setPieceOnCell(piece);
    }

    private void removePiece() {
        pieceOnCell = null;
        cellFrontEndHandler.removePiece();
    }

    private void setPieceOvertop(Piece piece) {
        cellFrontEndHandler.setPieceOvertopCell(piece);
        pieceOvertopCell = piece;
    }

    private void removePieceOvertop() {
        pieceOvertopCell = null;
        cellFrontEndHandler.removePieceOvertopCell();
    }

    public void removePieceOnTop() {
        if (pieceOvertopCell != null) {
            removePieceOvertop();
        }
        else if (pieceOnCell != null) {
            removePiece();
        }
    }

    public boolean setPieceOnTop(Piece piece) {
        if (pieceOnCell == null) {
            setPiece(piece);
            return false;
        }
        else {
            setPieceOvertop(piece);
            return true;
        }
    }

    public int getNumber() {
        if (pieceOnCell == null) {
            return 0;
        }
        else {
            return pieceOnCell.getNumber();
        }
    }

    public Board getParentBoard() {
        return parentBoard;
    }

    public void highlight() {
        enableButton();
        cellFrontEndHandler.highlight();
    }

    public void removeHighlight() {
        disableButton();
        cellFrontEndHandler.removeHighlight();
    }

    private void enableButton() {
        cellFrontEndHandler.enableButton();
    }

    private void disableButton() {
        cellFrontEndHandler.disableButton();
    }

    public void triggerActivation() {
        int gameState = parentBoard.getGame().getState();
        if (gameState == Game.gameStarted) {
            Move currentMove = parentBoard.getMove();
            int moveState = currentMove.getState();
            switch(moveState) {
                case Move.START_CELL_SELECTION:
                    parentBoard.selectMoveStartCell(this);
                    break;
                case Move.START_CELL_SELECTED:
                    if (parentBoard.selectedCell == this) {
                        parentBoard.removeMoveStartCell();
                    }
                    else {
                        parentBoard.movePiece(this);
                    }
                    break;
                case Move.PIECE_BOUNCING:
                    parentBoard.movePiece(this);
                    break;
            }
        }
        else {
            parentBoard.pickCell(this);
        }
    }

    public ArrayList<Step> getNeighbouringSteps() {
        ArrayList<Step> neighbouringSteps = new ArrayList<Step>();
        if (line > 0) {
            Step step = new Step(parentBoard.getCell(line - 1, column), Step.downDirection);
            neighbouringSteps.add(step);
        }
        if (line < parentBoard.numberOfLines - 1) {
            Step step = new Step(parentBoard.getCell(line + 1, column), Step.upDirection);
            neighbouringSteps.add(step);
        }
        if (column > 0) {
            Step step = new Step(parentBoard.getCell(line, column - 1), Step.leftDirection);
            neighbouringSteps.add(step);
        }
        if (column < parentBoard.numberOfColumns - 1) {
            Step step = new Step(parentBoard.getCell(line, column + 1), Step.rightDirection);
            neighbouringSteps.add(step);
        }
        if (line == parentBoard.numberOfLines - 1 && parentBoard.getGame().getTurn()) {
            Step step = new Step(parentBoard.getTopLineCell(), Step.winDirection);
            neighbouringSteps.add(step);
        }
        else if (line == 0 && !parentBoard.getGame().getTurn()) {
            Step step = new Step(parentBoard.getBottomLineCell(), Step.winDirection);
            neighbouringSteps.add(step);
        }

        return neighbouringSteps;
    }
}
