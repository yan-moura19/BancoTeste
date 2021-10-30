package repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.banco.Conta;

public interface ContaRepository extends CrudRepository<Conta, Long>{

}
