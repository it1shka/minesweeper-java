import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    private static final Random rnd = new Random();

    public static <T> T chooseRandom(List<T> array) {
        final var index = rnd.nextInt(array.size());
        final var value = array.get(index);
        array.remove(index);
        return value;
    }

    public static <T> ArrayList<T> chooseRandom(List<T> array, int amount) {
        final var chosen = new ArrayList<T>();
        for(var i = 0; i < amount; i++) {
            var elem = chooseRandom(array);
            chosen.add(elem);
        }
        return chosen;
    }

    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
