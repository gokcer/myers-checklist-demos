// IF06 · Interface · Does a method change a parameter that should only be an input?
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IF06AltersInputParameter {

    static int medianBuggy(List<Integer> values) {
        List<Integer> sorted = values;                       // BUG: the caller's list, not a copy
        Collections.sort(sorted);
        return sorted.get(sorted.size() / 2);
    }

    static int medianFixed(List<Integer> values) {
        List<Integer> sorted = new ArrayList<>(values);      // FIX: sort a copy
        Collections.sort(sorted);
        return sorted.get(sorted.size() / 2);
    }

    public static void main(String[] args) {
        System.out.println("IF06 Method alters an input parameter (Java)   arrival order 30, 10, 20");
        List<Integer> arrivals = new ArrayList<>(Arrays.asList(30, 10, 20));
        int median = medianBuggy(arrivals);
        System.out.println("  BUG -> median " + median + ", arrivals now " + arrivals);
        arrivals = new ArrayList<>(Arrays.asList(30, 10, 20));
        median = medianFixed(arrivals);
        System.out.println("  FIX -> median " + median + ", arrivals now " + arrivals);
    }
}
