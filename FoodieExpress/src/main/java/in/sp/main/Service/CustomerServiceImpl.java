package in.sp.main.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.Entity.Customer;
import in.sp.main.Repository.CustomerInterface;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerInterface customerInterface;
	
	
	
	@Override
	public Customer register(Customer customer) {
		
		return this.customerInterface.save(customer);
	}

	@Override
	public Customer login(String email, String password) {
		return this.customerInterface.findByEmailAndPassword(email,password);
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerInterface.findAll();
	}

}
