"""DR08 · Data reference · Does the object behind a name have the type the code expects?

json.loads gives a dict, not an object with attributes.
"""
import json

TEXT = '{"name": "Ada", "age": 36}'


class User:
    def __init__(self, name: str, age: int) -> None:
        self.name = name
        self.age = age


def name_buggy() -> str:
    data = json.loads(TEXT)
    user = data                                # BUG: the code treats the dict as a User
    return user.name                           # a dict has keys, not attributes


def name_fixed() -> str:
    data = json.loads(TEXT)
    user = User(data["name"], data["age"])     # FIX: turn the data into the type you expect
    return user.name


if __name__ == "__main__":
    print("DR08 Reference to an object of another type (Python)")
    try:
        print("  BUG ->", name_buggy())
    except AttributeError as e:
        print("  BUG -> AttributeError:", e)
    print("  FIX ->", name_fixed())
