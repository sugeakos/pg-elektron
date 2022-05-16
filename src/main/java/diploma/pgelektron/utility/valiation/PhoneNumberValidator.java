package diploma.pgelektron.utility.valiation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;

import static diploma.pgelektron.constant.ValidationConstant.*;
import static javax.validation.constraintvalidation.ValidationTarget.ANNOTATED_ELEMENT;
import static javax.validation.constraintvalidation.ValidationTarget.PARAMETERS;

@SupportedValidationTarget(value = {PARAMETERS,ANNOTATED_ELEMENT})
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(PHONE_REGEX_TEN_DIGIT_LONG) || value.matches(PHONE_REGEX_TWELVE_DIGIT_LONG)
                || value.matches(PHONE_REGEX_WITH_INTERNATIONAL_PREFIX) || value.matches(PHONE_REGEX_WITH_WHITESPACE_DOT_HYPHENS);
    }
}
