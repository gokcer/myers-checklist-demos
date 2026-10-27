// IF03 · Interface · Do the units of each argument match the units of the parameter (in both directions)?
// The Mars Climate Orbiter (1999) was lost because one team sent pound-force seconds, the other expected newton seconds.
public class IF03Units {

    static final double NS_PER_LBFS = 4.448222;

    // the navigation module expects the impulse in NEWTON SECONDS
    static double newVelocity(double velocity, double impulseNs, double massKg) {
        return velocity + impulseNs / massKg;
    }

    // the thruster reports its impulse in POUND-FORCE SECONDS
    static double afterBurnBuggy(double impulseLbfs) {
        return newVelocity(0, impulseLbfs, 640);                   // BUG: lbf·s passed as N·s
    }

    static double afterBurnFixed(double impulseLbfs) {
        return newVelocity(0, impulseLbfs * NS_PER_LBFS, 640);     // FIX: convert at the interface
    }

    // built-in functions have units too: Math.sin expects RADIANS
    static double sineBuggy(double degrees) {
        return Math.sin(degrees);                                  // BUG: degrees passed as radians
    }

    static double sineFixed(double degrees) {
        return Math.sin(Math.toRadians(degrees));                  // FIX
    }

    public static void main(String[] args) {
        System.out.println("IF03 Units (Java)   thruster reports 100 lbf·s, craft 640 kg; sin(90 degrees)");
        System.out.println("  BUG velocity -> " + afterBurnBuggy(100));
        System.out.println("  FIX velocity -> " + afterBurnFixed(100));
        System.out.println("  BUG sin(90 degrees) -> " + sineBuggy(90));
        System.out.println("  FIX sin(90 degrees) -> " + sineFixed(90));
    }
}
