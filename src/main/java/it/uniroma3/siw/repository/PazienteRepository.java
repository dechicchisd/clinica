package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Paziente;

public interface PazienteRepository extends CrudRepository<Paziente, Long>{

	@Query("SELECT e FROM Esame e WHERE e.paziente.id = ?1")
	List<Esame> esamiByPazienteId(Long pazienteId);

	List<Paziente> findByNomeAndCognome(String nome, String cognome);

}
