package diploma.pgelektron.person.dto.converter;

import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonEntity;

import diploma.pgelektron.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonConverter {
    private final PersonService personService;

    public PersonEntity convertDtoToEntity(PersonDto dto) {
        if (dto == null) {
            return null;
        }

        PersonEntity entity = new PersonEntity();
        PersonEntity setEntity = personService.findPersonByExternalId(dto.getExternalId());
        entity.setExternalId(dto.getExternalId());

        entity.setFirstName(setEntity.getFirstName());
        entity.setLastName(setEntity.getLastName());
        entity.setEmail(setEntity.getEmail());
        entity.setUsername(setEntity.getUsername());
        entity.setPassword(setEntity.getPassword());
        entity.setAddress(setEntity.getAddress());
        entity.setPhoneFix(setEntity.getPhoneFix());
        entity.setPhoneMobile(setEntity.getPhoneMobile());
        entity.setLastLoginDate(setEntity.getLastLoginDate());
        entity.setLastLoginDateDisplay(setEntity.getLastLoginDateDisplay());
        entity.setJoinDate(setEntity.getJoinDate());
        entity.setRole(setEntity.getRole());
        entity.setAuthorities(setEntity.getAuthorities());
        entity.setActive(setEntity.isActive());
        entity.setNotLocked(setEntity.isNotLocked());

        return entity;
    }

    public PersonDto convertEntityToDto(PersonEntity entity) {

        if (entity == null) {
            return null;
        }

        PersonDto dto = new PersonDto();
        PersonEntity getEntity = personService.findPersonByExternalId(entity.getExternalId());
        dto.setExternalId(entity.getExternalId());
        dto.setFirstName(getEntity.getFirstName());
        dto.setLastName(getEntity.getLastName());
        dto.setEmail(getEntity.getEmail());
        dto.setUsername(getEntity.getUsername());
        dto.setPassword(getEntity.getPassword());
        dto.setAddress(getEntity.getAddress());
        dto.setPhoneFix(getEntity.getPhoneFix());
        dto.setPhoneMobile(getEntity.getPhoneMobile());
        dto.setLastLoginDate(getEntity.getLastLoginDate());
        dto.setLastLoginDateDisplay(getEntity.getLastLoginDateDisplay());
        dto.setJoinDate(getEntity.getJoinDate());
        dto.setRole(getEntity.getRole());
        dto.setAuthorities(getEntity.getAuthorities());
        dto.setActive(getEntity.isActive());
        dto.setNotLocked(getEntity.isNotLocked());

        return dto;
    }
}
