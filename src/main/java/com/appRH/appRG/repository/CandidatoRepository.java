package com.appRH.appRG.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appRH.appRG.models.Candidato;
import com.appRH.appRG.models.Vaga;

public interface CandidatoRepository extends CrudRepository<Candidato, String>{
	Iterable<Candidato> findByVaga(Vaga vaga);	
	Candidato findByRg(String rg);
	Candidato findById(long id);
	List<Candidato> findBynomeCandidato(String nomeCandidato);
}
