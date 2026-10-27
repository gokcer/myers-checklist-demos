# 01 · Data reference errors

Errors in **reading or writing data**: a variable without a value, an index outside the array, a reference to
something that no longer exists, or data seen through a name with the wrong type or layout. Myers calls an
unset variable "probably the most frequent programming error".

Run this folder: `python tools/run_all.py all 01` (from the repository root).

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| DR01 | Is a variable used before it gets a value? | `DR01UnsetVariable` | `dr01_unset_variable` | Python: mypy · Java: no |
| DR02 | Is every index inside the bounds? | `DR02IndexOutOfBounds` | `dr02_index_out_of_bounds` | Java: SpotBugs · Python: no |
| DR03 | Does every index have an integer value? | `DR03NonIntegerIndex` | `dr03_non_integer_index` | Python: mypy (float index) · cast: no |
| DR04 | Does the referenced data still exist? | `DR04ReferencedDataGone` | `dr04_referenced_data_gone` | no |
| DR05 | Is the same data reached through aliases with different attributes? | `DR05AliasDifferentType` | `dr05_alias` | Java: javac `-Xlint` · Python: no |
| DR06 | Does the data really have the layout the code expects? | `DR06RecordLayout` | `dr06_record_layout` | no |
| DR07 | Are small memory units (bytes) read the way the code assumes? | `DR07SignedByte` | `dr07_bytes_vs_int` | Python: mypy · Java: no |
| DR08 | Does the object behind a reference have the expected type? | `DR08WrongReferenceType` | `dr08_wrong_reference_type` | no |
| DR09 | Is a data structure defined the same way everywhere? | `DR09StructureDefinedTwice` | `dr09_structure_defined_twice` | no |
| DR10 | Are string index limits off by one? | `DR10StringOffByOne` | `dr10_string_off_by_one` | no |
| DR11 | Are all inheritance requirements met? | `DR11InheritanceContract` | `dr11_inheritance_contract` | Java: javac, PMD, SpotBugs · Python: no |

---

## DR01 · Is a variable used before it gets a value?

**The problem.** A variable, array element or field is read on a path where nothing was assigned to it.

**Why it matters.** The program may crash (`NullPointerException`, `UnboundLocalError`), or, worse, run on with a
default value that nobody chose.

**The demo.**
- Java: `new String[3]` holds three `null`s. Only two names are set, and the loop calls `length()` on the third.
- Python: `fee` is set only for weights below or above 5 kg, so exactly 5 kg reaches `return fee` with no value.

**How to check it statically.**
- *Reading:* for every variable that is read, follow each path back to the start. Is there an assignment on
  **every** path? Pay special attention to boundary values (here: exactly 5) and to arrays created with a size but
  no contents.
- *Tools:* mypy reports `Name "fee" may be undefined [possibly-undefined]` (the error code must be enabled; it is in
  `pyproject.toml`). For Java *local* variables the compiler refuses unset reads, but array elements and fields get
  defaults (`null`, `0`), and no tool here reports the demo.

## DR02 · Is every index inside the bounds?

**The problem.** An index is smaller than 0 or not smaller than the length.

**Why it matters.** Java throws `ArrayIndexOutOfBoundsException`. Python throws `IndexError` for large indexes, but a
**negative** index is valid Python: `scores[-1]` silently reads the last element.

**The demo.**
- Java: `SCORES[SCORES.length]`.
- Python: `SCORES[len(SCORES)]`, and `previous_score(0)` reads `SCORES[-1]` and returns 90 without any error.

**How to check it statically.**
- *Reading:* for every index expression, write down its smallest and largest possible value and compare them with
  `0 .. length-1`. In Python, ask "can this index become negative?"
- *Tools:* SpotBugs reports `RANGE_ARRAY_INDEX` for the constant index in the Java demo. Nothing reports the Python
  cases, and no tool sees computed indexes in general.

## DR03 · Does every index have an integer value?

**The problem.** An index is computed with floating-point numbers and converted to an integer.

**Why it matters.** Some old languages truncated or rounded a non-integer index silently. Java and Python refuse a
float index, so programmers add a conversion: `(int)` and `int()` **truncate**, and `0.29 * 100` is
`28.999999999999996`, so the program reads element 28 instead of 29.

**The demo.**
- Java: `LABEL[(int) (priceLira * 100)]`.
- Python: the same with `int()`. Also `items[len(items) / 2]`: `/` always gives a float in Python 3.

**How to check it statically.**
- *Reading:* look at every cast or `int()` used for an index. Is the value exact, or can it be just below an integer?
  Prefer `round()`, or keep money as integer cents from the start.
- *Tools:* mypy reports the float index (`call-overload`). No tool reports the truncating cast, because it is legal.

## DR04 · Does the referenced data still exist?

**The problem.** Myers asks about pointers to memory that has been freed. Java and Python have no dangling pointers,
but a reference can still point to something that is used up.

**Why it matters.** A Java `Iterator` and a Python generator give their values once. A second pass over the same
iterator silently gives **nothing**, so the total is 0 and there is no error.

**The demo.** Both programs count the big orders and then try to add them up from the same iterator / generator.
The fix asks the list for a new iterator (Java) or keeps the data in a list (Python).

**How to check it statically.**
- *Reading:* for every iterator, stream, generator, cursor or connection, count how many times it is used. More than
  once means a bug. Also check objects that are closed, cleared or reassigned while someone still holds them.
