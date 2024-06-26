package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
    public User findAllById(Long id);

	
	public User findByCuoco(Cuoco c);
	
	//@Query("SELECT g FROM Cuoco g WHERE LOWER (g.name) LIKE LOWER (CONCAT ('%', : name , '%'))")
	public List<User> searchCuochiByNameContainingIgnoreCase(String nome);
	  	
}