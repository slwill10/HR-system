package com.appRH.appRG.repository;


import org.springframework.data.repository.CrudRepository;

import com.appRH.appRG.models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String>{

	Funcionario findById(long id);

	// busca
	Funcionario findByNome(String nome);

}