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