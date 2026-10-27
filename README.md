# Myers' Inspection Checklist — runnable examples in Java and Python

Glenford Myers' *The Art of Software Testing* (3rd edition, Chapter 3) gives an **error checklist for code
inspections**: questions that a reviewer asks while reading code, grouped into eight categories. This repository
turns **every question of the checklist (63 items)** into a small program, once in **Java** and once in **Python**.
Each program contains a buggy version and a fixed version, and prints both results:

```
DR03 Non-integer index (Java)   price = 0.29 lira
  BUG -> 28 kuruş
  FIX -> 29 kuruş
```

The fixed version keeps the **shape of the buggy version** and changes as little as possible: the same names, the
same control structure, and one exit point where the buggy code has one. Put the two side by side (or diff them) and
the difference is the fix. Where a cleaner rewrite exists (a `for` loop, an arrow `switch`, an f-string), a comment
next to the fix mentions it. `main` calls each version directly and prints the result; an expected exception is
caught with an ordinary `try`/`catch` (`try`/`except`) and printed.

Every folder has a README that explains, for each item, **what the problem is, why it matters, and how to find it
statically**: first by reading (the inspection question), then with a tool, and whether that tool really reports
the demo. The tool results were checked by running the tools on this code.

It is a companion to a university course on software testing (SENG 403), but it is meant for anyone who reviews code.

## The eight categories

| Folder | Category | Items | What it is about |
|---|---|---|---|
| [`01-data-reference`](01-data-reference/README.md) | Data reference | 11 | unset variables, bounds, aliases, layouts, inheritance contracts |
| [`02-data-declaration`](02-data-declaration/README.md) | Data declaration | 6 | implicit types, defaults, initialization, type and length, similar names |
| [`03-computation`](03-computation/README.md) | Computation | 10 | mixed types, overflow, division by zero, base-2 inaccuracy, precedence |
| [`04-comparison`](04-comparison/README.md) | Comparison | 8 | wrong operators, Boolean logic, floating-point equality, short-circuit |
| [`05-control-flow`](05-control-flow/README.md) | Control flow | 6 | missing cases, loop termination, fall-through, off by one, blocks |
| [`06-interface`](06-interface/README.md) | Interface | 8 | argument order, types, units, built-ins, entry points, globals, constants |
| [`07-input-output`](07-input-output/README.md) | Input / output | 9 | encoding, open mode, format strings, closing, end of file, I/O errors |
| [`08-other-checks`](08-other-checks/README.md) | Other checks | 5 | unused names, defaults, compiler warnings, input validation, missing functions |

Myers wrote the checklist for languages such as Fortran, COBOL, PL/I and C. Where a question is about a feature
that Java and Python do not have (Fortran `EQUIVALENCE`, computed `GOTO`, PL/I `ENTRY`), the example shows the
closest modern form of the same mistake, and the README says so.

## What the tools find, and what they do not

| Category | Items | Reported by a Python tool | Reported by a Java tool |
|---|---|---|---|
| Data reference | 11 | 3 | 3 |
| Data declaration | 6 | 4 | 4 |
| Computation | 10 | 0 | 2 |
| Comparison | 8 | 3 | 4 |
| Control flow | 6 | 2 | 2 |
| Interface | 8 | 2 | 1 |
| Input / output | 9 | 4 | 5 |
| Other checks | 5 | 3 | 3 |
| **Total** | **63** | **21** | **24** |

Python tools: Ruff, mypy, codespell. Java tools: javac `-Xlint:all`, PMD (quickstart rules), SpotBugs, codespell.
"Reported" includes a few strong hints, marked as hints in the folder READMEs.

**About half of the items (31 of 63) are reported by no tool at all**, in either language. These are bugs where the
code is legal and type-correct but does not do what the specification says: a wrong operator for "at least", money
in `float`, a missing overdraft check. Finding them needs a person who reads the code **with the specification and
a checklist**. That is the point of Myers' chapter, and it is still true.

## Requirements

