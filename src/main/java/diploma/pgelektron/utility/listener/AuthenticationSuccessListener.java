package diploma.pgelektron.utility.listener;

import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.service.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {
    private LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof PersonEntity) {
            PersonEntity person = (PersonEntity) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(person.getUsername());
        }
    }
}
