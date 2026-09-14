package org.example.statify.repository;

import org.example.statify.entity.DBCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository
    extends JpaRepository<DBCountry, Long>, JpaSpecificationExecutor<DBCountry> {

  DBCountry findByName(String name);

  boolean existsByName(String name);
}
