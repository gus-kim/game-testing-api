package br.ufscar.dc.dsw.game_testing_api.repository;

import br.ufscar.dc.dsw.game_testing_api.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
