package frontend;

abstract public class BoardFrontEndHandler {

    public abstract void addCellFrontEndHandler(CellFrontEndHandler CellCmdHandler);

    public abstract  void addTopLineCellFrontEndHandler(CellFrontEndHandler cellCmdHandler);

    public abstract  void addBottomLineCellFrontEndHandler(CellFrontEndHandler cellCmdHandler);

    public abstract void addPiecePickerCellFrontEndHandler(CellFrontEndHandler cellCmdHandler);

    public abstract  void displayBoard();

    public abstract  void displayPiecePicker();

    public abstract  void hidePiecePicker();

    public abstract  void showEndGameMessage(boolean winnerIsPlayer1);
}
