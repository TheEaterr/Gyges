package src.gui;

import src.piece.*;
import src.board.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class CellGUIHandler extends JPanel {
    private ImageIcon cellBackground = new ImageIcon("src/gui/assets/blank.png");
    private ImageIcon oneIcon = new ImageIcon("src/gui/assets/one.png");
    private ImageIcon twoIcon = new ImageIcon("src/gui/assets/two.png");
    private ImageIcon threeIcon = new ImageIcon("src/gui/assets/three.png");
    private ImageIcon oneOvertopIcon = new ImageIcon("src/gui/assets/oneOvertop.png");
    private ImageIcon twoOvertopIcon = new ImageIcon("src/gui/assets/twoOvertop.png");
    private ImageIcon threeOvertopIcon = new ImageIcon("src/gui/assets/threeOvertop.png");
    private JButton cellButton;
    private JLabel pieceOnCellLabel;
    private JLabel overtopLabel;
    private Insets buttonMargin = new Insets(0,0,0,0);
    private DimensionUIResource pieceSize = new DimensionUIResource(64, 64);
    public Cell cell;
    
    public CellGUIHandler() {
        setLayout(new BorderLayout());
        this.cellButton = new JButton();
        this.cellButton.setIcon(cellBackground);
        this.cellButton.setDisabledIcon(cellBackground);
        this.cellButton.setMargin(buttonMargin);
        this.cellButton.setContentAreaFilled(false);
        this.cellButton.setBorderPainted(false);
        this.cellButton.setEnabled(false);
        this.pieceOnCellLabel = new JLabel();
        this.pieceOnCellLabel.setPreferredSize(pieceSize);
        this.pieceOnCellLabel.setOpaque(false);
        this.overtopLabel = new JLabel();
        this.overtopLabel.setPreferredSize(pieceSize);
        this.overtopLabel.setOpaque(false);
        add(this.cellButton);
        this.cellButton.add(this.overtopLabel);
        this.cellButton.add(this.pieceOnCellLabel);
        setOpaque(false);

        this.cellButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                        cell.triggerActivation();
                    }  
                });
    }

    public void highlight() {
        this.cellButton.setBorderPainted(true);
        this.cell.isHighlighted = true;
    }

    public void removeHighlight() {
        this.cellButton.setBorderPainted(false);
        this.cell.isHighlighted = false;
    }

    public void enableButton() {
        this.cellButton.setEnabled(true);
    }

    public void disableButton() {
        this.cellButton.setEnabled(false);
    }

    public JButton getButton() {
        return this.cellButton;
    }

    public JLabel getPieceOnCellLabel() {
        return this.pieceOnCellLabel;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setPieceOnCell(Piece piece) {
        int pieceNumber = piece.getNumber();
        switch(pieceNumber) {
            case 1:
                this.pieceOnCellLabel.setIcon(oneIcon);
                break;
            case 2:
                this.pieceOnCellLabel.setIcon(twoIcon);
                break;
            case 3:
                this.pieceOnCellLabel.setIcon(threeIcon);
                break;
        }
    }

    public void setPieceOvertopCell(Piece piece) {
        int pieceNumber = piece.getNumber();
        switch(pieceNumber) {
            case 1:
                this.overtopLabel.setIcon(oneOvertopIcon);
                break;
            case 2:
                this.overtopLabel.setIcon(twoOvertopIcon);
                break;
            case 3:
                this.overtopLabel.setIcon(threeOvertopIcon);
                break;
        }
    }

    public void removePiece() {
        this.disableButton();
        this.pieceOnCellLabel.setIcon(null);
    }

    public void removePieceOvertopCell() {
        this.overtopLabel.setIcon(null);
    }
}
