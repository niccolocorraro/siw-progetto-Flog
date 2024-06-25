package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
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

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }
    
    @Transactional
	public void updateUser(Long id, User newC) {
    	
    	User oldC = (User)credentialsRepository.findById(id).get().getUser();
    	
    	newC.setId(oldC.getId());

    	
    	
    	if (newC.getName() != null && !newC.getName().equals(oldC.getName())) {
            oldC.setName(newC.getName());
        }

        if (newC.getSurname() != null && !newC.getSurname().equals(oldC.getSurname())) {
            oldC.setSurname(newC.getSurname());
        }

		/*
		 * if (newC.getSite_url() != null &&
		 * !newC.getSite_url().equals(oldC.getSite_url())) {
		 * oldC.setSite_url(newC.getSite_url()); }
		 * 
		 * if (newC.getDescription() != null &&
		 * !newC.getDescription().equals(oldC.getDescription())) {
		 * oldC.setDescription(newC.getDescription()); }
		 * 
		 * if (newC.getLogo() != null && !newC.getLogo().equals(oldC.getLogo())) {
		 * oldC.setLogo(newC.getLogo()); }
		 */
        
        Credentials c = credentialsRepository.findByUser(oldC);
        c.setUser(newC);
        credentialsRepository.save(c);
    	
    	
      
}

}
