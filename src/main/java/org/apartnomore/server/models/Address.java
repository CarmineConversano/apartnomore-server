package org.apartnomore.server.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apartnomore.server.payload.request.AddressRequest;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*ISO 3166-1 alpha-2 code*/
    @NotNull
    @Size(min = 2, max = 2)
    private String country;

    /*Provincia*/
    @NotNull
    @Size(max = 60)
    private String administrativeArea;

    @NotNull
    @Size(max = 100)
    private String locality;

    @Size(max = 100)
    @NotNull
    private String dependentLocality;

    @NotEmpty
    private String addressLine1;

    @NotNull
    private String addressLine2;

    @Size(min = 1, max = 9)
    private String postalCode;

    @NotNull
    private String lat;

    @NotNull
    private String lng;

    @OneToOne(optional = false)
    private User user;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", insertable = true, updatable = false)
    private Date created;


    public Address(AddressRequest address, User user) {
        this.country = address.getCountry();
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.administrativeArea = address.getAdministrativeArea();
        this.dependentLocality = address.getDependentLocality();
        this.locality = address.getLocality();
        this.postalCode = address.getPostalCode();
        this.lat = address.getLat();
        this.lng = address.getLng();
        this.user = user;
    }

    public Address() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDependentLocality() {
        return dependentLocality;
    }

    public void setDependentLocality(String dependentLocality) {
        this.dependentLocality = dependentLocality;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
