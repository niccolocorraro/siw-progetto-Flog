package it.uniroma3.siw.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {

	
	@Autowired 
	private RicettaRepository ricettaRepository;
	
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
} 
