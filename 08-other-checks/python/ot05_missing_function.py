"""OT05 · Other · Is a function missing?

Spec: "A password reset link expires after 15 minutes." There is no expiry check at all.
"""

LINK_LIFETIME_MINUTES = 15


def reset_allowed_buggy(link_age_minutes: int) -> bool:
    # BUG: the expiry check is missing
    return True


def reset_allowed_fixed(link_age_minutes: int) -> bool:
    return link_age_minutes <= LINK_LIFETIME_MINUTES      # FIX: the check the spec asks for


if __name__ == "__main__":
    print("OT05 Missing function (Python)   the link was created 120 minutes ago")
    print("  BUG ->", reset_allowed_buggy(120))
    print("  FIX ->", reset_allowed_fixed(120))
