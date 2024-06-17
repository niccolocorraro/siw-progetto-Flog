package it.uniroma3.siw.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Ricetta;
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
	
	@GetMapping(value="/admin/formNewRicetta")
	public String formNewRicetta(Model model) {
		model.addAttribute("ricetta", new Ricetta());
		return "admin/formNewRicetta.html";
	}
	
	/*
	 @GetMapping("/new")
	    public String showRecipeForm(Model model) {
	        Recipe recipe = new Recipe();
	        recipe.setIngredients(new ArrayList<>()); // Inizializza la lista degli ingredienti
	        model.addAttribute("recipe", recipe);
	        return "recipeForm";
	    }

	    @PostMapping("/save")
	    public String saveRecipe(@ModelAttribute Recipe recipe) {
	        recipeService.saveRecipe(recipe);
	        return "redirect:/recipe/list";
	    }
*/

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
	
	
	
	
	
	@GetMapping("/formSearchRicette")
	public String formSearchRicette() {
		return "formSearchRicette.html";
	}

	@PostMapping("/searchRicette")
	public String searchRicette(Model model, @RequestParam String nomeCuoco) {
		model.addAttribute("ricette", this.ricettaRepository.findByCuoco( cuocoRepository.findByName(nomeCuoco)) );
		return "foundRicette.html";
	}
	
	
	
	
}
