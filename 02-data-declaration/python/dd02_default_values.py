"""DD02 · Data declaration · If an attribute is not stated, is its default well understood?

A default argument value is created ONCE, when the function is defined, not on every call.
"""


def add_tag_buggy(tag: str, tags: list[str] = []) -> list[str]:   # BUG: one list for all calls
    tags.append(tag)
    return tags


def add_tag_fixed(tag: str, tags: list[str] | None = None) -> list[str]:
    if tags is None:                                               # FIX: a new list on every call
        tags = []
    tags.append(tag)
    return tags


if __name__ == "__main__":
    print("DD02 Default value evaluated once (Python)   first call 'a', second call 'b' should give ['b']")
    add_tag_buggy("a")
    print("  BUG ->", add_tag_buggy("b"))
    add_tag_fixed("a")
    print("  FIX ->", add_tag_fixed("b"))
