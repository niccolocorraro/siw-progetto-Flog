package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private CredentialsService credentialsService;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
  	private UserService userService;
    
    
    
    //temporaneo
    @GetMapping("/newRicetta")
	public String newRicetta(Model model) {
		return "newRicetta.html";
	
	}
    
    /*
    @GetMapping("/registration")
	public String getRegistration(Model model) {
		return "registration.html";
	
	}
    */
    @GetMapping("/login")
	public String getLogin(Model model) {
		return "login.html";
	
	}
    
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		System.out.println("peni1");
        return "registration";
    }
    
    @GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/indexAdmin.html"; 
			}
		}
        return "index.html";
	}
    
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin.html";
        }
        return "index.html";
    }
    
    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
    	
    	System.out.println("peni2");
    	System.out.println(userBindingResult.toString());
    	

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	System.out.println("peni3");
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "login";
        }
        return "redirect:/index.html";
    }
/*
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
    }*/

    
}
