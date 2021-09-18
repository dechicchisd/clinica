package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Medico;
import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Transactional
	public List<Medico> tutti() {
		return (List<Medico>) this.medicoRepository.findAll();
	}

	@Transactional
	public Medico inserisci(Medico medico) {
		return this.medicoRepository.save(medico);
	}

	@Transactional
	public Medico medicoPerId(Long idMedico) {
		Optional<Medico> medico = this.medicoRepository.findById(idMedico);
		
		if(medico.isPresent()) {
			return medico.get();
		}
		
		return null;
	}

	@Transactional
	public Medico medicoPerNomeCognome(String nome, String cognome) {

		return this.medicoRepository.findByNomeAndCognome(nome, cognome).get(0);
	}

	@Transactional
	public List<Esame> getEsamiPrenotati(Long id) {
		return this.medicoRepository.findEsamiByMedicoId(id);
	}

	public void delete(Medico medico) {
		this.medicoRepository.delete(medico);
	}

	public boolean alreadyExist(Medico medico) {
		List<Medico> medici = this.medicoRepository.findByNomeAndCognome(medico.getNome(), medico.getCognome());
		
		if (medici.size() > 0)
			return true;
		else 
			return false;
	
	}

}
