package it.uniroma3.siw.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class RicettaService {

	
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
	
	public void updateRicetta(Long id, @Valid Ricetta newRicetta) {
		
		Ricetta oldRicetta = ricettaRepository.findById(id).get();
	    
		newRicetta.setCuoco(oldRicetta.getCuoco());
	    
	    if (newRicetta.getNome() != null && !newRicetta.getNome().equals(oldRicetta.getNome())) {
            oldRicetta.setNome(newRicetta.getNome());
        }
	    
	    if (newRicetta.getPortata()!= null && !newRicetta.getPortata().equals(oldRicetta.getPortata())) {
            oldRicetta.setPortata(newRicetta.getPortata());
        }
	     
	    if (newRicetta.getDescrizione() != null && !newRicetta.getDescrizione().equals(oldRicetta.getDescrizione())) {
            oldRicetta.setDescrizione(newRicetta.getDescrizione());
        }
	    
	    if (newRicetta.getCopertina()!= null && !newRicetta.getCopertina().equals(oldRicetta.getCopertina())) {
            oldRicetta.setCopertina(newRicetta.getCopertina());
        }
	    
	    

	    ricettaRepository.save(newRicetta);
	    
		
	}
	
	
	@Transactional
	public void deleteRicetta(Long id) {
		
		Ricetta ricetta = ricettaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ricetta ID:" + id));
		ingredienteRepository.deleteAll(ricetta.getIngredienti());
		ricettaRepository.delete(ricetta);
		
	}
} 
