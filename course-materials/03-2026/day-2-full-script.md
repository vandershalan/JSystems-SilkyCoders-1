# Dzień 2 — Pełny scenariusz prowadzenia
**Temat: Od pomysłu do projektu — AI jako Twój PM, Architekt i Konfigurator**
**Godz. 9:00–16:00 | Zoom online | Grupa: SilkyCoders — Java devs, seniorzy, tech leads, architekci**

> 🎬 = co mówię (dosłownie lub prawie dosłownie)
> 📺 = co pokazuję na ekranie
> 💬 = wklejam na Zoom chat (gotowy tekst do skopiowania)
> 🏋️ = ćwiczenie dla uczestników
> ⏱️ = czas bloku
> 💡 = wskazówka / uwaga dla prowadzącego (nie mów tego głośno)
> 🔵 = zadanie dodatkowe dla zaawansowanych (opcjonalne)

---

## AGENDA DNIA (wyślij na starcie)

💬 WKLEJ NA CHAT:
```text
Dzień 2 – agenda:

=== RANO: DOMKNIĘCIE DNIA 1 ===
09:00  Reset + agenda + projekt tygodnia
09:10  Sandbox, Permissions, WSL/macOS — bezpieczeństwo agentów
09:50  IntelliJ: stara integracja vs nowy ACP Registry + IntelliJ MCP Server
10:25  Claude Code: komendy i funkcje (Ctrl+G, /loop, /batch, /schedule)
10:55  ☕ PRZERWA

=== DZIEŃ 2: OD POMYSŁU DO PROJEKTU ===
11:10  Moduł 2.1 — AI jako Twój PM: PRD, User Stories, Requirements
12:15  Moduł 2.2 — Research + Tech Stack + ADR
13:00  🍽️ PRZERWA
13:30  Moduł 2.2 cd — UML: Mermaid, PlantUML, architektura
14:30  Moduł 2.3 — MCP, Skills, Sub-agenci, CLAUDE.md
15:30  Projekt: decyzja o stacku + kickoff
15:55  Podsumowanie dnia
16:00  Koniec
```

---

## 09:00–09:10 — Reset + agenda + projekt tygodnia
⏱️ 10 min

🎬 **CO MÓWIĘ:**

„Dzień dobry! Kciuki jeśli mnie słychać i widać.

Zanim wejdziemy w dzień 2 — mam dla Was dobrą wiadomość i zła wiadomość.

Zła: nie zdążyłem wczoraj z kilkoma tematami i będziemy je robić rano. Są to tematy o bezpieczeństwie i narzędziach, bez których reszta dnia nie ma sensu, więc to jest absolutny priorytet.

Dobra: mamy projekt. Konkretny. Prawdziwy. I dzisiaj będziemy go planować — PRD, architektura, diagramy, decyzje technologiczne — z pomocą agenta. Wyjdziemy z tym dniem z pełnym planem gotowym do implementacji jutro.

Projekt to chatbot dla Sinsay Fashion — obsługa reklamacji i zwrotów. Zaraz powiem więcej, ale już teraz miejcie to z tyłu głowy — bo wszystkie ćwiczenia będziemy robić pod ten projekt."

💬 WKLEJ NA CHAT:
```text
Projekt tygodnia: Sinsay Complaint & Returns Chatbot

Flow:
1) Formularz: upload zdjęcia + opis + wybór (reklamacja / zwrot)
2) Agent analizuje zdjęcie (vision model — osobne wywołanie LLM)
3) Agent czyta dokumenty proceduralne (MD files — co wolno, co nie)
4) Podejmuje decyzję i uzasadnienie (drugie wywołanie LLM)
5) Klient może kontynuować rozmowę na chacie

Stack (bezpieczna ścieżka):
Java 21 + Spring Boot + OpenAI Java SDK lub Spring AI
Frontend: React + Vercel AI SDK lub AssistantUI
Baza: SQLite (sesje, historia czatu)

Opcjonalnie:
+ RAG: SQLite Vector lub ChromaDB (średnio-zaawansowani)
+ LangGraph4j + CopilotKit + AG-UI (zaawansowani, na własne ryzyko)
```

---

## 09:10–09:50 — Sandbox, Permissions, WSL/macOS, bezpieczeństwo
⏱️ 40 min

🎬 **CO MÓWIĘ:**

„Zaczynamy od bezpieczeństwa.

Wiem, że 'bezpieczeństwo agentów' brzmi jak temat na osobny kurs albo jak coś czym zajmuje się tylko security team. Ale nie — to jest coś co każdy z Was musi rozumieć, bo to wy prowadzicie agentów w swoich projektach.

Zadajcie sobie pytanie: co by się stalo, gdybyście napisali agentowi 'wyczys projekt, zacznijmy od nowa' — a agent zrozumiał to zbyt dosłownie? Albo agent przypadkowo wgrał klucze produkcyjne do publicznego repo? Albo odczytał plik `.env` i wysłał go w odpowiedzi?

To nie są SF. To są rzeczy które dzieją się naprawdę.

Mamy trzy warstwy ochrony:
1. **Sandbox** — izolacja środowiska na poziomie OS
2. **Permissions** — biała lista i czarna lista co agent może wykonywać
3. **Settings** — plik konfiguracyjny który wszystko łączy

I nie, WSL sam w sobie nie jest sandboxem — to jest częsty mit. WSL to narzędzie do uruchamiania linuxowych programow na Windows. Izolacja to osobny temat."

📺 **CO POKAZUJĘ:**
- Plik settings.json z przykładowymi permissions (z materials course)
- Wyjaśniam każdą sekcję

💬 WKLEJ NA CHAT:
```text
Gdzie jest plik settings.json:

• Lokalny (projekt):  .claude/settings.json  ← najwyższy priorytet!
• Globalny (user):    ~/.claude/settings.json
• Windows global:     %APPDATA%\Claude\settings.json
• macOS global:       ~/Library/Application Support/Claude/settings.json

Priorytety (od najwyzszego do najniższego):
1. .claude/settings.json  (w katalogu projektu)
2. ~/.claude/settings.json (użytkownika)
3. Domyślne ustawienia Claude Code

Docs: https://code.claude.com/docs/en/permissions
```

💬 WKLEJ NA CHAT:
```json
// Przykladowy .claude/settings.json (bezpieczny punkt startowy):
{
  "permissions": {
    "allow": [
      "Bash(cat:*)", "Bash(ls:*)", "Bash(find:*)", "Bash(grep:*)",
      "Bash(echo:*)", "Bash(wc:*)", "Bash(diff:*)",
      "Bash(git status:*)", "Bash(git log:*)", "Bash(git diff:*)",
      "Bash(git add:*)", "Bash(git commit:*)", "Bash(git checkout:*)",
      "Bash(git fetch:*)", "Bash(git merge:*)", "Bash(git branch:*)",
      "Bash(mvn:*)", "Bash(gradle:*)", "Bash(java:*)", "Bash(javac:*)",
      "Bash(npm install:*)", "Bash(npm run:*)", "Bash(npm test:*)",
      "Bash(npx:*)",
      "Bash(docker ps:*)", "Bash(docker logs:*)", "Bash(docker inspect:*)",
      "Bash(which:*)", "Bash(env:*)", "Bash(whoami)",
      "Edit", "Write", "MultiEdit",
      "WebSearch"
    ],
    "deny": [
      "Bash(rm:*)", "Bash(rmdir:*)",
      "Bash(git push --force:*)", "Bash(git push:*)",
      "Bash(git reset --hard:*)",
      "Bash(chmod:*)", "Bash(sudo:*)", "Bash(curl:*)", "Bash(wget:*)",
      "Bash(docker run:*)", "Bash(docker rm:*)", "Bash(docker rmi:*)",
      "Read(.env)", "Read(.env.*)", "Read(**/secrets/**)"
    ]
  }
}
```

🎬 **SANDBOX — SZCZEGOLY:**

„Teraz sandbox. Zależ od tego, czego używasz i na czym pracujesz.

**Windows bez WSL2**: AppContainer — ogranicza siec i filesystem, ale agent ma potencjalny dostep do plikow poza projektem. Nie jest pelna izolacja.

**Windows + WSL2**: możecie uzyc bwrap (bubblewrap) — linux namespace isolation. Uruchom `claude --sandbox`. Znacznie lepsza izolacja.

**Claude Code Desktop App na Windows Pro/Enterprise**: Hyper-V VM — pełna izolacja. ALE wymaga Windows Pro lub Enterprise — na Home nie działa!

**macOS**: Claude Code używa `sandbox-exec` — wbudowane, automatyczne.

**--dangerously-skip-permissions** — to jest nuklearny przycisk. I jak każdy przycisk nuklearny — jest po coś, ale używamy go świadomie."

💬 WKLEJ NA CHAT:
```text
--dangerously-skip-permissions — kiedy TAK a kiedy NIE:

❌ NIGDY gdy:
• Twój glowny laptop z dostepem do produkcji
• W katalogu jest .env z kluczami produkcyjnymi
• Repo ma push access do main/master
• Srodowisko bankowe / RODO-wrazliwe dane
• Nie wiesz co robią Twoje skrypty startowe

✅ OK gdy:
• Dedykowany izolowany kontener Docker
• VM bez wrazliwych danych (np. stary laptop-sandbox)
• CI/CD w dedykowanym, izolowanym srodowisku
• Testowanie nowego narzędzia w throwaway repo

Artykul: https://www.ksred.com/claude-code-dangerously-skip-permissions-when-to-use-it-and-when-you-absolutely-shouldnt/
Sandboxing docs: https://code.claude.com/docs/en/sandboxing
Permissions docs: https://code.claude.com/docs/en/permissions
```

