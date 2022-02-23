package frontend.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import frontend.BoardFrontEndHandler;
import frontend.CellFrontEndHandler;
import board.Board;

public class BoardGUIHandler extends BoardFrontEndHandler{  
    private JFrame f;
    final private JPanel boardGUI;
    final private JPanel gui;
    final private JPanel piecePickerGUI;
    final private JPanel mainBoardGUI;
    final private JPanel topLineGUI;
    final private JPanel bottomLineGUI;
    final private Board board;

    public BoardGUIHandler(Board board) {
        this.board = board;
        gui = new JPanel();
        gui.setLayout(new BoxLayout(gui, BoxLayout.X_AXIS));
        gui.setBackground(new Color(180, 90, 0));

        boardGUI = new JPanel();
        boardGUI.setLayout(new BoxLayout(boardGUI, BoxLayout.Y_AXIS));
        boardGUI.setOpaque(false);

        topLineGUI = new JPanel();
        topLineGUI.setLayout(new BorderLayout());
        topLineGUI.setOpaque(false);

        mainBoardGUI = new JPanel(new GridLayout(this.board.numberOfLines, this.board.numberOfColumns));
        mainBoardGUI.setOpaque(false);

        bottomLineGUI = new JPanel();
        bottomLineGUI.setLayout(new BorderLayout());
        bottomLineGUI.setOpaque(false);

        boardGUI.add(topLineGUI);
        boardGUI.add(mainBoardGUI);
        boardGUI.add(bottomLineGUI);

        piecePickerGUI = new JPanel();
        piecePickerGUI.setLayout(new BoxLayout(piecePickerGUI, BoxLayout.Y_AXIS));
        piecePickerGUI.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        piecePickerGUI.setOpaque(false);
    }

    public void addCellGUIHandler(CellGUIHandler cellGUIHandler) {
        mainBoardGUI.add(cellGUIHandler);
    }

    public void addCellGUIHandler(CellFrontEndHandler cellGUIHandler) {}

    public void addTopLineCellGUIHandler(CellGUIHandler cellGUIHandler) {
        topLineGUI.add(cellGUIHandler, BorderLayout.CENTER);
        topLineGUI.setMaximumSize(topLineGUI.getPreferredSize());
    }

    public void addTopLineCellGUIHandler(CellFrontEndHandler cellGUIHandler) {}

    public void addBottomLineCellGUIHandler(CellGUIHandler cellGUIHandler) {
        bottomLineGUI.add(cellGUIHandler, BorderLayout.CENTER);
        bottomLineGUI.setMaximumSize(bottomLineGUI.getPreferredSize());
    }

    public void addBottomLineCellGUIHandler(CellFrontEndHandler cellGUIHandler) {}

    public void addPiecePickerCellGUIHandler(CellGUIHandler cellGUIHandler) {
        piecePickerGUI.add(cellGUIHandler);
    }

    public void addPiecePickerCellGUIHandler(CellFrontEndHandler cellGUIHandler) {
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
        // Keeps these three buttons together. 
        piecePickerGUI.setMaximumSize(piecePickerGUI.getPreferredSize());
        f.setMinimumSize(new DimensionUIResource(0, 0));
        gui.add(piecePickerGUI);
        f.pack();
        f.setMinimumSize(f.getSize());
    }

    public void hidePiecePicker() {
        f.setMinimumSize(new DimensionUIResource(0, 0));
        gui.remove(piecePickerGUI);
        f.pack();
        f.setMinimumSize(f.getSize());
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