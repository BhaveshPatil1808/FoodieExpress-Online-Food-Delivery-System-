package in.sp.main.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.Entity.MenuItem;
import in.sp.main.Entity.Restaurant;
import in.sp.main.Repository.MenuItemRepository;
import in.sp.main.Repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;
    
    @Autowired 
    private MenuItemRepository menuItemRepo;

	@Override
	public Restaurant register(Restaurant restaurant) {
		
		return restaurantRepo.save(restaurant);
	}

	@Override
	public Restaurant login(String email, String password) {
		
		return restaurantRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public List<MenuItem> getMenuItems(Long restaurantId) {
		
		return menuItemRepo.findByRestaurantId(restaurantId);
	}

	@Override
	public MenuItem addMenuItem(Long restaurantId, MenuItem item) {
		Restaurant res = restaurantRepo.findById(restaurantId).get();
		item.setRestaurant(res);
		return menuItemRepo.save(item);
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepo.findAll();
	}

	@Override
	public MenuItem getMenuItemById(Long itemId) {
		// TODO Auto-generated method stub
		return  menuItemRepo.findById(itemId).orElse(null);
	}

	@Override
	public Restaurant getRestaurantById(Long id) {
		// TODO Auto-generated method stub
		return restaurantRepo.findById(id).get();
	}

}
