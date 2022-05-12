package diploma.pgelektron.utility.valiation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;

import static diploma.pgelektron.constant.ValidationConstant.EMAIL_REGEX;
import static javax.validation.constraintvalidation.ValidationTarget.ANNOTATED_ELEMENT;
import static javax.validation.constraintvalidation.ValidationTarget.PARAMETERS;

@SupportedValidationTarget(value = {PARAMETERS,ANNOTATED_ELEMENT})
public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(EMAIL_REGEX);
    }

}
