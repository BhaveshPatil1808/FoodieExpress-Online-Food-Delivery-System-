package in.sp.main.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.Entity.CartItem;
import in.sp.main.Entity.OrderItem;
import in.sp.main.Entity.Orders;
import in.sp.main.Entity.Restaurant;
import in.sp.main.Repository.OrderItemRepository;
import in.sp.main.Repository.OrderRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;
    
    @Autowired private OrderItemRepository orderItemRepo;

    @Override
    public void placeOrder(Orders order, List<CartItem> cart) {
        orderRepo.save(order);
        System.out.println("Saving order for customer: " + order.getCustomer().getName());
        for (CartItem ci : cart) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setMenuItem(ci.getMenuItem());
            item.setQuantity(ci.getQuantity());
            orderItemRepo.save(item);
            System.out.println("Saved item: " + ci.getMenuItem().getName() + " x" + ci.getQuantity());
        }
    }


    @Override
    public List<Orders> getOrdersByCustomerId(Long customerId) {
    	List<Orders> list = orderRepo.findByCustomerId(customerId);
    	Collections.reverse(list);
        return list;
    }

	@Override
	public List<Orders> getOrdersByRestaurantId(Long restaurantId) {
		// TODO Auto-generated method stub
		List<Orders> list = orderRepo.findByRestaurantId(restaurantId);
		Collections.reverse(list);
		return list;
	}

	@Override
	public void updateDeliveryStatus(Long orderId, String status) {
	    Orders order = orderRepo.findById(orderId).orElse(null);
	    if (order != null) {
	        order.setDeliveryStatus(status);
	        orderRepo.save(order);
	    }
	}


	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}


	

	
}

