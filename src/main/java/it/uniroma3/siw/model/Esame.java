package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Esame {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime dataDiPrenotazione;
	
	private LocalDate dataEsame;
	
	@OneToOne
	private Paziente paziente;
	
	@OneToOne
	private Medico medico;
	
}
