package org.nology.postcodeapi.repository;

import org.nology.postcodeapi.entity.Postcode;
import org.nology.postcodeapi.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//data access to postcode rows, extending JpaRepo instantly gives: save, findById, findAll
@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, Long> {


    // ──────────────────────────────────── Queries ────────────────────────────────────

    Optional<Postcode> findByCode(String code); // find a postcode entity by its code (eg.3000)

    boolean existsByCode(String code); //check if code exists

    List<Postcode> findByState(State state); //search by state

    Optional<Postcode> findByCodeAndState(String code, State state);

}
