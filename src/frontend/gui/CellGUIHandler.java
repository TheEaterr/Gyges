package frontend.gui;

import piece.*;
import board.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.plaf.DimensionUIResource;

public class CellGUIHandler extends JPanel {
    final private ImageIcon cellBackground = new ImageIcon("assets/blank.png");
    final private ImageIcon oneIcon = new ImageIcon("assets/one.png");
    final private ImageIcon twoIcon = new ImageIcon("assets/two.png");
    final private ImageIcon threeIcon = new ImageIcon("assets/three.png");
    final private ImageIcon oneOvertopIcon = new ImageIcon("assets/oneOvertop.png");
    final private ImageIcon twoOvertopIcon = new ImageIcon("assets/twoOvertop.png");
    final private ImageIcon threeOvertopIcon = new ImageIcon("assets/threeOvertop.png");
    final private JButton cellButton;
    final private JPanel labelHolder;
    final private JLabel pieceOnCellLabel;
    final private JLabel overtopLabel;
    final private Insets buttonMargin = new Insets(0,0,0,0);
    final private DimensionUIResource pieceSize = new DimensionUIResource(64, 64);
    private Cell cell;
    
    public CellGUIHandler() {
        setLayout(new BorderLayout());
        setOpaque(false);

        cellButton = new JButton();
        cellButton.setIcon(cellBackground);
        cellButton.setDisabledIcon(cellBackground);
        cellButton.setMargin(buttonMargin);
        cellButton.setContentAreaFilled(false);
        cellButton.setBorderPainted(false);
        cellButton.setEnabled(false);
        cellButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                cell.triggerActivation();
            }  
        });

        labelHolder = new JPanel();
        GroupLayout groupLayout = new GroupLayout(labelHolder);  
        labelHolder.setLayout(groupLayout);
        labelHolder.setPreferredSize(pieceSize);
        labelHolder.setOpaque(false);
        pieceOnCellLabel = new JLabel();
        pieceOnCellLabel.setOpaque(false);
        overtopLabel = new JLabel();
        overtopLabel.setOpaque(false);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.CENTER)
            .addComponent(overtopLabel)
            .addComponent(pieceOnCellLabel)
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.CENTER)
            .addComponent(overtopLabel)
            .addComponent(pieceOnCellLabel)
        );
                
        cellButton.add(labelHolder);
        add(cellButton);
    }
            
    public void highlight() {
        cellButton.setBorderPainted(true);
    }

    public void removeHighlight() {
        cellButton.setBorderPainted(false);
    }

    public void enableButton() {
        cellButton.setEnabled(true);
    }

    public void disableButton() {
        cellButton.setEnabled(false);
    }

    public JButton getButton() {
        return cellButton;
    }

    public JLabel getPieceOnCellLabel() {
        return pieceOnCellLabel;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setPieceOnCell(Piece piece) {
        int pieceNumber = piece.getNumber();
        switch(pieceNumber) {
            case 1:
                pieceOnCellLabel.setIcon(oneIcon);
                break;
            case 2:
                pieceOnCellLabel.setIcon(twoIcon);
                break;
            case 3:
                pieceOnCellLabel.setIcon(threeIcon);
                break;
        }
    }

    public void setPieceOvertopCell(Piece piece) {
        int pieceNumber = piece.getNumber();
        switch(pieceNumber) {
            case 1:
                overtopLabel.setIcon(oneOvertopIcon);
                break;
            case 2:
                overtopLabel.setIcon(twoOvertopIcon);
                break;
            case 3:
                overtopLabel.setIcon(threeOvertopIcon);
                break;
        }
    }

    public void removePiece() {
        disableButton();
        pieceOnCellLabel.setIcon(null);
    }

    public void removePieceOvertopCell() {
        overtopLabel.setIcon(null);
    }
}
