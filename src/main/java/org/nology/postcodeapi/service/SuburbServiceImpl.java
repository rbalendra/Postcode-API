package org.nology.postcodeapi.service;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.nology.postcodeapi.dto.PostcodeDTO;
import org.nology.postcodeapi.dto.RequestDTO;
import org.nology.postcodeapi.dto.SuburbDTO;
import org.nology.postcodeapi.entity.Postcode;
import org.nology.postcodeapi.entity.State;
import org.nology.postcodeapi.entity.Suburb;
import org.nology.postcodeapi.exception.ResourceNotFoundException;
import org.nology.postcodeapi.repository.PostcodeRepository;
import org.nology.postcodeapi.repository.SuburbRepository;
import org.springframework.stereotype.Service;

@Service
public class SuburbServiceImpl implements SuburbService{

    // ───────────────────────────── Dependency Injection ──────────────────────────────
    private final SuburbRepository suburbRepository; // lets us talk top suburb table in db
    private final PostcodeRepository postcodeRepository; // lets us talk to postcodes table in db
    private final ModelMapper modelMapper; // Helps us copy data between entities -> DTO

    // ────── Constructor So Spring Can Give Us The Above Dependencies Automatically ───────
    public SuburbServiceImpl(SuburbRepository suburbRepository, PostcodeRepository postcodeRepository, ModelMapper modelMapper){
    this.suburbRepository  = suburbRepository;
    this.postcodeRepository = postcodeRepository;
    this.modelMapper = modelMapper;
    }


    //      ╭──────────────────────────────────────────────────────────╮
    //      │                 LIST SUBURBS BY POSTCODE                 │
    //      ╰──────────────────────────────────────────────────────────╯
    @Override
    public List<SuburbDTO>listSuburbsByPostcode(String postcodeCode){
        //  Look in the DB for all suburbs that have the given postcode
        List<Suburb> suburbs = suburbRepository.findByPostcode_Code(postcodeCode);

        // If no suburbs found, throw an error so the user knows
        if (suburbs.isEmpty()) {
            throw new ResourceNotFoundException("No suburbs found for postcode: " + postcodeCode);
        }

        // Convert each suburb entity into suburbDTO (for sending back to client)
        return suburbs.stream()
                .map(suburb -> {
                    // map fields using modelMapper
            SuburbDTO dto = modelMapper.map(suburb, SuburbDTO.class);

            // Add postcode and state manually because modelMapper won't know how to flatten them
            dto.setPostcode(suburb.getPostcode().getCode());
            dto.setState(suburb.getPostcode().getState().name());
            return dto;
        }).collect(Collectors.toList());
    }

    //      ╭──────────────────────────────────────────────────────────╮
    //      │              FIND POSTCODES BY SUBURB NAME               │
    //      ╰──────────────────────────────────────────────────────────╯

    @Override
    public List<PostcodeDTO>findPostcodesBySuburbName(String suburbName){
        // clean the input(remove spaces, handle nulls)
        String cleaned = suburbName== null ? "" : suburbName.trim();

        // Look for suburbs with a name that matches
        List<Suburb> matches = suburbRepository.findByNameIgnoreCase(cleaned);

        // if none found, throw and error
        if(matches.isEmpty()){
            throw new ResourceNotFoundException("No postcode found for suburb " + suburbName);
        }

        // extract each suburb's postcode, remove duplicates, convert to DTO, return the list
        return matches.stream()
                .map(Suburb::getPostcode)
                .distinct()
                .map(pc->modelMapper.map(pc,PostcodeDTO.class))
                .collect(Collectors.toList());
    }


    //      ╭──────────────────────────────────────────────────────────╮
    //      │                   CREATE A NEW SUBURB                    │
    //      ╰──────────────────────────────────────────────────────────╯

    @Override
    @Transactional // this method write to db, so we need read/write mode
    public SuburbDTO createSuburb(RequestDTO request){
        // Get the postcode details from the request
        String code = request.getPostcode().trim();
        State state = request.getState();

        // Try to find an exisiting postcode with the code+state
        // if not found create a new one db
        Postcode postcode = postcodeRepository
                .findByCodeAndState(code, state)
                .orElseGet(()->{
                    Postcode pc = new Postcode();
                    pc.setCode(code);
                    pc.setState(state);
                    return postcodeRepository.save(pc); //save and return new postcode
                });

        //Create a new surburb entity and link it to postcode
        Suburb suburb = new Suburb();
        suburb.setName(request.getSuburbName().trim());
        suburb.setPostcode(postcode);

        //save the suburb to the db
        Suburb saved = suburbRepository.save(suburb);

        //convert saved suburb into a SuburbDTO for the response
        SuburbDTO dto = modelMapper.map(saved,SuburbDTO.class);
        {
            dto.setPostcode(saved.getPostcode().getCode());
            dto.setState(saved.getPostcode().getState().name());
            return dto;
        }


    }


}
