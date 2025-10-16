package in.sp.main.Service;

import java.util.List;

import in.sp.main.Entity.CartItem;
import in.sp.main.Entity.Orders;
import in.sp.main.Entity.Restaurant;

public interface OrderService {

	void placeOrder(Orders order, List<CartItem> cart);
	List<Orders> getOrdersByCustomerId(Long customerId);
	List<Orders> getOrdersByRestaurantId(Long restaurantId);
	void updateDeliveryStatus(Long orderId, String status);
	
	List<Orders> getAllOrders();

}
