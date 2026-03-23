---
name: "notebooklm"
description: "Enables Claude Code to communicate directly with Google NotebookLM notebooks. Query uploaded documents and get source-grounded, citation-backed answers from Gemini."
---

# NotebookLM Skill

This skill enables Claude Code to communicate directly with your Google NotebookLM notebooks. Query your uploaded documents and get source-grounded, citation-backed answers from Gemini. Features browser automation, library management, persistent authentication, and answers exclusively from your own knowledge base.

## Key Features

- **Direct communication**: Claude Code chats directly with NotebookLM
- **Source-grounded answers**: Answers exclusively from your uploaded documents
- **Browser automation**: Automatic handling of NotebookLM interactions
- **Library management**: Manage multiple NotebookLM notebooks
- **Persistent authentication**: One-time authentication for continuous use
- **Citation-backed**: Every answer includes source references

## Problem Solved

- **Reduced token consumption**: Avoids repeatedly reading multiple files
- **Improved retrieval accuracy**: Based on understanding, not just keyword search
- **Minimized hallucinations**: Answers only from user-uploaded documents
- **Eliminated manual copy-paste**: Get answers directly in the CLI

## Comparison with Other Approaches

| Approach | Token Cost | Setup Time | Hallucinations | Answer Quality |
|----------|------------|------------|----------------|----------------|
| Feed docs to Claude | 🔴 Very high | Instant | Yes - fills gaps | Variable retrieval |
| Web search | 🟡 Medium | Instant | High - unreliable sources | Hit or miss |
| Local RAG | 🟡 Medium-High | Hours (embeddings, chunking) | Medium - retrieval gaps | Depends on setup |
| NotebookLM Skill | 🟢 Minimal | 5 minutes | Minimal - source-grounded only | Expert synthesis |

## Why NotebookLM is Superior

- **Pre-processed by Gemini**: Upload docs once, get instant expert knowledge
- **Natural language Q&A**: Not just retrieval — actual understanding and synthesis
- **Multi-source correlation**: Connects information across 50+ documents
- **Citation-backed**: Every answer includes source references
- **No infrastructure**: No vector DBs, embeddings, or chunking strategies needed

## Installation

The simplest installation ever:

1. Create skills directory (if it doesn't exist):
   ```bash
   mkdir -p ~/.claude/skills
   ```

2. Clone this repository:
   ```bash
   cd ~/.claude/skills
   git clone https://github.com/PleasePrompto/notebooklm-skill notebooklm
   ```

3. That's it! Open Claude Code and say:
   ```
   What are my skills?
   ```

When you first use the skill, it automatically:

- Creates an isolated Python environment (.venv)
- Installs all dependencies including Google Chrome
- Sets up browser automation with Chrome (not Chromium) for maximum reliability
- Everything stays contained in the skill folder

Note: The setup uses real Chrome instead of Chromium for cross-platform reliability, consistent browser fingerprinting, and better anti-detection with Google services

## Quick Start

1. **Check your skills**
   Say in Claude Code:
   ```
   What skills do I have?
   ```
   Claude will list your available skills including NotebookLM.

2. **Authenticate with Google (one-time)**
   ```
   Set up NotebookLM authentication
   ```
   A Chrome window opens → log in with your Google account

3. **Create your knowledge base**
   Go to notebooklm.google.com → Create notebook → Upload your docs:
   - 📄 PDFs, Google Docs, markdown files
   - 🔗 Websites, GitHub repos
   - 🎥 YouTube videos
   - 📚 Multiple sources per notebook
   
   Share: ⚙️ Share → Anyone with link → Copy

4. **Add to your library**
   - **Option A: Smart Add**
     ```
     Query this notebook about its content and add it to my library: [your-link]
     ```
     Claude will automatically query the notebook to discover its content, then add it with appropriate metadata.
     
   - **Option B: Manual add**
     ```
     Add this NotebookLM to my library: [your-link]
     ```
     Claude will ask for a name and topics, then save it for future use.

5. **Start researching**
   ```
   What does my React docs say about hooks?
   ```
   Claude automatically selects the right notebook and gets the answer directly from NotebookLM.

## Important Note

⚠️ **Local Claude Code Only**
This skill works ONLY with local Claude Code installations, NOT in the web UI.
The web UI runs skills in a sandbox without network access, which this skill requires for browser automation. You must use Claude Code locally on your machine.
