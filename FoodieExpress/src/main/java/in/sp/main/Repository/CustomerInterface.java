package in.sp.main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.Entity.Customer;

public interface CustomerInterface extends JpaRepository<Customer, Long> {
	Customer findByEmailAndPassword(String email , String password);

}
