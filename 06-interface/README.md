# 06 · Interface errors

Errors at the **border between two modules**: the caller and the callee disagree about the number, order, type or
units of the arguments, or about who may change what.

Myers lists several questions twice: once for the parameters a module *receives* and once for the arguments it
*sends*. The demos cover both directions in one example (IF01 = Myers' questions on number/order in both
directions, IF03 = units in both directions).

Run this folder: `python tools/run_all.py all 06`

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| IF01 | Do the number and order of arguments match the parameters? | `IF01ParameterOrder` | `if01_parameter_order` | no |
| IF02 | Do the attributes (type, size) of arguments match? | `IF02ParameterType` | `if02_parameter_type` | Python: mypy · Java: no |
| IF03 | Do the units of arguments match? | `IF03Units` | `if03_units` | no |
| IF04 | Are built-in functions called with the right arguments? | `IF04BuiltInArguments` | `if04_built_in_arguments` | Java: SpotBugs · Python: Ruff |
| IF05 | With several entry points, is a value used that this entry never set? | `IF05MultipleEntryPoints` | `if05_multiple_entry_points` | no |
| IF06 | Does a module change a parameter that is input only? | `IF06AltersInputParameter` | `if06_alters_input_parameter` | no |
| IF07 | Do global values have the same definition in every module? | `IF07GlobalDefinitions` | `if07_global_definitions` | no |
| IF08 | Is a constant passed as an argument and then changed? | `IF08ConstantArgumentChanged` | `if08_constant_argument_changed` | no |

---

## IF01 · Do the number and order of arguments match the parameters?

**The problem.** The caller passes arguments in a different order from the parameters. With parameters of the same type, the compiler cannot notice.

**Why it matters.**
- Java: `transfer(acc, "bob", "ada", 30)` moves money the wrong way.
- Python: `create_user("Ada", "editor")` creates a user called "editor" with the role "Ada".
- The fixes pass the arguments in the right order (Java) or by name, `create_user(role="editor", name="Ada")` (Python).

**How to check it statically.**
- *Reading:* for every call, put the signature next to it and match the arguments one by one. Be most careful with several parameters of the same type. Prefer calls that cannot be swapped: a small class such as `Transfer(from, to, amount)` in Java, keyword arguments in Python.
- *Tools:* none. The calls are type-correct.

## IF02 · Do the attributes (type, size) of arguments match?

**The problem.** The argument has a different type from the one the parameter expects, and the language accepts it anyway or chooses another method.

**Why it matters.**
- Java: `List<Integer>.remove(1)` calls `remove(int index)`, not `remove(Object)`, so it removes the element at position 1.
- Python: a `str` is iterable, so passing `"python"` where a list of tags is expected adds six one-letter tags.

**How to check it statically.**
- *Reading:* for overloaded methods, check which overload is really chosen. In Python, look for strings passed where a collection is expected.
- *Tools:*
  - mypy `arg-type`: `Argument 2 to "add_tags" has incompatible type "str"; expected "list[str]"`.
  - Java: none in this configuration.

## IF03 · Do the units of arguments match?

**The problem.** Myers' example: a parameter in degrees, an argument in radians.

**Why it matters.**
- The Mars Climate Orbiter (1999) was lost because one team sent pound-force seconds and the other expected newton seconds.
- In the demos, milliseconds are passed as seconds (a retry in 500 s instead of 0.5 s), and `sin(90)` is not 1, because the built-in uses radians.

**How to check it statically.**
- *Reading:* write the unit next to every numeric parameter and argument (in the name: `delay_seconds`, `impulseNs`). At each call, compare the units. Convert at the interface.
- *Tools:* none. Units are not part of the types.

## IF04 · Are built-in functions called with the right arguments?

**The problem.** Myers asks whether the number, attributes and order of arguments to built-in functions are correct.

**Why it matters.**
- Java: `substring(begin, end)` is not `substring(begin, length)`, and `Arrays.asList(int[])` is a list with one element.
- Python: `"report.txt".strip(".txt")` removes the characters `.`, `t`, `x` and gives `"repor"`, and `randrange(1, 6)` never returns 6.

**How to check it statically.**
- *Reading:* for every library call you do not use every day, check its documentation: inclusive or exclusive end? length or end index? characters or substring?
- *Tools:*
  - SpotBugs `RANGE_STRING_INDEX` and `VA_PRIMITIVE_ARRAY_PASSED_TO_OBJECT_VARARG`.
  - Ruff `B005` and `PLE1310` (strip with a multi-character string).
  - No tool reports `randrange`.

## IF05 · With several entry points, is a value used that this entry never set?

**The problem.** Myers' example is a PL/I procedure with a second `ENTRY`: a parameter of one entry is used through the other. Today: a second constructor or a factory method.

**Why it matters.**
- Java: the one-argument constructor never sets `currency`, so `label()` throws `NullPointerException`.
- Python: a `classmethod` that skips `__init__` creates an object without `currency`.

**How to check it statically.**
- *Reading:* for every class, list the fields and check that **each** constructor or factory sets every one. Let secondary constructors delegate (`this(amount, "try")`, `cls(...)`). Make fields `final` in Java so the compiler checks it.
- *Tools:* none for the demos (the fixed Java version uses `final`, which makes the compiler enforce it).

## IF06 · Does a module change a parameter that is input only?

**The problem.** A function that should only *read* its argument changes it.

**Why it matters.** Computing a median by sorting the caller's list destroys the arrival order that the caller still needs.

**How to check it statically.**
- *Reading:* for every parameter that is a list, array, map or object, search the function for calls that modify it (`sort`, `add`, `remove`, `put`, item assignment). If the specification says "input", work on a copy (`sorted()`, `new ArrayList<>(values)`).
- *Tools:* none.

## IF07 · Do global values have the same definition in every module?

**The problem.** A shared value (a constant, a setting) is defined in more than one place, or seen differently by different modules.

**Why it matters.**
- Java: the checkout uses VAT 20% and the invoice still has its own copy with 18%.
- Python: `from config import VAT` copies the value at import time, so a later change in `config` is not seen.

**How to check it statically.**
- *Reading:* search the code base for each global name and each magic number. There must be one definition. In Python, read shared settings as `config.VAT`, not through `from config import VAT`.
- *Tools:* none.

## IF08 · Is a constant passed as an argument and then changed?

**The problem.** Myers' Fortran example: `CALL SUBX(J,3)` could change the value of the constant 3 if `SUBX` assigns to its parameter.

**Why it matters.**
- Java: a `static final int[]` is a final *reference*, but its content can change.
- Python: an UPPER_CASE list is still a list.
- In both demos, one VIP customer multiplies the default limits for everybody. The fix changes a copy (`limits.clone()`, `list(limits)`).

**How to check it statically.**
- *Reading:* for every "constant" that is a container, check its type. Is it really immutable (`Collections.unmodifiableList`, a tuple, a `frozenset`)? Then look at every function it is passed to.
- *Tools:* none in this configuration.

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
