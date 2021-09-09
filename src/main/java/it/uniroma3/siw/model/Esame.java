package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Esame {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime dataPrenotazione;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEsame;
	
	@ManyToOne
	private Tipo tipo;
	
	@OneToOne
	private Paziente paziente;
	
	@OneToOne
	private Medico medico;
	
}
