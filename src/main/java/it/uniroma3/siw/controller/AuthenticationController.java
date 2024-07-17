package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.service.AdminService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private CredentialsRepository credentialsRepository;

	
    @Autowired
	private UserService userService;
	
    @Autowired
    private CuocoService cuocoService;
    

    @Autowired
    private AdminService adminService;
    
    // Directory where profile images will be saved
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/newCuochi/";

    
    @GetMapping("/myPage")
    public String myPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        Optional<Credentials> c = credentialsRepository.findByUsername(email);
        User u = c.get().getUser();
        model.addAttribute("user", u );
        
      
        switch(c.get().getRole()) {
        case "DEFAULT": 
            return "myPage";
        case "ADMIN":

        	List<Credentials> users = adminService.loadUsers(); 
        	  model.addAttribute("users",users);
        	return "admin";
        	default :
        		return "index";
      }
    }
    
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "register";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "login";
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
				return "index.html";
			}
		}
        return "index.html";
	}
		
  

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 @RequestParam("file") MultipartFile file,
                 Model model) {
		
		 if (!file.isEmpty()) {
             try {
                 byte[] bytes = file.getBytes();
                 Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                 Files.write(path, bytes);
                 user.setFoto("/images/newCuochi/" + file.getOriginalFilename() );
             } catch (IOException e) {
                 e.printStackTrace();
                 model.addAttribute("message", "Failed to upload image");
                 return "register";
             }
         }
		 
		
		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	Cuoco cuoco = new Cuoco();
            // Imposta altri campi di Cuoco se necessario

            cuocoService.saveCuoco(cuoco);
            cuoco.setUser(user);

            user.setCuoco(cuoco);
            
        	userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "login";
        }
        return "register";
    }
}