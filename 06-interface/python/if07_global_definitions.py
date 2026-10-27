"""IF07 · Interface · Does a global value have the same definition in every module?

"from config import VAT" COPIES the value at import time. A later change in config is not seen.
"""
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), "if07_globals"))

import config
from config import VAT  # BUG: a private copy of the value, taken at import time


def gross_buggy(net: float) -> float:
    return round(net * (1 + VAT), 2)            # uses the copy


def gross_fixed(net: float) -> float:
    return round(net * (1 + config.VAT), 2)     # FIX: always read the one shared definition


if __name__ == "__main__":
    print("IF07 Global seen differently in two modules (Python)   VAT changes to 0.20, net 100")
    config.set_vat(0.20)
    print("  BUG ->", gross_buggy(100))
    print("  FIX ->", gross_fixed(100))
