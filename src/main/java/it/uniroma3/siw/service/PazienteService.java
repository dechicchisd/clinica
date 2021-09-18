package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Medico;
import it.uniroma3.siw.model.Paziente;
import it.uniroma3.siw.repository.PazienteRepository;

@Service
public class PazienteService {

	@Autowired
	private PazienteRepository pazienteRepository;

	@Transactional
	public Paziente pazientePerId(Long idPaziente) {
		
		if(idPaziente != null) {
			Optional<Paziente> paziente = this.pazienteRepository.findById(idPaziente);
		
			if(paziente.isPresent())
				return paziente.get();
		
		}
		
		return null;
	}

	@Transactional
	public Paziente inserisci(Paziente paziente) {
		return this.pazienteRepository.save(paziente);
	}

	@Transactional
	public void delete(Paziente paziente) {
		this.pazienteRepository.delete(paziente);
	}

	@Transactional
	public List<Esame> esamiPerPazienteId(Long pazienteId) {
		return this.pazienteRepository.esamiByPazienteId(pazienteId);
	}

	@Transactional
	public boolean alreadyExist(Paziente paziente) {
		List<Paziente> pazienti = this.pazienteRepository.findByNomeAndCognome(paziente.getNome(), paziente.getCognome());
		
		if(pazienti.size()>0)
			return true;
		
		return false;
	}

	public Paziente pazientePerNomeCognome(String nome, String cognome) {
		
		List<Paziente> pazienti = this.pazienteRepository.findByNomeAndCognome(nome, cognome);
		
		if(pazienti.size()>0)
			return pazienti.get(0);
		
		return null;
	}
}
