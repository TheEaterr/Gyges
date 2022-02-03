package src.board;

public class PiecePickerCell extends Cell {

    public PiecePickerCell(int line, int column, Board parentBoard) {
        super(line, column, parentBoard);
    }

    public void triggerActivation() {
        this.parentBoard.pickPiece(this.pieceOnCell);
    }
    
}