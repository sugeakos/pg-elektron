package diploma.pgelektron.person.entity;

import diploma.pgelektron.person.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
public class PersonPrincipal implements UserDetails {
    private final PersonEntity personEntity;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(this.personEntity.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.personEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.personEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.personEntity.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.personEntity.isActive();
    }
}
