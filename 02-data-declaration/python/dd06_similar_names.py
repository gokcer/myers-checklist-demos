"""DD06 · Data declaration · Are there variables with similar names (volt / volts)?"""


def max_voltage_buggy(volts: list[float]) -> float:
    volt = volts[0]
    for v in volts:
        if v > volt:
            volts = v                # BUG: meant 'volt'; the loop keeps comparing with the first value
    return volt


def max_voltage_fixed(volts: list[float]) -> float:
    highest = volts[0]               # FIX: a name that cannot be confused with 'volts'
    for v in volts:
        if v > highest:
            highest = v
    return highest


if __name__ == "__main__":
    print("DD06 Similar names (Python)   readings 220, 230, 225, expected 230.0")
    print("  BUG ->", max_voltage_buggy([220.0, 230.0, 225.0]))
    print("  FIX ->", max_voltage_fixed([220.0, 230.0, 225.0]))
