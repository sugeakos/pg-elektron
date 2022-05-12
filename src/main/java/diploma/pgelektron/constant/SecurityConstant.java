package diploma.pgelektron.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000; //5 days in ms
    public static final String TOKEN_PREFIX = "PGElektron ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED  = "Token Cannot be Verified";
    public static final String PG_ELEKTRON_DOO = "PG Elektron, DOO";
    public static final String PG_ELEKTRON_ADMINISTRATION = "PG Elektron Reservation Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    //    public static final String[] PUBLIC_URLS =
//            {"/user/login", "/user/register", "/user/reset-password/**", "/user/image/**",};
    public static final String[] PUBLIC_URLS = {"**",};
}
