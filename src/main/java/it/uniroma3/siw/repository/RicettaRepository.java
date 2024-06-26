package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;

public interface RicettaRepository extends CrudRepository<Ricetta, Long>{

	
	public List<Ricetta> findByCuoco(Cuoco cuoco);	
	
	abstract boolean existsByNome(String nome);	

	abstract boolean existsByCuoco(Cuoco cuoco);
	
    boolean existsByNomeAndCuoco(String nome, Cuoco cuoco);

	
	public List<Ricetta> findAll();
	
    List<Ricetta> findByNome(String nome);

  //@Query("SELECT g FROM Ricetta g WHERE LOWER (g.nome) LIKE LOWER (CONCAT ('%', : nome , '%'))")
  	public List<Ricetta> searchRicetteByNomeContainingIgnoreCase(String nome);
  	
  	
  	  
	

}
 