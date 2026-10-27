"""CO09 · Computation · Are the assumptions about operator precedence correct?"""


def average_buggy(a: int, b: int) -> float:
    return a + b / 2              # BUG: / before +  ->  a + (b / 2)


def average_fixed(a: int, b: int) -> float:
    return (a + b) / 2            # FIX: parentheses say what you mean


# spec: the square of minus x, (-x) * (-x)
def square_buggy(x: int) -> int:
    return -x ** 2                # BUG: ** before unary minus  ->  -(x ** 2)


def square_fixed(x: int) -> int:
    return (-x) ** 2              # FIX


if __name__ == "__main__":
    print("CO09 Operator precedence (Python)   a = 80, b = 100; x = 2")
    print("  BUG average ->", average_buggy(80, 100))
    print("  FIX average ->", average_fixed(80, 100))
    print("  BUG square of -x ->", square_buggy(2))
    print("  FIX square of -x ->", square_fixed(2))
