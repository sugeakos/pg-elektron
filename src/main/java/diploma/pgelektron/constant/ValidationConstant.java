package diploma.pgelektron.constant;

public class ValidationConstant {
    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$";
    public static final String INVALID_EMAIL_MESSAGE = "Invalid email address, please provide a valid email address for example: username@example.com";
    public static final String PHONE_REGEX_TEN_DIGIT_LONG = "^\\\\d{10}$";
    public static final String PHONE_REGEX_TWELVE_DIGIT_LONG = "^\\\\d{12}$";
    public static final String PHONE_REGEX_WITH_WHITESPACE_DOT_HYPHENS = "^(\\\\d{3}[- .]?){2}\\\\d{4}$";
    public static final String PHONE_REGEX_WITH_INTERNATIONAL_PREFIX = "^(\\\\+\\\\d{1,3}( )?)?((\\\\(\\\\d{2}\\\\))|\\\\d{3})[- .]?\\\\d{3}[- .]?\\\\d{4}$";
    public static final String INVALID_PHONE_NUMBER = "Invalid for number, please provide a valid phone number: for example +381641231234567";
}
