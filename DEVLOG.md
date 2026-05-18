# CodeDash DEVLOG

## 2026-05-02

### What's done
- JWT implemented: login returns signed access token, filter chain wired in SecurityConfig
- /api/auth/login and /api/auth/register endpoints working, tested manually
- UserDetailsService wired with DB lookup (PostgreSQL)
- Spring Security filter chain configured — token validated on every request
- Basic RBAC roles exist (STUDENT, STAFF, ADMIN) in the DB and on User entity

### What's NOT done yet (looks done but isn't)
- @EnableMethodSecurity not enabled — @PreAuthorize annotations exist on controllers
  but method-level security is NOT enforced. Any authenticated user can hit any endpoint.
- No refresh token logic — token expires and user gets 403 with no recovery path
- CORS not configured — will break the moment frontend makes a request
- audit logging table not created yet

### Current state
Backend boots, auth works, but RBAC is decoration not enforcement.
Do NOT assume role-based access is protecting anything right now.

### Next session — do these in order
1. Add seed script (seed.sql or DataInitializer) — ADMIN + STAFF + 2 STUDENT users
   with known credentials so DB can be wiped and restored in one command
2. Enable @EnableMethodSecurity in SecurityConfig
3. Test: STUDENT token hitting /admin/* should get 403
4. Then: LeetCode GraphQL fetch for one hardcoded handle, store result, expose endpoint

### Open questions / ideas (don't act on these now)
- Refresh token flow — design it properly before implementing
- placement_info and student_academic tables — v2, not now
- Audit logging — v2, not now

## 2026-05-03

### Progress
- Implemented LeetCode fetch service using local Docker API (`alfa-leetcode-api`)
- Implemented GitHub fetch service using public REST API
- Created controller endpoints for both services and verified using Bruno

### Infrastructure
- Pulled and ran LeetCode API locally:
  `docker run -d -p 3000:3000 --name leetcode-api alfaarghya/alfa-leetcode-api:2.0.4`

### Key Learnings
- LeetCode GraphQL cannot be reliably used directly due to CSRF + cookie restrictions
- Using a local wrapper API simplifies integration and avoids auth issues
- Docker basics:
  - `run` creates container
  - `stop` pauses container
  - `rm` deletes container
  - `ps` vs `ps -a` difference

### Current State
- Fetch → Map → Return flow is working
- Clean structured response (not raw API data)

### Next Step
- Integrate fetch service with HandleStats (DB persistence)

## 2026-05-04
- Created a better service method
- Only used /profile endpoint for the necessary data
- Converted to HandleStats object

## 2026-05-07
### DevLog — Handle, Fetch, Stats

- Built the complete Handle → Fetch → Stats backend flow for external coding platforms.

- Added `StudentHandle` system with platform-based unique handles linked to student profiles.

- Implemented JWT-based ownership flow:
  ```text
  JWT → User → Profile → Handle → Stats
  ```

- Created authenticated handle APIs:
  - create handle
  - update handle
  - delete handle
  - fetch all handles
  - fetch single handle

- Implemented LeetCode integration using a locally running Docker API service instead of direct GraphQL calls.

- Backend can now:
  - fetch LeetCode profile data
  - map important stats
  - store structured stats in DB
  - store recent submission history as raw JSON
  - update existing stats instead of duplicating rows

- Added `HandleStats` entity for:
  - solved counts
  - rankings
  - last activity
  - raw submission data
  - sync timestamps

- Fixed JSONB persistence and Hibernate serialization issues.

- Refactored controllers/services to use DTOs instead of returning entities directly.

- Standardized repository queries using nested relationship traversal:
  ```text
  userId → profile → handle → stats
  ```
- Enabled `@EnableMethodSecurity` in SecurityConfig
- Added separate DB-read stats flow apart from fetch/sync flow.

- Current working endpoints:
  - handle management
  - fetch LeetCode stats
  - read stored stats

- Remaining work:
  - scheduler/cron syncing
  - GitHub/Codeforces sync
  - dashboard queries
  - frontend integration
  - analytics/activity summaries

## 2026-05-09
### DevLog — Dashboard & Pagination

- Built institution-scoped leaderboard/dashboard API using JWT institution ownership filtering.

- Added aggregated `DashboardResponse` DTO combining:
  - student profile data
  - platform handle data
  - coding stats data

- Implemented manual DTO mapping for nested entity aggregation:
  ```text
  HandleStats → Handle → Profile → DashboardResponse
  ```

- Added pagination support using Spring `Pageable` and `Page<T>`.

- Added sorting support integrated with pageable leaderboard queries.

- Refactored leaderboard flow to return paginated DTO responses instead of entities.

- Implemented repository traversal queries across:
  ```text
  Institution → User → Profile → Handle → Stats
  ```

- Fixed DTO serialization and recursive entity response issues.

- Added leaderboard-ready response structure for frontend table rendering.

- Current leaderboard supports:
  - institution filtering
  - pagination
  - sorting
  - aggregated student stats view

- Remaining work:
  - filtering/search
  - activity analytics
  - scheduled stat syncing
  - frontend dashboard UI

## 2026-05-17
### DevLog — Automated Fetch Scheduler

- Implemented automated scheduled syncing for coding platform handles using Spring Scheduler.

- Added fetch scheduling metadata to handles:
  - fetch frequency
  - last fetched date
  - next fetch date

- Added admin-controlled bulk fetch frequency update API.

- Implemented scheduler flow:
  ```text
  eligible handles
  → platform fetch
  → stats update
  → next fetch date update
  ```

- Added automatic next-fetch calculation for:
  - DAILY
  - WEEKLY
  - NEVER

- Refactored fetch logic to update existing stats instead of recreating records.

- Added DB-driven fetch eligibility using:
  ```text
  nextFetchDate <= today
  ```

- Tested full scheduler lifecycle successfully:
  - scheduled execution
  - stat syncing
  - DB updates
  - duplicate prevention

- Improved security/error handling:
  - proper 403 handling for method security
  - cleaned JWT auth flow
  - added global authorization exception handling

- Remaining work:
  - fetch retry/error tracking
  - GitHub/Codeforces schedulers
  - fetch optimization (N+1 query reduction)
  - frontend integration
  - SMTP/email config
  - EmailService
  - Verification email sender
  - Verification endpoint
  - Token expiry handling
  - Resend verification endpoint

#### Completed
- Email service
- APP email
- deployed in railway