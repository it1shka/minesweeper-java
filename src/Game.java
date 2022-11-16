public class Game {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Expected 3 arguments, found " + args.length);
            System.out.println("Arguments are: ");
            System.out.println("<display method> <board size> <bombs amount>");
            System.exit(1);
        }
        final var displayMethod = args[0];
        final var boardSize = Integer.parseInt(args[1]);
        final var bombsAmount = Integer.parseInt(args[2]);

        final var display = Display.getDisplay(displayMethod);
        final var minesweeper = new Minesweeper(boardSize, bombsAmount);
        display.renderLoop(minesweeper);
    }

}
