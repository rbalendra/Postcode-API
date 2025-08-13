package org.nology.postcodeapi.controller;


import org.nology.postcodeapi.dto.SuburbDTO;
import org.nology.postcodeapi.service.SuburbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/postcodes")  // all end points in this class will start with /api/v1/postcodes
public class PostcodeController {

    // ─────────────────────────────── Dependency Injection ────────────────────────────────
    // we use suburbService to handle actual business logic of finiding suburbs by postcode
    private final SuburbService suburbService;


    // ────────────────────────────────── Constructor ──────────────────────────────────
    //Spring will automatically inject an instance of suburbservice

    public PostcodeController(SuburbService suburbService){
        this.suburbService = suburbService;
    }

    //      ╭──────────────────────────────────────────────────────────╮
    //      │         RETRIEVE ALL SUBURBS FOR GIVEN POSTCODE          │
    //      │        Example: GET /api/v1/postcodes/3000/suburbs       │
    //      ╰──────────────────────────────────────────────────────────╯
    @GetMapping("/{code}/suburbs")
    public ResponseEntity<List<SuburbDTO>>getSuburbsByPostCode(@PathVariable String code){
        // call the service layer to fetch suburbs by postcode
        // service will throw ResourceNotFoundException if no suburbs exists for this code
        List<SuburbDTO> suburbs = this.suburbService.listSuburbsByPostcode(code.trim());

        // Return the list inside the HTTP 200 OK response, automatically converted to JSON
        return new ResponseEntity<>(suburbs, HttpStatus.OK);
    }



}
