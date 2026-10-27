"""IO03 · Input/output · Does the format specification agree with the items being printed?"""


def points_buggy(name: str, score: int) -> str:
    return "%s scored %d points" % (score, name)          # BUG: arguments in the wrong order


def points_fixed(name: str, score: int) -> str:
    return "%s scored %d points" % (name, score)          # FIX: the order of the format


def out_of_buggy(name: str, score: int) -> str:
    return "{} scored {} of {}".format(name, score)       # BUG: three {}, two arguments


def out_of_fixed(name: str, score: int) -> str:
    return "{} scored {} of {}".format(name, score, 100)  # FIX: one argument for each {}


# Even better: an f-string such as f"{name} scored {score}" puts each value where it is used.

if __name__ == "__main__":
    print("IO03 Format string does not match the arguments (Python)   Ada, 95")
    try:
        print("  BUG ->", points_buggy("Ada", 95))
    except TypeError as e:
        print("  BUG -> TypeError:", e)
    print("  FIX ->", points_fixed("Ada", 95))
    try:
        print("  BUG ->", out_of_buggy("Ada", 95))
    except IndexError as e:
        print("  BUG -> IndexError:", e)
    print("  FIX ->", out_of_fixed("Ada", 95))
