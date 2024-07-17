
package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CredentialsRepository;

@Component
public class CredentialsValidator implements Validator {
	@Autowired
	private CredentialsRepository credentialsRepository;

	 @Override
	    public void validate(Object o, Errors errors) {
	        Credentials c = (Credentials) o;
	        if (c != null && credentialsRepository != null && credentialsRepository.existByUsername(c.getUsername())) {
	            errors.rejectValue("nome", "duplicate", "Ricetta già esistente per questo cuoco");
	        }
	    }

	@Override
	public boolean supports(Class<?> aClass) {
		return Ricetta.class.equals(aClass);
	}
	
	
}