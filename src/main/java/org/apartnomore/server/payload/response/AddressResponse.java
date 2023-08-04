package org.apartnomore.server.payload.response;

import org.apartnomore.server.payload.request.AddressRequest;

public class AddressResponse {
    private Long id;
    private String country;
    private String administrativeArea;
    private String locality;
    private String dependentLocality;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;

    public AddressResponse() {

    }

    public AddressResponse(AddressRequest address) {
        this.country = address.getCountry();
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.administrativeArea = address.getAdministrativeArea();
        this.dependentLocality = address.getDependentLocality();
        this.locality = address.getLocality();
        this.postalCode = address.getPostalCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
