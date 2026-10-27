"""IF04 · Interface · Are built-in functions called with the right arguments?

str.strip(".txt") removes the CHARACTERS . t x from both ends. It does not remove the suffix ".txt".
"""
import random


def base_name_buggy(file: str) -> str:
    return file.strip(".txt")                    # BUG: removes the characters ".", "t", "x"


def base_name_fixed(file: str) -> str:
    return file.removesuffix(".txt")             # FIX: removes the text ".txt" at the end


# spec: a dice gives 1, 2, 3, 4, 5 or 6. Does a 6 appear in 1000 throws?
def six_appears_buggy() -> bool:
    for _ in range(1000):
        if random.randrange(1, 6) == 6:          # BUG: the end of randrange is excluded: never 6
            return True
    return False


def six_appears_fixed() -> bool:
    for _ in range(1000):
        if random.randint(1, 6) == 6:            # FIX: randint includes the end
            return True
    return False


if __name__ == "__main__":
    print("IF04 Built-in function arguments (Python)")
    print("  BUG base name of report.txt ->", repr(base_name_buggy("report.txt")))
    print("  FIX base name of report.txt ->", repr(base_name_fixed("report.txt")))
    random.seed(1)
    print("  BUG a 6 in 1000 throws? ->", six_appears_buggy())
    print("  FIX a 6 in 1000 throws? ->", six_appears_fixed())
