# 05 · Control-flow errors

Errors in **which statements run, and how often**: multiway branches without a matching case, loops that never
end, search loops that fall through, off-by-one loops, wrong block structure, and decisions that are not exhaustive.

Run this folder: `python tools/run_all.py all 05`

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| CF01 | In a multiway branch, can the selector have a value with no branch? | `CF01MultiwayBranch` | `cf01_multiway_branch` | Java: javac, PMD, SpotBugs · Python: mypy |
| CF02 | Will every loop end? | `CF02LoopTermination` | `cf02_loop_termination` | no |
| CF03 | What if a loop runs zero times, or a search never finds anything? | `CF03LoopFallThrough` | `cf03_loop_fall_through` | no |
| CF04 | Are there off-by-one errors? | `CF04OffByOne` | `cf04_off_by_one` | no |
| CF05 | Does every block start and end where it should? | `CF05BlockStructure` | `cf05_block_structure` | Java: PMD, SpotBugs · Python: mypy |
| CF06 | Are there non-exhaustive decisions? | `CF06NonExhaustiveDecision` | `cf06_non_exhaustive_decision` | no |

---

## CF01 · In a multiway branch, can the selector have a value with no branch?

**The problem.** Myers' example is Fortran's computed `GOTO(200,300,400),i`: what if `i` is not 1, 2 or 3? Today:
a `switch`, a `match`, or a dictionary used as a dispatch table.

**Why it matters.**
- Java: zone 4 has no case, so the fee stays 0. Java adds a second trap: an old-style `case` without `break` falls through, so zone 1 costs 35 instead of 20.
- Python: `FEES.get(4)` returns `None`, and the `TypeError` appears one line later.

**How to check it statically.**
- *Reading:* list all values the selector can have (from the specification and from the code that sets it). Is there a branch for each one, and a `default` / `case _` that rejects the rest? In old-style switches, check every `break`.
- *Tools:*
  - javac `-Xlint`: `possible fall-through into case`.
  - PMD `NonExhaustiveSwitch`.
  - SpotBugs `SF_SWITCH_FALLTHROUGH` and `SF_SWITCH_NO_DEFAULT`.
  - mypy: `Unsupported operand types for + ("int" and "None")`.

## CF02 · Will every loop end?

**The problem.** Myers: give an informal argument that each loop terminates.

**Why it matters.**
- Java: counting down by 2 with `n != 0` never ends for an odd `n`.
- Python: a `continue` skips the `i += 1`, so an empty word makes the loop spin for ever.
- Both demos stop only because of a safety guard.

**How to check it statically.**
- *Reading:* for each loop, name the value that moves toward the exit condition. Does it move on **every** path through the body, including after `continue`? Can it jump over the exit value? Prefer `<`/`>` exit tests over `!=`.
- *Tools:* none.

## CF03 · What if a loop runs zero times, or a search never finds anything?

**The problem.** Myers asks what happens on loop fall-through, and when the entry conditions mean the loop never runs.

**Why it matters.**
- Java: a search loop that does not find the name leaves `i == length`, so `USERS[i]` throws.
- Python: after a `for` loop the variable keeps the **last** element, so "not found" returns the wrong user without any error.

**How to check it statically.**
- *Reading:* for every search loop, ask: "what happens after the loop if nothing was found?" Use `return` inside the loop and an explicit not-found result, or Python's `for … else`.
- *Tools:* none in this configuration.

## CF04 · Are there off-by-one errors?

**The problem.** One iteration too many or too few. Myers' Java example: `for (int i = 0; i <= 10; i++)` runs 11 times.

**Why it matters.** A loop meant for 10 runs 11 times. 21 results on pages of 10 need 3 pages, not 2. Python's `range(1, 5)` stops at 4.

**How to check it statically.**
- *Reading:* count the iterations by hand for the smallest case. Check `<` vs `<=`, and remember that `range()` and `substring()` exclude the end. Round up where a partial page, block or batch counts.
- *Tools:* none.

## CF05 · Does every block start and end where it should?

**The problem.** Myers asks whether every group of statements is closed correctly.

**Why it matters.**
- Java: an `if` without braces controls **one** statement. The indented `result = "REJECTED"` after it always runs. This is the shape of Apple's "goto fail" bug (2014).
- Python: the block is the indentation, so one wrong dedent moves a line out of the `if`.

**How to check it statically.**
- *Reading:* in Java, insist on braces for every `if`/`for`/`while`. In both languages, look for code after a `return` that can never run, or a value that is overwritten before anyone reads it: both usually show a block that ends too early.
- *Tools:*
  - PMD `ControlStatementBraces`.
  - SpotBugs `DLS_DEAD_LOCAL_STORE`: the "OK" value is overwritten before it is used.
  - mypy (`warn_unreachable`): `Statement is unreachable`.

## CF06 · Are there non-exhaustive decisions?

**The problem.** Myers: if the expected values are 1, 2 or 3, does the logic assume "not 1 or 2, so it must be 3"?

**Why it matters.** A final `else` treats every unexpected value (`"Card"` with a capital C, or `"bitcoin"`) as the last valid one, here "voucher".

**How to check it statically.**
- *Reading:* look at every final `else`. Does it handle **one** specific case? Then name that case explicitly and add an `else` that rejects everything else.
- *Tools:* none. Only PMD style rules (braces, literals first) report something on these lines, not the logic.

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