- **Python 3.10 or newer** (the examples use `X | None` type hints)
- **A JDK 17 or newer**. The Java examples use classic Java on purpose: classes, loops, `if`/`switch`, `try`/`catch`, no lambdas or streams. Only DD01 uses `var`, because `var` is the topic there
- Optional, for the static analysis: `pip install -r requirements-dev.txt` (Ruff, mypy, codespell) and Maven 3.9+

No example needs a database, a network or extra libraries. Java examples run with the single-file launcher
(`java File.java`), so no build is needed to run them.

## Running the examples

From the repository root:

```bash
python tools/run_all.py              # all 126 examples
python tools/run_all.py python       # only Python
python tools/run_all.py java 04      # only the Java examples of 04-comparison
```

One example:

```bash
cd 03-computation/python && python co07_binary_inaccuracy.py
cd 03-computation/java   && java CO07BinaryInaccuracy.java
```

### In GitHub Codespaces (nothing to install)

On GitHub: **Code → Codespaces → Create codespace on main**. Python 3.12, Java 17, Maven, the static
analysis tools and all VS Code extensions are installed automatically. The first start takes a few minutes.

### In VS Code

Open the `myers-checklist-demos` folder (File → Open Folder) and click **Install** when VS Code asks about
the recommended extensions. You need Python 3.10+ and a JDK 17+ on your computer.
(With Docker and the Dev Containers extension you can also choose **Reopen in Container** to get the same
setup as Codespaces.) Then, in **Run and Debug**:

| Configuration | What it does |
|---|---|
| Run this example (Python) | runs the Python file that is open in the editor |
| Run this example (Java) | runs the Java file that is open in the editor |
| Run ALL examples (Python + Java) | runs `tools/run_all.py` |
| Run ALL Python examples / Run ALL Java examples | one language only |
| Run one checklist category... | asks for a category and runs its examples |

**Terminal → Run Task** has the static analysis: Ruff, mypy, codespell, javac `-Xlint:all`, PMD and SpotBugs.

## Running the static analysis yourself

```bash
pip install -r requirements-dev.txt
ruff check .                    # settings in pyproject.toml
python -m mypy 0*/python        # settings in pyproject.toml
codespell --skip '*.md,./target' .

mvn -q clean compile            # javac -Xlint:all: compiler warnings
mvn compile pmd:check           # PMD: report in target/pmd.xml
mvn compile spotbugs:check      # SpotBugs: report in target/spotbugsXml.xml
```

The examples contain bugs **on purpose**. The tools are configured to report them, not to fail.
A few style rules that only add noise in small examples are turned off in `pyproject.toml`, with a reason next to each.

## How to use this in a review or a class

1. Pick a category. Read only the **buggy** function of an example and the one-line specification in its comment.
2. Ask the checklist question from the folder README. Find the bug **without running anything**.
3. Run the example to see the failure. Then run the tools: did they find it?
4. Compare: which bugs did you find, which did the tool find, which did nobody find?

## Source and credit

The checklist questions in the READMEs are **paraphrased and shortened** from G. J. Myers, T. Badgett and C. Sandler,
*The Art of Software Testing*, 3rd ed., Wiley, 2012, Chapter 3 ("An Error Checklist for Inspections",
Tables 3.1 and 3.2). Read the book for the full list and its context. All code and explanations in this repository
are original.

Prepared by Gökçer Gökdal for SENG 403 — Software Quality Assurance and Testing.

## License

- **Code** (`*.java`, `*.py`, tool and build configuration, the VS Code workspace): [MIT License](LICENSE).
- **Explanations** (the text of the `README.md` files): [Creative Commons Attribution 4.0 International (CC BY 4.0)](https://creativecommons.org/licenses/by/4.0/).
  You may share and adapt them for any purpose, if you give credit: *"Myers' Inspection Checklist — runnable
  examples" by Gökçer Gökdal, CC BY 4.0*, with a link to this repository.
- The checklist itself belongs to the authors and publisher of *The Art of Software Testing*. The paraphrased
  questions are used here for teaching, with credit; neither license above covers the book.
