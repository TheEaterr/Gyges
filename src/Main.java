import board.Board;
import frontend.FrontEndFactory;

class Main {
    public static void main(String[] args) {

        int frontEndMode = FrontEndFactory.GUI_MODE;
        for (String string : args) {
            if (string.equals("-cmd")) {
                frontEndMode = FrontEndFactory.CMD_MODE;
            }
        }
        Board board = new Board(frontEndMode);
        board.displayBoard();
    }
}