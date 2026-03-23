---
name: "test-driven-development"
description: "Enforces RED-GREEN-REFACTOR cycle: write failing test, watch it fail, write minimal code, watch it pass, commit. Invoke during implementation phase."
---

# Test-Driven Development

This skill enforces the RED-GREEN-REFACTOR cycle for test-driven development. It includes a reference of testing anti-patterns to avoid.

## Usage

1. Write a failing test first
2. Watch it fail
3. Write minimal code to make it pass
4. Watch it pass
5. Commit the changes
6. Delete any code written before tests

## Anti-patterns to Avoid

- Writing tests after implementation
- Writing tests that don't actually test the functionality
- Overly complex tests
- Tests that depend on external state
