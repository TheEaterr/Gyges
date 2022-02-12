package board;

import java.util.ArrayList;
import java.util.HashSet;

import piece.*;
import game.*;
import gui.*;

public class Board {
    final public int numberOfLines = 6;
    final public int numberOfColumns = 6;
    final private BoardGUIHandler guiHandler;
    final private ArrayList<ArrayList<Cell>> boardStateArray;
    final private HashSet<Cell> highlightedCells;
    final private Cell topLine;
    final private Cell bottomLine;
    final private HashSet<PiecePickerCell> piecePickerBoard;
    private Game game;
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
        guiHandler = new BoardGUIHandler(this);
        highlightedCells = new HashSet<Cell>();
        
        for (int i = numberOfLines - 1; i >= 0; i--) {
            ArrayList<Cell> line = this.boardStateArray.get(i);
            for (int j = 0; j < numberOfColumns; j++) {
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
        game = new Game(this);
        currentPiecePick = new PiecePick(game);
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
            currentMove.setBounce(endCell);
            this.selectedCell = endCell;
            HashSet<Bounce> possibleBounces = currentMove.getNextPossibleBounces();
            HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
            for (Bounce bounce : possibleBounces) {
                cellsToHighlight.add(bounce.getLast().getEndCell());
            }
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
        ArrayList<Path> possiblePaths = Piece.getPossiblePaths(this, startCell);
        currentMove.setStartCell(startCell, possiblePaths);
        this.clearHighlightedCells();
        HashSet<Bounce> possibleBounces = currentMove.getNextPossibleBounces();
        HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
        for (Bounce bounce : possibleBounces) {
            cellsToHighlight.add(bounce.getLast().getEndCell());
        }
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
        boolean winnerIsPlayer1 = game.getTurn();
        Cell endCell;
        if (winnerIsPlayer1) {
            endCell = getTopLineCell();
        }
        else {
            endCell = getBottomLineCell();
        }
        currentMove.setEndCell(endCell);
        game.endGame();
        guiHandler.showEndGameMessage(winnerIsPlayer1);
        restartGame();
    }

    private void restartGame() {
        clearHighlightedCells();
        clearBoard();
        guiHandler.displayPiecePicker();
        HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
        for (Cell cell : piecePickerBoard) {
            cellsToHighlight.add(cell);
        }
        game = new Game(this);
        currentPiecePick = new PiecePick(game);
        currentMove = null;
        selectedCell = null;
        highlightCells(cellsToHighlight);
    }

    private void clearBoard() {
        for (ArrayList<Cell> line : boardStateArray) {
            for (Cell cell : line) {
                cell.removePieceOnTop();
                cell.removePieceOnTop();
            }
        }
        topLine.removePieceOnTop();
        bottomLine.removePieceOnTop();
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