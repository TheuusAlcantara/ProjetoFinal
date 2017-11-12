package br.uniceub.saafa.repository;

import br.uniceub.saafa.domain.UBS;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UBS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UBSRepository extends JpaRepository<UBS, Long> {

}