🎬 **DOCKER SANDBOX (eksperymentalne):**

„Dla tych ktorzy chca najwyższy poziom bezpieczeństwa w CI/CD — Docker AI Sandboxes. To eksperymentalna funkcja od Dockera.

Kluczowy feature: **credential injection** — kontener dostaje tylko te klucze API których potrzebuje, bez dostepu do credentiali z hosta. Agent działa izolowany, dostaje swoje narzędzia i dane, i nie widzi nic poza swoim sandboxem."

💬 WKLEJ NA CHAT:
```text
Docker AI Sandboxes (eksperymentalne, dobre na CI/CD):
https://docs.docker.com/ai/sandboxes/architecture/#credential-injection

Idea:
• Agent działa w pelni izolowanym kontenerze Docker
• Credential injection: agent dostaje TYLKO klucze których potrzebuje
• Zero dostepu do credentiali hosta
• Dobre dla: agenty autonomiczne, CI/CD, produkcyjne środowiska

Kiedy rozwazyc:
• Masz agent w CI który ma dostep do API keys
• Chcesz --dangerously-skip-permissions ALE bezpiecznie
• Agenty workload bez nadzoru czlowieka
```

🏋️ **ĆWICZENIE (8 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie — Permissions setup:

1. W katalogu projektu stworzcie: .claude/settings.json
   (skopiujcie przykładowy JSON z chatu powyżej)

2. Uruchomcie claude w tym katalogu i sprawdzcie:
   /doctor         ← diagnostyka środowiska i permissions
   /permissions    ← lista aktywnych uprawnien (jeśli komenda dostepna)

3. Test: sprobujcie wydac polecenie które jest w deny:
   "Wykonaj polecenie: rm -rf node_modules"
   — co się dzieje? Dostajecie pytanie o zgodę? Odmowe?

4. Bonus: zapytajcie agenta:
   "Wymien dokladnie jakie masz teraz uprawnienia i czego nie mozesz robić."

Wyniki: 👍 działa / 🐛 problem
```

🎬 **DOMKNIĘCIE:**

„Zapamiętajcie jedno zdanie: **permissions to umowa z agentem, która mowi mu co wolno a czego nie — i chroni Was przed bledami obu stron**. Nie jest to ograniczenie AI. To jest dojrzala inzynieria.

I jeszcze jedno: settings.json w projekcie nadpisuje globalny. Czyli możecie mieć liberalne ustawienia globalne do eksperymentów, i restrykcyjne na poziomie projektu produkcyjnego. Separacja 100%."

---

## 09:50–10:25 — IntelliJ: stara integracja vs ACP + IntelliJ MCP Server
⏱️ 35 min

🎬 **CO MÓWIĘ:**

„Teraz IntelliJ. Jeśli pracujecie glownie w IntelliJ — a zakladam że wiekszosc z Was tak — to ten blok jest bezpośrednio przydatny.

Mam do pokazania dwie integracje. I chce żeby rozumieli roznice, bo ma to praktyczne znaczenie dla codziennego workflow.

Historia jest krótka: JetBrains miał przez jakiś czas własną implementację AI Assistanta opartana na Claude SDK od Anthropic. Działało, ale mialo haczyki. Po pierwsze — używało Waszych kredytów JetBrains AI, a nie subskrypcji Claude Code Teams. Po drugie — to nie był pełny Claude Code agent. Brakowało mu wielu funkcji: no MCP, brak wspólnej sesji z CLI, brak tych samych configów.

Potem JetBrains i Zed stworzyli ACP — Agent Client Protocol — otwarty standard, taki jak LSP ale dla agentów AI. I teraz Claude Code CLI może pracowac bezpośrednio wewnatrz IntelliJ GUI. Ta sama sesja, ta sama historia, ten sam CLAUDE.md, te same MCP serwery.

Jeden agent, dwa interfejsy. Terminal i IDE — zsynchornizowane."

📺 **CO POKAZUJĘ:**
- IntelliJ AI Chat panel
- Dropdown przy nazwie modelu
- ACP Registry
- Opcja "Pass IntelliJ MCP server"

💬 WKLEJ NA CHAT:
```text
IntelliJ + Claude Code — dwie integracje:

STARA (JetBrains AI Assistant z Anthropic SDK):
⚠️ Uzywa kredytow JetBrains AI (nie Waszej subskrypcji CC Teams)
⚠️ Nie wszystkie funkcje Claude Code
⚠️ Osobna sesja i historia niz CLI
⚠️ Osobna konfiguracja (nie czyta CLAUDE.md tak samo)

NOWA (ACP Registry — rekomendowana):
✅ Uzywa Waszej subskrypcji Claude Code Teams
✅ Pełne funkcje = dokładnie to samo co CLI
✅ Wspolna sesja, historia, configs z CLI
✅ Czyta ten sam CLAUDE.md co terminal
✅ Ten sam zestaw MCP serwerow
✅ Wymaga IntelliJ 2025.3+

Instalacja ACP:
AI Chat → dropdown (przy nazwie modelu) →
"Install From ACP Registry" → "Claude Code" → OK
```

🎬 **INTELLIJ MCP SERVER:**

„A teraz opcja 'Pass IntelliJ MCP server' — co ona robi i dlaczego warto ją włączyć.

MCP — Model Context Protocol — to standard który pozwala agentom uzyc zewnętrznych narzędzi. IntelliJ MCP Server to serwer który wystawia narzędzia IntelliJ dla agenta.

Kiedy włączysz te opcje, agent dostaje coś więcej niz tylko pliki z dysku. Dostaje:
- diagnostykę IDE — błędy kompilacji, warningi, inspekcje które IntelliJ widzi
- kontekst aktualnie otwartego pliku
- nawigacje przez strukturę projektu jak IDE to rozumie
- refaktoring przez IDE (nie tylko przez edycje tekstu)

To jest dużo lepszy kontekst. Agent nie 'czyta plik jak grep', tylko 'rozumie projekt jak developer w IntelliJ'."

💬 WKLEJ NA CHAT:
```text
IntelliJ MCP Server — co daje agentowi:

✅ Diagnostyka IDE (błędy kompilacji, warningi, code inspections)
✅ Kontekst aktualnie otwartego pliku i projektu
✅ Nawigacja jak Java-aware IDE (nie tylko grep po plikach)
✅ Znajomość run configurations, modułów, zależności

Jak wlaczyc:
Przy instalacji ACP → zaznacz: ✅ "Pass IntelliJ MCP server"

Albo recznie w ~/.jetbrains/acp.json:
{
  "agent_servers": {
    "Claude Code": {
      "command": "npx",
      "args": ["@zed-industries/claude-code-acp"],
      "env": {},
      "use_idea_mcp": true
    }
  }
}

JetBrains ACP docs: https://www.jetbrains.com/help/ai-assistant/acp.html
JetBrains ACP blog: https://blog.jetbrains.com/ai/2026/01/acp-agent-registry/
Introducing Claude Agent: https://blog.jetbrains.com/ai/2025/09/introducing-claude-agent-in-jetbrains-ides/
```

🏋️ **ĆWICZENIE (12 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie — ACP w IntelliJ:

1. Sprawdzcie wersje IntelliJ: Help → About (potrzeba 2025.3+)
   Jeśli nizsza — pozostancie przy CLI, to jest OK.

2. AI Chat → dropdown → "Install From ACP Registry" → "Claude Code"
   Zaznaczcie: ✅ "Pass IntelliJ MCP server"

3. W AI Chat wklejcie:
   "Jestem nowym developerem w tym projekcie.
    Przejrzyj strukture i odpowiedz:
    1) Co to jest za aplikacja i jaki problem rozwiązuje?
    2) Jaki stack technologiczny?
    3) Jakie są główne klasy/komponenty?
    4) Co jest core domain — gdzie jest serce logiki biznesowej?
    5) Co według Ciebie jest najpilniejsze do zrobienia?
    Odpowiedz zwiezle w punktach."

4. Porownajcie z tym samym pytaniem w CLI — czy IDE context robi roznice?

Wyniki: 👍 / 🐛
```

💡 **Uwaga:** Kto ma IntelliJ poniżej 2025.3 — niech robi ćwiczenie w CLI. ACP jako 🔵 homework.

🎬 **PO ĆWICZENIU:**

„Jedna ważna różnica która pewnie zauwazyliscie: z IDE MCP agent zna błędy kompilacji, zalez i kontekst który IntelliJ rozumie jako Java-aware IDE. To nie jest po prostu 'czytanie plikow'. To jest praca z Waszym projektem tak jak senior developer który zna projekt.

Jeśli jutro będę prowadzić agenta przez kodowanie — będę używać głównie ACP w IntelliJ właśnie dlatego."

---

## 10:25–10:55 — Claude Code: komendy i funkcje
⏱️ 30 min

🎬 **CO MÓWIĘ:**

„Ostatni blok przed przerwa — komendy i funkcje Claude Code które oszczedzaja czas i są mało znane.

Zacznijmy od mojego ulubionego: Ctrl+G."

📺 **CO POKAZUJĘ:**
- Demo Ctrl+G (otwieram zewnętrzny edytor z promptu)
- Demo /loop, /batch w skrócie
- Lista komend /help

🎬 **CTRL+G — $EDITOR:**

„Ctrl+G otwiera bieżący prompt lub konwersacje w Waszym zewnętrznym edytorze. Ustawiacie $EDITOR na Zed, IntelliJ, VSCode, Cursor — i zamiast pisac w terminalu, macie pełny edytor z podswietlaniem, zawijaniem linii, wszystkim.

Idealny kiedy piszecie długi, złożony prompt — albo kiedy chcecie zedytowac duzy blok kodu przed przekazaniem do agenta. Bez tego Shift+Enter w Windows Terminal staje się meczeninstwem."

💬 WKLEJ NA CHAT:
```text
Ctrl+G — otworz prompt w zewnętrznym edytorze:

Ustaw $EDITOR w .bashrc / .zshrc / .profile:
export EDITOR="zed --wait"         # Zed
export EDITOR="idea --wait"        # IntelliJ (idea w PATH)
export EDITOR="code --wait"        # VSCode
export EDITOR="cursor --wait"      # Cursor

Uzycie:
• W Claude Code CLI: Ctrl+G
• Otwiera bieżący prompt w edytorze
• Piszesz / edytujesz / zapisujesz → wracasz do CC
• Idealne dla: długich promptów, edycji kodu, złożonych promptów systemowych

Sprawdź czy działa: echo $EDITOR
```

🎬 **KOMENDY /loop, /batch, /schedule:**

„Teraz nowe komendy które zmieniają prace z dłuższymi zadaniami. To są swieze funkcje — nie są wszedzie dobrze udokumentowane — ale są bardzo użyteczne."

💬 WKLEJ NA CHAT:
```text
Nowe komendy Claude Code (2025/2026):

/loop [interval] [command]
   Uruchamia komende co [interval].
   Przyklad: /loop 5m /review       (code review co 5 min)
   Przyklad: /loop 1h /status       (status projektu co godzine)
   Uzycie: monitoring, cykliczne taski, watch

/batch [plik.txt lub inline]
   Wykonuje wiele promptow (jeden per linia) sekwencyjnie.
   Uzycie: masowe operacje, refactoring wielu plikow,
           generowanie testow dla listy klas

/schedule "[cron expr]" [command]
   Harmonogram zadan jak crontab.
   Przyklad: /schedule "0 9 * * 1" /weekly-review
   Uzycie: automatyczne raporty, tygodniowe review

/review
   Code review biezacego brancha (diff vs main).
   Szuka: błędy, security, pokrycie testami, konwencje.

/init
   Inicjalizuje CLAUDE.md — pyta o projekt, stack, zasady.
   Dobry punkt startowy dla nowego projektu.

/doctor
   Diagnostyka środowiska: konfiguracja, permissions, MCP servers.
   Używaj gdy coś nie działa.

/cośt
   Ile tokenow i $$$ wydales w tej sesji.
   Używaj gdy boisz się rachunku ;)
```

💬 WKLEJ NA CHAT:
```text
Inne przydatne rzeczy w Claude Code:

# Kilka sesji równolegle (git worktrees — bardzo potezne!):
git worktree add ../feature-sinsay-ui feature/sinsay-ui
cd ../feature-sinsay-ui && claude   # osobna sesja na osobnym branchu!
# Kazda sesja = osobny kontekst, osobna historia, te same configs

# One-liner bez sesji interaktywnej:
claude --print "Podsumuj zmiany w tym repo od ostatniego commitu"

# Nieinteraktywny (CI/CD):
claude --no-interactive "Run tests, summarize failures as JSON"

# Zmiana modelu w sesji:
/model                              # pokaz dostepne modele
/model claude-opus-4-6              # przełącz na Opus (trudne decyzje arch.)
/model claude-haiku-4-5             # przełącz na Haiku (szybkie proste taski)

# Kompresja gdy sesja jest bardzo dluga:
/compact                            # kompresuj historie, zachowaj wyniki
```

🏋️ **MINI-ĆWICZENIE (5 min):**

💬 WKLEJ NA CHAT:
```text
Mini-ćwiczenie:
Uruchomcie claude i sprawdzcie:

/help          ← pełna lista komend
/doctor        ← diagnostyka środowiska
/cost          ← ile kosztowała ta sesja (pewnie 0 :))

Opcjonalnie — ustawcie EDITOR i sprawdzcie Ctrl+G:
export EDITOR="code --wait"
# lub: export EDITOR="idea --wait"
# Potem w claude: Ctrl+G → edytor się otwiera

Kciuki gdy gotowe 👍
```

---

## 10:55–11:10 — Przerwa
⏱️ 15 min

💬 WKLEJ NA CHAT:
```text
☕ Przerwa 15 min → wracamy 11:10

Po przerwie zaczynamy wlasciwy Dzień 2:
AI jako Twój Product Manager.

Przypomnijcie sobie projekt: chatbot Sinsay do reklamacji i zwrotów.
Będę prosił o aktywna prace — będziesz odpowiadał na pytania agenta
i krytykował wygenerowane dokumenty.
```

---

## 11:10–12:15 — Moduł 2.1: AI jako Twój PM — PRD, User Stories, Requirements
⏱️ 65 min

🎬 **CO MÓWIĘ:**

„Zaczynamy wlasciwy Dzień 2. I zaczynamy od pytania, które może być niewygodne:

Ile razy zdarzylo się Wam zacząc kodowac, a po tygodniu okazalo się że rozwiazywaliscie nie ten problem? Albo że developer i PM mieli dwie rozne wizje tego samego zadania?

AI może tu pomoc — nie dlatego że jest madrzejsze od ludzi — ale dlatego że zadaje irytujaco dobre pytania. Bez wstydu. Bez 'to chyba oczywiste, nie bede pytal'. Bez politycznej gry o to kto powiedzial co na jakim meetingu.

Pokaze Wam teraz technikę którą nazywam 'interrogation mode' — zamiast od razu prosic o PRD, najpierw kazemy agentowi zadawac nam pytania. Agent jako PM.

To jest rowniez bardzo dobra technika dlatego, że czesto **sami nie wiemy czego chcemy, dopoki ktos nas nie zapyta**. Przekonacie się za chwile."

📺 **CO POKAZUJĘ:**
- Uruchamiam Claude Code
- Wklejam Prompt 1 — agent zadaje pytania jedno po jednym

🎬 **PO DEMONSTRACJI:**

„Widzieliscie to? Agent nie wygenerował od razu dokumentu. Zadal pytanie. I czeka. Takie mamy polecenie: 'pytaj o jedno na raz'.

Ta technika jest tez dobra do onboardingu nowych czlonkow zespolu, do retrospektyw, do design sessions. Wszedie tam gdzie chcemy wyciagnac z ludzi wiedze która jest w glowach ale nie na papierze."

💬 WKLEJ NA CHAT:
```text
Prompt 1 — AI jako PM: tryb pytań (interrogation mode)

Jesteś doświadczonym product managerem pracujacym dla Sinsay —
modowego eCommerce sprzedajacego ubrania (fast fashion, mloda grupa docelowa).

Musimy zbudować chatbota do obsługi reklamacji i zwrotów produktów.
Zanim napiszesz jakikolwiek dokument, zadaj mi 10 konkretnych pytań
które pomoga Ci lepiej zrozumiec wymagania.

Zasady zadawania pytań:
- Pytaj o JEDNO na raz i czekaj na odpowiedz
- Pytaj o to czego nie wiesz — nie zgaduj
- Pytaj o edge cases i sytuacje wyjatkowe
- Pytaj o ograniczenia techniczne i biznesowe
- Pytaj o persony uzytkownikow (kto korzysta i w jakim kontekscie)
- Pytaj o priorytety (co jest najwazniejsze jezeli nie zdazymy z wszystkim)

Zacznij od pierwszego pytania. Czekaj na moja odpowiedz.
Pisz po polsku.
```

🎬 **W TRAKCIE CWICZENIA:**

„Odpowiadajcie szczerze i konkretnie. Jeśli nie wiecie odpowiedzi — powiedzcie 'nie wiem' albo 'TBD'. To tez jest dobra odpowiedź dla PRD.

Zauważcie że agent pyta o rzeczy które na pierwszy rzut oka wydają się oczywiste: 'co się dzieje ze zdjęciem po decyzji?' albo 'jak długo przechowujemy dane sesji?' — To są te rzeczy które później okazują się blokerami w implementacji."

🏋️ **ĆWICZENIE 1 — Generowanie PRD (25 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie 1: PRD z pomocą agenta

Krok 1 — Interrogation mode (12 min):
Wklejcie Prompt 1 powyżej.
Odpowiedzcie na pytania agenta zgodnie z Wasza wizja projektu.
Mozecie dostosowac projekt do Waszego kontekstu.

Krok 2 — Wygeneruj PRD (10 min):
Po odpowiedzeniu na pytania wklejcie Prompt 2 (poniżej).

Krok 3 — Krytyka (3 min):
Przeczytajcie dokument. Znajdzcie:
• 1 brakujaca user story
• 1 acceptance criteria które NIE jest mierzalne (poprawcie!)
• 1 rzecz która jest zbyt ogolna

Wynik wrzuccie na chat: 1 zdanie co Was zaskoczylo w pytaniach agenta.
```

💬 WKLEJ NA CHAT:
```text
Prompt 2 — Wygeneruj PRD

Dziekuje za pytania. Na podstawie naszej rozmowy
przygotuj teraz PRD dla chatbota Sinsay.

Format:
## 1. Executive Summary (2-3 zdania)
## 2. Problem Statement
## 3. Uzytkownicyi / Persony (2-3 konkretne persony)
## 4. Główny Flow (krok po kroku, dla każdego głównego scenariusza)
## 5. User Stories
   Format: "Jako [kto], chcę [co], żeby [dlaczego]"
   Minimum 8 user stories, uwzgledniajac happy path i błędy
## 6. Acceptance Criteria
   Mierzalne i konkretne — nie "uzytkownik będzie zadowolony"
## 7. Out of Scope
   Co NIE wchodzi w MVP — równie ważne jak scope!
## 8. Ograniczenia techniczne i biznesowe
## 9. Metryki sukcesu MVP (liczby, nie opisy)

Pisz po polsku. Konkretnie. Bez marketingowego lania wody.
PRD ma być uzyteczny dla developera i agenta — nie do prezentacji.
Zapisz wynik jako docs/PRD.md
```

🎬 **DOMKNIĘCIE MODULU 2.1:**

„Dobry PRD to dokument który redukuje entropy. Kiedy jutro agent będzie pisał kod — nie będzie zgadywał. Będzie budował z planem. Ale — i to jest ważne — PRD nie jest wieczny. To żywy dokument. Aktualizujcie go po każdym sprincie przez rozmowę z agentem.

Jeden quick tip: zawsze trzymajcie PRD w repozytorium, w `docs/PRD.md`. Agent czyta go automatycznie jeśli jest w projekcie i opisany w CLAUDE.md."

💬 WKLEJ NA CHAT:
```text
Dobry PRD dla agenta — checklist:

□ Kazda user story ma JEDNO jasne acceptance criteria
□ Out of scope jest tak samo konkretne jak scope
□ Persony są konkretne — nie "uzytkownik", ale "Kasia, 23 lata, zamawia przez telefon"
□ Edge cases są wymienione wprost, nie ukryte w tekscie
□ Metryki sukcesu to liczby (np. "80% decyzji bez eskalacji do czlowieka")
□ Dokument jest krotki — nie esej. Jeśli > 3 strony: skroc
□ Zapisany w repo: docs/PRD.md (agent będzie go czytal)
```

---

## 12:15–13:00 — Moduł 2.2: Research + Tech Stack + ADR
⏱️ 45 min

🎬 **CO MÓWIĘ:**

„Mamy PRD. Teraz musimy podjąć kluczowa decyzję: co używamy do budowania.

I tu jest pułapka w która często wpadają zespoly: wybierają stack zanim naprawdę rozumieją co im on daje. 'Użyjmy Spring AI bo jest cool' albo 'mamy już Vercel w innym projekcie'. To są decyzje na bazę presji lub przyzwyczajenia, nie na bazie dobrze przeprowadzonego research.

Dzisiaj będziemy robić research razem z agentem. I pokaze Wam narzędzie które bardzo to przyspiesza: Context7 MCP."

📺 **CO POKAZUJĘ:**
- Dodaje Context7 MCP do settings.json
- Demo: agent pobiera dokumentacje Spring AI

🎬 **CONTEXT7 MCP:**

„Context7 to serwer MCP który pobiera aktualną dokumentację bibliotek zamiast opierać się na wiedzy modelu z treningu. To jest kluczowe bo — jak pewnie wiecie — modele mają cutoff date. Claude nie wie co się zmieniło w Spring AI 3 miesiące temu. Context7 wie, bo pobiera wprost z dokumentacji."

💬 WKLEJ NA CHAT:
```text
Context7 MCP — dokumentacja bibliotek w czasie rzeczywistym:

Instalacja:
npm install -g @upstash/context7-mcp

Konfiguracja (dodaj do ~/.claude/settings.json lub .claude/settings.json):
{
  "mcpServers": {
    "context7": {
      "command": "npx",
      "args": ["-y", "@upstash/context7-mcp"]
    }
  }
}

Weryfikacja: uruchom claude → /mcp → powinna być widoczna "context7"

Uzycie w prompcie:
• Dodaj "use context7" lub "fetch docs for [library]"
• Agent automatycznie pobierze aktualna dokumentacje
• Brak hallucynacji o nieistniejacych API!
```

🎬 **TRZY SCIEZKI TECHNOLOGICZNE:**

„Teraz — trzy sciezki dla projektu Sinsay. Mówiłem o nich rano, ale teraz czas na decyzję. Każda ma swoje pros i cons. Będę pokazywał ścieżkę bezpieczną na żywo. Średnio-zaawansowani — dam materiały. Zaawansowani — dam kierunek, ale droga jest Wasza."

💬 WKLEJ NA CHAT:
```text
Trzy ścieżki technologiczne — wybierz swoją:

🟢 BEZPIECZNA (pokazuje na żywo):
Backend:  Java 21 + Spring Boot 3.x + OpenAI Java SDK lub Spring AI
Frontend: React + Vercel AI SDK lub AssistantUI components
Baza:     SQLite (JDBC + Spring Data JPA z SQLite dialect)
Zakres:   formularz → analiza zdjęcia → decyzja → chat

🟡 SREDNIO-ZAAWANSOWANA (dam kierunek, reszta samodzielnie):
Jak wyżej + prosta RAG:
• SQLite Vector Store (wbudowane w Spring AI)
• lub ChromaDB jako zewnętrzny vector DB
• Embeddingi dla dokumentów proceduralnych
Kiedy: chcesz żeby agent szukal w procedurach, nie tylko w plikach MD

🔴 ZAAWANSOWANA (na własne ryzyko, bez prowadzenia przeze mnie):
LangGraph4j + CopilotKit + AG-UI Protocol
Agent kontroluje UI: wyswietla formularze dynamicznie,
pre-filluje dane z rozmowy, pokazuje wyniki jako UI komponenty
Kiedy: chcesz deep dive w LangGraph + agent-controlled UI

Wasza decyzja — wrzuccie na chat: 🟢 / 🟡 / 🔴
```

💬 WKLEJ NA CHAT:
```text
Prompt — Research tech stack (wklej do Claude Code z context7):

Jesteś doświadczonym architektem Java.

Projektuję chatbota do obsługi reklamacji i zwrotów produktów
dla Sinsay (fashion eCommerce). Stack bazowy: Java 21 + Spring Boot 3.x.

Przeprowadz research i porownaj:

1) Vercel AI SDK (React) vs AssistantUI (React)
   — do budowania interfejsu czatu z LLM

2) OpenAI Java SDK (oficjalny) vs Spring AI
   — do wywolan LLM z backendu Java

