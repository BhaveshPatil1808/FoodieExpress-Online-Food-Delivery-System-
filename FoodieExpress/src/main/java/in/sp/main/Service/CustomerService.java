package in.sp.main.Service;

import java.util.List;

import in.sp.main.Entity.Customer;

public interface CustomerService {
	List<Customer> getAllCustomers();
	Customer register(Customer customer);
    Customer login(String email, String password);
}
