package com.example.pgelektron.bootstrap;

import com.example.pgelektron.customer.Customer;
import com.example.pgelektron.customer.CustomerService;
import com.example.pgelektron.person.Person;
import com.example.pgelektron.person.PersonRole;
import com.example.pgelektron.person.PersonService;
import com.example.pgelektron.tv.TV;
import com.example.pgelektron.tv.TvService;
import com.example.pgelektron.tvcategory.TVCategory;
import com.example.pgelektron.tvcategory.TvCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PersonService personService;
    private final TvCategoryService tvCategoryService;
    private final TvService tvService;
    private final CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading Person data....");
        loadPersonData();
        System.out.println("--------------");
        System.out.println("Loading Tv category data....");
        loadTvCategory();
        System.out.println("-----------------");
        System.out.println("Loading Csutomer and TV data......");
        loadCustomerAndTv();
        System.out.println("-----------------------------");

        System.out.println(personService.getAllPersons()+ " ");
    }

    private void loadPersonData() {
        Person person1 = new Person();
        person1.setFirstName("Akos");
        person1.setLastName("Suge");
        person1.setEmail("asd@asd.com");
        person1.setPassword(personService.passwordEncoder("admin"));
        person1.setPhoneFix("66433");
        person1.setPhoneMobile("77777");
        person1.setAddress("ahfkjshdfkj");
        person1.setUserRole(PersonRole.ADMIN);
        personService.savePerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Proba2");
        person2.setLastName("SAD");
        person2.setEmail("proba.proba2@gmail.com");
        person2.setPassword(personService.passwordEncoder("sghjfghjhkdsfjfhjksdhfkshfksh65745$%"));
        person2.setPhoneFix("656756");
        person2.setPhoneMobile("5765657");
        person2.setAddress("ashgjakhf");
        person2.setUserRole(PersonRole.CUSTOMER);
        personService.savePerson(person2);
    }
    public void loadTvCategory(){
        TVCategory category1 = new TVCategory();
        category1.setDescription("Samsung");
        tvCategoryService.saveTvCategory(category1);

        TVCategory category2 = new TVCategory();
        category2.setDescription("LG");
        tvCategoryService.saveTvCategory(category2);

        TVCategory category3 = new TVCategory();
        category3.setDescription("Philips");
        tvCategoryService.saveTvCategory(category3);

        TVCategory category4 = new TVCategory();
        category4.setDescription("Sony");
        tvCategoryService.saveTvCategory(category4);


    }

    public void loadCustomerAndTv(){
        TV tv1 = new TV();
        tv1.setTvCategoryId(tvCategoryService.getTvCategoryById(1L));
        tv1.setErrorSeenByCustomer("Nincs hang!");
        tv1.setReservedDateToRepair(LocalDateTime.now());
        tv1.setPerson(personService.getPersonById(1L));
        tvService.saveTv(tv1);

        tvService.createNewTv(personService.getPersonById(2L),tvCategoryService.getTvCategoryById(3L),"dhfkshfkjsfhsdfkh", LocalDateTime.now());

        Customer customer1 = new Customer();
        customer1.setPerson_id(personService.getPersonById(1L));
        customer1.setTv_id(tvService.getTvById(1L));
        customerService.saveCustomer(customer1);

        Customer customer2 = new Customer();
        customer2.setPerson_id(personService.getPersonById(2L));
        customer2.setTv_id(tvService.getTvById(2L));
        customerService.saveCustomer(customer2);

    }
}
