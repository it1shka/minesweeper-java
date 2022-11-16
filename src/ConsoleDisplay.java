import java.util.Scanner;

public class ConsoleDisplay implements Display.DisplayImpl {

    private final Scanner input = new Scanner(System.in);
    private String action;
    private int row;
    private int col;
    private int boardSize;
    private Minesweeper minesweeper;

    @Override public void renderLoop(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        boardSize = minesweeper.getBoardSize();
        while (true) {
            render();
            var status = minesweeper.checkStatus();
            switch(status) {
                case WON -> {
                    System.out.println("You WON the game!");
                    return;
                }
                case LOST -> {
                    System.out.println("You LOST the game!");
                    return;
                }
            }

            getGamePrompt();
            switch (action) {
                case "flag" -> minesweeper.toggleFlag(row, col);
                case "defuse" -> minesweeper.defuse(row, col);
            }
        }
    }

    private void render() {
        clearConsole();
        printHorizontalCoords();
        printHorizontalSeparator();

        final var board = minesweeper.getBoard();

        for(var i = 0; i < boardSize; i++) {
            System.out.printf("%d|", i);
            for (final var cell : board[i]) {
                System.out.print(cell);
            }
            System.out.printf("|%d", i);
            System.out.println();
        }

        printHorizontalSeparator();
        printHorizontalCoords();
    }

    private void getGamePrompt() {
        System.out.println("Enter your next move: ");
        action = input.next();
        if(action.equals("exit") || action.equals("quit"))
            System.exit(0);
        row = input.nextInt();
        col = input.nextInt();
    }

    private void printHorizontalCoords() {
        var builder = new StringBuilder();
        builder.append("  ");
        for(var i = 0; i < boardSize; i++) {
            var chr = (char)(i + '0');
            builder.append(chr);
        }
        var line = builder.toString();
        System.out.println(line);
    }

    private void printHorizontalSeparator() {
        System.out.print("  ");
        System.out.print("-".repeat(boardSize));
        System.out.println("  ");
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}