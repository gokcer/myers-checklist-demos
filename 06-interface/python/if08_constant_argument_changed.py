"""IF08 · Interface · Is a "constant" passed as an argument and then changed?

Python has no real constants. An UPPER_CASE list is still a mutable list.
"""

DEFAULT_LIMITS = [100, 500, 1000]          # looks constant, but it is a list: used by the BUG demo
STANDARD_LIMITS = [100, 500, 1000]         # the same values, used by the FIX demo


def vip_limits_buggy(limits: list[int]) -> list[int]:
    result = limits                        # BUG: the same list as the caller's "constant"
    for i in range(len(result)):
        result[i] *= 10
    return result


def vip_limits_fixed(limits: list[int]) -> list[int]:
    result = list(limits)                  # FIX: change a copy (better still: keep constants in a tuple)
    for i in range(len(result)):
        result[i] *= 10
    return result


if __name__ == "__main__":
    print("IF08 Constant argument changed by the callee (Python)   defaults [100, 500, 1000]")
    vip = vip_limits_buggy(DEFAULT_LIMITS)
    print("  BUG -> VIP", vip, "defaults now", DEFAULT_LIMITS)
    vip = vip_limits_fixed(STANDARD_LIMITS)
    print("  FIX -> VIP", vip, "defaults now", STANDARD_LIMITS)
