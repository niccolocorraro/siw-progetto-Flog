package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cuoco {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String name;
	private String surname;
	
	private String dateOfBirth;
	
	private String urlOfPicture;

	private String biografia;

	
	@OneToMany(mappedBy="cuoco")
	private List<Ricetta> ricetteCreate;
	
	public Cuoco(){
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUrlOfPicture() {
		return urlOfPicture;
	}

	public void setUrlOfPicture(String urlOfPicture) {
		this.urlOfPicture = urlOfPicture;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public List<Ricetta> getRicetteCreate() {
		return ricetteCreate;
	}

	public void setRicetteCreate(List<Ricetta> ricetteCreate) {
		this.ricetteCreate = ricetteCreate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, ricetteCreate, id, name, surname, urlOfPicture);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuoco other = (Cuoco) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(ricetteCreate, other.ricetteCreate)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname) && Objects.equals(urlOfPicture, other.urlOfPicture);
	}
	
	}

