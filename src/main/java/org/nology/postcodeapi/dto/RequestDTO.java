package org.nology.postcodeapi.dto;

// ────────── What Clients Posts When They Want To Add Suburb+postcode Combo ───────────

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.nology.postcodeapi.entity.State;

public class RequestDTO {

    @NotBlank
    private String suburbName;

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$", message = "AU postcodes are 4 digits (e.g., 3000)")
    private String postcode;

    @NotNull
    private State state;


    public String getSuburbName() {
        return suburbName;
    }

    public void setSuburbName(String suburbName) {
        this.suburbName = suburbName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
