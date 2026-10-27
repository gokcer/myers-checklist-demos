"""IF01 · Interface · Do the number and ORDER of arguments match the parameters (in both directions)?"""


def create_user(role: str, name: str) -> dict:
    return {"name": name, "role": role}


def make_ada_editor_buggy() -> dict:
    return create_user("Ada", "editor")              # BUG: the parameters are (role, name)


def make_ada_editor_fixed() -> dict:
    return create_user(role="editor", name="Ada")    # FIX: keyword arguments cannot be swapped by mistake


if __name__ == "__main__":
    print("IF01 Argument order (Python)   create Ada as an editor")
    print("  BUG ->", make_ada_editor_buggy())
    print("  FIX ->", make_ada_editor_fixed())
