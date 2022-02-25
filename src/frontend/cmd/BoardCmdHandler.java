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

    public void addCellFrontEndHandler(CellFrontEndHandler cellFrontEndHandler) {
        CellCmdHandler cellCmdHandler = (CellCmdHandler) cellFrontEndHandler;
        mainBoard.add(cellCmdHandler);
    }

    public void addTopLineCellFrontEndHandler(CellFrontEndHandler cellFrontEndHandler) {
        CellCmdHandler cellCmdHandler = (CellCmdHandler) cellFrontEndHandler;
        topLine = cellCmdHandler;
    }
    
    public void addBottomLineCellFrontEndHandler(CellFrontEndHandler cellFrontEndHandler) {
        CellCmdHandler cellCmdHandler = (CellCmdHandler) cellFrontEndHandler;
        bottomLine = cellCmdHandler;
    }

    public void addPiecePickerCellGUIHandler(CellCmdHandler cellCmdHandler) {
        piecePickerBoard.add(cellCmdHandler);
    }

    public void addPiecePickerCellFrontEndHandler(CellFrontEndHandler CellCmdHandler) {}

    public void displayBoard() {
        System.out.println("Game Started");
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
