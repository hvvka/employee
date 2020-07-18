package com.github.hvvka.employeeservice.domain;

import com.github.hvvka.employeeservice.domain.enumeration.AddressType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
//    @Size(min = 2, max = 2)
//    @Column(name = "country", length = 2, nullable = false)
//    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    //    @NotNull
//    @Size(max = 50)
//    @Column(name = "city", length = 50, nullable = false)
//    private String city;
//
//    @NotNull
//    @Size(max = 8)
//    @Column(name = "postal_code", length = 8, nullable = false)
//    private String postalCode;
//
//    @Size(max = 50)
//    @Column(name = "street", length = 50)
//    private String street;
//
//    @NotNull
//    @Size(max = 10)
//    @Column(name = "house_number", length = 10, nullable = false)
//    private String houseNumber;
//
//    @Size(max = 10)
//    @Column(name = "apartment_number", length = 10)
//    private String apartmentNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressType=" + addressType +
                '}';
    }
}
