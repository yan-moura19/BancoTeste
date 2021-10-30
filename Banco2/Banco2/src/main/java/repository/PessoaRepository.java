package repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.banco.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long>{

}
