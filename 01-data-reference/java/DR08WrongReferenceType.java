// DR08 · Data reference · Does the object behind a reference have the type the code expects?
public class DR08WrongReferenceType {

    interface Shape { }

    static class Circle implements Shape {
        final double r;
        Circle(double r) { this.r = r; }
    }

    static class Square implements Shape {
        final double side;
        Square(double side) { this.side = side; }
    }

    static double areaBuggy(Shape s) {
        double area;
        Circle c = (Circle) s;                 // BUG: the code "knows" that every shape is a circle
        area = Math.PI * c.r * c.r;
        return area;
    }

    static double areaFixed(Shape s) {
        double area;
        if (s instanceof Circle) {             // FIX: check the real type before the cast ...
            Circle c = (Circle) s;
            area = Math.PI * c.r * c.r;
        } else if (s instanceof Square) {      // ... and handle every type
            Square q = (Square) s;
            area = q.side * q.side;
        } else {
            throw new IllegalArgumentException("unknown shape");
        }
        return area;
    }

    public static void main(String[] args) {
        System.out.println("DR08 Reference to an object of another type (Java)   a square with side 2");
        Shape s = new Square(2);
        try {
            System.out.println("  BUG -> " + areaBuggy(s));
        } catch (ClassCastException e) {
            System.out.println("  BUG -> ClassCastException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + areaFixed(s));
    }
}
