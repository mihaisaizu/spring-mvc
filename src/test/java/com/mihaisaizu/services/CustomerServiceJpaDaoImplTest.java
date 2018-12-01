package com.mihaisaizu.services;

import com.mihaisaizu.config.JpaIntegrationConfig;
import com.mihaisaizu.domain.Customer;
import com.mihaisaizu.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class CustomerServiceJpaDaoImplTest {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    public void testListMethod() throws Exception {
        List<Customer> customer = (List<Customer>) customerService.listAll();
        assert customer.size() == 3;
    }

    @Test
    public void testSaveWithUser(){
        Customer customer = new Customer();
        User user = new User();
        user.setUsername("this is my username");
        user.setPassword("MyPassword");
        customer.setUser(user);

        Customer savedCustomer = customerService.saveOrUpdate(customer);

        assert savedCustomer.getUser().getId() != null;
    }
    //test for getId, saveOrUpdate, delete methods
}
