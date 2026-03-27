# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

@AGENTS.md

## Claude Code Instructions

- Commit after every completed step; format: `Area: short summary` (e.g. `Backend:`, `Frontend:`, `Docs:`)
- Run lint + tests before the final commit on any task
- Read `docs/ADR/` before making architectural or structural changes
- Use Context7 MCP (`resolve-library-id` + `query-docs`) for any library listed in AGENTS.md
