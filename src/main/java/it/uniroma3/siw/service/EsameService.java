package it.uniroma3.siw.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.repository.EsameRepository;

@Service
public class EsameService {
	
	@Autowired
	private EsameRepository esameRepository;
	
	@Transactional
	public Esame inserisci(Esame esame) {
		return (Esame) this.esameRepository.save(esame);
	}

	@Transactional
	public Esame esamePerId(Long id) {
		Optional<Esame> esame = this.esameRepository.findById(id);
		
		if(esame.isPresent())
			return esame.get();
		
		return null;
	}
}
