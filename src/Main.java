import java.util.Scanner;

import board.Board;

class Main {
    public static void main(String[] args) {
      
        if (args.length > 0) {
            String flag = args[0];
            System.out.println(flag);
            Scanner input = new Scanner(System.in);
            int i = input.nextInt();
            System.out.println(i);
            input.close();
        }
        Board board = new Board();
        board.displayBoard();
    }
}