package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;

/**
 * The UserService handles logic for Users.
 */
@Service
public class UserService {

	@Autowired
	private CredentialsRepository credentialsRepository;
	
    @Autowired
    protected UserRepository userRepository;
    
   
    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
    
    @Transactional
    public List<Credentials> loadUsers() {
		 List<Credentials> users = credentialsRepository.findAllByRole("DEFAULT");
	        System.out.println("Number of users found: " + users.size());  // Debug line
	        return users;
	}

    
    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<User> getAllUsers() {
        List<Credentials> defaultRoleCredentials = credentialsRepository.findAllByRole("DEFAULT");
        List<User> result = new ArrayList<>();

        for (Credentials credentials : defaultRoleCredentials) {
            result.add(credentials.getUser());
        }
        return result;
    }    
    
   

    @Transactional
	public void updateUser(Long id, User newC) {
    	
    	User oldC = (User)credentialsRepository.findById(id).get().getUser();
    	
    	newC.setId(oldC.getId());
    	newC.setEmail(oldC.getEmail());
    	newC.setCuoco(oldC.getCuoco());
    	
    	
    	if (newC.getName() != null && !newC.getName().equals(oldC.getName())) {
            oldC.setName(newC.getName());
        }

        if (newC.getSurname() != null && !newC.getSurname().equals(oldC.getSurname())) {
            oldC.setSurname(newC.getSurname());
        }
        
        if (newC.getBio() != null && !newC.getBio().equals(oldC.getBio())) {
            oldC.setBio(newC.getBio());
        }

        if (newC.getDate() != null && !newC.getDate().equals(oldC.getDate())) {
            oldC.setDate(newC.getDate());
        }
        
        if (newC.getFoto() != null && !newC.getFoto().equals(oldC.getFoto())) {
            oldC.setFoto(newC.getFoto());
        }

        /*
        Cuoco cu = new Cuoco();
        cu.setName(newC.getName());
        cu.setSurname(newC.getSurname());
        cu.setDateOfBirth(newC.getDate());
        cu.setBiografia(newC.getBio());
        cu.setUrlOfPicture(newC.getFoto());
        
       
        
        cuocoService.updateCuoco(oldC.getCuoco().getId(), cu);
        */
        
        Credentials c = credentialsRepository.findByUser(oldC);
        c.setUser(newC);
        credentialsRepository.save(c);
    	
      
}

}
