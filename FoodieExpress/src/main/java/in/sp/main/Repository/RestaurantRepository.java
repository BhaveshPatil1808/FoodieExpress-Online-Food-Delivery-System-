package in.sp.main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.Entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	Restaurant findByEmailAndPassword(String email, String password);
}
