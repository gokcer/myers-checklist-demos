"""IF02 · Interface · Does the type of each argument match the parameter?

A str is iterable, so passing "python" where a list of tags is expected "works": you get 6 tags.
"""


def add_tags(post_tags: set[str], new_tags: list[str]) -> set[str]:
    for t in new_tags:
        post_tags.add(t)
    return post_tags


def tag_post_buggy() -> list[str]:
    tags = add_tags(set(), "python")          # BUG: a str, not a list of str
    return sorted(tags)


def tag_post_fixed() -> list[str]:
    tags = add_tags(set(), ["python"])        # FIX: a list with one tag
    return sorted(tags)


if __name__ == "__main__":
    print("IF02 Argument of the wrong type (Python)   add the tag 'python'")
    print("  BUG ->", tag_post_buggy())
    print("  FIX ->", tag_post_fixed())
