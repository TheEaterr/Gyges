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
    private ArrayList<Integer> piecePickedListPlayer1; 
    private ArrayList<Integer> piecePickedListPlayer2; 
    public ArrayList<Move> moveList;
    public ArrayList<PiecePick> piecePickList;

    public Game(Board board) {
        this.associatedBoard = board;
        this.gameState = Game.piecePicking;
        this.moveList = new ArrayList<Move>();
        this.piecePickList = new ArrayList<PiecePick>();
        this.isItPlayer1Turn = true;
        this.numberOfPieceToPick = this.associatedBoard.numberOfColumns*2;
        this.numberOfPiecePicked = 0;
        piecePickedListPlayer1 = new ArrayList<Integer>();
        piecePickedListPlayer2 = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            piecePickedListPlayer1.add(0);
            piecePickedListPlayer2.add(0);
        }
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
        int piecePickedNumber = piecePick.getSelectedPiece().getNumber();
        this.numberOfPiecePicked += 1;
        if (this.numberOfPiecePicked >= this.numberOfPieceToPick) {
            this.gameState = Game.gameStarted;
        }
        ArrayList<Integer> lineToAddInfoTo;
        if (getTurn()) {
            lineToAddInfoTo = piecePickedListPlayer1;
        }
        else {
            lineToAddInfoTo = piecePickedListPlayer2;
        }
        lineToAddInfoTo.set(piecePickedNumber - 1, lineToAddInfoTo.get(piecePickedNumber - 1) + 1);
        this.isItPlayer1Turn = !this.isItPlayer1Turn;
    }

    public boolean allowPiecePick(int pieceNumber) {
        ArrayList<Integer> lineToRead;
        if (getTurn()) {
            lineToRead = piecePickedListPlayer1;
        }
        else {
            lineToRead = piecePickedListPlayer2;
        }
        int numberOfSuchPiecePicked = lineToRead.get(pieceNumber - 1);
        int numberToCompareWith = numberOfPieceToPick/2/3;
        if ((numberOfPieceToPick/2)%3 > 0) {
            numberToCompareWith++;
        }
        return numberOfSuchPiecePicked < numberToCompareWith;
    }
}
