"""Shared settings for the IF07 demo."""

VAT = 0.18


def set_vat(rate: float) -> None:
    global VAT  # noqa: PLW0603  (the demo needs a module-level setting)
    VAT = rate
