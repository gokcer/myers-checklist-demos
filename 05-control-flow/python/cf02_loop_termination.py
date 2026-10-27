"""CF02 · Control flow · Will every loop end?"""

SAFETY = 1000        # only so that this demo stops; real code has no such guard


def initials_buggy(words: list[str]) -> str:
    i, steps, out = 0, 0, []
    while i < len(words) and steps < SAFETY:
        steps += 1
        word = words[i]
        if not word:
            continue                       # BUG: jumps over the i += 1 below -> loops forever on ""
        out.append(word[0])
        i += 1
    return "".join(out) if steps < SAFETY else "does not end (stopped by the guard)"


def initials_fixed(words: list[str]) -> str:
    i, steps, out = 0, 0, []
    while i < len(words) and steps < SAFETY:
        steps += 1
        word = words[i]
        i += 1                             # FIX: move the counter before any continue (or use a for loop)
        if not word:
            continue
        out.append(word[0])
    return "".join(out) if steps < SAFETY else "does not end (stopped by the guard)"


if __name__ == "__main__":
    print('CF02 Loop termination (Python)   words = ["apple", "", "egg"]')
    print("  BUG ->", initials_buggy(["apple", "", "egg"]))
    print("  FIX ->", initials_fixed(["apple", "", "egg"]))
