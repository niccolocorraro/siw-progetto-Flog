package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ricetta {
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        
        
        @NotBlank
        private String nome; 
		
		private String foto;
		
		@OneToMany(mappedBy="ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
		private List<Ingrediente> ingredienti;
		
		private String descrizione;
        
		@ManyToOne
        private Cuoco cuoco;
		
		//@ManyToMany(mappedBy = "ricettePreferite")
	   // private List<Cuoco> cuochiPreferiti;
	
		private String portata; 
		
		
		public Ricetta() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPortata() {
			return portata;
		}

		public void setPortata(String portata) {
			this.portata = portata;
		}

		public String getNome() {
			return nome; 
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		
		public Cuoco getCuoco() {
			return cuoco;
		}

		public void setCuoco(Cuoco cuoco) {
			this.cuoco = cuoco;
		}

		
		public String getDescrizione() {
			return descrizione;
		}
		


		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cuoco, descrizione, id, nome);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ricetta other = (Ricetta) obj;
			return Objects.equals(cuoco, other.cuoco) && Objects.equals(descrizione, other.descrizione)
					&& Objects.equals(id, other.id) ;
		}

		

		

		public String getFoto() {
			return foto;
		}

		public void setFoto(String foto) {
			this.foto = foto;
		}

		public List<Ingrediente> getIngredienti() {
			return ingredienti;
		}

		public void setIngredienti(List<Ingrediente> ingredienti) {
			this.ingredienti = ingredienti;
		}

		@Override
		public String toString() {
			return "Ricetta [id=" + id + ", nome=" + nome + ", foto=" + foto
					+ ", ingredienti=" + ingredienti + ", descrizione=" + descrizione + ", cuoco=" + cuoco.toString()
					+ ", portata=" + portata + "]";
		}
		
		

		
		
        
 
    }