package br.uniceub.saafa.repository;

import br.uniceub.saafa.domain.Agendamento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Agendamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
