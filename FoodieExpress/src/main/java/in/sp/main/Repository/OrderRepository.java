package in.sp.main.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.Entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
	List<Orders> findByCustomerId(Long customerId);
	List<Orders> findByRestaurantId(Long restaurantId);

}
