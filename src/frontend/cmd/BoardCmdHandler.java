package frontend.cmd;

import java.util.ArrayList;

import board.Board;
import frontend.BoardFrontEndHandler;
import frontend.CellFrontEndHandler;

public class BoardCmdHandler extends BoardFrontEndHandler{
    final private Board board;
    final private ArrayList<CellCmdHandler> mainBoard;
    final private ArrayList<CellCmdHandler> piecePickerBoard;
    private CellCmdHandler topLine;
    private CellCmdHandler bottomLine;

    public BoardCmdHandler(Board board) {
        this.board = board;
        mainBoard = new ArrayList<CellCmdHandler>();
        piecePickerBoard = new ArrayList<CellCmdHandler>();
    }

    public void addCellGUIHandler(CellCmdHandler CellCmdHandler) {
        mainBoard.add(CellCmdHandler);
    }

    public void addCellGUIHandler(CellFrontEndHandler CellCmdHandler) {}

    public void addTopLineCellGUIHandler(CellCmdHandler cellCmdHandler) {
        topLine = cellCmdHandler;
    }

    public void addTopLineCellGUIHandler(CellFrontEndHandler CellCmdHandler) {}

    public void addBottomLineCellGUIHandler(CellCmdHandler cellCmdHandler) {
        bottomLine = cellCmdHandler;
    }
    
    public void addBottomLineCellGUIHandler(CellFrontEndHandler CellCmdHandler) {}

    public void addPiecePickerCellGUIHandler(CellCmdHandler cellCmdHandler) {
        piecePickerBoard.add(cellCmdHandler);
    }

    public void addPiecePickerCellGUIHandler(CellFrontEndHandler CellCmdHandler) {}

    public void displayBoard() {
    }

    public void displayPiecePicker() {
    }

    public void hidePiecePicker() {
    }

    public void showEndGameMessage(boolean winnerIsPlayer1) {
        String playerName;
        if (winnerIsPlayer1) {
            playerName = "Player 1";
        }
        else {
            playerName = "Player 2";
        }
        String endGameMessage = playerName + " has won !";
        System.out.println(endGameMessage);
    }
}
