package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
  	private UserService userService;
    
    @GetMapping("/login")
	public String getLogin(Model model) {
		return "login.html";
	
	}
    
    //temporaneo
    @GetMapping("/newRicetta")
	public String newRicetta(Model model) {
		return "newRicetta.html";
	
	}
    
    @GetMapping("/registration")
	public String getRegistration(Model model) {
		return "registration.html";
	
	}
    
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        Credentials credentials = new Credentials();
        credentials.setUsername(user.getEmail());
        credentials.setPassword(passwordEncoder.encode(user.getPassword()));
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setUser(user);
        
        credentialsService.saveCredentials(credentials);
        
        return "redirect:/login";
    }

    
}
