package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;

@Controller
public class CuocoController {
	
	@Autowired 
	private CuocoRepository cuocoRepository;

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

	@GetMapping("/cuoco/{id}")
	public String getCuoco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", this.cuocoRepository.findById(id).get());
		return "cuoco.html";
	}

	@GetMapping("/cuochi")
	public String getCuochi(Model model) {
		model.addAttribute("cuochi", this.cuocoRepository.findAll());
		return "cuochi.html";
	} 
	
	@GetMapping("/foundCuochi")
    public String searchCuochi(@RequestParam("name") String name, Model model) {
        List<Cuoco> cuochi = cuocoRepository.searchCuochiByNameContainingIgnoreCase(name);
        model.addAttribute("cuochi", cuochi);
        return "foundCuochi.html";
    }

	
}
