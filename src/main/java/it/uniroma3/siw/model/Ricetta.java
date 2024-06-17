package it.uniroma3.siw.model;

import java.util.List;
  import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ricetta {
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @NotBlank
        private String nome; 
		private String copertina;
		
		private List<String> url_images;
		
		private List<String> ingredienti;
        
		private String descrizione;
        
		@ManyToOne
        private Cuoco cuoco;
	
		private String portata;
		
		
		public Ricetta() {
			super();
			// TODO Auto-generated constructor stub
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
		public List<String> getUrl_images() {
			return url_images;
		}

		public void setUrl_images(List<String> url_images) {
			this.url_images = url_images;
		}


		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cuoco, descrizione, id, nome, copertina);
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
					&& Objects.equals(id, other.id) 
					&& Objects.equals(nome, other.nome) && Objects.equals(copertina, other.copertina);
		}

		public List<String> getIngredienti() {
			return ingredienti;
		}

		public void setIngredienti(List<String> ingredienti) {
			this.ingredienti = ingredienti;
		}

		public String getCopertina() {
			return copertina;
		}

		public void setCopertina(String copertina) {
			this.copertina = copertina;
		}

		
		
        
 
    }