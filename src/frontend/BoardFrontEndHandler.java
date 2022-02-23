package frontend;

abstract public class BoardFrontEndHandler {

    public abstract void addCellGUIHandler(CellFrontEndHandler CellCmdHandler);

    public abstract  void addTopLineCellGUIHandler(CellFrontEndHandler cellCmdHandler);

    public abstract  void addBottomLineCellGUIHandler(CellFrontEndHandler cellCmdHandler);

    public abstract void addPiecePickerCellGUIHandler(CellFrontEndHandler cellCmdHandler);

    public abstract  void displayBoard();

    public abstract  void displayPiecePicker();

    public abstract  void hidePiecePicker();

    public abstract  void showEndGameMessage(boolean winnerIsPlayer1);
}