3) SQLite (JDBC) vs H2 vs PostgreSQL
   — do przechowywania sesji i historii czatu w MVP

4) Dwa osobne wywołania LLM (vision osobno, decision osobno)
   vs jedno multimodalne wywołanie
   — dla analizy zdjęcia + decyzji o reklamacji

Dla kazdej opcji podaj:
- Glowne zalety i wady w kontekscie tego projektu
- Latwosc integracji z Java/Spring
• Dojrzalosc i community support
- Kiedy wybrac ta opcje

Zakoncz rekomendacja dla MVP (small team, 3-4 tygodnie dev time).

Uzywaj context7 do pobrania aktualnej dokumentacji bibliotek.
```

🏋️ **ĆWICZENIE 2 — Research (15 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie 2: Research technologiczny

1. Upewnijcie się że macie context7 MCP (jeśli nie — zrobie demo)
2. Wklejcie powyzszy research prompt
3. Przeczytajcie rekomendacje krytycznie
4. Zadajcie JEDEN follow-up — np:
   "Pokaz przykładowy kod integracji Spring AI z GPT-4o w Javie"
   "Jak działa streaming w Vercel AI SDK?"
   "Czy SQLite działa z Spring Data JPA na produkcji?"

Zanotujcie decyzję — potrzebna do ADR po przerwie!

Na chat: jaką technologię wybrałyście i dlaczego 1 zdaniem
```

