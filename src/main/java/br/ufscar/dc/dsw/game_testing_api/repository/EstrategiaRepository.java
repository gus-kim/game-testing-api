package br.ufscar.dc.dsw.game_testing_api.repository;

import br.ufscar.dc.dsw.game_testing_api.model.Estrategia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstrategiaRepository extends JpaRepository<Estrategia, Long> {
    
    /**
     * Busca estratégias por nome (case insensitive)
     */
    @Query("SELECT e FROM Estrategia e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Estrategia> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    /**
     * Busca estratégia por nome exato (case insensitive)
     */
    @Query("SELECT e FROM Estrategia e WHERE LOWER(e.nome) = LOWER(:nome)")
    Optional<Estrategia> findByNomeIgnoreCase(@Param("nome") String nome);
    
    /**
     * Verifica se existe uma estratégia com o nome especificado (case insensitive)
     */
    @Query("SELECT COUNT(e) > 0 FROM Estrategia e WHERE LOWER(e.nome) = LOWER(:nome)")
    boolean existsByNomeIgnoreCase(@Param("nome") String nome);
    
    /**
     * Verifica se existe uma estratégia com o nome especificado, excluindo um ID específico
     */
    @Query("SELECT COUNT(e) > 0 FROM Estrategia e WHERE LOWER(e.nome) = LOWER(:nome) AND e.id != :id")
    boolean existsByNomeIgnoreCaseAndIdNot(@Param("nome") String nome, @Param("id") Long id);
}
