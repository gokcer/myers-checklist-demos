"""CO05 · Computation · Can an intermediate result overflow or underflow?

Multiplying many small probabilities underflows to 0.0, even though the comparison we need is fine.
"""
import math

PROBS = [1e-5] * 80        # e.g. 80 independent events, each with probability 0.00001


def likelihood_buggy(probs: list[float]) -> float:
    result = 1.0
    for p in probs:
        result = result * p          # BUG: 1e-400 is below the smallest float -> 0.0
    return result


def log_likelihood_fixed(probs: list[float]) -> float:
    result = 0.0
    for p in probs:
        result = result + math.log(p)   # FIX: add logarithms instead of multiplying tiny numbers
    return result


if __name__ == "__main__":
    print("CO05 Intermediate underflow (Python)   80 probabilities of 0.00001")
    print("  BUG product ->", likelihood_buggy(PROBS))
    print("  FIX log of the product ->", round(log_likelihood_fixed(PROBS), 2))
