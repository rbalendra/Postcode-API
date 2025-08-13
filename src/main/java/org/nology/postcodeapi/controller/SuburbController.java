package org.nology.postcodeapi.controller;


import jakarta.validation.Valid;
import org.nology.postcodeapi.dto.PostcodeDTO;
import org.nology.postcodeapi.dto.RequestDTO;
import org.nology.postcodeapi.dto.SuburbDTO;
import org.nology.postcodeapi.service.SuburbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/suburbs")
public class SuburbController {

    // ───────────────────────────── Dependency Injection ──────────────────────────────
    private final SuburbService surburbService;

    // ───────────────────────────── Constructor Injection ─────────────────────────────
    public SuburbController(SuburbService surburbService) {
        this.surburbService = surburbService;
    }

    //      ╭──────────────────────────────────────────────────────────╮
    //      │        FIND ALL POSTCODES FOR A GIVEN SUBURB NAME        │
    //      ╰──────────────────────────────────────────────────────────╯

    @GetMapping("/{name}/postcodes")
    public ResponseEntity<List<PostcodeDTO>>getPostcodesBySuburbName(@PathVariable String name){
        //Ask the service to find matching postcodes for this suburb name
        List<PostcodeDTO> postcodes = surburbService.findPostcodesBySuburbName(name);
        //Send the result back to the client as JSON with a 200 OK status
        return new ResponseEntity<>(postcodes, HttpStatus.OK);
    }

    //      ╭──────────────────────────────────────────────────────────╮
    //      │        CREATE A NEW SUBURB ENTITY IN THE DATABASE        │
    //      ╰──────────────────────────────────────────────────────────╯
     //      ╭──────────────────────────────────────────────────────────╮
     //      │  "suburbName": "Richmond",                               │
     //      │  "postcode": "3121",                                     │
     //      │  "state": "VIC"                                          │
     //      ╰──────────────────────────────────────────────────────────╯

    @PostMapping
    public ResponseEntity<SuburbDTO>createSuburb(@Valid @RequestBody RequestDTO request){
        //Call the service to create and save the new suburb
        SuburbDTO created = this.surburbService.createSuburb(request);

        //Build a URI from URL for the new resource's location (/api/v1/suburbs/12)
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}") //append /{id} to the current url
                .buildAndExpand(created.getId())// replace {id} with a new suburb's ID
                .toUri(); // convert URL to URI object

        // Return 201
        return ResponseEntity.created(location).body(created);

    }

}
