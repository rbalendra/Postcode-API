package org.nology.postcodeapi.service;

import org.nology.postcodeapi.dto.PostcodeDTO;
import org.nology.postcodeapi.dto.RequestDTO;
import org.nology.postcodeapi.dto.SuburbDTO;

import java.util.List;

public interface SuburbService {


    // ────────────── List All Suburbs That Share The Given Postcode Code ──────────────
    List<SuburbDTO> listSuburbsByPostcode(String postcodeCode);

    // ────────────────── Given The Suburb Name, Return Its Postcode. ──────────────────
    List<PostcodeDTO>findPostcodesBySuburbName(String suburbName);

    // ───────────────── Create A New Suburb Tied To A Postcode/state. ─────────────────
    SuburbDTO createSuburb(RequestDTO request);


}
