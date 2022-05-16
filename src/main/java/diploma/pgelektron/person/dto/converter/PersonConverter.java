package diploma.pgelektron.person.dto.converter;

import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonEntity;

import diploma.pgelektron.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonConverter {


    public PersonEntity convertDtoToEntity(PersonDto dto) {
        if (dto == null) {
            return null;
        }

        PersonEntity entity = new PersonEntity();
        entity.setExternalId(dto.getExternalId());

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setAddress(dto.getAddress());
        entity.setPhoneFix(dto.getPhoneFix());
        entity.setPhoneMobile(dto.getPhoneMobile());
        entity.setLastLoginDate(dto.getLastLoginDate());
        entity.setLastLoginDateDisplay(dto.getLastLoginDateDisplay());
        entity.setJoinDate(dto.getJoinDate());
        entity.setRole(dto.getRole());
        entity.setAuthorities(dto.getAuthorities());
        entity.setActive(dto.isActive());
        entity.setNotLocked(dto.isNotLocked());

        return entity;
    }

    public PersonDto convertEntityToDto(PersonEntity entity) {

        if (entity == null) {
            return null;
        }

        PersonDto dto = new PersonDto();
        dto.setExternalId(entity.getExternalId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setAddress(entity.getAddress());
        dto.setPhoneFix(entity.getPhoneFix());
        dto.setPhoneMobile(entity.getPhoneMobile());
        dto.setLastLoginDate(entity.getLastLoginDate());
        dto.setLastLoginDateDisplay(entity.getLastLoginDateDisplay());
        dto.setJoinDate(entity.getJoinDate());
        dto.setRole(entity.getRole());
        dto.setAuthorities(entity.getAuthorities());
        dto.setActive(entity.isActive());
        dto.setNotLocked(entity.isNotLocked());

        return dto;
    }
}
