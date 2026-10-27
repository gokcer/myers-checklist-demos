"""IF03 · Interface · Do the units of each argument match the units of the parameter?"""
import math


def schedule_retry(delay_seconds: float) -> str:
    return "retry in " + str(delay_seconds) + " seconds"


# the configuration gives the delay in MILLISECONDS
def retry_buggy(delay_ms: int) -> str:
    return schedule_retry(delay_ms)               # BUG: milliseconds passed as seconds


def retry_fixed(delay_ms: int) -> str:
    return schedule_retry(delay_ms / 1000)        # FIX: convert at the interface


# built-in functions have units too: math.sin expects RADIANS
def sine_buggy(degrees: float) -> float:
    return math.sin(degrees)                      # BUG: degrees passed as radians


def sine_fixed(degrees: float) -> float:
    return math.sin(math.radians(degrees))        # FIX


if __name__ == "__main__":
    print("IF03 Units (Python)   a delay of 500 ms; sin(90 degrees)")
    print("  BUG ->", retry_buggy(500))
    print("  FIX ->", retry_fixed(500))
    print("  BUG sin(90 degrees) ->", sine_buggy(90))
    print("  FIX sin(90 degrees) ->", sine_fixed(90))
