package br.ufscar.dc.dsw.game_testing_api.repository;

import br.ufscar.dc.dsw.game_testing_api.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface BugRepository extends JpaRepository<Bug, Long> {
    List<Bug> findBySession_Id(Long sessionId);

    void deleteAllBySession_Id(Long sessionId);
}
