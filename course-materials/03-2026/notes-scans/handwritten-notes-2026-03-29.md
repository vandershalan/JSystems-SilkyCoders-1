# Silky Coders 03.2026 - notatki z odręcznych skanów

Opracowanie na podstawie zdjęć notatek, w kolejności czasowej plików. To nie jest surowy OCR, tylko uporządkowany odczyt treści z dopowiedzeniem struktury. Fragmenty niepewne oznaczyłem cytatem blokowym.

Pierwsze strony z imionami uczestników to notatki z rozmowy o ich problemach z AI, dotychczasowych doświadczeniach i oczekiwaniach wobec kursu.

---

## Strona 1 - `20260329_141210(1).jpg`

# Silky Coders

- 03.2026
- III edition

## Kontekst rozmowy

- Start zajęć i zebranie oczekiwań
- Rozmowa o problemach z AI i agentami
- Rozpoznanie doświadczenia uczestników

## Tematy otwierające

- Codex
- MCP
- Jira, GitHub, Confluence
- Agent
- Co agent ma robić
- Claude

## Uczestnicy i ich potrzeby

### Artur

- FE developer
- React
- Codex w API IntelliJ
- Testy, commit, NPM-y
- Hallucynacje
- Weryfikacja: jak to zrobić

> Obok jest dopisek w ramce, prawdopodobnie: "Gemini app / przepytanie".

### Sebastian

- Java
- System architect
- React
- JS, TS
- Java, C++

> Pierwsza linia przy Sebastianie jest słabo czytelna.

---

## Strona 2 - `20260329_141216(1).jpg`

### Sebastian

- Mało pisał kodu teraz
- Testy
- Architektura
- Refactoring
- Hallucynacje
- Przegląd

> Między "architektura" i "refactoring" jest jeszcze jeden słabo czytelny dopisek.

### Daniel

- Senior Java dev
- AI assistant
- Turnie
- Full stack, też FE
- Spam
- Hallucynacje
- Gubienie kontekstu

### Tomasz

- Architect
- Outlook, Teams, Excel
- Czytanie kodu
- Bolt.new
- Cursor
- Agent
- Dużo tekstu / spam / podsumowanie
- Hallucynacje: limity czy cena

## Dodatkowe hasła

- PlantUML
- Taki Mermaid
- ChatGPT Deep Research
- Kontekstu

> Ostatnie dwa dopiski po prawej są częściowo nieczytelne i mogą należeć do innego wątku.

---

## Strona 3 - `20260329_141224(0).jpg`

### Kamil

- Java dev
- DevOps
- FE więcej niż AI ostatnio
- Od 3 tygodni nie pisze kodu
- Terminal 100%
- Claude Code
- Skills
- Agenci
- Jak radzić sobie z hallucynacjami
- Współbieżna praca subagentów
- Lepiej definiować skills
- Zapomina, że skill ma użyć MCP

### Karol

- Kotlin
- IntelliJ
- Full stack
- Server side
- HTMX
- Android
- Nie używa agentów

> Ostatnia linia przy Karolu wygląda jak uwaga o złych doświadczeniach albo o "trenowaniu", ale nie jestem pewien odczytu.

---

## Strona 4 - `20260329_141231.jpg`

### Rafał

- Tech lead
- BE, DevOps, FE
- Z AI od 8 miesięcy
- Chat, Sonet, Turnie
- Claude Code
- Sam nie wychwyci błędów, trzeba go pilnować
- Testy integracyjne
- Gubi się, nie pamięta
- TODO?
- Który agent do czego ma najlepiej pasować
- Nowy styl programowania
- Potrzeba czegoś, żeby pamiętać

## Testy i jakość

- Endpoint
- Wynik testu
- Chce bity danych z danymi
- Happy path, edge case
- Asercje
- Kombinacje

> Ostatnia kolumna po prawej jest nieostra. Widać coś w rodzaju: "ma ukryty factory / do seed bazy", ale to wymaga ręcznej weryfikacji.

---

## Strona 5 - `20260329_141239.jpg`

### Arek

- Java dev
- Kotlin
- React
- BE głównie
- iOS
- Animal research
- Mniej pisania kodu
- Gubi się
- Marnowanie czasu
- W dużym projekcie gubi się
- Mało używa AI
- Subagenci
- TDD
- Chce
- AI first: pracować, ale tak, żeby nadal się na czymś uczyć
- Architekty / wykręcanie stubów

### Patryk

- Android
- Kotlin
- BE
- Chat / Android Studio
- Gemini
- Błędy
- Testy

> Fragment "animal research" może być błędnie odczytany. Końcowa linia o "stubach" też jest częściowo niepewna.

---

## Strona 6 - `20260329_141252(1).jpg`

## Pytania i tematy do wyjaśnienia

- I need compression 400 if context?
- My context in Claude default?
- 1x or 2x price / limit?

### Karol

- Policja
- Linux
- Docker

### WSL

- Kto ma quiz na rano

## Badanie

- Badanie jakości agentów
- Tego

## Review i narzędzia

- Agent review w Temu? / Teams?
- W chmurze więcej pracy
- Odpieczkować poza CI/CD workflow
- Jira
- Confluence

> Ta strona ma kilka bardzo niepewnych fragmentów. Szczególnie linia "Agent review w Temu/Teams" wymaga ręcznego sprawdzenia.

---

## Strona 7 - `20260329_141302.jpg`

## Claude Code

- Desktop dispatch?
- Teams? - NO
- Pro / Max only
- Private mobile phone
- Sandbox
- MacOS?
- Linux i WSL2

