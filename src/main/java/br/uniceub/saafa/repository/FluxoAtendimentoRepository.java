package br.uniceub.saafa.repository;

import br.uniceub.saafa.domain.FluxoAtendimento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the FluxoAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FluxoAtendimentoRepository extends JpaRepository<FluxoAtendimento, Long> {
    @Query("select distinct fluxo_atendimento from FluxoAtendimento fluxo_atendimento left join fetch fluxo_atendimento.funcionarios")
    List<FluxoAtendimento> findAllWithEagerRelationships();

    @Query("select fluxo_atendimento from FluxoAtendimento fluxo_atendimento left join fetch fluxo_atendimento.funcionarios where fluxo_atendimento.id =:id")
    FluxoAtendimento findOneWithEagerRelationships(@Param("id") Long id);

}
