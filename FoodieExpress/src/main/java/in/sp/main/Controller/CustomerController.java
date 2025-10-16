package in.sp.main.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.Entity.CartItem;
import in.sp.main.Entity.Customer;
import in.sp.main.Entity.MenuItem;
import in.sp.main.Entity.Orders;
import in.sp.main.Entity.Restaurant;
import in.sp.main.Service.CustomerService;
import in.sp.main.Service.OrderService;
import in.sp.main.Service.RestaurantService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, Model model) {
        customerService.register(customer);
        model.addAttribute("message", "Registration successful!");
        return "customer-login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "customer-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Customer customer = customerService.login(email, password);
        if (customer != null) {
            session.setAttribute("customer", customer);
            return "redirect:/customer/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "customer-login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/customer/login";
        }
        model.addAttribute("customer", customer);
        return "customer-dashboard";
    }
    
    @GetMapping("/restaurants")
    public String browseRestaurants(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "customer-restaurant-list";
    }
    
    
    @GetMapping("/orders")
    public String viewOrders(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/customer/login";
        }
        List<Orders> orders = orderService.getOrdersByCustomerId(customer.getId());
        model.addAttribute("orders", orders);
        return "customer-orders";
    }
    
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long itemId,
                            HttpSession session,
                            RedirectAttributes redirectAttrs) {

        MenuItem item = restaurantService.getMenuItemById(itemId);
        if (item == null) {
            redirectAttrs.addFlashAttribute("error", "Item not found");
            return "redirect:/restaurant/menu?restaurantId=" + item.getRestaurant().getId();
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        boolean found = false;
        for (CartItem ci : cart) {
            if (ci.getMenuItem().getId().equals(itemId)) {
                ci.setQuantity(ci.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            CartItem newItem = new CartItem();
            newItem.setMenuItem(item);
            newItem.setQuantity(1);
            cart.add(newItem);
        }

        session.setAttribute("cart", cart);
        redirectAttrs.addFlashAttribute("message", "Item added to cart!");
        return "redirect:/restaurant/menu?restaurantId=" + item.getRestaurant().getId();
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        model.addAttribute("cart", cart != null ? cart : new ArrayList<>());
        return "customer-cart";
    }

    @RequestMapping(value = "/place-order", method = {RequestMethod.GET, RequestMethod.POST})
    public String placeOrder(HttpSession session, RedirectAttributes redirectAttrs) {
        Customer customer = (Customer) session.getAttribute("customer");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (customer == null || cart == null || cart.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Cart is empty or session expired.");
            return "redirect:/customer/cart";
        }

        double total = 0;
        for (CartItem ci : cart) {
            total += ci.getMenuItem().getPrice() * ci.getQuantity();
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setRestaurant(cart.get(0).getMenuItem().getRestaurant()); // assuming all items are from same restaurant
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setPaymentStatus("paid");
        order.setDeliveryStatus("Ordered");

        orderService.placeOrder(order, cart);
        session.removeAttribute("cart");

        redirectAttrs.addFlashAttribute("message", "Order placed successfully!");
        return "redirect:/customer/orders";
    }
    
    
    @GetMapping("/checkout")
    public String showPaymentPage(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (customer == null || cart == null || cart.isEmpty()) {
            return "redirect:/customer/cart";
        }

        double total = cart.stream()
            .mapToDouble(ci -> ci.getMenuItem().getPrice() * ci.getQuantity())
            .sum();

        model.addAttribute("amount", total);
        model.addAttribute("orderId", "FE" + System.currentTimeMillis()); // mock order ID
        return "payment_gateway"; // this is your HTML file
    }



}