## Claude auto mode

- Teams only
- Admin settings
- Codex guardian approvals

## Przydatne funkcje

- Voice mode -> hold Space to talk
- `/update-config`
- Claude helps with own config file, np. alias command
- `/schedule`
- `/export`
- `/debug`
- `/helpdump`

> Pierwsza sekcja zawiera kilka skrótowych haseł. "Desktop dispatch" i "Teams only" mogą wymagać doprecyzowania z Twojej pamięci.

---

## Strona 8 - `20260329_141309(1).jpg`

## Skróty i komendy w Claude

- `Ctrl + R` - search prompts
- `(Ctrl + Shift + F w terminalu)` - powiązany skrót do szukania
- `!command` - bash mode
- `Shift + Tab` - auto accept changes
- `Ctrl + O` - verbose output
- `Ctrl + Shift + -` - undo
- `Alt + V` - paste image
- `Ctrl + S` - stash prompt
- `16k w` - side question
- Write next prompt
- Queue prompts
- `Ctrl + G` - edit prompt in `$EDITOR` bez `@filename`

## Pętle i agenci

- `loop < 5m` (10m default) `<command/prompt>`
- `batch`
- 5-30 worktree agents
- Each separate PR

> Wiersz "16k w - side question" jest niepewny, ale wygląda na skrót związany z pytaniem pobocznym.

---

## Strona 9 - `20260329_141316(1)(1).jpg`

## Diagramy i dokumentacja

- PlantUML - GitLab: tak
- GitHub: nie, zamiast tego Mermaid

## Praktyka pracy w CLI

- Nie pisać długich promptów w oknie CLI
- Używać `Ctrl + G`
- Alternatywnie używać tools / desktop app

## Pytania konfiguracyjne

- Envs do Claude
- `.claude` folder?
- `.mcp.json` w projekcie nie działało?
- LangGraph Studio: jak działa?
- Online czy offline?
- Jak update kodu robi?

## Dokumentacja projektowa

- PRD i ADR gotowe stworzyć
- Dopracować tylko wzór
- Skill do robienia ADR z PRD?
- Problem z generowaniem ich z szablony

> Ostatni punkt prawdopodobnie oznacza problem z generowaniem zbyt szablonowych ADR-ów.

---

## Strona 10 - `20260329_141324(1).jpg`

## Pytania ogólne

- Claude.md: zagnieżdżone czy czyta?
- Context7 skill vs MCP
- Linux sandbox: o wszystko go pyta?
- Nie ma auto allow

## Sterowanie zespołowe

- Team sterowanie
- Wspólne rules i MD files dla wielu projektów

## Zarządzanie taskami

- Zmianie taski, aby curation Claude.md był wolny
- Jak master ma nie być developerem
- W Jenkins flow

> Dolna część strony jest słabo czytelna. Sens wygląda na pytanie o podział ról i prowadzenie pracy przez przepływ typu Jenkins.

---

## Strona 11 - `20260329_141330.jpg`

## Edit plan i automatyzacje

- Edit plan - how to?
- Only from users?
- `claude/plans`?
- `Ctrl + G`
- Hooks
- Automatyzacje
- Schedule -> 1/week odpytywanie plików `CLAUDE.md` i skills

## Subagenci

- Subagents: Claude vs Codex
- Wspólne

## Worktrees i bash

- Flaga worktree
- CLI worktrees
- Allow bash (`*`, np. git, code?)

> Ostatnia linia może odnosić się do whitelisty poleceń bash.

---

## Strona 12 - `20260329_141336.jpg`

## Praca na wielu repo

- How to work on multiple separate repos, not monorepo
- `/add-dir`

## Hooks i konfiguracja

- Hooks do skills
- `/hooks`
- `Ctrl + C` - background
- `/update-config <prompt>`
- Add Playwright MCP na poziomie globalnym albo project level
- `/mcp` - włączenie / wyłączenie MCP

## Prompt workflow

- `Ctrl + S` - stash prompt
- `/b…`
- Directly send prompt
- `/bash`
- Background

> Jedna komenda po `Ctrl + S` jest nieczytelna i wygląda jak `/b...`.

---

## Zbiorcze wnioski z notatek

### Profil pierwszych stron

- To są głównie notatki o uczestnikach
- Każda osoba opisywała swoje doświadczenia, obawy i oczekiwania wobec AI
- Powtarzały się problemy z hallucynacjami, utratą kontekstu i weryfikacją wyników
- Wiele osób chciało lepiej używać agentów, testów i workflow opartych o CLI

### Najczęstsze tematy

- Hallucynacje i sposoby weryfikacji odpowiedzi agentów
- Gubienie kontekstu przy większych projektach
- Sens używania subagentów i worktree
- Jak dobrze pisać skills, rules i pliki typu `CLAUDE.md`
- Testy: integracyjne, TDD, happy path, edge case, asercje
- Różnice między Claude Code, Codex, Cursor, Gemini i innymi narzędziami
- Sandbox, approvals, MCP, konfiguracja lokalna vs globalna

### Pytania, które warto doprecyzować na kolejnych zajęciach

- Kiedy używać subagentów, a kiedy nie
- Jak ograniczać hallucynacje w praktyce
- Jak organizować pamięć projektu i utrzymywać kontekst
- Jak dzielić pracę agentów na osobne worktree i PR-y
- Jak układać ADR/PRD i czy warto to wspierać skillami
- Co powinno być globalne, a co projektowe: MCP, hooks, config, rules
