package src.board;

import java.util.ArrayList;

import src.game.Game;
import src.game.Move;
import src.game.Step;
import src.gui.CellGUIHandler;
import src.piece.*;

public class Cell {
    final private int line;
    final private int column;
    private Piece pieceOnCell;
    private Piece pieceOvertopCell;
    final private CellGUIHandler cellGUIHandler;
    final private Board parentBoard;

    public Cell(int line, int column, Board parentBoard) {
        this.line = line;
        this.column = column;
        this.parentBoard = parentBoard;

        cellGUIHandler = new CellGUIHandler();
        cellGUIHandler.setCell(this);
    }

    public CellGUIHandler getCellGUIHandler() {
        return cellGUIHandler;
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
        cellGUIHandler.setPieceOnCell(piece);
    }

    private void removePiece() {
        pieceOnCell = null;
        cellGUIHandler.removePiece();
    }

    private void setPieceOvertop(Piece piece) {
        cellGUIHandler.setPieceOvertopCell(piece);
        pieceOvertopCell = piece;
    }

    private void removePieceOvertop() {
        pieceOvertopCell = null;
        cellGUIHandler.removePieceOvertopCell();
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
        cellGUIHandler.highlight();
    }

    public void removeHighlight() {
        disableButton();
        cellGUIHandler.removeHighlight();
    }

    private void enableButton() {
        cellGUIHandler.enableButton();
    }

    private void disableButton() {
        cellGUIHandler.disableButton();
    }

    public void triggerActivation() {
        int gameState = parentBoard.getGame().getState();
        if (gameState == Game.gameStarted) {
            Move currentMove = parentBoard.getMove();
            int moveState = currentMove.getState();
            switch(moveState) {
                case Move.startCellSelection:
                    parentBoard.selectMoveStartCell(this);
                    break;
                case Move.startCellSelected:
                    if (parentBoard.selectedCell == this) {
                        parentBoard.removeMoveStartCell();
                    }
                    else {
                        parentBoard.movePiece(this);
                    }
                    break;
                case Move.pieceBouncing:
                    parentBoard.movePiece(this);
                    break;
            }
        }
        else {
            parentBoard.pickCell(this);
        }
    }

    public ArrayList<Cell> getNeighbouringCells(Board board) {
        ArrayList<Cell> neighbouringCells = new ArrayList<Cell>();
        if (line > 0) {
            neighbouringCells.add(board.getCell(line - 1, column));
        }
        if (line < board.numberOfLines - 1) {
            neighbouringCells.add(board.getCell(line + 1, column));
        }
        if (column > 0) {
            neighbouringCells.add(board.getCell(line, column - 1));
        }
        if (column < board.numberOfColumns - 1) {
            neighbouringCells.add(board.getCell(line, column + 1));
        }
        if (line == board.numberOfLines - 1 && parentBoard.getGame().getTurn()) {
            neighbouringCells.add(board.getTopLineCell());
        }
        else if (line == 0 && !parentBoard.getGame().getTurn()) {
            neighbouringCells.add(board.getBottomLineCell());
        }

        return neighbouringCells;
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
            Step step = new Step(parentBoard.getTopLineCell(), Step.upDirection);
            neighbouringSteps.add(step);
        }
        else if (line == 0 && !parentBoard.getGame().getTurn()) {
            Step step = new Step(parentBoard.getBottomLineCell(), Step.downDirection);
            neighbouringSteps.add(step);
        }

        return neighbouringSteps;
    }
}
