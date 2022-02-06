package src;

import src.board.*;
import src.piece.DoublePiece;
import src.piece.Piece;
import src.piece.SimplePiece;

class Main {
  public static void main(String[] args) {
    
    Board board = new Board();
    board.displayBoard();

    Cell testCell = board.getCell(0, 0);
    Cell testCell2 = board.getCell(0, 1);
    testCell2.setPieceOnTop(new DoublePiece());
    testCell.setPieceOnTop(new SimplePiece());
    System.out.println(Piece.getPossibleMoves(board, testCell));
  }
}