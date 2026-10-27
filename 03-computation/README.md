# 03 · Computation errors

Errors in **arithmetic**: wrong types, mixed integer and floating-point arithmetic, values that are too big or
too small, division by zero, base-2 inaccuracy, values outside their meaning, and precedence.

Run this folder: `python tools/run_all.py all 03`

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| CO01 | Is arithmetic done on non-arithmetic types? | `CO01NonArithmeticTypes` | `co01_non_arithmetic_types` | no |
| CO02 | Are integers and floating point mixed (mixed mode)? | `CO02MixedMode` | `co02_mixed_mode` | Java: SpotBugs · Python: no |
| CO03 | Are values of the same type but different lengths mixed? | `CO03DifferentLengths` | `co03_different_lengths` | Java: SpotBugs · Python: no |
| CO04 | Is the target of an assignment smaller than the result? | `CO04TargetTooSmall` | `co04_target_too_small` | no |
| CO05 | Can an intermediate result overflow or underflow? | `CO05IntermediateOverflow` | `co05_intermediate_underflow` | no |
| CO06 | Can a divisor be zero? | `CO06DivisionByZero` | `co06_division_by_zero` | no |
| CO07 | Does base-2 inaccuracy matter here? | `CO07BinaryInaccuracy` | `co07_binary_inaccuracy` | no |
| CO08 | Can a value go outside its meaningful range? | `CO08MeaningfulRange` | `co08_meaningful_range` | no |
| CO09 | Are the assumptions about operator precedence correct? | `CO09OperatorPrecedence` | `co09_operator_precedence` | no |
| CO10 | Is integer arithmetic (especially division) used correctly? | `CO10IntegerArithmetic` | `co10_integer_arithmetic` | no |

Only 2 of 10 computation items are reported by a tool. Most arithmetic bugs are legal code with a wrong meaning,
so they need a reviewer who knows what the result should be.

---

## CO01 · Is arithmetic done on non-arithmetic types?

**The problem.** Values that look like numbers are text (or characters), and the operator does something else.

**Why it matters.**
- Java: `"Total: " + coffee + cake` gives `Total: 4060`, and `'B' + 1` is `67`.
- Python: `"40" + "60"` is `"4060"`, and `"3" * 2` is `"33"`.

**How to check it statically.**
- *Reading:* follow every value read from input, files or the web to its first arithmetic operation. Is it converted at the border? In Java, check every `+` in a string expression and put arithmetic in parentheses.
- *Tools:* none. Both expressions are legal and type-correct.

## CO02 · Are integers and floating point mixed (mixed mode)?

**The problem.** Myers' own Java example: `int x = 1, y = 2; z = x / y;` gives 0. The conversion rules decide the result.

**Why it matters.**
- Java: `double avg = sum / count` divides as integers **before** widening: 7 / 2 = 3.0.
- Python: `int(-75 / 30)` truncates toward zero (−2), while `-75 // 30` floors (−3). Code that mixes the two disagrees for negative numbers.

**How to check it statically.**
- *Reading:* in every expression with both `int` and `double`/`float`, find the exact point where the conversion happens. Is it before or after the operation that matters?
- *Tools:*
  - SpotBugs `ICAST_IDIV_CAST_TO_DOUBLE` reports the Java average.
  - Python: none.

## CO03 · Are values of the same type but different lengths mixed?

**The problem.** `int` and `long` (or 16-bit and 64-bit storage) are combined.

**Why it matters.**
- Java: `long bytes = megabytes * 1024 * 1024` multiplies in 32-bit `int` and overflows to 0 for 4096 MB. Only the wrong result is widened to `long`.
- Python ints have no limit, but `array("h")` holds 16-bit values and rejects 40000.

**How to check it statically.**
- *Reading:* when the target is wider than the operands, ask: "does the calculation happen in the narrow type?" Widen one operand first (`(long) megabytes`).
- *Tools:*
  - SpotBugs `ICAST_INTEGER_MULTIPLY_CAST_TO_LONG`.
  - Python: none.

## CO04 · Is the target of an assignment smaller than the result?

**The problem.** The result is stored in a type that cannot hold it.

**Why it matters.**
- Java: `short total += 10000` gives −25536, because compound assignment casts silently, and `(byte) 200` is −56.
- Python's `ctypes.c_uint8(300)` stores 44.

**How to check it statically.**
- *Reading:* for every compound assignment on `byte`/`short`, every explicit narrowing cast, and every fixed-size store (ctypes, arrays, database columns), ask: "what is the largest value that can arrive here?" Use a type big enough for the result, or a range check that fails loudly.
- *Tools:* none of the tools used here.

## CO05 · Can an intermediate result overflow or underflow?

**The problem.** The final result fits, but a value on the way does not.

**Why it matters.**
- Java: the classic binary-search bug (reported by Joshua Bloch in 2006). `(low + high) / 2` overflows for large indexes.
- Python: multiplying 80 probabilities of 1e-5 underflows to `0.0`.

**How to check it statically.**
- *Reading:* look at sums and products of large or small values. Rewrite them so that every intermediate value fits: `low + (high - low) / 2`, sums of logarithms.
- *Tools:* none for these demos.

## CO06 · Can a divisor be zero?

**The problem.** A division (or modulo) by a value that can be zero.

**Why it matters.**
- Java integer division throws, but **floating-point** division does not: an empty list gives `NaN`, and the program continues.
- Python throws `ZeroDivisionError`.

**How to check it statically.**
- *Reading:* for every `/` and `%`, ask: "can the right side be zero? empty list, no users, zero minutes?" Then check what the specification says should happen in that case.
- *Tools:* none (javac only warns about the constant `10 / 0`; see `08-other-checks/OT03`).

## CO07 · Does base-2 inaccuracy matter here?

**The problem.** Myers: 10 × 0.1 is rarely 1.0 on a binary machine.

**Why it matters.** The sum is `0.9999999999999999`. Totals drift, and equality tests fail (see `04-comparison/CP06`).

**How to check it statically.**
- *Reading:* is this value money, or something counted in decimal steps? Then use `BigDecimal` / `Decimal`, integer cents, or `math.fsum` for sums.
- *Tools:* none.

## CO08 · Can a value go outside its meaningful range?

**The problem.** Myers' example: a probability must stay between 0 and 1. A percentage, a price or an age has limits too.

**Why it matters.**
- Java: a 60% and a 50% discount give a **negative** price of −10.
- Python: a "probability" of 1.1.

**How to check it statically.**
- *Reading:* for every value with a meaning, write down its allowed range. Can each assignment produce something outside it? Where is the check?
- *Tools:* none. The range is in the specification, not in the code.

## CO09 · Are the assumptions about operator precedence correct?

**The problem.** The expression is evaluated in a different order from the one the programmer meant.

**Why it matters.**
- `a + b / 2` is 130, not the average 90.
- Java: `flags << 1 + 1` shifts by 2.
- Python: `-2 ** 2` is −4.

**How to check it statically.**
- *Reading:* in every expression with more than one kind of operator, add the parentheses in your head. Do they match the intent? If you are not sure, the code needs parentheses.
- *Tools:* none for these demos.

## CO10 · Is integer arithmetic (especially division) used correctly?

**The problem.** Myers: whether `2*i/2 == i` depends on whether `i` is odd and on the order of the operations.

**Why it matters.** `done / total * 100` (Java) or `done // total * 100` (Python) is 0 for 45 of 60, not 75.

**How to check it statically.**
- *Reading:* for every integer division, ask: "is the remainder thrown away on purpose?" Multiply before you divide, or use floating point where fractions matter.
- *Tools:* none.

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
