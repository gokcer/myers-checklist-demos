# 02 · Data declaration errors

Errors in **how variables are declared and initialized**: implicit declarations, defaults nobody thought about,
wrong initial values, the wrong type or length, and names that are easy to confuse.

Run this folder: `python tools/run_all.py all 02`

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| DD01 | Is every variable declared explicitly, with the intended type? | `DD01ImplicitDeclaration` | `dd01_implicit_declaration` | Java: javac `-Xlint` · Python: Ruff |
| DD02 | If an attribute is not stated, is its default understood? | `DD02DefaultValues` | `dd02_default_values` | Python: Ruff · Java: SpotBugs hint |
| DD03 | Is a variable initialized correctly in its declaration? | `DD03InitializationShared` | `dd03_initialization_shared` | no |
| DD04 | Does each variable have the right type and length? | `DD04TypeAndLength` | `dd04_type_and_length` | no |
| DD05 | Does the initialization match how long the variable lives? | `DD05StaticVsPerCall` | `dd05_class_vs_instance` | Python: Ruff · Java: SpotBugs |
| DD06 | Are there variables with similar names? | `DD06SimilarNames` | `dd06_similar_names` | Java: PMD, SpotBugs · Python: mypy |

---

## DD01 · Is every variable declared explicitly, with the intended type?

**The problem.** The type of a variable is not written down, so it is not the type the programmer had in mind.
Myers' example: a parameter that should be an array but is not declared as one.

**Why it matters.**
- Java's `var price = 10` makes `price` an `int`. `price *= 1.5` compiles, because compound assignment casts back to `int`, so 18.75 becomes 18.
- Python has no declarations at all: a typo (`totl = total + p`) silently creates a new variable, and `total` stays 0.

**The demo.** A price with two multipliers (Java) and a sum with a typo (Python).

**How to check it statically.**
- *Reading:* for every `var` (Java) or new name (Python), ask: "what type is this, and is that what I need?" Look for names that are assigned only once.
- *Tools:*
  - javac `-Xlint:all`: `implicit cast from double to int in compound assignment is possibly lossy`.
  - Ruff `F841`: local variable `totl` is assigned to but never used.

## DD02 · If an attribute is not stated, is its default understood?

**The problem.** Myers: Java's default attributes are often a surprise. Unset fields are `false`, `0` or `null`.

**Why it matters.**
- Java: a user whose `active` field was never set is inactive, and a login limit of 0 locks everybody out.
- Python: a default argument value is created **once**, so `tags=[]` is shared by every call.

**The demo.** A `User` class (Java) and `add_tag("b")` that returns `['a', 'b']` (Python).

**How to check it statically.**
- *Reading:* for every field without an initializer, and every default argument, ask: "is this default the value the specification wants?" In Python, any `[]`, `{}` or object as a default is suspicious.
- *Tools:*
  - Ruff `B006`: mutable default argument.
  - SpotBugs only gives a hint for Java: `URF_UNREAD_FIELD` / `UWF_UNWRITTEN_FIELD` on fields that are never written or never read.

## DD03 · Is a variable initialized correctly in its declaration?

**The problem.** Myers: initializing arrays and strings is complicated and therefore error prone.

**Why it matters.** A list created once before the loop (Java) and `[[0] * 3] * 3` (Python) put the **same**
inner object in every slot. Changing one shelf or row changes all of them.

**The demo.** Three shelves that all receive the book (Java); a 3×3 board where one move fills a whole column (Python).

**How to check it statically.**
- *Reading:* when a container is built by repetition, ask: "is the repeated element created once or each time?" Use a loop or a comprehension that creates a new inner object.
- *Tools:* none of the tools used here reports these patterns.

## DD04 · Does each variable have the right type and length?

**The problem.** The type is too short for the range of values, or too imprecise for their meaning.

**Why it matters.**
- 30 days in milliseconds is 2,592,000,000, which overflows `int` to −1,702,967,296.
- Binary floating point gives `0.30000000000000004` for money.

**The demo.**
- Java: `int` milliseconds, and `double` money.
- Python: `float` money; `Decimal` is the fix.

**How to check it statically.**
- *Reading:* for every numeric variable, write down its largest possible value and its unit. Does the type hold it? Is it money, which needs `BigDecimal` / `Decimal` or integer cents?
- *Tools:* none reports these demos. This needs a person who knows the domain.

## DD05 · Does the initialization match how long the variable lives?

**The problem.** Myers' example: a Fortran `DATA` statement initializes once, not on every call. Today the same
mistake is a `static` field (Java) or a class attribute (Python) used as per-object state.

**Why it matters.**
- Java: a `static` counter of failed logins is shared by all users. After Ada fails twice, Bob is locked out after his first mistake.
- Python: a list in the class body is shared by every cart.

**The demo.** The login counter (Java) and the shopping cart (Python).

**How to check it statically.**
- *Reading:* for every `static` field and class-level attribute, ask: "does this belong to the whole program, or to one object or one call?"
- *Tools:*
  - Ruff `RUF012`: mutable class attributes should be annotated with `ClassVar`. This is exactly the hint you need.
  - SpotBugs `ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD`: an object method changes a `static` field.

## DD06 · Are there variables with similar names?

**The problem.** Myers: names like `VOLT` and `VOLTS` are a warning sign, because they get confused.

**Why it matters.**
- Java: the loop adds `reading` (the first value) instead of `r`, so the average is wrong.
- Python: `volts = v` overwrites the list instead of updating `volt`.

**The demo.** An average of readings (Java) and a maximum voltage (Python).

**How to check it statically.**
- *Reading:* list the names in a function. Are any two different by one letter or a plural `s`? Rename them so that they cannot be confused.
- *Tools:*
  - PMD `UnusedLocalVariable` and SpotBugs `DLS_DEAD_LOCAL_STORE`: the loop variable `r` is never used. This is a strong hint.
  - mypy: `Incompatible types in assignment (expression has type "float", variable has type "list[float]")`, because the Python function has type hints.

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
