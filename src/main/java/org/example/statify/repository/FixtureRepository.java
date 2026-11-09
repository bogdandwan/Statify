package org.example.statify.repository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import org.example.statify.entity.DBFixture;
import org.example.statify.entity.DBVenue;
import org.springframework.data.jpa.repository.*;

public interface FixtureRepository
    extends JpaRepository<DBFixture, Long>, JpaSpecificationExecutor<DBFixture> {

  DBFixture findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);

  @EntityGraph(attributePaths = "scores")
  List<DBFixture> findByDateGreaterThanEqualAndDateLessThanAndStatusShortIn(
      OffsetDateTime from, OffsetDateTime to, Collection<String> statuses);

  @Modifying
  @Query(value = "update DBFixture f set f.venue=?2 where f=?1")
  void updateVenue(DBFixture dBFixture, DBVenue dbVenue);

  List<DBFixture> findAllByApiIdIn(Collection<Integer> apiIds);
}
