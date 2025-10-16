package in.sp.main.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String showHomePage(Model model) {
		return "home"; // This is your home.html view
	}

	@GetMapping("/login/restaurant")
	public String redirectToRestaurantLogin() {
		return "redirect:/restaurant/login";
	}

	@GetMapping("/login/customer")
	public String redirectToCustomerLogin() {
		return "redirect:/customer/login";
	}

	@GetMapping("/login/admin")
	public String redirectToAdminLogin() {
		return "redirect:/admin/login";
	}
}
