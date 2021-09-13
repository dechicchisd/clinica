package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Paziente;
import it.uniroma3.siw.repository.PazienteRepository;

@Service
public class PazienteService {

	@Autowired
	private PazienteRepository pazienteRepository;

	@Transactional
	public Paziente pazientePerId(Long idPaziente) {
		Optional<Paziente> paziente = this.pazienteRepository.findById(idPaziente);
		
		if(paziente.isPresent())
			return paziente.get();
		
		return null;
	}

	@Transactional
	public Paziente inserisci(Paziente paziente) {
		return this.pazienteRepository.save(paziente);
	}

	@Transactional
	public List<Esame> esamiPerPazienteId(Long pazienteId) {
		return this.pazienteRepository.esamiByPazienteId(pazienteId);
	}
}
