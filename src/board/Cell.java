package src.board;

import java.util.ArrayList;

import src.game.Move;
import src.gui.CellGUIHandler;
import src.piece.*;

public class Cell {
    int line;
    int column;
    Piece pieceOnCell;
    Piece pieceOvertopCell;
    CellGUIHandler cellGUIHandler;
    Board parentBoard;
    public boolean isHighlighted;

    public Cell(int line, int column, Board parentBoard) {
        this.line = line;
        this.column = column;
        this.parentBoard = parentBoard;
        this.isHighlighted = false;

        this.cellGUIHandler = new CellGUIHandler();
        this.cellGUIHandler.setCell(this);
    }

    public CellGUIHandler getCellGUIHandler() {
        return this.cellGUIHandler;
    }

    public Piece getPieceOvertop() {
        return this.pieceOvertopCell;
    }

    public Piece getPiece() {
        return this.pieceOnCell;
    }

    public Piece getPieceOnTopOfCell() {
        if (this.pieceOvertopCell != null) {
            return this.pieceOvertopCell;
        }
        else {
            return this.pieceOnCell;
        }
    }

    private void setPiece(Piece piece) {
        this.pieceOnCell = piece;
        this.cellGUIHandler.setPieceOnCell(piece);
    }

    private void removePiece() {
        this.pieceOnCell = null;
        this.cellGUIHandler.removePiece();
    }

    private void setPieceOvertopCell(Piece piece) {
        this.cellGUIHandler.setPieceOvertopCell(piece);
        this.pieceOvertopCell = piece;
    }

    private void removePieceOvertopCell() {
        this.pieceOvertopCell = null;
        this.cellGUIHandler.removePieceOvertopCell();
    }

    public void removePieceOnTopOfCell() {
        if (this.pieceOvertopCell != null) {
            removePieceOvertopCell();
        }
        else if (this.pieceOnCell != null) {
            removePiece();
        }
    }

    public boolean setPieceOnTopOfCell(Piece piece) {
        if (this.pieceOnCell == null) {
            setPiece(piece);
            return false;
        }
        else {
            setPieceOvertopCell(piece);
            return true;
        }
    }

    public int getNumber() {
        if (this.pieceOnCell == null) {
            return 0;
        }
        else {
            return pieceOnCell.getNumber();
        }
    }

    public void highlight() {
        this.enableButton();
        this.isHighlighted = true;
        this.cellGUIHandler.highlight();
    }

    public void removeHighlight() {
        this.disableButton();
        this.cellGUIHandler.removeHighlight();
        this.isHighlighted = false;
    }

    private void enableButton() {
        this.cellGUIHandler.enableButton();
    }

    private void disableButton() {
        this.cellGUIHandler.disableButton();
    }

    public void triggerActivation() {
        Move currentMove = this.parentBoard.getMove();
        int moveState = currentMove.getState();
        switch(moveState) {
            case Move.startCellSelection:
                this.parentBoard.selectMoveStartCell(this);
                break;
            case Move.startCellSelected:
                if (this.parentBoard.selectedCell == this) {
                    this.parentBoard.removeMoveStartCell();
                }
                else {
                    this.parentBoard.movePiece(this);
                }
                break;
            case Move.pieceBouncing:
                this.parentBoard.movePiece(this);
                break;
        }
    }

    public ArrayList<Cell> getNeighbouringCells(Board board) {
        ArrayList<Cell> neighbouringCells = new ArrayList<Cell>();
        int line = this.line;
        int column = this.column;
        if (this.line > 0) {
            neighbouringCells.add(board.getCell(line - 1, column));
        }
        if (this.line < board.numberOfLines - 1) {
            neighbouringCells.add(board.getCell(line + 1, column));
        }
        if (this.column > 0) {
            neighbouringCells.add(board.getCell(line, column - 1));
        }
        if (this.column < board.numberOfColumns - 1) {
            neighbouringCells.add(board.getCell(line, column + 1));
        }
        if (this.line == board.numberOfLines - 1 && this.parentBoard.getGame().getTurn()) {
            neighbouringCells.add(board.getTopLineCell());
        }
        else if (this.line == 0 && !this.parentBoard.getGame().getTurn()) {
            neighbouringCells.add(board.getBottomLineCell());
        }

        return neighbouringCells;
    }
}
