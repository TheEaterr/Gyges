package frontend;

import board.Cell;
import piece.Piece;

abstract public class CellFrontEndHandler {
            
    public abstract void highlight();

    public abstract void removeHighlight();

    public abstract void enableButton();

    public abstract void disableButton();

    public abstract void setCell(Cell cell);

    public abstract void setPieceOnCell(Piece piece);

    public abstract void setPieceOvertopCell(Piece piece);

    public abstract void removePiece();

    public abstract void removePieceOvertopCell(); 
}
