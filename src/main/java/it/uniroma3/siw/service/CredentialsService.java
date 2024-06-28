package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CuocoRepository cuocoRepository;
    
    
    @Autowired
    protected RicettaRepository ricettaRepository;
    
    
    @Transactional
    public Credentials getCredentials(Long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credentials getCredentials(String username) {
        Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
    

    @Transactional
    public void deleteCredentials(Long credentialsId) {
        Credentials credentials = credentialsRepository.findById(credentialsId).orElse(null);
        User u = userRepository.findAllById(credentialsId);
        ricettaRepository.deleteAll(u.getCuoco().getRicette());
        cuocoRepository.delete(u.getCuoco());
        
        if(credentials!=null) credentialsRepository.delete(credentials);
        
    }

}
