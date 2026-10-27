"""CP01 · Comparison · Are values of different types compared (text with a number)?

Text from input() or a file compared with a number is never equal. Text compared with text sorts by characters.
"""


def same_id_buggy(id_text: str, user_id: int) -> bool:
    return id_text == user_id             # BUG: text and a number are never equal


def same_id_fixed(id_text: str, user_id: int) -> bool:
    return int(id_text) == user_id        # FIX: convert the text first


def is_adult_buggy(age_text: str) -> bool:
    return age_text >= "18"               # BUG: string comparison: "9" >= "18" is True


def is_adult_fixed(age_text: str) -> bool:
    return int(age_text) >= 18            # FIX: compare numbers as numbers


if __name__ == "__main__":
    print("CP01 Comparison of different types (Python)   values read as text: '42', '9'")
    print("  BUG is '42' user 42? ->", same_id_buggy("42", 42))
    print("  FIX is '42' user 42? ->", same_id_fixed("42", 42))
    print("  BUG is '9' an adult? ->", is_adult_buggy("9"))
    print("  FIX is '9' an adult? ->", is_adult_fixed("9"))
