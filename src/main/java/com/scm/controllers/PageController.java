package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.Messagetype;
import com.scm.services.ContactService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@Autowired
	private ContactService contactService;

	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}

	@RequestMapping("/home")
	public String home(Model model) {
		System.out.println("Home page handler");

		// sending data to view
		model.addAttribute("name", "substring attribute");
		model.addAttribute("UserName", "Mohammad Shahbaz");
		model.addAttribute("Github Repository", "https://github.com/shahbaz786929");
		return "home";
	}

	// about
	@RequestMapping("/about")
	public String aboutPage() {
		System.out.println("About page loading");
		return "about";
	}

	// services
	@RequestMapping("/services")
	public String servicesPage() {
		System.out.println("services page loading");
		return "services";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	// This is showing login page

	@GetMapping("/login")
	public String login() {
		return "login";
	}

// 	@GetMapping("/user/dashboard")
// public String dashboard(Model model, Principal principal) {

//     String email = principal.getName();

//     User user = userService.getUserByEmail(email);

//     // TOTAL CONTACTS
//     int totalContacts = contactService.countByUser(user);

//     // FAVORITES
//     int favoriteContacts = contactService.countFavorite(user);

//     // RECENT CONTACTS (latest 5)
//     List<Contact> recentContacts = contactService.getRecentContacts(user);

//     model.addAttribute("loggedInUser", user);
//     model.addAttribute("totalContacts", totalContacts);
//     model.addAttribute("favoriteContacts", favoriteContacts);
//     model.addAttribute("recentContacts", recentContacts);

//     return "user/dashboard";
// }

	// registration page
	@GetMapping("/register")
	public String register(Model model) {
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "register";
	}

	// processing register

	@RequestMapping(value = "/do-register", method = RequestMethod.POST)
	public String processRegister(
			@Valid @ModelAttribute UserForm userForm,
			BindingResult rBindingResult,
			@RequestParam("profilePic") MultipartFile file,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		System.out.println("processing registration");

		if (rBindingResult.hasErrors()) {
			System.out.println("VALIDATION ERROR");
			System.out.println(rBindingResult);
			return "register";
		}

		// CHECK DUPLICATE EMAIL
		User existingUser = userService.getUserByEmail(userForm.getEmail());

		if (existingUser != null) {

			Message message = Message.builder()
					.content("Email already registered!")
					.type(Messagetype.red)
					.build();

			redirectAttributes.addFlashAttribute("message", message);

			return "redirect:/register";
		}

		String imageName = "default.png";

		try {

			if (!file.isEmpty()) {

				String uploadDir = System.getProperty("user.dir") + "/uploads/";

				File uploadFolder = new File(uploadDir);

				if (!uploadFolder.exists()) {
					uploadFolder.mkdirs();
				}

				String originalFileName = file.getOriginalFilename();
				String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

				File saveFile = new File(uploadDir + uniqueFileName);

				file.transferTo(saveFile);

				imageName = uniqueFileName;

				System.out.println("Saved at: " + saveFile.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// save user
			User user = new User();
			user.setName(userForm.getName());
			user.setEmail(userForm.getEmail());
			user.setPassword(userForm.getPassword());
			user.setAbout(userForm.getAbout());
			user.setPhoneNumber(userForm.getPhoneNumber());
			user.setProfilePic(imageName);
			user.setAddress(userForm.getAddress());

			userService.saveUser(user);

			Message message = Message.builder()
					.content("Registration Successful")
					.type(Messagetype.green)
					.build();
			System.out.println("REGISTER METHOD CALLED");
			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {

			e.printStackTrace();

			Message message = Message.builder()
					.content("Something went wrong!")
					.type(Messagetype.red)
					.build();

			redirectAttributes.addFlashAttribute("message", message);
		}

		return "redirect:/register";
	}
}
