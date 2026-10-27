// IF04 · Interface · Are built-in functions called with the right number, types and ORDER of arguments?
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IF04BuiltInArguments {

    // the plate "TR-34-ANK": the city number is 2 characters long and starts at index 3
    static String cityBuggy() {
        String plate = "TR-34-ANK";
        return plate.substring(3, 2);            // BUG: substring(begin, END), not substring(begin, LENGTH)
    }

    static String cityFixed() {
        String plate = "TR-34-ANK";
        return plate.substring(3, 3 + 2);        // FIX: the end index is begin + length
    }

    // how many scores?
    static int countScoresBuggy() {
        int[] scores = {70, 85, 90};
        List<int[]> list = Arrays.asList(scores);    // BUG: a List with ONE element (the whole int[])
        return list.size();
    }

    static int countScoresFixed() {
        int[] scores = {70, 85, 90};
        List<Integer> list = new ArrayList<>();      // FIX: copy the int values one by one
        for (int s : scores) {
            list.add(s);
        }
        return list.size();
    }

    public static void main(String[] args) {
        System.out.println("IF04 Built-in function arguments (Java)");
        try {
            System.out.println("  BUG city number -> " + cityBuggy());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("  BUG city number -> StringIndexOutOfBoundsException: " + e.getMessage());
        }
        System.out.println("  FIX city number -> " + cityFixed());
        System.out.println("  BUG number of scores -> " + countScoresBuggy());
        System.out.println("  FIX number of scores -> " + countScoresFixed());
    }
}
