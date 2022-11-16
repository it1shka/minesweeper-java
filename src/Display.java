public class Display {
    public interface DisplayImpl {
        void renderLoop(Minesweeper minesweeper);
    }

    public static DisplayImpl getDisplay(final String displayType) {
        return switch (displayType) {
            case "window" -> new WindowDisplay();
            case "console" -> new ConsoleDisplay();
            default -> new NoDisplay();
        };
    }

    public static class NoDisplay implements DisplayImpl {
        @Override public void renderLoop(Minesweeper minesweeper) {
            System.out.println("No acceptable display method was provided");
        }
    }

}
