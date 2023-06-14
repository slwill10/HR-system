package com.appRH.appRG.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appRH.appRG.models.Vaga;

public interface VagaRepository extends CrudRepository<Vaga, String>{
	Vaga findByCodigo(long codigo); 
	List<Vaga> findByNome (String nome);
}
