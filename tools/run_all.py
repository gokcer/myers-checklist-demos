"""Run every checklist example and show its BUG / FIX output.

    python tools/run_all.py                 # everything
    python tools/run_all.py python          # only Python examples
    python tools/run_all.py java            # only Java examples
    python tools/run_all.py java 04         # only Java examples of folder 04-comparison

Java examples are started with the single-file source launcher (java File.java), so no build is needed.
"""
import re
import shutil
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
PY_NAME = re.compile(r"^[a-z]{2}\d\d_.+\.py$")
JAVA_NAME = re.compile(r"^[A-Z]{2}\d\d.+\.java$")


def examples(language: str, prefix: str) -> list[Path]:
    found = []
    for folder in sorted(ROOT.glob("[0-9][0-9]-*")):
        if prefix and not folder.name.startswith(prefix):
            continue
        if language in ("all", "python"):
            found += sorted(p for p in (folder / "python").glob("*.py") if PY_NAME.match(p.name))
        if language in ("all", "java"):
            found += sorted(p for p in (folder / "java").glob("*.java") if JAVA_NAME.match(p.name))
    return found


def command(path: Path) -> list[str]:
    if path.suffix == ".py":
        return [sys.executable, "-I", path.name]
    return ["java", path.name]


def main() -> int:
    language = sys.argv[1] if len(sys.argv) > 1 else "all"
    prefix = sys.argv[2] if len(sys.argv) > 2 else ""
    if language not in ("all", "python", "java"):
        print(__doc__)
        return 2
    if language in ("all", "java") and shutil.which("java") is None:
        print("java was not found on PATH; install a JDK (17 or newer) or run: python tools/run_all.py python")
        return 2
    failed = []
    items = examples(language, prefix)
    for path in items:
        print(f"\n=== {path.relative_to(ROOT)}")
        try:
            result = subprocess.run(command(path), cwd=path.parent, capture_output=True, text=True,
                                    encoding="utf-8", timeout=60, check=False)
        except subprocess.TimeoutExpired:
            print("  (timed out)")
            failed.append(path)
            continue
        print(result.stdout.rstrip())
        if result.stderr.strip():
            # compiler and interpreter warnings are part of the lesson (see 08-other-checks/OT03)
            print("  [stderr] " + result.stderr.strip().replace("\n", "\n  [stderr] "))
        if result.returncode != 0:
            failed.append(path)
    print(f"\n{len(items)} examples run, {len(failed)} did not finish normally.")
    for path in failed:
        print(f"  - {path.relative_to(ROOT)}")
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())
