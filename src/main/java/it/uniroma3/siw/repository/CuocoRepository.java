package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;


public interface CuocoRepository extends CrudRepository<Cuoco, Long>{

	public Cuoco findAllById(Long id);
	
	//public boolean existsByNameAndSurname(String name, String surname);

	
	//public List<Cuoco> findByName(String name);
	
	//public Cuoco findByCuoco(Cuoco cuoco);
	
	//@Query("SELECT g FROM Cuoco g WHERE LOWER (g.name) LIKE LOWER (CONCAT ('%', : name , '%'))")
	//public List<Cuoco> searchCuochiByNameContainingIgnoreCase(String nome);
  	
  	 // Metodo personalizzato per eliminare un Cuoco dato un oggetto Cuoco
    //default void deleteByCuoco(Cuoco cuoco) {
    //    deleteById(cuoco.getId());
    //}
	
	
}
 