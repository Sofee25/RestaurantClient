# 🚨 Critical Backend Integration Issues Report

## **Status (2025-12-15): Admin Dashboard UI Ready – Backend Verification Pending**

### **Summary:** Front-end now supports role-aware login, routing, and admin tooling. Remaining work is validating that the backend emits the required role/user payloads and exposes the admin endpoints described below.

---

## **1. Role Information System** 🟡

### **Current State:**
- `LoginResponse.kt` already accepts an optional `UserDTO` payload (`val user: UserDTO? = null`).
- `AuthViewModel` persists the returned user (including role) when provided and gracefully falls back to fetching/inferring the role when backend omits it.
- `TokenManager` now decodes JWT payloads to persist username + role automatically.

### **Backend Requirement:**
To avoid heuristics/fallbacks, the backend should populate the `user` object (with role data) in the login/refresh responses, or encode the role/roleIds in the JWT payload. Front-end support is ready for either format.

---

## **2. AuthViewModel Role Handling** ✅

### **Highlights:**
- Stores the current `UserDTO`, username, and role (pulled from backend response or fetched separately).
- Exposes `getUserRole()`, `isAdmin()`, `isCustomer()`, `setUserRole()` plus a `determineUserRole()` fallback that probes admin endpoints when backend data is missing.
- Persists role/username via `TokenManager` so activities can call `loadStoredUserInfo()` on cold start.

### **Action:** None required on the client; backend should keep returning consistent role info to avoid fallback detection.

---

## **3. MainActivity Role-Aware Routing** ✅

### **Current Implementation:**
- `MainActivity` now loads stored user info, checks `authViewModel.getUserRole()`, and routes admins to `AdminDashboardActivity` and customers to `ProductListActivity`.
- If role data is missing, it waits briefly for async determination and finally falls back to heuristics (or remote check) to avoid trapping admins.

### **Action:** None on the client. Verify backend provides role info promptly so the fallback heuristics are rarely needed.

---

## **4. Backend API Verification** ⚠️

### **Problem:**
The Android client now calls several admin endpoints (users, orders, products, role assignment). We still need confirmation that each endpoint exists server-side and honors the expected payloads/auth.

**Endpoints to Verify:**
```kotlin
@GET("api/v1/admin/users")
@POST("api/v1/admin/users")
@DELETE("api/v1/admin/users/{id}")
@POST("api/v1/admin/users/assign-role")
@GET("api/v1/admin/orders")
@PUT("api/v1/admin/orders/{id}")
@GET("api/v1/admin/products")
@POST("api/v1/admin/products")
@PUT("api/v1/admin/products/{id}")
@DELETE("api/v1/admin/products/{id}")
```

**Need to Verify:**
- Are these endpoints implemented and secured?
- Does the login/refresh response (or JWT payload) include role info?
- Does the first registered user still become Admin automatically (or should backend decide differently)?

---

## **5. Token Manager Role Support** ✅

### **Current Implementation:**
- Decodes JWT payloads to capture `username`, `role`, or `roles` arrays and normalizes them to `Admin`/`Customer`.
- Persists role/username so activities can reconstruct the current user after process death.
- Provides `saveUserRole(role: RoleDTO)` / `saveUserRole(role: String)` helpers plus `getUserRole()` for quick checks.

### **Backend Requirement:** Keep encoding either `role` or `roles` (array of IDs) in JWTs so automatic extraction succeeds.

---

## **🔧 Required Fixes**

### **Fix 1: Update LoginResponse**
```kotlin
data class LoginResponse(
    val token: String,
    val message: String,
    val user: UserDTO  // Add user with role info
)
```

### **Fix 2: Enhance AuthViewModel**
```kotlin
@HiltViewModel
class AuthViewModel @Inject constructor(...) : ViewModel() {
    
    private var currentUser: UserDTO? = null
    
    fun getUserRole(): RoleDTO? = currentUser?.role
    fun isAdmin(): Boolean = getUserRole() == RoleDTO.Admin
    fun isCustomer(): Boolean = getUserRole() == RoleDTO.Customer
    
    fun login(loginDto: LoginDTO) {
        // Store user info including role
        if (result is Result.Success) {
            currentUser = result.data.user
            tokenManager.saveUserRole(result.data.user.role)
        }
    }
}
```

### **Fix 3: Update MainActivity**
```kotlin
private fun navigateBasedOnRole() {
    if (authViewModel.isAdmin()) {
        startActivity(Intent(this, AdminDashboardActivity::class.java))
    } else {
        startActivity(Intent(this, ProductListActivity::class.java))
    }
}
```

### **Fix 4: Enhance TokenManager**
```kotlin
fun saveUserRole(role: RoleDTO?) {
    role?.let {
        prefs.edit().putString(ROLE_KEY, it.name).apply()
    }
}

fun getUserRole(): RoleDTO? {
    val roleString = prefs.getString(ROLE_KEY, null)
    return roleString?.let { RoleDTO.valueOf(it) }
}
```

---

## **🧪 Testing Requirements**

### **Backend Verification Needed:**

1. **Test Login Response:**
   ```bash
   curl -X POST https://your-server.com/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"password"}'
   ```
   **Should Return:**
   ```json
   {
     "token": "jwt_token_here",
     "message": "Login successful", 
     "user": {
       "userId": 1,
       "username": "admin",
       "role": "Admin",
       "createdAt": "2025-12-08"
     }
   }
   ```

2. **Test Admin Endpoints:**
   ```bash
   curl -X GET https://your-server.com/api/v1/admin/users \
     -H "Authorization: Bearer jwt_token_here"
   ```

3. **Test First User Registration:**
   - Register on fresh database
   - Verify user gets Admin role automatically

---

## **📋 Action Items**

### **Immediate Actions Required:**

1. **🔥 Priority 1 - Backend Verification (Still Pending):**
   - [ ] Confirm all admin endpoints above exist and return expected payloads
   - [ ] Verify login/refresh responses (or JWT payloads) include role info
   - [ ] Test first-user-admin (or updated) provisioning logic on backend

2. **🔥 Priority 2 - Frontend Tasks (Completed):**
   - [x] Update LoginResponse DTO to carry `UserDTO`
   - [x] Add role handling to AuthViewModel
   - [x] Implement role-based navigation in MainActivity
   - [x] Update TokenManager for role storage/decoding

3. **🔥 Priority 3 - Integration Testing (Blocked by Backend):**
   - [ ] Test admin login flow against live backend
   - [ ] Test customer login flow
   - [ ] Test role-based navigation end-to-end
   - [ ] Test admin dashboard/order/product management with real data

---

## **⚠️ Current Status (2025-12-15)**

**Admin Dashboard Status:** 🟡 **Frontend Ready / Backend Pending**
- UI components, role-based navigation, and token handling are ✅ complete.
- Backend role payloads + admin endpoints still need confirmation before full E2E testing.

**Recommendation:**
Continue with backend verification (login payloads + admin endpoints). No further client-side blockers remain.

**Next Steps:**
1. Exercise each admin endpoint from a REST client to confirm availability/auth.
2. Ensure login/refresh responses (or JWT payload) include user role or role IDs.
3. Run end-to-end admin vs customer login tests against the live backend.

Once backend verification passes, the existing Android implementation should work without additional changes.