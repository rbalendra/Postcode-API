package org.nology.postcodeapi.dto;

public class SuburbDTO {

    private Long id;
    private String name;
    private String postcode;
    private String state;

    public SuburbDTO(){
    }

    public SuburbDTO(Long id, String name, String postcode, String state) {
        this.id = id;
        this.name = name;
        this.postcode = postcode;
        this.state = state;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



}
