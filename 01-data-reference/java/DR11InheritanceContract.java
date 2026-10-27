// DR11 · Data reference · Does the class meet every inheritance requirement?
// Overriding equals() also requires overriding hashCode(). Otherwise HashSet and HashMap break.
import java.util.HashSet;
import java.util.Set;

public class DR11InheritanceContract {

    static final class IsbnBuggy {
        final String isbn;

        IsbnBuggy(String isbn) {
            this.isbn = isbn;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IsbnBuggy)) {
                return false;
            }
            IsbnBuggy other = (IsbnBuggy) o;
            return other.isbn.equals(isbn);
        }
        // BUG: equals() without hashCode(): equal books get different hash codes
    }

    static final class IsbnFixed {
        final String isbn;

        IsbnFixed(String isbn) {
            this.isbn = isbn;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IsbnFixed)) {
                return false;
            }
            IsbnFixed other = (IsbnFixed) o;
            return other.isbn.equals(isbn);
        }

        @Override
        public int hashCode() {                 // FIX: equal objects, equal hash codes
            return isbn.hashCode();
        }
    }

    static int booksInSetBuggy() {
        Set<IsbnBuggy> books = new HashSet<>();
        books.add(new IsbnBuggy("978-0"));
        books.add(new IsbnBuggy("978-0"));     // the same book a second time
        return books.size();
    }

    static int booksInSetFixed() {
        Set<IsbnFixed> books = new HashSet<>();
        books.add(new IsbnFixed("978-0"));
        books.add(new IsbnFixed("978-0"));
        return books.size();
    }

    public static void main(String[] args) {
        System.out.println("DR11 Inheritance contract: equals without hashCode (Java)   expected 1");
        System.out.println("  BUG -> books in set: " + booksInSetBuggy());
        System.out.println("  FIX -> books in set: " + booksInSetFixed());
    }
}
