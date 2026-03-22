package com.eccomerce.project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street name must be atleast 5 charcters.")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Building name must be atleast 5 charcters.")
    private String buildingName;

    @NotBlank
    @Size(min = 3, message = "City name must be atleast 3 charcters.")
    private String city;

    @NotBlank
    @Size(min = 2, message = "State name must be atleast 2 charcters.")
    private String state;

    @NotBlank
    @Size(min = 2, message = "Country name must be atleast 2 charcters.")
    private String country;

    @NotBlank
    @Size(min = 6, message = "PinCode must be atleast 6 charcters.")
    private String pinCode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<Users> users = new ArrayList<>();


    public Address(String street, String buildingName, String city, String state, String country, String pinCode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
    }


}
