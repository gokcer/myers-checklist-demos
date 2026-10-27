// DR09 · Data reference · Is a data structure defined the same way in every place that uses it?
// The writer and the reader each define the CSV columns themselves, and they do not agree.
public class DR09StructureDefinedTwice {

    static final class WriterBuggy {
        static final int NAME = 0;                 // the writer's idea: name, age
        static final int AGE = 1;

        static String row(String name, int age) {
            String[] fields = new String[2];
            fields[NAME] = name;
            fields[AGE] = String.valueOf(age);
            return fields[0] + "," + fields[1];
        }
    }

    static final class ReaderBuggy {
        static final int AGE = 0;                  // BUG: the reader's own idea: age, name

        static int age(String row) {
            String[] fields = row.split(",");
            return Integer.parseInt(fields[AGE]);
        }
    }

    static final class Columns {                   // FIX: ONE definition of the columns ...
        static final int NAME = 0;
        static final int AGE = 1;
    }

    static final class WriterFixed {
        static String row(String name, int age) {
            String[] fields = new String[2];
            fields[Columns.NAME] = name;           // ... used by the writer ...
            fields[Columns.AGE] = String.valueOf(age);
            return fields[0] + "," + fields[1];
        }
    }

    static final class ReaderFixed {
        static int age(String row) {
            String[] fields = row.split(",");
            return Integer.parseInt(fields[Columns.AGE]);   // ... and by the reader
        }
    }

    public static void main(String[] args) {
        System.out.println("DR09 Structure defined differently in two places (Java)   expected 36");
        try {
            System.out.println("  BUG -> " + ReaderBuggy.age(WriterBuggy.row("Ada", 36)));
        } catch (NumberFormatException e) {
            System.out.println("  BUG -> NumberFormatException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + ReaderFixed.age(WriterFixed.row("Ada", 36)));
    }
}
