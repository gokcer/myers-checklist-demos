"""DD03 · Data declaration · Is a variable initialized correctly where it is declared?

[[0] * 3] * 3 repeats a reference to ONE inner list three times.
"""


def board_buggy() -> list[list[int]]:
    board = [[0] * 3] * 3                  # BUG: three references to the same row
    board[0][0] = 1
    return board


def board_fixed() -> list[list[int]]:
    board = [[0] * 3 for _ in range(3)]    # FIX: a new row for each of the 3 places
    board[0][0] = 1
    return board


if __name__ == "__main__":
    print("DD03 Initialization shares one object (Python)   set board[0][0] = 1")
    print("  BUG ->", board_buggy())
    print("  FIX ->", board_fixed())
