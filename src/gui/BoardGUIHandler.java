package src.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.DimensionUIResource;

import src.board.Board;

public class BoardGUIHandler {  
    JFrame f;
    final JPanel boardGUI = new JPanel();
    final private JPanel gui = new JPanel();
    private JPanel piecePickerGUI = new JPanel();
    private JPanel mainBoardGUI;
    private JPanel topLineGUI;
    private JPanel bottomLineGUI;
    private Board board;

    public BoardGUIHandler(Board board) {
        this.board = board;
        gui.setLayout(new BoxLayout(gui, BoxLayout.X_AXIS));
        gui.setBackground(new Color(180, 90, 0));
        boardGUI.setLayout(new BoxLayout(boardGUI, BoxLayout.Y_AXIS));
        boardGUI.setBorder(new EmptyBorder(0, 0, 0, 0));
        boardGUI.setBackground(new Color(180, 90, 0));
        topLineGUI = new JPanel();
        topLineGUI.setBackground(new Color(180, 90, 0));
        boardGUI.add(topLineGUI);
        mainBoardGUI = new JPanel(new GridLayout(this.board.numberOfLines, this.board.numberOfColumns));
        mainBoardGUI.setBackground(new Color(180, 90, 0));
        boardGUI.add(mainBoardGUI);
        bottomLineGUI = new JPanel();
        bottomLineGUI.setBackground(new Color(180, 90, 0));
        boardGUI.add(bottomLineGUI);
        this.piecePickerGUI.setLayout(new BoxLayout(this.piecePickerGUI, BoxLayout.Y_AXIS));
        this.piecePickerGUI.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        this.piecePickerGUI.setBackground(new Color(180, 90, 0));
    }

    public void addCellGUIHandler(CellGUIHandler cellGUIHandler) {
        mainBoardGUI.add(cellGUIHandler);
    }

    public void addTopLineCellGUIHandler(CellGUIHandler cellGUIHandler) {
        this.topLineGUI.add(cellGUIHandler);
    }

    public void addBottomLineCellGUIHandler(CellGUIHandler cellGUIHandler) {
        this.bottomLineGUI.add(cellGUIHandler);
    }

    public void addPiecePickerCellGUIHandler(CellGUIHandler cellGUIHandler) {
        this.piecePickerGUI.add(cellGUIHandler);
    }

    public void displayBoard() {
        f = new JFrame("Gyges");
        gui.add(boardGUI);
        f.add(gui);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        displayPiecePicker();

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setResizable(true);
        f.setVisible(true);
    }

    public void displayPiecePicker() {
        this.piecePickerGUI.setMaximumSize(this.piecePickerGUI.getPreferredSize());
        f.setMinimumSize(new DimensionUIResource(0, 0));
        gui.add(this.piecePickerGUI);
        f.pack();
        f.setMinimumSize(f.getSize());
    }

    public void hidePiecePicker() {
        f.setMinimumSize(new DimensionUIResource(0, 0));
        this.piecePickerGUI.setVisible(false);
        gui.remove(this.piecePickerGUI);
        f.pack();
        f.setMinimumSize(f.getSize());
    }
        
    public final JComponent getGui() {
        return boardGUI;
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