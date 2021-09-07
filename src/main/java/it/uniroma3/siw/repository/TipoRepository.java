package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Tipo;

public interface TipoRepository extends CrudRepository<Tipo, Long>{

	List<Tipo> findByNome(String nome);

}
