package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Tipo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String descrizione;

	private String indicatori;

	private String prerequisiti;
	
	private Float prezzo;
	
	@OneToMany(mappedBy="tipo")
	private List<Esame> esamiPrenotati;
	
	
	public String addIndicatore(String indicatore) {
		indicatori = indicatori + ',' + indicatore;
		
		return indicatori;
	}
	
}
