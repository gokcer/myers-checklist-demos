"""DR09 · Data reference · Is a data structure defined the same way everywhere it is used?

The writer and the reader each "know" the order of the fields, and they do not agree.
"""

WRITER_NAME = 0         # the writer's idea of a row: name, age
WRITER_AGE = 1
READER_AGE = 0          # BUG: the reader's own idea: age, name

NAME = 0                # FIX: ONE definition of the row, used by the writer and by the reader
AGE = 1


def write_row_buggy(name: str, age: int) -> list:
    row: list = [None, None]
    row[WRITER_NAME] = name
    row[WRITER_AGE] = age
    return row


def read_age_buggy(row: list) -> object:
    return row[READER_AGE]


def write_row_fixed(name: str, age: int) -> list:
    row: list = [None, None]
    row[NAME] = name
    row[AGE] = age
    return row


def read_age_fixed(row: list) -> object:
    return row[AGE]


if __name__ == "__main__":
    print("DR09 Structure defined differently in two places (Python)   expected 36")
    print("  BUG ->", read_age_buggy(write_row_buggy("Ada", 36)))
    print("  FIX ->", read_age_fixed(write_row_fixed("Ada", 36)))
