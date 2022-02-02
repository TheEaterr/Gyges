package src.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import src.board.Board;

public class BoardGUIHandler {  
    JFrame f;
    final JPanel gui = new JPanel();
    private JPanel piecePicker = new JPanel();
    private JPanel gygesBoard;
    private JPanel topLine;
    private JPanel bottomLine;
    private Board board;

    public BoardGUIHandler(Board board) {
        this.board = board;
        gui.setLayout(new BoxLayout(gui, BoxLayout.Y_AXIS));
        gui.setBorder(new EmptyBorder(0, 0, 0, 0));
        topLine = new JPanel();
        topLine.setBackground(new Color(180, 90, 0));
        gui.add(topLine);
        gygesBoard = new JPanel(new GridLayout(this.board.numberOfLines, this.board.numberOfColumns));
        gygesBoard.setBackground(new Color(180, 90, 0));
        gui.add(gygesBoard);
        bottomLine = new JPanel();
        bottomLine.setBackground(new Color(180, 90, 0));
        gui.add(bottomLine);
    }

    public void addCellGUIHandler(CellGUIHandler cellGUIHandler) {
        gygesBoard.add(cellGUIHandler);
    }

    public void addTopLineCellGUIHandler(CellGUIHandler cellGUIHandler) {
        this.topLine.add(cellGUIHandler);
    }

    public void addBottomLineCellGUIHandler(CellGUIHandler cellGUIHandler) {
        this.bottomLine.add(cellGUIHandler);
    }

    public void displayBoard() {
        f = new JFrame("Gyges");
        f.add(gui);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setResizable(true);
        f.setVisible(true);
    }

    public void displayPiecePicker() {
        this.piecePicker.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.piecePicker.add(new CellGUIHandler());
        gui.add(this.piecePicker);
    }

    public void hidePiecePicker() {
        gui.remove(this.piecePicker);
    }
        
    public final JComponent getGui() {
        return gui;
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
        JOptionPane.showMessageDialog(f, endGameMessage);
    }
}  