package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;



@Controller
public class AdminController {

	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
    private CuocoService cuocoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private RicettaRepository ricettaRepository;
	
	@Autowired
	private RicettaService ricettaService;
	
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/newCuochi/";

	

	
	@GetMapping("/editUser/{id}")
	public String editUser(@PathVariable("id") Long id,Model model) {
		
		
		Credentials credentials = credentialsRepository.findAllById(id);
		User user = credentials.getUser();
		
		model.addAttribute("user", user);
		model.addAttribute("credentials", credentials);
		return "editUser";
	}
	
	
 
	@PostMapping("/editUser/{id}")
	public String editUser(@ModelAttribute("user") User c, @PathVariable("id") Long id,
	                       @RequestParam("file") MultipartFile file,
	                       @RequestParam("email") String email,
	                       @RequestParam("username") String username,
	                       @RequestParam("password") String password,
	                       Model model) {
	    userService.updateUser(id, c, email, password, username, file, model);
	    return "redirect:/myPage";
	}

	
	@GetMapping("/editRicetta/{id}")
	public String editRicetta(@PathVariable("id") Long id, Model m) {
		m.addAttribute("ricetta", ricettaRepository.findById(id).get());
		m.addAttribute("id", id);
		return "editRicetta";
	}
	
	@PostMapping("/editRicetta/{id}")
	public String editRicetta(Model m,@Valid @ModelAttribute("ricetta") Ricetta r,  @PathVariable("id") Long id,
			@RequestParam("file") MultipartFile file,Model model,Authentication authentication,@RequestParam Map<String, String> ingredienti) {
		
		String email = authentication.getName();
	    Optional<Credentials> c = credentialsRepository.findByUsername(email);
	    User u = c.get().getUser();
	    

        switch(c.get().getRole()) {
        case "DEFAULT": 
        	if(u.equals(ricettaRepository.findById(id).get().getCuoco().getUser())) {
	    		ricettaService.updateRicetta(id,r,ingredienti);
	    		return "redirect:/myPage";
	    	}
	    	else {
	    		return "redirect:/myPage";
	    	}
        case "ADMIN":

        	ricettaService.updateRicetta(id,r,ingredienti);
			return "redirect:/myPage";
      }
	    
	    return "redirect:/";

		
	}
	
	



	@GetMapping("myPage/delete/{id}") 
	public String deleteUser(@PathVariable("id") Long id){
		credentialsService.deleteCredentials(id); 
		
		return "redirect:/myPage"; 
	}
	
	@GetMapping("/ricetta/{id}/delete")
    public String getRicetta(@PathVariable Long id) {
        ricettaService.deleteRicetta(id);
        return "redirect:/myPage"; 
    }
	
	@GetMapping(value = "/registerUser") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerUser";
	}
	
	@PostMapping(value = { "/registerUser" })
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
                 return "registerUser";
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
            return "redirect:/";
        }
        return "admin";
    }

}



