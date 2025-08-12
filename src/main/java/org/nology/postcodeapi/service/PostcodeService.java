package org.nology.postcodeapi.service;

import org.nology.postcodeapi.dto.PostcodeDTO;

public interface PostcodeService {
    PostcodeDTO getByCode(String code);
}
