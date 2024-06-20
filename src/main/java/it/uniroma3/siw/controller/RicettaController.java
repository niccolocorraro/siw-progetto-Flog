package it.uniroma3.siw.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class RicettaController {
	@Autowired 
	private RicettaRepository ricettaRepository;
	
	@Autowired 
	private CuocoRepository cuocoRepository;

	@Autowired 
	private RicettaValidator ricettaValidator;

	@Autowired
	private RicettaService ricettaService;
	
	@Autowired
	private CredentialsRepository credentialsRepository;

	  // Directory where profile images will be saved
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/piatti/";

	
	@GetMapping(value="/admin/formNewRicetta")
	public String formNewRicetta(Model model) {
		model.addAttribute("ricetta", new Ricetta());
		return "admin/formNewRicetta.html";
	}
	
	

	@GetMapping(value="/admin/formUpdateRicetta/{id}")
	public String formUpdateRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", ricettaRepository.findById(id).get());
		return "admin/formUpdateRicetta.html";
	}

	@GetMapping(value="/admin/indexRicetta")
	public String indexRicetta() {
		return "admin/indexRicetta.html";
	}
	
	@GetMapping(value="/admin/manageRicette")
	public String manageRicette(Model model) {
		model.addAttribute("ricette", this.ricettaRepository.findAll());
		return "admin/manageRicette.html";
	}
	
	
	@GetMapping(value="/admin/addCuoco/{id}")
	public String addCuoco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuochi", cuocoRepository.findAll());
		model.addAttribute("ricetta", ricettaRepository.findById(id).get());
		return "admin/cuochiToAdd.html";
	}

	@PostMapping("/admin/ricetta")
	public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, Model model) {
		
		this.ricettaValidator.validate(ricetta, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.ricettaRepository.save(ricetta); 
			model.addAttribute("ricetta", ricetta);
			return "ricetta.html";
		} else {
			return "admin/formNewRicetta.html"; 
		}
	}
	
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", this.ricettaRepository.findById(id).get());
		return "ricetta.html";
	}

	@GetMapping("/ricette")
	public String getRicette(Model model) {		
		model.addAttribute("ricette", this.ricettaRepository.findAll());
		
		List<Ricetta> randomRicette = ricettaService.getRandomRicette(4);
	    model.addAttribute("randomRicette", randomRicette);
		return "ricette.html";
	}
	

	@GetMapping("/foundRicette")
    public String searchRicette(@RequestParam("nome") String nome, Model model) {
        List<Ricetta> ricette = ricettaRepository.searchRicetteByNomeContainingIgnoreCase(nome);
        model.addAttribute("ricette", ricette);
        return "foundRicette.html";
    } 
	
	
	 //temporaneo
    @GetMapping("/newRicetta")
	public String newRicetta(Model model) {
    	model.addAttribute("ricetta",new Ricetta());
		return "newRicetta.html";
	
	}
    
    @PostMapping("/newRicetta")
    public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, 
                                     Model model, @AuthenticationPrincipal UserDetails userDetails,Authentication authentication,@RequestParam("file") MultipartFile file){

    	 if (!file.isEmpty()) {
             try {
                 byte[] bytes = file.getBytes();
                 Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                 Files.write(path, bytes);
                 ricetta.setFoto("/images/piatti/" + file.getOriginalFilename() );
             } catch (IOException e) {
                 e.printStackTrace();
                 model.addAttribute("message", "Failed to upload image");
                 return "newRicetta";
             }
         }
    	
    	 String email = authentication.getName();
         Optional<Credentials> c = credentialsRepository.findByUsername(email);
         User u = c.get().getUser();

        this.ricettaValidator.validate(ricetta, bindingResult);
        if (!bindingResult.hasErrors()) {
            Cuoco cuoco = u.getCuoco();
            ricetta.setCuoco(cuoco);
            this.ricettaRepository.save(ricetta);
            cuoco.getRicette().add(ricetta);
            this.cuocoRepository.save(cuoco);
            return "redirect:/";
        } else {
            return "redirect:/mypage";
        }
    }
     
    
    
	
	
	
	
	
}
