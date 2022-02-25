package frontend;

import board.Board;
import frontend.cmd.BoardCmdHandler;
import frontend.cmd.CellCmdHandler;
import frontend.gui.BoardGUIHandler;
import frontend.gui.CellGUIHandler;

public class FrontEndFactory {
    final static public int GUI_MODE = 0;
    final static public int CMD_MODE = 1;
    final private int mode;

    public FrontEndFactory(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public BoardFrontEndHandler createBoardFrontEndHandler(Board board) {
        BoardFrontEndHandler boardFrontEndHandler;
        if (mode == GUI_MODE) {
            boardFrontEndHandler = new BoardGUIHandler(board);
        }
        else {
            boardFrontEndHandler = new BoardCmdHandler(board);
        }
        return boardFrontEndHandler;
    }

    public CellFrontEndHandler createCellFrontEndHandler() {
        CellFrontEndHandler cellFrontEndHandler;
        if (mode == GUI_MODE) {
            cellFrontEndHandler = new CellGUIHandler();
        }
        else {
            cellFrontEndHandler = new CellCmdHandler();
        }
        return cellFrontEndHandler;
    }
}
