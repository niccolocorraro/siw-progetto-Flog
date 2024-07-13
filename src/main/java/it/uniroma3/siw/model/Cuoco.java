package it.uniroma3.siw.model;

import java.util.Iterator;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cuoco {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@OneToMany(mappedBy="cuoco")
	private List<Ricetta> ricette;
	
    @OneToMany
	private List<Ricetta> ricettePreferite;
	
	@OneToOne
	private User user;
	
	public Cuoco(){
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<Ricetta> getRicette() {
		return ricette;
	}

	public void setRicette(List<Ricetta> ricetteCreate) {
		this.ricette = ricetteCreate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Cuoco [id=" + id + ", ricette=" + ricette + ", user=" + user + "]";
	}

	public List<Ricetta> getRicettePreferite() {
		return ricettePreferite;
	}

	public void setRicettePreferite(List<Ricetta> ricettePreferite) {
		this.ricettePreferite = ricettePreferite;
	}
	
	 public void rimuoviRicettaPreferita(Ricetta ricetta) {
	        Iterator<Ricetta> iterator = ricettePreferite.iterator();
	        while (iterator.hasNext()) {
	            Ricetta r = iterator.next();
	            if (r.equals(ricetta)) {
	                iterator.remove();
	                break;
	            }
	        }
	    }
	
	

	}

