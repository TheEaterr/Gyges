package src;
import java.util.ArrayList;

import src.board.*;
import src.piece.*;

// import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    
    Board board = new Board();
    int[] firstPlayerPiecesNumbers = {3, 1, 1, 2, 3, 2};
    int[] secondPlayerPiecesNumbers = {2, 1, 3, 3, 1, 2};
    ArrayList<Piece> firstPlayerPieces = new ArrayList<Piece>();
    for (int pieceNumber : firstPlayerPiecesNumbers) {
      firstPlayerPieces.add(Piece.createNewWithNumber(pieceNumber));
    }
    ArrayList<Piece> secondPlayerPieces = new ArrayList<Piece>();
    for (int pieceNumber : secondPlayerPiecesNumbers) {
      secondPlayerPieces.add(Piece.createNewWithNumber(pieceNumber));
    }

    board.placePieces(firstPlayerPieces, secondPlayerPieces);

  }
}