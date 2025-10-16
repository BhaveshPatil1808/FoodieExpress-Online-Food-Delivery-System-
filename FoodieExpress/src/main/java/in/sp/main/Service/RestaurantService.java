package in.sp.main.Service;

import java.util.List;


import in.sp.main.Entity.MenuItem;
import in.sp.main.Entity.Restaurant;


public interface RestaurantService {

	Restaurant register(Restaurant restaurant);
    Restaurant login(String email, String password);
    List<MenuItem> getMenuItems(Long restaurantId);
    List<Restaurant> getAllRestaurants();
    MenuItem addMenuItem(Long restaurantId, MenuItem item);
    
    Restaurant getRestaurantById(Long id);
    MenuItem getMenuItemById(Long itemId);

}
