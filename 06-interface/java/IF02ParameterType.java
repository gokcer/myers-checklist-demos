// IF02 · Interface · Do the attributes (type, size) of each argument match the parameter?
// List<Integer>.remove(1) calls remove(int index), not remove(Object value).
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IF02ParameterType {

    // spec: remove the user id 1 from the list
    static String removeIdBuggy() {
        List<Integer> ids = new ArrayList<>(Arrays.asList(5, 9, 1));
        ids.remove(1);                          // BUG: an int argument -> remove(int index): removes 9
        return ids.toString();
    }

    static String removeIdFixed() {
        List<Integer> ids = new ArrayList<>(Arrays.asList(5, 9, 1));
        ids.remove(Integer.valueOf(1));         // FIX: an Integer argument -> remove(Object value)
        return ids.toString();
    }

    public static void main(String[] args) {
        System.out.println("IF02 Argument type picks a different method (Java)   remove id 1 from [5, 9, 1]");
        System.out.println("  BUG -> " + removeIdBuggy());
        System.out.println("  FIX -> " + removeIdFixed());
    }
}