- *Tools:* none of the tools used here reports either demo.

## DR05 · Is the same data reached through aliases with different attributes?

**The problem.** Two names refer to the same memory, and one of them has different attributes. Myers' examples are
Fortran `EQUIVALENCE` and COBOL `REDEFINES`.

**Why it matters.** A change through one name appears under the other name, often with a different type.

**The demo.**
- Java: a raw `List` alias of a `List<String>` lets an `Integer` in. The `ClassCastException` appears later, at a
  line that did nothing wrong.
- Python: `backup = scores` does not copy; the "backup" changes together with the scores.

**How to check it statically.**
- *Reading:* for every assignment of a list, array, map or object, ask: "is this a copy or a second name?" In Java,
  look for raw types and casts on collections.
- *Tools:* `javac -Xlint:all` warns `found raw type` and `unchecked call to add(E)`. Nothing reports the Python alias.

## DR06 · Does the data really have the layout the code expects?

**The problem.** A record (from a file, network or device) is read with a different layout from the one it was
written with: byte order, field sizes, signedness.

**Why it matters.** The value 1000 is read as −402,456,576 (Java) or 3,892,510,720 (Python), and nothing fails.

**The demo.** A 4-byte little-endian integer is read as big-endian: `ByteBuffer` (Java) and `struct ">I"` (Python).

**How to check it statically.**
- *Reading:* compare every read of binary data with the format specification of the writer. Is the byte order
  stated explicitly (`ByteOrder.LITTLE_ENDIAN`, `"<"`) on both sides?
- *Tools:* none. The tools cannot know the layout of the file.

## DR07 · Are small memory units (bytes) read the way the code assumes?

**The problem.** Myers mentions memory units that are smaller than the addressable unit. In today's languages the
trap is how a single byte is represented.

**Why it matters.** Java's `byte` is signed: 200 is stored as −56. In Python, `packet[0]` is an `int` (65), while
`b"A"` is `bytes`, so `packet[0] == b"A"` is always `False`.

**The demo.** A length byte of 200 (Java), and an `ACK` check on a packet (Python).

**How to check it statically.**
- *Reading:* wherever bytes are read, ask: signed or unsigned? Index (an int) or slice (bytes)?
- *Tools:* mypy (with `strict_equality`) reports `Non-overlapping equality check (left operand type: "int", right
  operand type: "bytes")`. No tool reports the signed byte.

## DR08 · Does the object behind a reference have the expected type?

**The problem.** The code assumes a more specific type than the object really has.

**Why it matters.** Java throws `ClassCastException`. Python throws `AttributeError`, or works by accident for some
inputs.

**The demo.**
- Java: every `Shape` is cast to `Circle`.
- Python: the result of `json.loads` is a `dict`, but the code reads `user.name`.

**How to check it statically.**
- *Reading:* look at every cast and at every value that comes from outside (JSON, database, framework). What type is
  it *really*? Check the type with `instanceof` before a cast, or convert the data into the class you expect.
- *Tools:* none reports these demos (the cast is legal Java, and `json.loads` returns `Any`).

## DR09 · Is a data structure defined the same way everywhere?

**The problem.** Two modules each define the same structure themselves, and the definitions differ.

**Why it matters.** The writer stores `(name, age)`, the reader reads `(age, name)`. The program crashes (Java,
`NumberFormatException`) or returns a name where an age was expected (Python).

**The demo.** A CSV row (Java) and a list (Python) shared by a writer and a reader. Each side has its own column
numbers. The fix keeps the column numbers in **one** place that both sides use.

**How to check it statically.**
- *Reading:* search for every place that knows the field order or layout. There should be **one** definition (a
  class, a set of constants, a schema) used by everybody.
- *Tools:* none. Each side is correct on its own; only the two together are wrong.

## DR10 · Are string index limits off by one?

**The problem.** A substring or character index is one position too far or too short.

**Why it matters.** `".pdf"` instead of `"pdf"`, or `charAt(length())` crashes.

**The demo.**
- Java: an extension starting at the dot, and the last character read at `length()`.
- Python: `s[1:len(s)]` removes only the first quote.

**How to check it statically.**
- *Reading:* test each index mentally with the shortest example, such as `"a.b"` or `"x"`. Remember: start index
  included, end index excluded, last index is `length - 1`.
- *Tools:* none for these demos.

## DR11 · Are all inheritance requirements met?

**The problem.** A subclass does not fulfil the rules of its parent or of the language.

**Why it matters.** Java: overriding `equals` without `hashCode` breaks `HashSet` and `HashMap` (two equal books are
stored twice). Python: a subclass `__init__` that does not call `super().__init__()` never creates the parent's
fields.

**The demo.** The ISBN class (Java) and the savings account (Python).

**How to check it statically.**
- *Reading:* for every subclass or override, list the parent's contract: `equals` with `hashCode`, `compareTo`
  consistent with `equals`, calling the parent constructor, abstract methods.
- *Tools:* very well covered in Java: javac (`overrides equals, but neither it nor any superclass overrides
  hashCode`), PMD `OverrideBothEqualsAndHashcode`, SpotBugs `HE_EQUALS_USE_HASHCODE`. Python: none of the tools
  configured here (Pylint's `W0231 super-init-not-called` would report it).

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
