package src.board;

import java.util.ArrayList;
import java.util.HashSet;

import src.piece.*;
import src.game.*;
import src.gui.*;

public class Board {
    final public int numberOfLines = 6;
    final public int numberOfColumns = 6;
    final private BoardGUIHandler guiHandler;
    final private ArrayList<ArrayList<Cell>> boardStateArray;
    final private HashSet<Cell> highlightedCells;
    final private Cell topLine;
    final private Cell bottomLine;
    final private HashSet<PiecePickerCell> piecePickerBoard;
    final private Game game;
    public Cell selectedCell;
    private Move currentMove;
    private PiecePick currentPiecePick;

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
        this.game = new Game(this);
        this.currentPiecePick = new PiecePick(game);

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
        piecePickerBoard = new HashSet<PiecePickerCell>();
        HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
        for (int i = 0; i < 3; i++) {
            PiecePickerCell cell = new PiecePickerCell(i, this.numberOfColumns, this);
            CellGUIHandler cellGUIHandler = cell.getCellGUIHandler();
            this.guiHandler.addPiecePickerCellGUIHandler(cellGUIHandler);
            Piece piece = Piece.createNewWithNumber(i + 1);
            cell.setPieceOnTop(piece);
            piecePickerBoard.add(cell);
            cellsToHighlight.add(cell);
        }
        highlightCells(cellsToHighlight);
    }

    public void highlightCellsToPickPieces() {
        ArrayList<Cell> line;
        if (this.game.getTurn()) {
            line = this.boardStateArray.get(0);
        }
        else {
            line = this.boardStateArray.get(this.numberOfLines - 1);
        }
        HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
        for (Cell cell : line) {
            if (cell.getPiece() == null) {
                cellsToHighlight.add(cell);
            }
        }
        highlightCells(cellsToHighlight);
    }

    public void highlightCells(HashSet<Cell> cellsToHighlight) {
        for (Cell cell : cellsToHighlight) {
            cell.highlight();
            highlightedCells.add(cell);
        }
    }

    public void clearHighlightedCells() {
        for (Cell hightlightedCell : this.highlightedCells) {
            hightlightedCell.removeHighlight();
        }
        highlightedCells.clear();
    }

    public void movePiece(Cell endCell) {
        Cell startCell = this.selectedCell;
        Piece pieceToMove = startCell.getPieceOnTop();
        startCell.removePieceOnTop();
        boolean isPieceBouncing = endCell.setPieceOnTop(pieceToMove);
        clearHighlightedCells();
        if (endCell.getClass() == GoalCell.class) {
            this.endGame();
        }
        else if (isPieceBouncing) {
            this.selectedCell = endCell;
            HashSet<Cell> cellsToHighlight = endCell.getPiece().getPossibleSteps(this, endCell);
            this.highlightCells(cellsToHighlight);
        }
        else {
            this.startNewMove(endCell);
        }
    }

    public void pickPiece(Piece piece) {
        highlightCellsToPickPieces();
        this.currentPiecePick.selectPiece(piece);
    }

    public void pickCell(Cell cell) {
        cell.setPieceOnTop(this.currentPiecePick.getSelectedPiece());
        this.currentPiecePick.selectCell(cell);
        clearHighlightedCells();
        HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
        if (this.game.getState() == Game.gameStarted) {
            this.guiHandler.hidePiecePicker();
            this.currentMove = new Move(game);
            highlightCellsAvailableToMove();
        }
        else {
            this.currentPiecePick = new PiecePick(game);
            for (PiecePickerCell piecePickerCell : piecePickerBoard) {
                int pieceNumber = piecePickerCell.getPieceOnTop().getNumber();
                if (game.allowPiecePick(pieceNumber)) {
                    cellsToHighlight.add(piecePickerCell);
                }
            }
            highlightCells(cellsToHighlight);
        }
    }

    public void selectMoveStartCell(Cell startCell) {
        this.selectedCell = startCell;
        currentMove.setStartCell(startCell);
        this.clearHighlightedCells();
        HashSet<Cell> cellsToHighlight = startCell.getPiece().getPossibleSteps(this, startCell);
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
            if (cell.getPiece() != null) {
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

    public void displayBoard() {
        guiHandler.displayBoard();
    }
}