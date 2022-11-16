import javax.swing.*;
import java.awt.*;

public class WindowDisplay implements Display.DisplayImpl {

    private static final int cellSize = 30;
    private int boardSize;
    private JFrame window;
    private JPanel panel;
    private JButton[][] cells;
    private Minesweeper minesweeper;
    private boolean isFinished;

    @Override public void renderLoop(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        isFinished = false;
        boardSize = minesweeper.getBoardSize();
        createWindow();
        createPanel();
        createBoard();
        window.setVisible(true);
        updateWindow();
    }

    public void createWindow() {
        window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final var realSize = cellSize * boardSize;
        window.setSize(realSize, realSize);
    }

    public void createPanel() {
        panel = new JPanel();
        final var layout = new GridLayout(boardSize, boardSize);
        panel.setLayout(layout);
        layout.setVgap(1);
        layout.setHgap(1);
        panel.setBackground(Color.LIGHT_GRAY);
        window.add(panel);
    }

    public void createBoard() {
        cells = new JButton[boardSize][boardSize];
        final var font = new Font("Arial", Font.BOLD, 24);
        for (var row = 0; row < boardSize; row++) {
            for (var col = 0; col < boardSize; col++) {
                final var cell = new JButton();
                cell.setFont(font);
                cells[row][col] = cell;
                panel.add(cell);

                final var $row = row;
                final var $col = col;
                cell.addActionListener(e -> onButtonClick($row, $col));
            }
        }
    }

    private void onButtonClick(int row, int col) {
        if (isFinished) return;
        minesweeper.defuse(row, col);
        updateWindow();
        isFinished = minesweeper.checkStatus() != Minesweeper.GameStatus.IN_PROCESS;
    }

    private void updateWindow() {
        final var board = minesweeper.getBoard();
        for (var row = 0; row < boardSize; row++) {
            for (var col = 0; col < boardSize; col++) {
                final var cell = cells[row][col];
                final var value = board[row][col];
                cell.setForeground(switch (value) {
                    case '#' -> Color.ORANGE;
                    case 'F' -> Color.GREEN;
                    case 'B' -> Color.RED;

                    // digits
                    case '1' -> Color.BLUE;
                    case '2' -> new Color(1, 110, 30);
                    case '3' -> Color.RED;
                    case '4' -> Color.GRAY;

                    default -> Color.BLACK;
                });
                cell.setText(Character.toString(value));
            }
        }
    }

}
