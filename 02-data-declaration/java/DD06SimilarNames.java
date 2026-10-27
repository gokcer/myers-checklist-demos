// DD06 · Data declaration · Are there variables with similar names (VOLT and VOLTS)?
import java.util.Arrays;
import java.util.List;

public class DD06SimilarNames {

    // spec: return the average of the readings
    static double averageBuggy(List<Double> readings) {
        double reading = readings.get(0);       // a variable whose name looks like 'readings'
        double sum = 0;
        for (double r : readings) {
            sum += reading;                     // BUG: 'reading' instead of 'r'
        }
        return sum / readings.size();
    }

    static double averageFixed(List<Double> readings) {
        double sum = 0;                         // FIX: no look-alike variable, a clear loop name
        for (double value : readings) {
            sum += value;
        }
        return sum / readings.size();
    }

    public static void main(String[] args) {
        System.out.println("DD06 Similar names (Java)   readings 10, 20, 30   expected 20.0");
        List<Double> data = Arrays.asList(10.0, 20.0, 30.0);
        System.out.println("  BUG -> " + averageBuggy(data));
        System.out.println("  FIX -> " + averageFixed(data));
    }
}
