package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
                                               JpaSpecificationExecutor<Funcionario> {

    List<Funcionario> findByNome(String nome);

    @Query("SELECT f FROM Funcionario f " +
            "WHERE f.nome = :nome " +
            "AND f.salario >= :salario " +
            "AND f.dataContratacao = :data")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);

    @Query(value = "SELECT * FROM funcionarios f  " +      // se executado sem o value o spring achará que será um jpql e nao uma
                   "WHERE f.data_contratacao >= :data",    // native query, e com o parametro nativeQuery informa ao spring que este comando
                    nativeQuery = true)                    // é uma native query.
    List<Funcionario> findDataContratacaoMaior(LocalDate data);

    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f",
                    nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();     //consigo trabalhar com os atributos que quero


}
