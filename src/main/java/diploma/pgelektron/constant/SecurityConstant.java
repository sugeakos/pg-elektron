package diploma.pgelektron.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000; //5 days in ms
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token Cannot be Verified";
    public static final String PG_ELEKTRON_DOO = "PG Elektron, DOO";
    public static final String PG_ELEKTRON_ADMINISTRATION = "PG Elektron Reservation Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "Ahhoz hogy hozzáférjen az oldalhoz, kérem jelentkezzen be.";
    public static final String ACCESS_DENIED_MESSAGE = "Nincs elég jogosultsága, ehhez a funkcióhoz.";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS =
            {"/api/v1/registration", "/api/v1/login", "/api/v1/person/reset-password/**", "/api/v1/verify/**"};

    public static final String BASE_URL = "http://localhost:4200";
}
