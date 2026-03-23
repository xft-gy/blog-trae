---
name: "finishing-a-development-branch"
description: "Verifies tests, presents options (merge/PR/keep/discard), cleans up worktree. Invoke when tasks complete."
---

# Finishing a Development Branch

This skill activates when tasks complete to verify tests, present options (merge/PR/keep/discard), and clean up the worktree.

## Usage

1. **Run tests**: Verify all tests pass
2. **Present options**: Offer choices for the branch:
   - Merge into main
   - Create a pull request
   - Keep the branch for future work
   - Discard the branch
3. **Execute chosen option**: Perform the selected action
4. **Clean up**: Remove temporary files and worktrees
5. **Document completion**: Record what was accomplished
