package com.example.pgelektron.bootstrap;

import com.example.pgelektron.service.CustomerService;
import com.example.pgelektron.service.impl.PersonServiceImpl;
import com.example.pgelektron.service.impl.TvServiceImpl;
import com.example.pgelektron.service.impl.TvCategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PersonServiceImpl personService;
    private final TvCategoryServiceImpl tvCategoryService;
    private final TvServiceImpl tvService;
    private final CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
//        loadPersonRoles();
//        System.out.println("Loading Person data....");
//        loadPersonData();
//        System.out.println("--------------");
//        System.out.println("Loading Tv category data....");
//        loadTvCategory();
//        System.out.println("-----------------");
//        System.out.println("Loading Csutomer and TV data......");
//        loadCustomerAndTv();
//        System.out.println("-----------------------------");
//
//        System.out.println(personService.getAllPersons()+ " ");
    }

//    private void loadPersonRoles(){
//        PersonRole role1 = new PersonRole();
//        role1.setDescription("ROLE_USER");
//        personService.saveRole(role1);
//
//        PersonRole role2 = new PersonRole();
//        role2.setDescription("ROLE_MANAGER");
//        personService.saveRole(role2);
//
//        PersonRole role3 = new PersonRole();
//        role3.setDescription("ROLE_ADMIN");
//        personService.saveRole(role3);
//
//        PersonRole role4 = new PersonRole();
//        role4.setDescription("ROLE_SUPER_ADMIN");
//        personService.saveRole(role4);
//
//        PersonRole role5 = new PersonRole();
//        role5.setDescription("ROLE_EMPLOYEE");
//        personService.saveRole(role5);
//
//
//    }
//
//    private void loadPersonData() {
//        Person person1 = new Person();
//        person1.setFirstName("Akos");
//        person1.setLastName("Suge");
//        person1.setEmail("asd@asd.com");
//        person1.setPassword(personService.passwordEncoder("admin"));
//        person1.setPhoneFix("66433");
//        person1.setPhoneMobile("77777");
//        person1.setAddress("ahfkjshdfkj");
//        person1.getUserRole().add(personService.findRoleByDescription("ROLE_ADMIN"));
//        personService.savePerson(person1);
//
//        Person person2 = new Person();
//        person2.setFirstName("Proba2");
//        person2.setLastName("SAD");
//        person2.setEmail("proba.proba2@gmail.com");
//        person2.setPassword(personService.passwordEncoder("sghjfghjhkdsfjfhjksdhfkshfksh65745$%"));
//        person2.setPhoneFix("656756");
//        person2.setPhoneMobile("5765657");
//        person2.setAddress("ashgjakhf");
//        person2.getUserRole().add(personService.findRoleByDescription("ROLE_USER"));
//        personService.savePerson(person2);
//    }
//    public void loadTvCategory(){
//        TVCategory category1 = new TVCategory();
//        category1.setDescription("Samsung");
//        tvCategoryService.saveTvCategory(category1);
//
//        TVCategory category2 = new TVCategory();
//        category2.setDescription("LG");
//        tvCategoryService.saveTvCategory(category2);
//
//        TVCategory category3 = new TVCategory();
//        category3.setDescription("Philips");
//        tvCategoryService.saveTvCategory(category3);
//
//        TVCategory category4 = new TVCategory();
//        category4.setDescription("Sony");
//        tvCategoryService.saveTvCategory(category4);
//
//
//    }
//
//    public void loadCustomerAndTv(){
//
//
//        TV tv1 = new TV();
//        tv1.setPerson(personService.getPersonById(1L));
//        tv1.setTvCategoryId(tvCategoryService.getTvCategoryById(1L));
//        tv1.setErrorSeenByCustomer("Nincs hang!");
//        tv1.setReservedDateToRepair(LocalDateTime.now());
//
//
//        tvService.saveTv(tv1);
//
//        Customer customer1 = new Customer();
//        customer1.setPerson_id(personService.getPersonById(1L));
//        customer1.getTvs().clear();
//        customer1.getTvs().add(tv1);
//        customerService.saveCustomer(customer1);
//        //tvService.createNewTv(personService.getPersonById(2L),tvCategoryService.getTvCategoryById(3L),"dhfkshfkjsfhsdfkh", LocalDateTime.now());
//
//
//    }
}