---

## 13:00–13:30 — Przerwa obiadowa
⏱️ 30 min

💬 WKLEJ NA CHAT:
```text
🍽️ Przerwa 30 min → wracamy 13:30

Po przerwie:
• Diagramy UML — Mermaid (GitHub) i PlantUML (GitLab)
• Dlaczego UML pomaga AI myslec strukturalnie (naprawdę pomaga!)
• ADR — zapisujemy decyzję architektoniczne
• Pozniej: MCP, Skills, Sub-agenci, CLAUDE.md

Dobry moment żeby zapisać swoją decyzję o stacku — będziecie jej potrzebować do ADR po przerwie.
```

---

## 13:30–14:30 — Moduł 2.2 cd: UML — Mermaid, PlantUML, architektura
⏱️ 60 min

🎬 **CO MÓWIĘ:**

„Wracamy. Teraz diagramy. Ale najpierw chcę Wam powiedzieć dlaczego to jest ważne z perspektywy pracy z AI — bo jest pewien efekt który jest nieoczywisty.

Kiedy prosicie agenta o diagram, dzieje się coś ciekawego: agent musi myslec strukturalnie. Zeby narysowac diagram sekwencji, musi zrozumiec przeplyw danych. Zeby narysowac diagram klas, musi zdecydowac o abstrakcjach. Zeby narysowac diagram komponentow, musi przemyslec granice odpowiedzialności.

Innymi słowy: **prosząc agenta o diagram, wymuszamy na nim myślenie architektoniczne**. To jest jeden z moich ulubionych tricksów — jeśli nie jestem pewien czy agent dobrze rozumie co ma zbudowac, proszę go najpierw o diagram. Jeśli diagram jest sensowny — kod będzie sensowny. Jeśli diagram jest chaotyczny — lepiej to zobaczyć *przed* kodowaniem niż po.

To działa rowniez jako narzędzie do walidacji Waszego wlasnego rozumienia domeny. Jeśli agent rysuje sekwencje i wy nie rozumiecie jednego kroku — to jest sygnalizacja że to miejsce wymaga doprecyzowania.

A teraz szczegóły techniczne: dwa formaty które będziemy używać."

📺 **CO POKAZUJĘ:**
- Generuje diagram Mermaid w claude
- Wkleja do pliku MD
- Pokazuje rendering na GitHub
- Generuje PlantUML
- Pokazuje online renderer

