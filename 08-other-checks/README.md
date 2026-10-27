# 08 · Other checks

The last group of Myers' checklist: use the **compiler's own listings and warnings**, check that the module
**validates its input**, and check that **no function is missing**.

Run this folder: `python tools/run_all.py all 08`

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| OT01 | Are there names never used, or used only once (cross-reference listing)? | `OT01UnusedOrUsedOnce` | `ot01_unused_or_used_once` | Java: PMD · Python: Ruff |
| OT02 | Did anything get an unexpected default attribute (attribute listing)? | `OT02UnexpectedDefaults` | `ot02_unexpected_defaults` | Java: PMD · Python: mypy |
| OT03 | Were the compiler's warnings read? | `OT03CompilerWarnings` | `ot03_compiler_warnings` | yes: that is the point |
| OT04 | Is the module robust: does it check its input? | `OT04Robustness` | `ot04_robustness` | no |
| OT05 | Is a function missing? | `OT05MissingFunction` | `ot05_missing_function` | no |

---

## OT01 · Are there names never used, or used only once?

**The problem.** Myers: look at the compiler's cross-reference listing for variables that are never referenced or
referenced only once. Today a linter makes this list.

**Why it matters.** An unused value is often a **missing step**, not only untidy code. Here the member discount is computed and never applied.

**How to check it statically.**
- *Reading:* for every name that is assigned and never read, ask: "what was this for?" The answer often reveals the bug.
- *Tools:*
  - PMD `UnusedLocalVariable`.
  - Ruff `F841`.
  - javac does not warn about unused locals.

## OT02 · Did anything get an unexpected default attribute?

**The problem.** Myers: check the attribute listing for defaults you did not expect.

**Why it matters.**
- Java: a leading zero makes a literal octal (`010` is 8), and integer literals are `int`, so `1 << 40` shifts an `int` and gives 256 instead of 2⁴⁰.
- Python: a function that falls off its end returns the default `None`.

**How to check it statically.**
- *Reading:* look at literals with leading zeros, shifts and large constants (add `L`), and every path of a function that should return a value.
- *Tools:*
  - PMD `AvoidUsingOctalValues`.
  - mypy `Missing return statement`.
  - No tool here reports `1 << 40`.

## OT03 · Were the compiler's warnings read?

**The problem.** Myers: if the program compiled but the compiler gave warnings, check each one carefully.

**Why it matters.** Warnings point at real bugs:
- Java: a `switch` fall-through gives a score of 95 the grade "B", there is a constant division by zero, and raw types are used.
- Python: `assert (cond, "message")` is **always true**, because it tests a non-empty tuple, and `level is "gold"` compares identity.

**The demo.**
- Java: compile with `javac -Xlint:all OT03CompilerWarnings.java`, or run the "javac -Xlint:all" task.
- Python: the two `SyntaxWarning`s appear when the file is compiled. `tools/run_all.py` shows them as `[stderr]`.

**How to check it statically.**
- *Reading:* make "zero warnings" part of the review: every warning is fixed or explained in a comment.
- *Tools:*
  - javac `-Xlint:all`: `possible fall-through into case`, `division by zero`, `found raw type`.
  - Python's own `SyntaxWarning`, Ruff `F631` and `F632`, and mypy.
  - Run Python with `-W error::SyntaxWarning` to make the warnings fatal.

## OT04 · Is the module robust: does it check its input?

**The problem.** Myers: does the module check its input for validity?

**Why it matters.** A quantity of −3 makes the total −150: the shop pays the customer. OWASP Juice Shop has a challenge built on exactly this mistake.

**How to check it statically.**
- *Reading:* list every input that comes from outside (user, file, network, other team's module). For each one, find the check of type, range and format, and the reaction when it fails.
- *Tools:* none. The valid range is in the specification.

## OT05 · Is a function missing?

**The problem.** Myers' last question: is a function missing from the program?

**Why it matters.** The specification says a withdrawal must not make the balance negative, and that a reset link expires after 15 minutes. The checks are simply not there, so the code is "correct" for everything it does and still wrong.

**How to check it statically.**
- *Reading:* go through the specification sentence by sentence and point at the code that implements each one. A sentence with no code is a missing function. This is the review that only a human who knows the specification can do.
- *Tools:* none. A tool cannot find code that does not exist.

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
