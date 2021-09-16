package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Medico;

public interface MedicoRepository  extends CrudRepository<Medico, Long>{

	public List<Medico> findByNomeAndCognome(String nome, String cognome);

	@Query("SELECT e FROM Esame e WHERE e.medico.id = ?1")
	public List<Esame> findEsamiByMedicoId(Long id);

}