🎬 **MERMAID vs PlantUML:**

„Mermaid — natywnie renderuje się na GitHub w Markdown. Prosty, dobry do podstawowych diagramow. Wystarczy wlozyc do bloku kodu z tagiem mermaid.

PlantUML — wspierany przez GitLab, Confluence, IntelliJ. Bardziej rozbudowany, więcej typow diagramow. Uzywany w enterprise projektach gdzie jest GitLab lub Jira/Confluence.

Fajna wlasciwosc: agent potrafi konwertowac miedzy nimi. Jedno podejscie: generujcie w Mermaid do szybkiej wizualizacji, konwertujcie do PlantUML kiedy repo jest na GitLab."

💬 WKLEJ NA CHAT:
```text
Prompt — Diagramy UML dla projektu Sinsay (Mermaid):

Jesteś architektem systemow. Zaprojektuj chatbota Sinsay
do obslugi reklamacji i zwrotów na podstawie PRD w docs/.

Przygotuj 4 diagramy Mermaid:

1) Sequence Diagram — pełny flow reklamacji:
   Uzytkownik → React Frontend → Spring Boot API →
   OpenAI Vision API (analiza zdjęcia) →
   Decision Service (czytanie docs MD) →
   OpenAI API (decyzja) → odpowiedz do użytkownika

2) Class Diagram — główne klasy Java:
   ClaimRequest, ClaimResponse, ChatSession, ClaimDocument,
   ClaimService, VisionAnalysisService, DecisionService,
   ChatSessionRepository
   Pokaz metody publiczne i zaleznosci

3) Component Diagram — architektura aplikacji:
   Frontend (React) <-> Spring Boot REST API <-> OpenAI API
   Spring Boot API <-> SQLite DB
   Spring Boot API <-> File System (docs/*.md)

4) State Diagram — stany zgłoszenia:
   DRAFT → SUBMITTED → IMAGE_ANALYZED → DECISION_MADE → RESOLVED
   + ścieżka: DECISION_MADE → ESCALATED (gdy pewnosc za niska)

Każdy diagram w osobnym bloku ```mermaid ... ```
Diagramy maja być czytelne i zwiezle — bez nadmiernej szczegolowosci.
Na poczatku kazdego — 1 zdanie opisu dla nowego developera.
```

🎬 **PO GENEROWANIU DIAGRAMOW:**

„Widzicie? To jest 10 minut pracy agenta zamiast 2 godzin Visio. Ale wazne: to są diagramy do przemyślenia, nie do podpisania i schowania. Przejrzyjcie je krytycznie. Czy sequence diagram pokazuje gdzie są miejsca które mogą zawierać błędy? Czy class diagram ma odpowiednie zakresy odpowiedzialności?

Teraz konwertujmy na PlantUML — bo w enterprise srodowisku GitLab + Confluence to jest bardziej użyteczne."

💬 WKLEJ NA CHAT:
```text
Prompt — Konwersja Mermaid na PlantUML:

Skonwertuj powyzsze 4 diagramy Mermaid na format PlantUML.
PlantUML jest używany na GitLab i w IntelliJ (plugin PlantUML).

Każdy diagram w osobnym bloku @startuml ... @enduml
Zachowaj te same informacje — dostosuj tylko składnię.

Dodatkowo: dla Sequence Diagram dodaj komentarze (@note) przy krytycznych krokach:
- gdzie może wystąpić błąd sieci
- gdzie jest timeout ryzyko
- gdzie są dane wrazliwe klienta (RODO)
```

💬 WKLEJ NA CHAT:
```text
Gdzie renderuja diagramy:

Mermaid:
✅ GitHub (w README.md, issues, PRs — automatycznie!)
✅ Obsidian, HackMD, Notion
✅ Online: https://mermaid.live

PlantUML:
✅ GitLab (w Markdown — automatycznie)
✅ IntelliJ IDEA (plugin: PlantUML Integration)
✅ Confluence (plugin)
✅ Online: https://www.plantuml.com/plantuml/uml/

Konwersja online miedzy formatami:
https://kroki.io/
```

🏋️ **ĆWICZENIE 3 — UML jako narzędzie myslenia (15 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie 3: UML jako narzędzie myslenia architektonicznego

Krok 1 (7 min):
Wygenerujcie Sequence Diagram dla Waszego projektu.
Ale NAJPIERW dodajcie do prompta:
"Zanim narysujesz diagram, opisz w 3 punktach:
 1) jakie widzisz główne wyzwania architektoniczne,
 2) gdzie może być waskiegardlo wydajnosciowe,
 3) co jest najmniej oczywiste w tym flow."

Krok 2 (5 min):
Przeczytajcie opis wyzwan — czy agent zidentyfikowal coś
czego nie braliscie pod uwage?

Krok 3 (3 min):
Sprobujcie alternatywy:
"Narysuj alternatywny Sequence Diagram gdzie JEDEN call
do multimodalnego modelu zastepuje dwa osobne wywołania
(vision + decision). Jakie są zalety i wady tej architektury?"

Na chat: 1 insight architektoniczny który dostaliscie od agenta
(może być: potwierdzenie, zaskoczenie, lub 'agent się myli, bo...')
```

🎬 **ADR — ARCHITECTURE DECISION RECORD:**

„Po diagramach — ADR. To jest krótki dokument który zapisuje *dlaczego* podjęliśmy daną decyzję techniczną, jakie były alternatywy, i jakie są konsekwencje.

ADR nie istnieje po to żeby wygladac profesjonalnie. ADR istnieje po to żeby za 6 miesiecy nie pytać 'DLACZEGO TO TAK ZROBIONO???' — i nie dostawac odpowiedzi 'hm, już nie pamietam'. I żeby agent — kiedy będzie modyfikowal kod — mial kontekst decyzji, a nie tylko wynik.

Jeśli nie piszecie ADR — Agent tez nie będzie ich pisal automatycznie. Ale jezeli poprosicie, to zrobi to sprawnie i lepiej niz wiekszosci developerow gdyby mieli pisac o 23:00 przed deployem."

💬 WKLEJ NA CHAT:
```text
Prompt — ADR dla projektu Sinsay:

Napisz trzy krótkie ADR dla projektu Sinsay Chatbot.
Uzasadnij decyzję na podstawie research który przeprowadziliśmy wcześniej.

ADR-001: OpenAI Java SDK vs Spring AI
ADR-002: Dwa osobne wywołania LLM (vision + decision) vs jedno multimodalne
ADR-003: SQLite jako baza sesji vs Redis vs PostgreSQL w MVP

Format kazdego ADR:
## ADR-XXX: [Tytuł]
**Status:** Accepted | Proposed | Superseded
**Data:** [dzis]
**Kontekst:** [Dlaczego ta decyzja byla potrzebna, 2-3 zdania]
**Decyzja:** [Co zdecydowalismy i krótkie uzasadnienie]
**Odrzucone alternatywy:** [Co rozważaliśmy z 1-2 zdaniami dlaczego odrzucono]
**Konsekwencje:** [Plusy i minusy tej decyzji]
**Trigger rewizji:** [Kiedy wrocic do tej decyzji — konkretny warunek]

Pisz po polsku. Konkretnie. Każdy ADR max pol strony A4.
Zapisz jako docs/ADR/ADR-001.md, ADR-002.md, ADR-003.md
```

---

## 14:30–15:30 — Moduł 2.3: MCP, Skills, Sub-agenci, CLAUDE.md
⏱️ 60 min

🎬 **CO MÓWIĘ:**

„Teraz moduł który może najbardziej zmienic sposob w jaki praujecie z agentami dluzej niz jeden dzień.

Wyobrazcie sobie że zatrudniacie nowego seniora. Co mu dacie pierwszego dnia?
- Dostep do narzędzi (IDE, baza, API keys)
- Opis projektu i zasady pracy (onboarding doc)
- Liste specjalistow do których może się zwrocic

To samo robimy dla agenta:
- MCP = dostep do narzędzi
- CLAUDE.md = opis projektu i zasady
- Sub-agenci = specjalisci do delegowania

Bez tego agent startuje każdą sesje od zera. Z tym — startuje jako osoba która zna projekt od pierwszego zdania."

📺 **CO POKAZUJĘ:**
- konfiguracja MCP w settings.json
- demo Context7 (już znamy) + demo Playwright MCP

🎬 **MCP — PRZEGLAD:**

„MCP — Model Context Protocol. Standard stworzony przez Anthropic, teraz pod Linux Foundation. Pozwala agentom używać zewnętrznych narzędzi poza swoim naturalnym zakresem.

Dzialaja jak API dla agenta: agent pyta 'co potrafisz?', serwer odpowiada lista narzędzi, agent ich używa. Nie wymaga restartowania agenta — MCP serwery są dołączone na starcie sesji.

Najpopularniejsze MCP które są użyteczne bezpośrednio dla Was:"

💬 WKLEJ NA CHAT:
```text
Popularne MCP serwery — przykladowa konfiguracja ~/.claude/settings.json:

{
  "mcpServers": {

    "context7": {
      "command": "npx",
      "args": ["-y", "@upstash/context7-mcp"]
    },

    "playwright": {
      "command": "npx",
      "args": ["@playwright/mcp@latest"],
      "env": { "PLAYWRIGHT_HEADLESS": "true" }
    },

    "github": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": { "GITHUB_PERSONAL_ACCESS_TOKEN": "<token>" }
    }

  }
}

Rejestr MCP serwerow:
https://registry.smithery.ai/
https://mcp.so/servers

