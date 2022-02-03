package src.game;

import java.util.ArrayList;

import src.board.Board;

public class Game {
    public static final int piecePicking = 0;
    public static final int gameStarted = 1;
    private Board associatedBoard;
    private int gameState;
    private boolean isItPlayer1Turn;
    private int numberOfPieceToPick;
    private int numberOfPiecePicked;
    public ArrayList<Move> moveList;
    public ArrayList<PiecePick> piecePickList;

    public Game(Board board) {
        this.associatedBoard = board;
        this.gameState = Game.piecePicking;
        this.moveList = new ArrayList<Move>();
        this.piecePickList = new ArrayList<PiecePick>();
        this.isItPlayer1Turn = false;
        this.numberOfPieceToPick = this.associatedBoard.numberOfColumns*2;
        this.numberOfPiecePicked = 0;
    }

    public int getState() {
        return this.gameState;
    }
    
    public boolean getTurn() {
        return this.isItPlayer1Turn;
    }

    public void registerMove(Move move) {
        this.moveList.add(move);
        this.isItPlayer1Turn = !this.isItPlayer1Turn;
    }

    public void registerPiecePick(PiecePick piecePick) {
        this.piecePickList.add(piecePick);
        this.isItPlayer1Turn = !this.isItPlayer1Turn;
    }

    public void finishPiecePick() {
        this.numberOfPiecePicked += 1;
        if (this.numberOfPiecePicked >= this.numberOfPieceToPick) {
            this.gameState = Game.gameStarted;
        }
    }
}
