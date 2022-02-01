package src.board;

import java.util.ArrayList;
import java.util.HashSet;

import src.piece.*;
import src.game.*;
import src.gui.*;

public class Board {
    final public int numberOfLines = 6;
    final public int numberOfColumns = 6;
    final BoardGUIHandler guiHandler;
    ArrayList<ArrayList<Cell>> boardStateArray;
    private HashSet<Cell> highlightedCells;
    private Cell topLine;
    private Cell bottomLine;
    public Cell selectedCell;
    private Move currentMove;
    private Game game;

    public Board() {
        this.boardStateArray = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < numberOfLines; i++) {
            ArrayList<Cell> line = new ArrayList<Cell>();
            for (int j = 0; j < numberOfColumns; j++) {
                line.add(null);
            }
            this.boardStateArray.add(line);
        }
        this.guiHandler = new BoardGUIHandler(this);
        this.highlightedCells = new HashSet<Cell>();
        this.game = new Game();
        this.currentMove = new Move(this.game);

        for (int i = numberOfLines - 1; i >= 0; i--) {
            ArrayList<Cell> line = this.boardStateArray.get(i);
            for (int j = numberOfColumns -1; j >= 0; j--) {
                Cell cell = new Cell(i, j, this);
                CellGUIHandler cellGUIHandler = cell.getCellGUIHandler();
                this.guiHandler.addCellGUIHandler(cellGUIHandler);
                line.set(j, cell);
            }
        }
        {
        Cell cell = new GoalCell(this.numberOfLines, -1, this);
        CellGUIHandler cellGUIHandler = cell.getCellGUIHandler();
        this.guiHandler.addTopLineCellGUIHandler(cellGUIHandler);
        this.topLine = cell;
        }
        {
        Cell cell = new GoalCell(-1, -1, this);
        CellGUIHandler cellGUIHandler = cell.getCellGUIHandler();
        this.guiHandler.addBottomLineCellGUIHandler(cellGUIHandler);
        this.bottomLine = cell;
        }

        this.guiHandler.displayBoard();
    }

    public void placePieces(ArrayList<Piece> firstPlayerPieces, ArrayList<Piece> secondPlayerPieces) {
        for (int i = 0; i < this.numberOfColumns; i++) {
            Piece piece = firstPlayerPieces.get(i);
            Cell cell = this.boardStateArray.get(0).get(i);
            cell.setPieceOnTopOfCell(piece);
        }
        for (int i = 0; i < this.numberOfColumns; i++) {
            Piece piece = secondPlayerPieces.get(i);
            Cell cell = this.boardStateArray.get(this.numberOfLines - 1).get(i);
            cell.setPieceOnTopOfCell(piece);
        }
        highlightCellsAvailableToMove();
    }

    public void highlightCells(HashSet<Cell> cellsToHightlight) {
        for (Cell cell : cellsToHightlight) {
            cell.highlight();
        }
        this.highlightedCells = cellsToHightlight;
    }

    public void clearHighlightedCells() {
        for (Cell hightlightedCell : this.highlightedCells) {
            hightlightedCell.removeHighlight();
        }
        highlightedCells.clear();
    }

    public void movePiece(Cell endCell) {
        Cell startCell = this.selectedCell;
        Piece pieceToMove = startCell.getPieceOnTopOfCell();
        startCell.removePieceOnTopOfCell();
        boolean isPieceBouncing = endCell.setPieceOnTopOfCell(pieceToMove);
        clearHighlightedCells();
        if (endCell.getClass() == GoalCell.class) {
            this.endGame();
        }
        else if (isPieceBouncing) {
            this.selectedCell = endCell;
            HashSet<Cell> cellsToHighlight = endCell.pieceOnCell.getPossibleSteps(this, endCell);
            this.highlightCells(cellsToHighlight);
        }
        else {
            this.startNewMove(endCell);
        }
    }

    public void selectMoveStartCell(Cell startCell) {
        this.selectedCell = startCell;
        currentMove.setStartCell(startCell);
        this.clearHighlightedCells();
        HashSet<Cell> cellsToHighlight = startCell.pieceOnCell.getPossibleSteps(this, startCell);
        cellsToHighlight.add(startCell);
        this.highlightCells(cellsToHighlight);
    }

    public void removeMoveStartCell() {
        currentMove.removeStartCell();
        this.clearHighlightedCells();
        this.highlightCellsAvailableToMove();
    }

    private void highlightCellsAvailableToMove() {
        if (this.game.getTurn()) {
            for (int i = 0; i < numberOfLines; i++) {
                ArrayList<Cell> line = this.boardStateArray.get(i);
                if (isPieceOnLine(line)) {
                    highlightCellsOnLine(line);
                    break;
                }
            }
        }
        else {
            for (int i = numberOfLines - 1; i > 0; i--) {
                ArrayList<Cell> line = this.boardStateArray.get(i);
                if (isPieceOnLine(line)) {
                    highlightCellsOnLine(line);
                    break;
                }
            }
        }
    }

    private boolean isPieceOnLine(ArrayList<Cell> line) {
        boolean isPieceOnLine = false;
        for (Cell cell : line) {
            if (cell.pieceOnCell != null) {
                isPieceOnLine = true;
            }
        }
        return isPieceOnLine;
    }

    private void highlightCellsOnLine(ArrayList<Cell> line) {
        HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
        for (Cell cell : line) {
            if (cell.getPiece() != null) {
                cellsToHighlight.add(cell);
            }
        }
        this.highlightCells(cellsToHighlight);
    }

    private void startNewMove(Cell endCell) {
        this.currentMove.setEndCell(endCell);
        this.currentMove = new Move(game);
        this.highlightCellsAvailableToMove();
    }

    private void endGame() {
        boolean winnerIsPlayer1 = this.game.getTurn();
        Cell endCell;
        if (winnerIsPlayer1) {
            endCell = this.getTopLineCell();
        }
        else {
            endCell = this.getBottomLineCell();
        }
        this.currentMove.setEndCell(endCell);
        this.guiHandler.showEndGameMessage(winnerIsPlayer1);
    }

    public Move getMove() {
        return this.currentMove;
    }

    public Cell getCell(int line, int column) {
        return boardStateArray.get(line).get(column);
    }

    public Cell getTopLineCell() {
        return topLine;
    }

    public Cell getBottomLineCell() {
        return bottomLine;
    }

    public Game getGame() {
        return this.game;
    }
}