package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;
@Service
public class AdminService {
	@Autowired
	private CredentialsRepository credentialRepository;
	
	public List<Credentials> loadUsers() {
		 List<Credentials> users = credentialRepository.findAllByRole("DEFAULT");
	        System.out.println("Number of users found: " + users.size());  // Debug line
	        return users;
	}

}
