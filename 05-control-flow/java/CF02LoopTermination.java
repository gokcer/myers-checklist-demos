// CF02 · Control flow · Will every loop end? Try to argue (informally) that it terminates.
public class CF02LoopTermination {

    static final int SAFETY = 1000;      // only so that this demo stops; real code has no such guard

    // count down by 2 until zero
    static int countdownBuggy(int n) {
        int steps = 0;
        while (n != 0 && steps < SAFETY) {    // BUG: an odd n jumps over 0 and never stops
            n -= 2;
            steps++;
        }
        return steps;
    }

    static int countdownFixed(int n) {
        int steps = 0;
        while (n > 0) {                        // FIX: a condition the loop must reach
            n -= 2;
            steps++;
        }
        return steps;
    }

    public static void main(String[] args) {
        System.out.println("CF02 Loop termination (Java)   n = 7");
        System.out.println("  BUG steps (1000 = stopped by the guard) -> " + countdownBuggy(7));
        System.out.println("  FIX steps -> " + countdownFixed(7));
    }
}
