package src.game;

import src.board.Cell;

public class Move {
    public static final int startCellSelection = 0;
    public static final int startCellSelected = 1;
    public static final int pieceBouncing = 2;
    public static final int moveOver = 3;
    int moveState;
    Cell startCell;
    Cell endCell;
    Game parentGame;
    
    public Move(Game game) {
        this.parentGame = game;
        this.moveState = startCellSelection;
        game.registerMove(this);
    }

    public int getState() {
        return this.moveState;
    }

    public void setState(int moveState) {
        this.moveState = moveState;
    }

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
        this.moveState = Move.startCellSelected;
    }

    public void removeStartCell() {
        this.startCell = null;
        this.moveState = Move.startCellSelection;
    }

    public void setBouncing() {
        this.moveState = Move.pieceBouncing;
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
        this.moveState = Move.moveOver;
    }
}
