package it.uniroma3.siw.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class RicettaService {

	  // Directory where profile images will be saved
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/editedPiatti/";

	
	
	@Autowired 
	private RicettaRepository ricettaRepository;
	
	@Autowired 
	private IngredienteRepository ingredienteRepository;
	
	public Ricetta findById(Long id) {
		return ricettaRepository.findById(id).get();
	} 
	
	public List<Ricetta> findAll() {
		return ricettaRepository.findAll();
	}
	 
	public void save(Ricetta ricetta) { 
		
		ricettaRepository.save(ricetta);
	} 
	
	public List<Ricetta> getRandomRicette(int count) {
	    List<Ricetta> allRicette = ricettaRepository.findAll();
	    Collections.shuffle(allRicette);
        return allRicette.subList(0, Math.min(count, allRicette.size()));
	}
	
	public void updateRicetta(Long id, @Valid Ricetta newRicetta,@RequestParam("file") MultipartFile file, Model model) {
		
		Ricetta oldRicetta = ricettaRepository.findById(id).get();
		
			   
	    
	    if (newRicetta.getNome() != null && !newRicetta.getNome().equals(oldRicetta.getNome())) {
            oldRicetta.setNome(newRicetta.getNome());
        }
	    
	    if (newRicetta.getPortata()!= null && !newRicetta.getPortata().equals(oldRicetta.getPortata())) {
            oldRicetta.setPortata(newRicetta.getPortata());
        }
	     
	    if (newRicetta.getDescrizione() != null && !newRicetta.getDescrizione().equals(oldRicetta.getDescrizione())) {
            oldRicetta.setDescrizione(newRicetta.getDescrizione());
        }
	    

	    if (file != null && !file.isEmpty()) {
	        try {
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);
	            oldRicetta.setFoto("/images/editedPiatti/" + file.getOriginalFilename());
	        } catch (IOException e) {
	            e.printStackTrace();
	            model.addAttribute("message", "Failed to upload image");
	        }
	    }
	    
	    

	    ricettaRepository.save(oldRicetta);
	    
		
	}
	
	
	@Transactional
	public void deleteRicetta(Long id) {
		
		Ricetta ricetta = ricettaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ricetta ID:" + id));
		ingredienteRepository.deleteAll(ricetta.getIngredienti());
		ricettaRepository.delete(ricetta);
		
	}
} 