Weryfikacja w Claude Code:
/mcp        ← lista aktywnych serwerow i ich narzędzi
/doctor     ← diagnostyka calego środowiska
```

🏋️ **ĆWICZENIE 4 — Context7 w akcji (10 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie 4: Context7 — research biblioteki z aktualna dokumentacja

Jeśli nie macie context7 MCP — zainstalujcie teraz:
npm install -g @upstash/context7-mcp
(Dodajcie do settings.json jak powyżej, zrestartujcie claude)

Wklejcie w claude (powinna być dostepna context7):

"Uzywajac context7, pobierz dokumentacje Spring AI.
Nastepnie pokaz mi:
1) Jak skonfigurowac Spring AI z OpenAI API w Spring Boot
2) Jak wykonac pierwsze wywołanie modelu GPT-4o z Javy
3) Jak obsluzyc multimodalne wywołanie — tekst + obraz (vision)
4) Jak skonfigurowac SQLite Vector Store dla RAG (jeśli dostepne)

Pokazuj konkretne przykladowe kody Java.
Zaznacz jeśli coś jest nowoscia od Marca 2026."

Cel: skopiujcie przykladowe kody do docs/tech-notes/ w projekcie.
```

🎬 **SKILLS:**

„Skills — czyli umiejętności agentów. To są gotowe workflow które definiujecie raz i uruchamiacie wiele razy jednym /komenda.

Najlepszy sposób na zrozumienie skilla: myśleć o nim jak o przepisie kucharskim. Agent wie co robić krok po kroku, wie co sprawdzic, wie co zrobic jeśli coś się nie udalo. Bez Waszego ponownego tlumaczenia."

💬 WKLEJ NA CHAT:
```text
Skills — przykładowe definicje (dodaj do CLAUDE.md):

## Skills

### /commit
1) Wykonaj: git status + git diff --staged
2) Przeanalizuj zmiany
3) Zaproponuj commit message w formacie conventional commits
   (feat/fix/refactor/test/docs + scope + opis)
4) Zapytaj o potwierdzenie lub korekte
5) Po potwierdzeniu: git add -A && git commit -m "[message]"
6) Zapytaj czy git push

### /review
Przeprowadz code review bieżących zmian (git diff vs main).
Sprawdź kolejno:
1) Pokrycie testami dla nowej logiki biznesowej
2) Bezpieczenstwo: injection, hardcoded credentials, RODO-wrazliwe dane
3) Zgodnosc z konwencjami projektu z CLAUDE.md
4) Czytelnosc i krótkie dokumentacja (Javadoc dla public API)
5) Potencjalne błędy: null pointer, edge cases, threading
Wynik: lista ✅ OK / ⚠️ Uwaga / ❌ Blad z numerami linii

### /prd-sync
Przeczytaj docs/PRD.md i biezace zmiany w kodzie.
Sprawdź czy implementacja jest zgodna z acceptance criteria.
Zaproponuj aktualizacje PRD jezeli zaszly znaczace zmiany.
Nie modyfikuj PRD bez mojej zgody.
```

🎬 **SUB-AGENCI:**

„Sub-agenci — specjalisci. Zamiast jednego agenta który robi wszystko sredniej jakosci, możecie mieć kilka agentów wyspecjalizowanych w konkretnych obszarach.

Dla projektu Sinsay — cztery naturalne role:"

💬 WKLEJ NA CHAT:
```text
Sub-agenci dla projektu Sinsay Chatbot — propozycja rol:

☕ BACKEND AGENT (Java Dev):
Specjalizacja: Spring Boot, OpenAI Java SDK, REST API, domenowa logika
Zna: konwencje Java projektu, error handling, security patterns
NIE robi: zmiany w React UI, modyfikacje dokumentów proceduralnych

🎨 FRONTEND AGENT (FE Dev):
Specjalizacja: React, Vercel AI SDK / AssistantUI, formularz, UX
Zna: design system Sinsay, dostepnosc, state management
NIE robi: zmiany w Spring Boot, zmiany w bazie danych

📄 DOCUMENTS AGENT (Content/RAG):
Specjalizacja: dokumenty MD (procedury reklamacji/zwrotów),
               embeddingi, wyszukiwanie semantyczne
Zna: procesy biznesowe Sinsay, strukture dokumentów
NIE robi: kod aplikacji, zmiany w UI

🧪 QA AGENT (Tester):
Specjalizacja: testy (JUnit, Mockito, E2E), scenariusze testowe
Zna: PRD, user stories, acceptance criteria, edge cases
NIE robi: implementacja features, tylko weryfikacja

Jak uzyc sub-agenta w prompcie:
"Dzialaj jako Backend Agent zgodnie z CLAUDE.md.
 Twoje zadanie to..."
```

🎬 **CLAUDE.md — FUNDAMENT:**

„I teraz CLAUDE.md — fundament. To jest plik, który mówi agentowi wszystko co musi wiedziec o projekcie zanim zacznie pracowac. Bez CLAUDE.md agent startuje każdą sesje od zera. Z CLAUDE.md — od razu wie gdzie jest i co może robić.

Jest tez inny ciekawy efekt: dobry CLAUDE.md zmniejsza context rot. Kiedy sesja robi się dluga i agent 'zapomina' o wczesniejszych ustaleniach — CLAUDE.md działa jak reset. Nowa sesja, ten sam agent, pelna wiedza o projekcie."

💬 WKLEJ NA CHAT:
```text
Prompt — Wygeneruj CLAUDE.md dla projektu Sinsay:

Jesteś doświadczonym tech leadem.

Stwórz CLAUDE.md dla projektu "Sinsay Complaint & Returns Chatbot".

Projekt:
Chatbot do obsługi reklamacji i zwrotów produktów dla Sinsay (fashion eCommerce).
Stack: Java 21 + Spring Boot + OpenAI Java SDK lub Spring AI + React + SQLite.
Flow: formularz (obraz + opis + typ) → analiza vision → analiza dokumentów → decyzja → chat.

Uwzgledniaj sekcję:
## Project Overview
## Architecture (key components, tech decisions, dlaczego taki stack — linkuj do ADR)
## Development Guidelines
   - Java coding style (Google Java Style Guide)
   - Nazewnictwo pakietow i klas
   - Gdzie pisac testy (unit vs integration) i jakie frameworki
   - Commit messages: conventional commits (feat/fix/refactor/test)
   - Jak radzic sobie z kluczami API (env variables, nigdy hardcode)
## Security Requirements
   - Dane klientów (RODO): co wolno przechowywac, jak długo
   - Klucze API: gdzie trzymac, jak przekazywac do agenta
   - Co wymaga security review przed mergem
## Agent Instructions
   - Jakie kroki przed każdą wiekszą zmiana (sprawdź testy!)
   - Kiedy pytać o zgodę zanim coś wykona
   - Jak się zachowac gdy wymaganie jest niejasne
   - Jak zgłaszac potencjalne problemy bezpieczeństwa
## Available Skills (/commit, /review, /prd-sync)
## Sub-agents (Backend, Frontend, Documents, QA — role i zakresy)

Wymagania:
- Plik ma być praktyczny, nie ceremonialny
- Max 200 linii — agent czyta to przy kazdej sesji, nie może być esej
- Konkretne zasady, nie ogólniki
- Zapisz jako CLAUDE.md w root projektu
```

🏋️ **ĆWICZENIE 5 — CLAUDE.md (15 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie 5: CLAUDE.md dla Waszego projektu

1. Wygenerujcie CLAUDE.md używając powyższego prompta (8 min)

2. Przeczytajcie krytycznie i znajdzcie 3 rzeczy do poprawienia.
   Typowe problemy:
   • Za ogolne zasady ("pisz czysty kod" to nie zasada)
   • Brakujace odwolanie do ADR decyzji
   • Brak konkretnych linii "kiedy pytaj o zgodę"

3. Poprawcie przez rozmowę z agentem:
   "W sekcji Agent Instructions dodaj: przed każdym DELETE
    lub DROP na bazie danych, zapytaj o potwierdzenie z opisem
    co zostanie usuniete."

4. Test CLAUDE.md — otworzcie NOWA sesje claude i wklejcie:
   "Przeczytaj CLAUDE.md i odpowiedz:
    1) Czym jest ten projekt i jaki problem rozwiązuje?
    2) Jaki stack technologiczny i dlaczego?
    3) Jakie są kluczowe zasądy pracy?
    4) Co mozesz robić samodzielnie, a o co musisz pytać?
    5) Jakie skills i sub-agenci są dostępni?"

   Jeśli agent odpowiada poprawnie — CLAUDE.md działa ✅
   Jeśli zgaduje — CLAUDE.md trzeba poprawic 🔧
```

---

## 15:30–15:55 — Projekt: Decyzja o stacku + Kickoff
⏱️ 25 min

🎬 **CO MÓWIĘ:**

„Ostatni blok dnia — decyzja i pierwsze kroki.

Macie teraz: PRD, diagramy, ADR, CLAUDE.md. To jest więcej niz wiekszosci projektow ma po pierwszym sprincie. I to jest dlatego że mieliscie agenta jako PM, architekta i tech writera.

Teraz czas na inicjalizacje projektu. Pokaze ścieżkę 🟢. Dla 🟡 dam wskazowke do RAG. Dla 🔴 — dam linki i research prompt.

Ale najpierw — mam dla Was narzędzie które pomaga w pisaniu lepszych promptów. Nazwy: PromptCowboy."

💬 WKLEJ NA CHAT:
```text
PromptCowboy — ulepszanie promptów:
https://www.promptcowboy.ai/

Jak uzyc:
1. Wklejcie Wasz prompt (np. system prompt dla agenta lub sekcję CLAUDE.md)
2. Narzedzie zaproponuje ulepszenia struktury, jasnosci, precyzji
3. Wroccie z lepszym promptem do agenta

