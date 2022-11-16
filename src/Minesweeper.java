import java.util.ArrayList;
import java.util.Stack;

public class Minesweeper {

    private final int boardSize;
    private final int bombsAmount;

    private ArrayList<int[]> bombs;
    private final ArrayList<int[]> flags;
    private final boolean[][] revealed;
    private int[][] countmap;
    private final char[][] board;

    // PUBLIC INTERFACE

    public Minesweeper(int boardSize, int bombsAmount) {
        this.boardSize = boardSize;
        this.bombsAmount = bombsAmount;

        generateBombs();
        generateCountmap();
        flags = new ArrayList<>();
        revealed = new boolean[boardSize][boardSize];
        board = new char[boardSize][boardSize];
    }

    public void toggleFlag(int row, int col) {
        if(!isValidPosition(row, col)) return;
        if(revealed[row][col]) return;
        for(int i = 0; i < flags.size(); i++) {
            var cur = flags.get(i);
            if(cur[0] == row && cur[1] == col) {
                flags.remove(i);
                return;
            }
        }
        flags.add(new int[]{ row, col });
    }

    public void defuse(int row, int col)  {
        if(!isValidPosition(row, col)) return;
        for(final var flag: flags) {
            int r = flag[0], c = flag[1];
            if(r == row && c == col) return;
        }
        var stack = new Stack<int[]>();
        stack.add(new int[]{row, col});
        while(!stack.empty()) {
            var last = stack.pop();
            int r = last[0], c = last[1];
            revealed[r][c] = true;
            if(countmap[r][c] > 0) continue;
            for(var nr = r - 1; nr <= r + 1; nr++) {
                for(var nc = c - 1; nc <= c + 1; nc++) {
                    if(!isValidPosition(nr, nc)) continue;
                    if(revealed[nr][nc]) continue;
                    stack.add(new int[]{nr, nc});
                }
            }
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public char[][] getBoard() {
        prepareBoard();
        return board;
    }

    public enum GameStatus {
        IN_PROCESS, WON, LOST
    }

    public GameStatus checkStatus() {
        var bombsMap = new boolean[boardSize][boardSize];
        for(final var bomb: bombs) {
            int r = bomb[0], c = bomb[1];
            if(revealed[r][c]) return GameStatus.LOST;
            bombsMap[r][c] = true;
        }

        var matchedFlags = 0;
        for(final var flag: flags) {
            int r = flag[0], c = flag[1];
            if(bombsMap[r][c]) matchedFlags++;
        }
        if(matchedFlags == flags.size() && matchedFlags == bombsAmount)
            return GameStatus.WON;

        var unrevealed = 0;
        for(var row = 0; row < boardSize; row++) {
            for(var col = 0; col < boardSize; col++) {
                if(!revealed[row][col]) unrevealed++;
            }
        }
        if(unrevealed == bombsAmount)
            return GameStatus.WON;

        return GameStatus.IN_PROCESS;
    }

    // PRIVATE FUNCTIONS

    private void generateBombs() {
        var possible = new ArrayList<int[]>();
        for(var row = 0; row < boardSize; row++) {
            for(var col = 0; col < boardSize; col++) {
                possible.add(new int[]{row, col});
            }
        }
        bombs = Utils.chooseRandom(possible, bombsAmount);
    }

    private void generateCountmap() {
        countmap = new int[boardSize][boardSize];
        for(final var bomb: bombs) {
            for(var row = bomb[0] - 1; row <= bomb[0] + 1; row++) {
                for(var col = bomb[1] - 1; col <= bomb[1] + 1; col++) {
                    if(isValidPosition(row, col))
                        countmap[row][col]++;
                }
            }
        }
    }

    private char determineCell(int row, int col) {
        if(!revealed[row][col]) return '#';
        var value = countmap[row][col];
        if(value <= 0) return ' ';
        return (char)(value + '0');
    }

    private void prepareBoard() {
        for(var row = 0; row < boardSize; row++) {
            for(var col = 0; col < boardSize; col++) {
                board[row][col] = determineCell(row, col);
            }
        }

        for(final var flag: flags) {
            int r = flag[0], c = flag[1];
            if(revealed[r][c]) continue;
            board[r][c] = 'F';
        }

        for(final var bomb: bombs) {
            int r = bomb[0], c = bomb[1];
            if(!revealed[r][c]) continue;
            board[r][c] = 'B';
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }

}
