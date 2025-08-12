package org.nology.postcodeapi.service;


import org.modelmapper.ModelMapper;
import org.nology.postcodeapi.dto.PostcodeDTO;
import org.nology.postcodeapi.entity.Postcode;
import org.nology.postcodeapi.exception.ResourceNotFoundException;
import org.nology.postcodeapi.repository.PostcodeRepository;
import org.springframework.stereotype.Service;

@Service
public class PostcodeServiceImpl {

    // ───────────────────────────── Dependency Injection ──────────────────────────────
    private final PostcodeRepository postcodeRepository;
    private final ModelMapper modelMapper;

    // ───────────── Constructors - Spring Will Inject The Repo And Mapper ─────────────
    public PostcodeServiceImpl(PostcodeRepository postcodeRepository, ModelMapper modelMapper){
        this.postcodeRepository = postcodeRepository;
        this.modelMapper = modelMapper;
    }


    //      ╭──────────────────────────────────────────────────────────╮
    //      │                   GET POSTCODE BY CODE                   │
    //      ╰──────────────────────────────────────────────────────────╯

    public PostcodeDTO getByCode(String code){
        // find the postcode in db by its code (3000)
        // if not found throw an error so the caller knows
        Postcode pc = postcodeRepository.findByCode(code)
                .orElseThrow(()-> new ResourceNotFoundException("Postcode not found: " + code));

        // convert the postcode entity to a postcodeDTO so we can send it back to the caller
        return modelMapper.map(pc, PostcodeDTO.class);
    }


}
