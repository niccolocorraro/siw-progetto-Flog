package it.uniroma3.siw.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
    
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/newCuochi/";

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   
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
    public void updateUser(Long id, User newC, String newEmail, String newPassword, String newUsername, MultipartFile file, Model model) {
        User oldC = credentialsRepository.findById(id).get().getUser();
        
        // Aggiorna i dettagli dell'utente
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

        // Aggiorna l'immagine del profilo
        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                oldC.setFoto("/images/newCuochi/" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Failed to upload image");
            }
        }

        // Aggiorna i dettagli delle credenziali
        Credentials credentials = credentialsRepository.findByUser(oldC);
        if (newEmail != null && !newEmail.equals(credentials.getUser().getEmail())) {
            credentials.getUser().setEmail(newEmail);
        }
        if (newUsername != null && !newUsername.equals(credentials.getUsername())) {
            credentials.setUsername(newUsername);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            credentials.setPassword(newPassword); // Assicurati che la password sia crittografata
        }
        credentialsRepository.save(credentials);
        userRepository.save(oldC); // Salva le modifiche apportate all'entità User
    }



}
