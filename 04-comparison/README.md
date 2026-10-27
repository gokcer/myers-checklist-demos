# 04 · Comparison errors

Errors in **comparisons and Boolean expressions**: comparing different types, the wrong operator, wrong
and/or/not logic, floating-point equality, precedence, and evaluation order.

Run this folder: `python tools/run_all.py all 04`

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| CP01 | Are values of different types compared? | `CP01DifferentTypes` | `cp01_different_types` | Java: SpotBugs · Python: no |
| CP02 | Are mixed-mode values or values of different lengths compared? | `CP02MixedModeComparison` | `cp02_mixed_mode_comparison` | Java: SpotBugs · Python: Ruff (preview) |
| CP03 | Is the comparison operator correct (at least, at most…)? | `CP03WrongOperator` | `cp03_wrong_operator` | no |
| CP04 | Does each Boolean expression say what it should? | `CP04BooleanLogic` | `cp04_boolean_logic` | no |
| CP05 | Are comparison and Boolean operators mixed up? | `CP05BooleanOperands` | `cp05_boolean_operands` | Python: mypy · Java: no |
| CP06 | Are floating-point numbers compared for equality? | `CP06FloatEquality` | `cp06_float_equality` | Java: SpotBugs · Python: no |
| CP07 | Are the assumptions about and/or precedence correct? | `CP07BooleanPrecedence` | `cp07_boolean_precedence` | Python: Ruff · Java: no |
| CP08 | Does the evaluation order of a Boolean expression matter? | `CP08ShortCircuit` | `cp08_short_circuit` | Java: SpotBugs · Python: no |

---

## CP01 · Are values of different types compared?

**The problem.** Myers: comparing a string with a number, a date or an address.

**Why it matters.**
- Java: `Long.valueOf(42).equals(42)` is `false`, so a `Map<Long, …>` lookup with an `int` key returns `null`.
- Python: `"9" >= "18"` is `True`, because strings compare character by character.

**How to check it statically.**
- *Reading:* for every `==`, `equals`, `<`, `in` and every map lookup, check that both sides have the **same type**. Values from input are text until they are converted.
- *Tools:*
  - SpotBugs `GC_UNRELATED_TYPES` (map lookup) and `EC_UNRELATED_TYPES` (`equals`).
  - Python: none for the demo function. mypy's `strict_equality` only reports the literal `"42" == 42`.

## CP02 · Are mixed-mode values or values of different lengths compared?

**The problem.** The two sides are converted before the comparison, and the conversion changes the value.

**Why it matters.**
- Java: `0.1f == 0.1` is `false`, because the float is widened to double.
- Python: `Decimal("0.1") == 0.1` is `False`.

**How to check it statically.**
- *Reading:* are both operands exactly the same type and precision? If not, which one is converted, and does the conversion keep the value?
- *Tools:*
  - SpotBugs `FE_FLOATING_POINT_EQUALITY`.
  - Ruff `RUF069` (float-equality-comparison; a preview rule, turned on in `pyproject.toml`).

## CP03 · Is the comparison operator correct?

**The problem.** Myers: programmers confuse "at most", "at least", "greater than", "not less than".

**Why it matters.** "Free shipping for **at least** 500 TL" written as `> 500` refuses exactly 500. "**At most** 3 items" checked with `<= 3` before adding lets a 4th in.

**How to check it statically.**
- *Reading:* translate the words of the specification into an operator and compare. Then test the boundary in your head: what happens at **exactly** 500?
- *Tools:* none. Only the specification knows which operator is right.

## CP04 · Does each Boolean expression say what it should?

**The problem.** Mistakes with `and`, `or`, `not`, especially when "not" is involved.

**Why it matters.** `!day.equals("Sat") || !day.equals("Sun")` is always true. By De Morgan's law, "not (Saturday or Sunday)" is "not Saturday **and** not Sunday".

**How to check it statically.**
- *Reading:* read the expression aloud in plain words and compare it with the specification. Try the values that should give `false`. Does it ever give `false`?
- *Tools:* none in this configuration.

## CP05 · Are comparison and Boolean operators mixed up?

**The problem.** Myers' examples: `2<i<10`, `i>x||y`, `a==b==c`.

**Why it matters.**
- Java rejects `2 < i < 10` for ints, but `a == b == c` compiles for booleans and means `(a == b) == c`.
- Python *allows* `2 < i < 10` and it means what you expect. But `role == "admin" or "owner"` is always truthy.

**How to check it statically.**
- *Reading:* in every `or`/`and`, check that **each side is a complete comparison**. A bare string, number or variable on one side is suspicious.
- *Tools:*
  - mypy reports the Python demo only because the function says it returns `bool` and the expression can return `str`.
  - Java: none.

## CP06 · Are floating-point numbers compared for equality?

**The problem.** Base-2 numbers are approximations, so exact equality fails.

**Why it matters.**
- Java: a loop `for (x = 0.0; x != 1.0; x += 0.1)` never ends. The demo stops it with a guard.
- Python: a payment of `0.1 + 0.2` is not "paid in full" for a price of 0.3.

**How to check it statically.**
- *Reading:* every `==` or `!=` between floating-point values is a defect unless there is a written reason. Use a tolerance (`abs(a - b) < 1e-9`, or `math.isclose`), integer counters for loops, or decimal types.
- *Tools:*
  - SpotBugs `FL_FLOATS_AS_LOOP_COUNTERS`.
  - Python: none for the demo function.

## CP07 · Are the assumptions about and/or precedence correct?

**The problem.** `&&` binds tighter than `||` (and `and` binds tighter than `or`).

**Why it matters.** "Discount for members who are children or seniors" written as `member && age < 12 || age > 65` gives the discount to **every** senior.

**How to check it statically.**
- *Reading:* in every expression with both `and` and `or`, add the parentheses that the language uses, then compare them with the specification.
- *Tools:*
  - Ruff `RUF021` (parenthesize chained `and`/`or`).
  - Java: none in this configuration.

## CP08 · Does the evaluation order of a Boolean expression matter?

**The problem.** Myers: `if (x==0 && (x/y)>z)` is safe only if evaluation stops early.

**Why it matters.** In Java, `&` and `|` on booleans evaluate **both** sides, so `minutes != 0 & distance / minutes > 2` divides by zero. In Python the same happens with `&` between two comparisons.

**How to check it statically.**
- *Reading:* when the right side of a condition is only safe because the left side was checked, make sure the operator is `&&` / `and`. Also check that the guard is on the **left**.
- *Tools:* SpotBugs `NS_NON_SHORT_CIRCUIT` reports the Java `&`. Python: none.

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
