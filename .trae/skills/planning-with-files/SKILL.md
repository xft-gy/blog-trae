---
name: "planning-with-files"
description: "Implements Manus-style persistent markdown planning — the workflow pattern behind the $2B acquisition. Invoke when starting a planning session."
---

# Planning with Files

This skill implements Manus-style persistent markdown planning — the workflow pattern behind the $2B acquisition by Meta. It transforms your workflow to use persistent markdown files for planning, progress tracking, and knowledge storage.

## Key Features

- **Persistent planning**: Uses markdown files for planning, progress tracking, and knowledge storage
- **Session recovery**: Automatically recovers previous session when context fills up and you run /clear
- **Multi-platform support**: Compatible with 16 IDE platforms
- **Sandbox runtime**: Supports BoxLite hardware-isolated micro-VM runtime
- **Command support**: /plan, /plan:status, /planning commands

## Supported IDEs

- Claude Code
- Gemini CLI
- OpenClaw
- Kiro
- Cursor
- Continue
- Kilocode
- OpenCode
- Codex
- FactoryAI Droid
- Antigravity
- CodeBuddy
- AdaL CLI (Sylph AI)
- Pi Agent
- GitHub Copilot
- Mastra Code

## Commands

| Command | Autocomplete | Description |
|---------|-------------|-------------|
| /plan | Type /plan | Start planning session (v2.11.0+) |
| /plan:status | Type /plan:status | Show planning progress at a glance (v2.15.0+) |
| /planning | Type /planning | Original start command |

## Session Recovery

When your context fills up and you run /clear, this skill automatically recovers your previous session:

1. Checks for previous session data in ~/.claude/projects/
2. Finds when planning files were last updated
3. Extracts conversation that happened after (potentially lost context)
4. Shows a catchup report so you can sync

Pro tip: Disable auto-compact to maximize context before clearing:
```json
{ "autoCompact": false }
```

## Installation

In Claude Code, run:
```
/plugin marketplace add OthmanAdi/planning-with-files
/plugin install planning-with-files@planning-with-files
```

Alternative: If you want /planning-with-files (without prefix), copy skills to your local folder:

macOS/Linux:
```bash
cp -r ~/.claude/plugins/cache/planning-with-files/planning-with-files/*/skills/planning-with-files ~/.claude/skills/
```

Windows (PowerShell):
```powershell
Copy-Item -Recurse -Path "$env:USERPROFILE\.claude\plugins\cache\planning-with-files\planning-with-files\*\skills\planning-with-files" -Destination "$env:USERPROFILE\.claude\skills\"
```

## Current Version

v2.18.2 - Mastra Code hooks fix (hooks.json + docs accuracy)

## Experimental Features

Isolated parallel planning (.planning/{uuid}/ folders) is being tested on experimental/isolated-planning.
