import java.util.Scanner;

public class Game {
    private static final Scanner input = new Scanner(System.in);
    private static Minesweeper sweeper;
    private static String action;
    private static int row, col;

    public static void main(String[] args) {
        Utils.clearConsole();
        final var boardSize = promptInt("Board size: ");
        final var bombsAmount = promptInt("Bombs amount: ");
        sweeper = new Minesweeper(boardSize, bombsAmount);
        gameLoop();
    }

    private static void gameLoop() {
        while(true) {
            sweeper.render();

            var status = sweeper.checkStatus();
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
                case "flag" -> sweeper.toggleFlag(row, col);
                case "defuse" -> sweeper.defuse(row, col);
            }
        }
    }

    private static int promptInt(String prompt) {
        System.out.println(prompt);
        return input.nextInt();
    }

    private static void getGamePrompt() {
        System.out.println("Enter your next move: ");
        action = input.next();
        if(action.equals("exit") || action.equals("quit"))
            System.exit(0);
        row = input.nextInt();
        col = input.nextInt();
    }

}
