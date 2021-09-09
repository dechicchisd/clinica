package it.uniroma3.siw.service;

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
}
