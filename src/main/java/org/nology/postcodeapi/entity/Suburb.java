package org.nology.postcodeapi.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="suburbs", uniqueConstraints = {@UniqueConstraint(name="uk_suburbs_name_postcode", columnNames = {"name", "postcodeID"})})
public class Suburb {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Suburb is required")
    @Column(name = "name", nullable = false, length = 120)
    private String name;


    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="postcodeID", nullable = false, foreignKey = @ForeignKey(name="fk_suburb_postcode"))
    private Postcode postcode;

    // ──────────────────────────── // Getters And Setters ─────────────────────────────


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Postcode getPostcode() {
        return postcode;
    }

    public void setPostcode(Postcode postcode) {
        this.postcode = postcode;
    }
}
