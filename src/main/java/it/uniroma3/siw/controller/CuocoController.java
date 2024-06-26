package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.UserService;

@Controller
public class CuocoController {
	
	@Autowired 
	private CuocoRepository cuocoRepository;

	@Autowired 
	private UserRepository userRepository;

	@Autowired 
	private UserService userService;
	
	/*
	@GetMapping(value="/admin/formNewCuoco")
	public String formNewCuoco(Model model) {
		model.addAttribute("cuoco", new Cuoco());
		return "admin/formNewCuoco.html";
	}
	
	@GetMapping(value="/admin/indexCuoco")
	public String indexCuoco() {
		return "admin/indexCuoco.html";
	}
	
	@PostMapping("/admin/cuoco")
	public String newCuoco(@ModelAttribute("cuoco") Cuoco cuoco, Model model) {
		if (!cuocoRepository.existsByNameAndSurname(cuoco.getName(), cuoco.getSurname())) {
			this.cuocoRepository.save(cuoco); 
			model.addAttribute("cuoco", cuoco);
			return "cuoco.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo cuoco esiste già");
			return "admin/formNewCuoco.html"; 
		}
	}
*/
	@GetMapping("/cuoco/{id}")
	public String getCuoco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", this.userRepository.findById(id).get());
		return "cuoco.html";
	}
	
	@GetMapping("/cuoco2/{id}")
	public String getCuoco2(@PathVariable("id") Long id, Model model) {
		Cuoco c = cuocoRepository.findAllById(id);
		model.addAttribute("cuoco", this.userRepository.findByCuoco(c));
		return "cuoco.html";
	}
	
	

	@GetMapping("/cuochi")
	public String getCuochi(Model model) {
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("cuochi",users);
		return "cuochi.html";
	} 
	
	@GetMapping("/foundCuochi")
    public String searchCuochi(@RequestParam("name") String name, Model model) {
        List<User> cuochi = userRepository.searchCuochiByNameContainingIgnoreCase(name);
        model.addAttribute("cuochi", cuochi);
        return "foundCuochi.html";
    }
	
	

	
}
