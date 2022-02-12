package board;

public class PiecePickerCell extends Cell {

    public PiecePickerCell(int line, int column, Board parentBoard) {
        super(line, column, parentBoard);
    }

    @Override
    public void triggerActivation() {
        this.getParentBoard().pickPiece(this.getPiece());
    }
    
}