Dobre do:
• Długich promptów systemowych
• Sekcji instrukcji dla sub-agentow
• Skomplikowanych zapytan architektonicznych
• Wszedzie gdzie czujecie że agent "nie rozumie co chcecie"
```

🎬 **INICJALIZACJA PROJEKTU — SCIEZKA ZIELONA:**

„Teraz inicjalizacja. Wysle Wam prompt. Jeśli masz zainstalowany i uruchomiony claude z CLAUDE.md w projekcie — to ten prompt zainicjalizuje strukture Spring Boot sprzezong z Waszymi decyzjami architektonicznymi."

💬 WKLEJ NA CHAT:
```text
Prompt — Inicjalizacja projektu Spring Boot (ścieżka 🟢):

Przeczytaj CLAUDE.md i docs/PRD.md i docs/ADR/ w tym projekcie.
Na ich podstawie zainicjalizuj projekt Spring Boot.

Stwórz:
1) Struktura Maven:
   src/main/java/com/sinsay/chatbot/
     controller/ (REST endpoints)
     service/    (logika biznesowa)
     domain/     (klasy domenowe)
     repository/ (SQLite persistence)
     config/     (Spring konfiguracja, beans)
   src/main/resources/ (application.properties)
   src/test/java/      (testy — ta sama struktura)

2) pom.xml z zależnościami zgodnymi z ADR:
   spring-boot-starter-web
   spring-boot-starter-validation
   [openai-java lub spring-ai] — zgodnie z ADR-001
   sqlite-jdbc
   spring-data-jpa + hibernata-community-dialects (SQLite dialect)

3) application.properties z placeholderami:
   spring.ai.openai.api-key=${OPENAI_API_KEY}
   spring.datasource.url=jdbc:sqlite:./data/sinsay-chat.db

4) Placeholder endpoints (TODO w javadoc):
   POST /api/claims — multipart: image + description + type
   POST /api/chat/{sessionId} — kontynuacja rozmowy
   GET  /api/sessions/{sessionId} — historia sesji

5) README.md z instrukcja uruchomienia

Nie implementuj logiki AI — tylko struktura i konfiguracja.
Na końcu sprawdź: mvn compile
```

💬 WKLEJ NA CHAT:
```text
Prompt — Research dla sciezki 🔴 (LangGraph4j + CopilotKit):

Jesteś architektem znajacym Java i nowoczesne wzorce AI.

Zbadaj nastepujace technologie i opisz jak moga wspolpracowac
dla chatbota Sinsay:
1) LangGraph4j — https://github.com/bsorrentino/langgraph4j
2) CopilotKit — https://docs.copilotkit.ai/
3) AG-UI Protocol — https://docs.ag-ui.com/

Konkretnie:
• Jak LangGraph4j modeluje flow decyzji (reklamacja/zwrot)?
• Jak CopilotKit + AG-UI pozwala agentowi kontrolowac UI?
• Jak wyswietlic formularz dynamicznie gdy agent wykryje intencje?
• Jaka jest realna zlozonosc vs benefit dla 3-tygodniowego MVP?

Uzywaj context7 do pobrania aktualnej dokumentacji tych bibliotek.
Zakoncz ocena: czy warto dla MVP na tym kursie?

Template startowy: https://github.com/bsorrentino/langgraph4j-copilotkit-template
```

🏋️ **ĆWICZENIE 6 — Kickoff projektu (8 min):**

💬 WKLEJ NA CHAT:
```text
Ćwiczenie 6: Inicjalizacja projektu

Wybierzcie ścieżkę i zacznijcie:

🟢 BEZPIECZNA:
Wklejcie prompt inicjalizacji powyżej.
Sprawdzcie: mvn compile lub ./mvnw compile
Otworzcie projekt w IntelliJ.

🟡 SREDNIA (dodatkowo po 🟢):
"Jak dodac SQLite Vector Store do Spring Boot dla prostego RAG?
 Chce przechowywac embeddingi dokumentów MD i wyszukiwac semantycznie.
 Uzywaj context7 dla Spring AI vector store dokumentacji."

🔴 ZAAWANSOWANA:
Wklejcie research prompt LangGraph4j powyżej.
Przeczytajcie analize.
Zdecydujcie: kontynuujecie? Na czym się skupicie?

Na koniec: każdy wrzuca na chat:
✅ co ma uruchomione
🔧 co wymaga jeszcze pracy
❓ gdzie jest blokada
```

---

## 15:55–16:00 — Podsumowanie dnia
⏱️ 5 min

🎬 **CO MÓWIĘ:**

„Koniec Dnia 2. Zrobielismy dzis naprawdę duzo.

Rano domknelismy to czego brakowalo: sandbox, permissions, IntelliJ ACP, komendy Claude Code.

A potem — cały cykl 'od pomysłu do projektu': PRD, research technologiczny, diagramy UML które wymuszają myślenie architektoniczne, ADR, MCP, skills, sub-agenci, CLAUDE.md.

I projekt jest zainicjalizowany. Jutro agent nie będzie zgadywał — będzie budowal z planem.

Jedno pytanie na koniec: co z dzisiejszego dnia będzie dla Was najbardziej użyteczne jutro w implementacji? 1 zdanie na chat."

*[Poczekaj na odpowiedzi, skomentuj krotko 2-3]*

💬 WKLEJ NA CHAT:
```text
✅ Po Dniu 2 mamy:

□ Sandbox + permissions skonfigurowane (.claude/settings.json)
□ IntelliJ ACP + IntelliJ MCP Server — wspolna sesja z CLI
□ Claude Code commands: Ctrl+G, /loop, /batch, /schedule, /doctor
□ PRD dla projektu Sinsay (docs/PRD.md)
□ Diagramy UML: Mermaid + PlantUML
□ ADR — kluczowe decyzje architektoniczne (docs/ADR/)
□ Context7 MCP — research z aktualna dokumentacja
□ CLAUDE.md — kontekst dla agentów
□ Decyzja o tech stacku
□ Projekt Spring Boot zainicjalizowany

Jutro (Dzień 3): Implementacja
→ Backend: claim processing, vision API, decision logic
→ Frontend: chat UI, formularz z uploadem
→ Testy i code review przez agenta
→ Praca z sub-agentami w praktyce
```

💬 WKLEJ NA CHAT:
```text
Opcjonalnie na wieczór (bez presji):

□ Przeczytaj PRD — popraw 3 rzeczy które są za ogolne
□ Zrob test CLAUDE.md (nowa sesja, 5 pytań diagnostycznych)
□ Jeśli ścieżka 🔴 — zbadaj LangGraph4j template i zrob research

Pytania? Wrzucajcie tu albo na grupowy kanal.
Do jutra! 👋
```

---

## APPENDIX A — Linki (wszystko w jednym miejscu)

| Co | Link |
|---|---|
| Permissions docs (CC) | https://code.claude.com/docs/en/permissions |
| Sandboxing docs (CC) | https://code.claude.com/docs/en/sandboxing |
| Docker AI Sandboxes | https://docs.docker.com/ai/sandboxes/architecture/#credential-injection |
| dangerously-skip-permissions artykul | https://www.ksred.com/claude-code-dangerously-skip-permissions-when-to-use-it-and-when-you-absolutely-shouldnt/ |
| JetBrains ACP docs | https://www.jetbrains.com/help/ai-assistant/acp.html |
| JetBrains ACP Registry blog | https://blog.jetbrains.com/ai/2026/01/acp-agent-registry/ |
| Introducing Claude Agent in JetBrains | https://blog.jetbrains.com/ai/2025/09/introducing-claude-agent-in-jetbrains-ides/ |
| Context7 MCP | https://upstash.com/docs/context7 |
| MCP Registry Smithery | https://registry.smithery.ai/ |
| MCP Registry mcp.so | https://mcp.so/servers |
| Mermaid live editor | https://mermaid.live |
| PlantUML online | https://www.plantuml.com/plantuml/uml/ |
| Kroki.io (Mermaid+PlantUML) | https://kroki.io/ |
| PromptCowboy | https://www.promptcowboy.ai/ |
| CLAUDE.md / AGENTS.md standard | https://agents.md/ |
| CLAUDE.md blog Anthropic | https://claude.com/blog/using-claude-md-files |
| LangGraph4j | https://github.com/bsorrentino/langgraph4j |
| LangGraph4j-CopilotKit template | https://github.com/bsorrentino/langgraph4j-copilotkit-template |
| CopilotKit docs | https://docs.copilotkit.ai/ |
| AG-UI Protocol | https://docs.ag-ui.com/ |
| Spring AI docs | https://docs.spring.io/spring-ai/reference/index.html |
| AssistantUI | https://www.assistant-ui.com/ |
| Vercel AI SDK | https://sdk.vercel.ai/docs |
| OpenAI Java SDK | https://github.com/openai/openai-java |
| Context engineering (Anthropic) | https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents |

---

## APPENDIX B — Wszystkie prompty do skopiowania

### B1 — Sandbox test
```text
Jakie masz teraz uprawnienia? Wymien dokladnie:
1) co mozesz wykonac bez pytania,
2) co wymaga mojej zgody,
3) czego absolutnie nie mozesz robić.
Odpowiedz w punktach.
```

### B2 — AI jako PM (interrogation mode)
```text
Jesteś doświadczonym product managerem pracujacym dla Sinsay —
modowego eCommerce sprzedajacego ubrania.

Musimy zbudować chatbota do obsługi reklamacji i zwrotów produktów.
Zanim napiszesz jakikolwiek dokument, zadaj mi 10 konkretnych pytań
które pomoga Ci lepiej zrozumiec wymagania.

