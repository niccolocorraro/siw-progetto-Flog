package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class CuocoService {

	
	@Autowired
	private CuocoRepository cuocoRepository;
	
	
	public Cuoco findById(Long id) {
		return cuocoRepository.findById(id).get();
	}
	
	public Iterable<Cuoco> findAll() {
		return cuocoRepository.findAll();
		
	}
	
	 public void saveCuoco(Cuoco cuoco) {
	        cuocoRepository.save(cuoco);
	    }
	 /*
	 @Transactional
		public void updateCuoco(Long id, Cuoco newC) {
	    	
	    	Cuoco oldC = (Cuoco)cuocoRepository.findById(id).get();
	    	
	    	newC.setRicette(oldC.getRicette());
	    	
	    	
	    	if (newC.getName() != null && !newC.getName().equals(oldC.getName())) {
	            oldC.setName(newC.getName());
	        }

	        if (newC.getSurname() != null && !newC.getSurname().equals(oldC.getSurname())) {
	            oldC.setSurname(newC.getSurname());
	        }
	        
	        if (newC.getDateOfBirth() != null && !newC.getDateOfBirth().equals(oldC.getDateOfBirth())) {
	            oldC.setDateOfBirth(newC.getDateOfBirth());
	        }
	        
	        if (newC.getUrlOfPicture() != null && !newC.getUrlOfPicture().equals(oldC.getUrlOfPicture())) {
	            oldC.setUrlOfPicture(newC.getUrlOfPicture());
	        }
	        
	        if (newC.getBiografia() != null && !newC.getBiografia().equals(oldC.getBiografia())) {
	            oldC.setBiografia(newC.getBiografia());
	        }
	       

			
	        
	        User c = userRepository.findByCuoco(oldC);
	        c.setCuoco(newC);
	        userRepository.save(c);
	        cuocoRepository.delete(oldC);
	    	
	    	
	      
	}
	 */
}
