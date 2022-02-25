package frontend.cmd;

import board.Cell;
import frontend.CellFrontEndHandler;
import piece.Piece;

public class CellCmdHandler extends CellFrontEndHandler{
    private Cell cell;
    
    public CellCmdHandler() {
    }
            
    public void highlight() {
    }

    public void removeHighlight() {
    }

    public void enableButton() {
    }

    public void disableButton() {
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setPieceOnCell(Piece piece) {
    }

    public void setPieceOvertopCell(Piece piece) {
    }

    public void removePiece() {
    }

    public void removePieceOvertopCell() {
    }
    
}