Zasady:
- Pytaj o JEDNO na raz i czekaj na odpowiedz
- Pytaj o to czego nie wiesz — nie zgaduj
- Pytaj o edge cases i sytuacje wyjatkowe
- Pytaj o ograniczenia techniczne i biznesowe
- Pytaj o persony uzytkownikow i ich kontekst

Zacznij od pierwszego pytania. Pisz po polsku.
```

### B3 — Generowanie PRD
```text
Dziekuje za pytania. Na podstawie naszej rozmowy przygotuj PRD
dla chatbota Sinsay do obsługi reklamacji i zwrotów.

Format:
## 1. Executive Summary
## 2. Problem Statement
## 3. Persony (2-3 konkretne)
## 4. Główny Flow
## 5. User Stories (min 8, format: "Jako [kto], chcę [co], żeby [dlaczego]")
## 6. Acceptance Criteria (mierzalne!)
## 7. Out of Scope
## 8. Ograniczenia techniczne i biznesowe
## 9. Metryki sukcesu MVP

Pisz po polsku. Konkretnie. PRD ma być uzyteczny dla developera.
Zapisz jako docs/PRD.md
```

### B4 — Research tech stack
```text
Jesteś doświadczonym architektem Java. Projektuję chatbota Sinsay.
Stack bazowy: Java 21 + Spring Boot 3.x.

Porownaj:
1) Vercel AI SDK vs AssistantUI — do React chat UI
2) OpenAI Java SDK vs Spring AI — do LLM z Javy
3) SQLite vs H2 vs PostgreSQL — do sesji i historii czatu
4) Dwa osobne wywołania LLM (vision + decision) vs jedno multimodalne

Dla kazdej opcji: zalety, wady, latwosc integracji, kiedy wybrac.
Zakoncz rekomendacja dla MVP (small team, 3-4 tygodnie).
Uzywaj context7 do aktualnej dokumentacji bibliotek.
```

### B5 — Diagramy Mermaid
```text
Jesteś architektem systemow. Zaprojektuj chatbota Sinsay
do obslugi reklamacji i zwrotów na podstawie PRD w docs/.

Przygotuj 4 diagramy Mermaid:
1) Sequence Diagram — pełny flow reklamacji (user → frontend → backend → LLM vision → LLM decision → response)
2) Class Diagram — główne klasy Java (ClaimRequest, ClaimResponse, ChatSession, ClaimDocument, ClaimService, VisionAnalysisService, DecisionService)
3) Component Diagram — architektura (Frontend <-> Spring Boot API <-> OpenAI API; Spring Boot <-> SQLite; Spring Boot <-> File System)
4) State Diagram — stany zgloszenia (DRAFT → SUBMITTED → IMAGE_ANALYZED → DECISION_MADE → RESOLVED; z ESCALATED)

Każdy w bloku ```mermaid ... ```
Kazdemu diagram — 1 zdanie opisu dla nowego developera.
```

### B6 — Konwersja Mermaid na PlantUML
```text
Skonwertuj powyzsze diagramy na format PlantUML.
Każdy w bloku @startuml ... @enduml
Dla Sequence Diagram dodaj @note przy:
- miejscach gdzie może wystąpić błąd sieci
- miejscach z timeoutem
- miejscach z danymi wrażliwymi RODO
```

### B7 — ADR
```text
Napisz trzy krótkie ADR dla projektu Sinsay Chatbot.

ADR-001: OpenAI Java SDK vs Spring AI
ADR-002: Dwa osobne wywołania LLM (vision + decision) vs jedno multimodalne
ADR-003: SQLite jako baza sesji vs Redis vs PostgreSQL w MVP

Format:
## ADR-XXX: [Tytuł]
**Status:** Accepted
**Data:** [dzis]
**Kontekst:** ...
**Decyzja:** ...
**Odrzucone alternatywy:** ...
**Konsekwencje:** ...
**Trigger rewizji:** ...

Zapisz jako docs/ADR/ADR-001.md itd.
```

### B8 — CLAUDE.md
```text
Jesteś doświadczonym tech leadem.
Stwórz CLAUDE.md dla projektu "Sinsay Complaint & Returns Chatbot".

Sekcje:
## Project Overview
## Architecture (tech decisions, linki do ADR)
## Development Guidelines (Java style, testy, commits)
## Security Requirements (RODO, klucze API, co wymaga review)
## Agent Instructions (kiedy pytać o zgodę, jak zgłaszać problemy)
## Available Skills (/commit, /review, /prd-sync)
## Sub-agents (Backend, Frontend, Documents, QA)

Max 200 linii. Konkretne zasady, nie ogólniki.
Zapisz jako CLAUDE.md w root projektu.
```

### B9 — Inicjalizacja Spring Boot
```text
Przeczytaj CLAUDE.md i docs/PRD.md i docs/ADR/.
Na ich podstawie zainicjalizuj projekt Spring Boot dla chatbota Sinsay.

Stwórz:
1) Struktura Maven: controller, service, domain, repository, config
2) pom.xml z zależnościami zgodnymi z ADR-001
3) application.properties z placeholderami na klucze
4) Placeholder endpoints:
   POST /api/claims (multipart: image + description + type)
   POST /api/chat/{sessionId}
   GET  /api/sessions/{sessionId}
5) README.md z instrukcja uruchomienia

Tylko struktura — bez logiki AI.
Na końcu sprawdź: mvn compile
```

### B10 — Test CLAUDE.md (nowa sesja)
```text
Przeczytaj CLAUDE.md i odpowiedz:
1) Czym jest ten projekt i jaki problem rozwiązuje?
2) Jaki stack technologiczny i dlaczego ten wybór?
3) Jakie są kluczowe zasady pracy (top 5)?
4) Co możesz robić samodzielnie, a o co musisz pytać?
5) Jakie skills i sub-agenci są dostępni?
```

---

## APPENDIX C — Troubleshooting

| Problem | Rozwiązanie |
|---|---|
| `settings.json` nie jest respektowany | Sprawdź lokalizacje: `.claude/settings.json` w root projektu. Jeśli globalny — `~/.claude/settings.json` |
| Agent pyta o zgodę na rzeczy w `allow` | Sprawdź składnię: `"Bash(mvn:*)"` — musi być dokładna komenda przed `:*`. Użyj `/doctor` |
| ACP nie widać w IntelliJ | Sprawdź wersje: Help → About. Potrzeba 2025.3+. Zaktualizuj lub użyj CLI |
| Context7 MCP nie działa | Sprawdź w CC: `/mcp`. Zrestartuj claude po zmianie settings.json. `npm install -g @upstash/context7-mcp` |
| IntelliJ MCP nie przekazuje diagnostyki | Włącz opcje "Pass IntelliJ MCP server" przy konfiguracji ACP. Sprawdź czy projekt jest otwarty w IntelliJ |
| Ctrl+G nic nie robi | Sprawdź: `echo $EDITOR`. Musi być ustawiony i dostępny w PATH. Ustaw: `export EDITOR="code --wait"` |
| CLAUDE.md ignorowany przez agenta | Plik musi być w root katalogu projektu (gdzie uruchamiasz `claude`). Sprawdź przez `/memory` |
| `mvn compile` nie działa | `java --version` — potrzeba Java 21. `echo $JAVA_HOME`. Jeśli brak: zainstaluj przez SDK Manager lub ścieżkę |
| PlantUML nie renderuje w IntelliJ | Zainstaluj plugin: Settings → Plugins → "PlantUML Integration". Wymaga Graphviz: `choco install graphviz` (Windows) |

---

## APPENDIX D — Plan B: jeśli mocno za mało czasu

Jeśli masz 30+ minut opóźnienia:

| Co przycinamy | Jak |
|---|---|
| Sandbox ćwiczenie | Skróć do 3 min: tylko pokaz settings.json, bez ćwiczenia |
| IntelliJ ACP | Pokaz demo, instalacja jako 🔵 homework |
| Context7 ćwiczenie | Pokaz live, uczestnicy robią samodzielnie po kursie |
| PlantUML | Pomin konwersje, zostaw tylko Mermaid |
| Sub-agenci | Opisz koncepcje (2 min), CLAUDE.md generuj bez ćwiczenia |
| Inicjalizacja projektu | Prześlij prompt na chat, robią samodzielnie wieczorem |

**Minimum must-have na koniec Dnia 2:**
1. Sandbox + permissions — rozumieją i mają settings.json
2. PRD dla projektu Sinsay — wygenerowany i krytycznie przeczytany
3. Przynajmniej 1 diagram Mermaid — Sequence Diagram
4. ADR — chocby 1 decyzja udokumentowana
5. CLAUDE.md — wygenerowany i przetestowany nowa sesja
6. Decyzja o tech stacku — kazdý wie co buduje jutro

---

## APPENDIX E — Dla prowadzacego: punkt startowy jutro (Dzień 3)

Po Dniu 2 uczestnicy maja:
- PRD, ADR, CLAUDE.md, diagramy, zainicjalizowany projekt
- Context7 MCP działa
- IntelliJ ACP + IntelliJ MCP Server skonfigurowany
- Zdecydowana ścieżka techologiczna

Dzień 3 zaczyna się od:
1. Sprawdzenie czy projekt kompiluje się u wszystkich (mvn compile)
2. Pierwsze implementacyjne zadanie dla agenta: "Zaimplementuj ClaimService.submitClaim() zgodnie z PRD i CLAUDE.md"
3. Obserwacja jak agent rozumie projekt dzieki CLAUDE.md
4. Praca z sub-agentami: Backend Agent dla logiki, Frontend Agent dla UI
