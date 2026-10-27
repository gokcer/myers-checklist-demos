// DR04 · Data reference · Does the data a reference points to still exist?
// Myers talks about dangling pointers. Java has no dangling pointers, but a reference can still point to
// something that is "used up": an Iterator can be read only once.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DR04ReferencedDataGone {

    static final List<Integer> ORDERS = Arrays.asList(120, 45, 300);

    static List<Integer> bigOrders() {
        List<Integer> big = new ArrayList<>();
        for (int order : ORDERS) {
            if (order > 100) {
                big.add(order);
            }
        }
        return big;
    }

    static String summaryBuggy() {
        List<Integer> big = bigOrders();
        Iterator<Integer> it = big.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        int total = 0;
        while (it.hasNext()) {            // BUG: the same iterator is already at the end -> total stays 0
            total += it.next();
        }
        return count + " big orders, total " + total;
    }

    static String summaryFixed() {
        List<Integer> big = bigOrders();
        Iterator<Integer> it = big.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        int total = 0;
        it = big.iterator();              // FIX: a new iterator for the second pass
        while (it.hasNext()) {
            total += it.next();
        }
        return count + " big orders, total " + total;
    }

    public static void main(String[] args) {
        System.out.println("DR04 Referenced data no longer exists (Java)");
        System.out.println("  BUG -> " + summaryBuggy());
        System.out.println("  FIX -> " + summaryFixed());
    }
}
