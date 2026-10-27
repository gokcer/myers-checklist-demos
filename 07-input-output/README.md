# 07 · Input/output errors

Errors in **reading and writing files and messages**: wrong file attributes or open mode, format strings that do
not match their values, files used after closing or never closed, end-of-file and I/O errors, file-not-found, and
mistakes in the text the user sees.

The Java and Python demos write only to the system's temporary folder.

Run this folder: `python tools/run_all.py all 07`

| ID | Checklist question (paraphrased) | Java demo | Python demo | Found by a tool? |
|---|---|---|---|---|
| IO01 | Are the declared attributes of the file correct (encoding)? | `IO01FileEncoding` | `io01_file_encoding` | partly: SpotBugs / Ruff report a *missing* encoding |
| IO02 | Are the attributes of the open correct (mode)? | `IO02OpenMode` | `io02_open_mode` | no |
| IO03 | Does the format specification agree with the values? | `IO03FormatSpecification` | `io03_format_specification` | Python: mypy, Ruff · Java: no |
| IO04 | Is every file open when it is used? Is there enough memory? | `IO04UseBeforeOpenOrAfterClose` | `io04_use_after_close` | Java: PMD hint · Python: no |
| IO05 | Is every file closed after use? | `IO05FileNotClosed` | `io05_file_not_closed` | Java: PMD, SpotBugs · Python: Ruff |
| IO06 | Is end-of-file detected and handled correctly? | `IO06EndOfFile` | `io06_end_of_file` | Java: SpotBugs · Python: no |
| IO07 | Are I/O errors handled correctly? | `IO07IoErrorsHandled` | `io07_io_errors_handled` | Java: PMD · Python: Ruff hint |
| IO08 | Are there spelling or grammar mistakes in the output? | `IO08SpellingInOutput` | `io08_spelling_in_output` | codespell (both) |
| IO09 | Is "file not found" handled properly? | `IO09FileNotFound` | `io09_file_not_found` | no |

---

## IO01 · Are the declared attributes of the file correct (encoding)?

**The problem.** The file is read with a different character encoding from the one it was written with.

**Why it matters.** "Gökçer, İzmir" becomes "GÃ¶kÃ§er, Ä°zmir". In Python, `open()` without `encoding=` uses the
operating system's default, which is often not UTF-8 on Windows, so the same program works on one computer and not
on another.

**The demo.** A UTF-8 file read as ISO-8859-1 / latin-1. The wrong encoding is written explicitly, so the demo behaves
the same everywhere.

**How to check it statically.**
- *Reading:* every file open, reader and writer must state the encoding, and it must match the producer of the file.
- *Tools:* they report a *missing* encoding, not a wrong one.
  - SpotBugs `DM_DEFAULT_ENCODING` (reported on the `FileWriter` in `IO02`).
  - Ruff `PLW1514` (`open` in text mode without explicit `encoding`; preview rule, enabled).

## IO02 · Are the attributes of the open correct (mode)?

**The problem.** The file is opened in the wrong mode.

**Why it matters.** `new FileWriter(file)` and `open(path, "w")` **empty the file** first. A log that should keep
every event keeps only the last one.

**How to check it statically.**
- *Reading:* for every open, check the mode against the specification: read, write (truncate), append, create-only, binary or text?
- *Tools:* none. Both modes are legal.

## IO03 · Does the format specification agree with the values?

**The problem.** Myers asks whether each `FORMAT` agrees with its `READ`/`WRITE`. Today: `String.format`, `printf`, `%` and `str.format`.

**Why it matters.** Swapped or missing arguments throw at run time (`IllegalFormatConversionException`,
`MissingFormatArgumentException`, `TypeError`, `IndexError`), often only on an error path that tests rarely reach.

**How to check it statically.**
- *Reading:* count the placeholders and the arguments, and match their types one by one. In Python, prefer f-strings, where each value sits at its place.
- *Tools:*
  - mypy `str-format` reports both Python lines.
  - Ruff `F524` reports the missing `.format` argument.
  - Java: javac does not check format strings, and SpotBugs does not report these calls. Error Prone's `FormatString` check is a common choice for Java projects.

## IO04 · Is every file open when it is used? Is there enough memory?

**The problem.** Myers asks whether every file is opened before use, and whether there is enough memory for the file.

**Why it matters.**
- A write after `close()` (Java) or after the `with` block (Python) fails: `Stream closed`, `I/O operation on closed file`.
- Reading a whole large file into memory (`Files.readAllLines`, `f.read()`) can exhaust memory. Reading line by line cannot.

**How to check it statically.**
- *Reading:* every use of a file object must come before it is closed, inside the block that opened it. For files of unknown size, look for whole-file reads.
- *Tools:*
  - PMD `CloseResource` and SpotBugs `OS_OPEN_STREAM_EXCEPTION_PATH` give hints on the Java writer.
  - Python: none.

## IO05 · Is every file closed after use?

**The problem.** A file is opened and never closed.

**Why it matters.** Written data waits in a buffer. Right after "saving", the file is still **empty**. A crash loses the data. Open handles also run out on servers.

**How to check it statically.**
- *Reading:* every open must be inside `try`-with-resources (Java) or `with` (Python), or closed in `finally`.
- *Tools:*
  - PMD `CloseResource`.
  - SpotBugs `OS_OPEN_STREAM`.
  - Ruff `SIM115` (use a context manager for opening files).

## IO06 · Is end-of-file detected and handled correctly?

**The problem.** The code assumes the number of lines, or mistakes a blank line for the end of the file.

**Why it matters.**
- Java: `readLine()` returns `null` at the end, so a loop that expects 4 lines crashes on a 3-line file.
- Python: `readline()` returns `""` at the end, but a blank line is `"\n"`. Testing `not line.strip()` stops at the first blank line, and the sum is 30 instead of 60.

**How to check it statically.**
- *Reading:* find the exact end-of-file signal of each read call and check that the loop tests that signal, and only that signal.
- *Tools:*
  - SpotBugs `NP_DEREFERENCE_OF_READLINE_VALUE` (the result of `readLine()` is used without a `null` check).
  - Python: none.

## IO07 · Are I/O errors handled correctly?

**The problem.** An I/O error is caught and ignored.

**Why it matters.** The backup fails because the folder does not exist, and the program says "backup done".

**How to check it statically.**
- *Reading:* every `catch`/`except` must do something visible: report, retry, rethrow, or a written decision to ignore. An empty handler is a defect until proven otherwise.
- *Tools:*
  - PMD `EmptyCatchBlock`.
  - Ruff `SIM105` only suggests `contextlib.suppress`: a hint that the error is ignored, not a warning.

## IO08 · Are there spelling or grammar mistakes in the output?

**The problem.** Typos and grammar mistakes in messages, such as "Sucessfully" and "1 files".

**Why it matters.** Users judge quality by what they see. Wrong messages also break scripts and tests that search for the correct text.

**How to check it statically.**
- *Reading:* read every user-visible string. Check plurals for 0, 1 and many.
- *Tools:* codespell reports `Sucessfully ==> Successfully` in both files. No tool checks the plural.

## IO09 · Is "file not found" handled properly?

**The problem.** A missing file crashes the program.

**Why it matters.** A missing optional settings file shows the user a stack trace, instead of the default the specification describes.

**How to check it statically.**
- *Reading:* for every file the program opens, ask: "what if it is not there?" Check that the specification's answer (default, message, create it) is in the code.
- *Tools:* none (Java's checked `IOException` forces *some* handling, but not the right one).

---

*Text: CC BY 4.0 · Code: MIT · see the [License section](../README.md#license).*
