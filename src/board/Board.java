package board;

import java.util.ArrayList;
import java.util.HashSet;

import frontend.BoardFrontEndHandler;
import frontend.CellFrontEndHandler;
import frontend.FrontEndFactory;
import piece.*;
import game.*;

public class Board {
    final public int numberOfLines = 6;
    final public int numberOfColumns = 6;
    final private BoardFrontEndHandler frontEndHandler;
    final private ArrayList<ArrayList<Cell>> boardStateArray;
    final private HashSet<Cell> highlightedCells;
    final private Cell topLine;
    final private Cell bottomLine;
    final private HashSet<PiecePickerCell> piecePickerBoard;
    final private FrontEndFactory frontEndFactory;
    private Game game;
    public Cell selectedCell;
    private Move currentMove;
    private PiecePick currentPiecePick;

    public Board(int frontEndMode) {
        frontEndFactory = new FrontEndFactory(frontEndMode);
        this.boardStateArray = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < numberOfLines; i++) {
            ArrayList<Cell> line = new ArrayList<Cell>();
            for (int j = 0; j < numberOfColumns; j++) {
                line.add(null);
            }
            this.boardStateArray.add(line);
        }
        frontEndHandler = frontEndFactory.createBoardFrontEndHandler(this);
        highlightedCells = new HashSet<Cell>();
        
        for (int i = numberOfLines - 1; i >= 0; i--) {
            ArrayList<Cell> line = this.boardStateArray.get(i);
            for (int j = 0; j < numberOfColumns; j++) {
                Cell cell = new Cell(i, j, this);
                CellFrontEndHandler cellFrontEndHandler = cell.getCellFrontEndHandler();
                this.frontEndHandler.addCellFrontEndHandler(cellFrontEndHandler);
                line.set(j, cell);
            }
        }
        {
            Cell cell = new GoalCell(this.numberOfLines, -1, this);
            CellFrontEndHandler cellFrontEndHandler = cell.getCellFrontEndHandler();
            this.frontEndHandler.addTopLineCellFrontEndHandler(cellFrontEndHandler);
            this.topLine = cell;
        }
        {
            Cell cell = new GoalCell(-1, -1, this);
            CellFrontEndHandler cellFrontEndHandler = cell.getCellFrontEndHandler();
            this.frontEndHandler.addBottomLineCellFrontEndHandler(cellFrontEndHandler);
            this.bottomLine = cell;
        }
        piecePickerBoard = new HashSet<PiecePickerCell>();
        HashSet<Cell> cellsToHighlight = new HashSet<Cell>();
        for (int i = 0; i < 3; i++) {
            PiecePickerCell cell = new PiecePickerCell(i, this.numberOfColumns, this);
            CellFrontEndHandler cellFrontEndHandler = cell.getCellFrontEndHandler();
            this.frontEndHandler.addPiecePickerCellFrontEndHandler(cellFrontEndHandler);
            Piece piece = Piece.createNewWithNumber(i + 1);
            cell.setPieceOnTop(piece);
            piecePickerBoard.add(cell);
            cellsToHighlight.add(cell);
        }
        game = new Game(this);
        currentPiecePick = new PiecePick(game);
        highlightCells(cellsToHighlight);
    }

    public FrontEndFactory getFrontEndFactory() {
        return frontEndFactory;
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
            this.frontEndHandler.hidePiecePicker();
            startNewMove(null);
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
        highlightCells(currentMove.getPossibleStartCells());
    }

    private void addPossiblePathsForCellsOnLine(ArrayList<Cell> line, ArrayList<Path> allPossiblePaths, HashSet<Cell> possibleStartCells) {
        for (Cell cell : line) {
            if (cell.getPiece() != null) {
                allPossiblePaths.addAll(Piece.getPossiblePaths(this, cell));
                possibleStartCells.add(cell);
            }
        }
    }

    private void startNewMove(Cell endCell) {
        if (endCell != null) {
            this.currentMove.setEndCell(endCell);
        }
        ArrayList<Path> allPossiblePaths = new ArrayList<Path>();
        HashSet<Cell> possibleStartCells = new HashSet<Cell>();
        if (this.game.getTurn()) {
            for (int i = 0; i < numberOfLines; i++) {
                ArrayList<Cell> line = this.boardStateArray.get(i);
                addPossiblePathsForCellsOnLine(line, allPossiblePaths, possibleStartCells);
                if (possibleStartCells.size() > 0) {
                    break;
                }
            }
        }
        else {
            for (int i = numberOfLines - 1; i > 0; i--) {
                ArrayList<Cell> line = this.boardStateArray.get(i);
                addPossiblePathsForCellsOnLine(line, allPossiblePaths, possibleStartCells);
                if (possibleStartCells.size() > 0) {
                    break;
                }
            }
        }
        if (possibleStartCells.size() == 0) {
            endGame();
        }
        else {
            this.currentMove = new Move(game, allPossiblePaths, possibleStartCells);
            this.highlightCellsAvailableToMove();
        }
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
        frontEndHandler.showEndGameMessage(winnerIsPlayer1);
        restartGame();
    }

    private void restartGame() {
        clearHighlightedCells();
        clearBoard();
        frontEndHandler.displayPiecePicker();
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
        frontEndHandler.displayBoard();
    }
}