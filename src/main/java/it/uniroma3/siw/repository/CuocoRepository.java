package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;


public interface CuocoRepository extends CrudRepository<Cuoco, Long>{

	public boolean existsByNameAndSurname(String name, String surname);

	
	public List<Cuoco> findByName(String name);
	

	//@Query("SELECT g FROM Cuoco g WHERE LOWER (g.name) LIKE LOWER (CONCAT ('%', : name , '%'))")
  	public List<Cuoco> searchCuochiByNameContainingIgnoreCase(String nome);
  	
}
 