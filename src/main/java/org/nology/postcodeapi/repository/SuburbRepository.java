package org.nology.postcodeapi.repository;

import org.nology.postcodeapi.entity.Postcode;
import org.nology.postcodeapi.entity.Suburb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuburbRepository extends JpaRepository<Suburb, Long> {

    // ──────────────────────────────────── Queries ────────────────────────────────────

    List<Suburb> findByPostcode_Code(String postcodeCode);


    @Query("SELECT s FROM Suburb s WHERE LOWER(s.name) = LOWER(:name)")
    List<Suburb> findByNameIgnoreCase(@Param("name") String name);
    //find suburbs by name


}
