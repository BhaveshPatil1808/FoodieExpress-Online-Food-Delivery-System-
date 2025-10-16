package in.sp.main.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.Entity.MenuItem;
import in.sp.main.Entity.Orders;
import in.sp.main.Entity.Restaurant;
import in.sp.main.Service.OrderService;
import in.sp.main.Service.RestaurantService;
import in.sp.main.Service.RestaurantServiceImpl;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant-register";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "restaurant-login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Restaurant restaurant,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           Model model) throws IOException {

        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/");
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            restaurant.setImageUrl("/uploads/" + fileName); // ✅ Save image URL
        }

        restaurantService.register(restaurant);
        model.addAttribute("message", "Restaurant registered successfully!");
        return "restaurant-login";
    }


    // ✅ Login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Restaurant restaurant = restaurantService.login(email, password);
        if (restaurant != null) {
            session.setAttribute("restaurant", restaurant); // ✅ Store in session
            return "redirect:/restaurant/dashboard";        // ✅ Redirect to GET
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "restaurant-login";
        }
    }
    @GetMapping("/menu/add")
    public String showAddMenuForm(HttpSession session, Model model) {
        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (restaurant == null) {
            return "redirect:/restaurant/login";
        }

        model.addAttribute("restaurantId", restaurant.getId());
        return "restaurant-add-menu"; // ✅ This should be your Thymeleaf form page
    }


    // ✅ Add Menu Item (with image upload)
    @PostMapping("/add-item")
    public String addMenuItem(@RequestParam Long restaurantId,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam double price,
                              @RequestParam String category,
                              @RequestParam("image") MultipartFile imageFile,
                              RedirectAttributes redirectAttrs) throws IOException {

        String fileName = imageFile.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/");
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        MenuItem item = new MenuItem();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setImageUrl("/uploads/" + fileName);

        restaurantService.addMenuItem(restaurantId, item);

        redirectAttrs.addFlashAttribute("message", "Dish added successfully!");
        return "redirect:/restaurant/dashboard";
    }


    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (restaurant == null) {
            return "redirect:/restaurant/login";
        }

        // ✅ Fetch related data
        List<Orders> orders = orderService.getOrdersByRestaurantId(restaurant.getId());
        List<MenuItem> menuItems = restaurantService.getMenuItems(restaurant.getId());

        // ✅ Calculate dashboard stats
        int totalDishes = menuItems.size();

        long pendingOrders = orders.stream()
            .filter(order -> !"Delivered".equalsIgnoreCase(order.getDeliveryStatus()) &&
                             !"Cancelled".equalsIgnoreCase(order.getDeliveryStatus()) &&
                             !"Rejected".equalsIgnoreCase(order.getDeliveryStatus()))
            .count();

        double todaysRevenue = orders.stream()
            .filter(order -> "Delivered".equalsIgnoreCase(order.getDeliveryStatus()))
            .filter(order -> order.getOrderDate().toLocalDate().equals(java.time.LocalDate.now()))
            .mapToDouble(Orders::getTotalAmount)
            .sum();

        // ✅ Add attributes to model
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("orders", orders);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("totalDishes", totalDishes);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("todaysRevenue", todaysRevenue);

        return "restaurant-dashboard";
    }


    // ✅ View Menu Items
    @GetMapping("/menu")
    public String viewMenu(@RequestParam Long restaurantId, Model model) {
        List<MenuItem> items = restaurantService.getMenuItems(restaurantId);
        model.addAttribute("menuItems", items);
        return "restaurant-menu";
    }
    
    
    @GetMapping("/orders")
    public String viewReceivedOrders(HttpSession session, Model model) {
        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (restaurant == null) {
            return "redirect:/restaurant/login";
        }

        List<Orders> orders = orderService.getOrdersByRestaurantId(restaurant.getId());
        model.addAttribute("orders", orders);
        return "restaurant-orders";
    }
    
    @PostMapping("/order/update")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttrs) {
        orderService.updateDeliveryStatus(orderId, status);
        redirectAttrs.addFlashAttribute("message", "Order status updated.");
        return "redirect:/restaurant/orders";
    }
    
    @GetMapping("/order/accept/{id}")
    public String acceptOrder(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        orderService.updateDeliveryStatus(id, "Confirmed");
        redirectAttrs.addFlashAttribute("message", "Order accepted.");
        return "redirect:/restaurant/orders";
    }

    @GetMapping("/order/reject/{id}")
    public String rejectOrder(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        orderService.updateDeliveryStatus(id, "Rejected");
        redirectAttrs.addFlashAttribute("message", "Order rejected.");
        return "redirect:/restaurant/orders";
    }

    @GetMapping("/order/preparing/{id}")
    public String startPreparing(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        orderService.updateDeliveryStatus(id, "Preparing");
        redirectAttrs.addFlashAttribute("message", "Order is now being prepared.");
        return "redirect:/restaurant/orders";
    }

    @GetMapping("/order/ready/{id}")
    public String markReady(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        orderService.updateDeliveryStatus(id, "Out for Delivery");
        redirectAttrs.addFlashAttribute("message", "Order marked as ready.");
        return "redirect:/restaurant/orders";
    }



}
