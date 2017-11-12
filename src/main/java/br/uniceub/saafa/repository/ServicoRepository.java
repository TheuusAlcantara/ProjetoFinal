package br.uniceub.saafa.repository;

import br.uniceub.saafa.domain.Servico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Servico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
