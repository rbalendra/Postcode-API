package org.nology.postcodeapi.dto;

import org.nology.postcodeapi.entity.State;

public class PostcodeDTO {

    private String code;
    private State state;

    public PostcodeDTO() {}
    public PostcodeDTO(String code, State state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
