package com.appRH.appRG.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appRH.appRG.models.Dependentes;
import com.appRH.appRG.models.Funcionario;

public interface DependentesRepository extends CrudRepository<Dependentes, String> {

	Iterable<Dependentes> findByFuncionario(Funcionario funcionario);

	// pensando no método delete
	Dependentes findByCpf(String cpf);
	Dependentes findById(long id);

	// criado para implementar
	List<Dependentes> findByNome(String nome);

}