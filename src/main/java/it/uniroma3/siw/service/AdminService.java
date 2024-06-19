package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.repository.CredentialsRepository;
@Service
public class AdminService {
	@Autowired
	private CredentialsRepository credentialRepository;
	
	public void loadUsers(Model model) {
		model.addAttribute("users",credentialRepository.findAll());
	}

}
