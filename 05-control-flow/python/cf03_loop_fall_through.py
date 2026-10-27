"""CF03 · Control flow · What happens if a search loop never finds anything (or runs zero times)?

After a for loop, the loop variable keeps the LAST value. Without a found-check you return the wrong item.
"""

USERS = [{"id": 1, "name": "Ada"}, {"id": 2, "name": "Linus"}]


def find_buggy(user_id: int) -> dict | None:
    for user in USERS:
        if user["id"] == user_id:
            break
    return user                           # BUG: not found -> the loop variable is still the last user


def find_fixed(user_id: int) -> dict | None:
    found = None                          # FIX: a separate result that stays None if nothing matches
    for user in USERS:
        if user["id"] == user_id:
            found = user
            break
    return found


if __name__ == "__main__":
    print("CF03 Search loop falls through (Python)   search id 99")
    print("  BUG ->", find_buggy(99))
    print("  FIX ->", find_fixed(99))
