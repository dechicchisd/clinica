package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.repository.TipoRepository;

@Service
public class TipoService {
	
	@Autowired
	private TipoRepository tipoRepository;
	
	
	@Transactional
	public Tipo inserisci(Tipo tipo) {
		return tipoRepository.save(tipo);
	}
	
	@Transactional
	public List<Tipo> tutti(){
		return (List<Tipo>) tipoRepository.findAll();
	}

	@Transactional
	public Tipo tipoPerId(Long id){
		Optional<Tipo> optional = tipoRepository.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}
	
	@Transactional
	public boolean alreadyExists(Tipo tipo) {
		List<Tipo> attori = this.tipoRepository.findByNome(tipo.getNome());
		if (attori.size() > 0)
			return true;
		else 
			return false;
	}

	public void deleteTipo(Tipo tipo) {

		this.tipoRepository.delete(tipo);
	}
	
	

}
