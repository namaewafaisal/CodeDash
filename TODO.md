## Phase 1 — Fix broken things first (1–2 days)

These should have been done already. Nothing else works properly without them.

1. **`@EnableMethodSecurity`** — RBAC is decoration right now, not enforcement
2. **CORS config** — frontend will fail immediately without this
3. **Fix `RuntimeException` in GlobalExceptionHandler** — swallows specific exceptions, replace with `Exception.class` fallback

---

## Phase 2 — Data foundation (1 day)

4. **Seed script** — `DataInitializer.java` that creates on startup if DB is empty:
   - 1 institution (approved)
   - 1 admin user
   - 1 staff user
   - 3–4 student users with profiles and handles
   - Run fetch once so stats exist too
   
   Without this you rebuild test data manually every time you wipe the DB.

---

## Phase 3 — Core missing backend (3–4 days)

5. **Scheduler + FetchFrequency** — nightly cron, DAILY/WEEKLY/NEVER per handle, `fetchTonight` staff override, `lastFetchedDate` drift-free logic
6. **GitHub sync service** — public REST API, no Docker needed
7. **Codeforces sync service** — free public API
8. **Dashboard filter endpoint** — department, year, section filters + sort by stats + activity indicator (green/red based on `lastSubmissionAt`)

---

## Phase 4 — Admin + institution management (1–2 days)

9. **Admin endpoints**:
   - promote user to STAFF
   - list all users in institution
   - delete institution
10. **Master endpoints**:
    - list pending institutions
    - approve/reject institution
    - these exist but verify they're secured properly

---

## Phase 5 — Polish before frontend (1 day)

11. **Handle verification** — check username exists on platform before saving (LeetCode GraphQL existence check, GitHub REST 404 check)
12. **Export endpoint** — filter students + download Excel with selected fields including stats columns
13. **Content-type header on export** — missing from current implementation
14. **Audit log** — who triggered a fetch, when, for which student

---

## Phase 6 — Frontend (2–3 weeks)

Only after Phase 1–5 are solid.

---

## What's explicitly deferred

- Docker compose setup
- Railway deployment
- Email service (institution approval, verification)
- Refresh token flow
- Unit/integration tests
- Rate limiting
