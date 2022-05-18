package diploma.pgelektron.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = {"user:read", "tv:read", "tv:create", "tv:update"};
    public static final String[] HR_AUTHORITIES = {"user:read", "user:update", "tv:read", "user:find", "tv:create", "tv:update"};
    public static final String[] MANAGER_AUTHORITIES = {"user:read", "user:update", "user:find", "tv:read","tv:create", "tv:update"};
    public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:update", "user:find", "user:create", "tv:read", "tv:create",
            "tv:update", "tv:delete"};
    public static final String[] SUPER_ADMIN_AUTHORITIES = {"user:read", "user:update", "user:find", "user:create", "user:delete",
            "tv:read", "tv:create", "tv:update", "tv:delete"};
}
