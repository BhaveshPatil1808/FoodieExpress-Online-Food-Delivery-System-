package in.sp.main.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sp.main.Entity.MenuItem;
import in.sp.main.Entity.Restaurant;
import in.sp.main.Service.CustomerService;
import in.sp.main.Service.OrderService;
import in.sp.main.Service.RestaurantService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;
    
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if ("admin@gmail.com".equals(email) && "admin123".equals(password)) {
            session.setAttribute("admin", true);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "admin-login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        int totalOrders = orderService.getAllOrders().size();
        int totalRestaurants = restaurantService.getAllRestaurants().size();
        int totalCustomers = customerService.getAllCustomers().size();

        double totalRevenue = orderService.getAllOrders().stream()
            .filter(o -> "Paid".equalsIgnoreCase(o.getPaymentStatus()))
            .mapToDouble(o -> o.getTotalAmount())
            .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", totalOrders);
        stats.put("totalRestaurants", totalRestaurants);
        stats.put("totalCustomers", totalCustomers);
        stats.put("totalRevenue", totalRevenue);

        model.addAttribute("stats", stats);
        return "admin-dashboard";
    }


    @GetMapping("/restaurants")
    public String viewRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "admin-restaurants";
    }

    @GetMapping("/customers")
    public String viewCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "admin-customers";
    }

    @GetMapping("/transactions")
    public String viewTransactions(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin-transactions";
    }
   
    @GetMapping("/restaurant/{id}/dishes")
    public String viewRestaurantDishes(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        List<MenuItem> dishes = restaurantService.getMenuItems(id);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("menuItems", dishes);
        return "admin-restaurant-dishes";
    }

}

