package src.game;

import src.board.Cell;
import src.piece.Piece;

public class PiecePick {
    public static final int startPieceSelection = 0;
    public static final int pieceSelected = 1;
    public static final int piecePickOver = 2;
    int piecePickState;
    Game parentGame;
    Cell cellChosen;
    Piece pieceChosen;

    public PiecePick(Game game) {
        this.parentGame = game;
        piecePickState = PiecePick.startPieceSelection;
        game.registerPiecePick(this);
    }

    public int getState() {
        return this.piecePickState;
    }

    public Piece getSelectedPiece() {
        return pieceChosen;
    }

    public void selectPiece(Piece piece) {
        pieceChosen = piece;
        piecePickState = PiecePick.pieceSelected;
    }

    public void selectCell(Cell cell) {
        this.cellChosen = cell;
        piecePickState = PiecePick.piecePickOver;
        this.parentGame.finishPiecePick(this.pieceChosen.getNumber());
    }
}
