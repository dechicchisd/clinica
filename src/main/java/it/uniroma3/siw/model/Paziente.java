package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Paziente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	@OneToOne(cascade=CascadeType.ALL)
	private User utente;
	
	@OneToMany(mappedBy="paziente")
	private List<Esame> esamiPrenotati;
	
	public Paziente() {
		this.esamiPrenotati = new ArrayList<Esame>();
	}
}
