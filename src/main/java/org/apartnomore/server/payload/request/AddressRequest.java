package org.apartnomore.server.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressRequest {
    /*ISO 3166-1 alpha-2 code*/
    @NotNull
    @Size(min = 2, max = 2)
    private String country;

    /*Provincia*/
    @NotNull
    @Size(min = 1, max = 60)
    private String administrativeArea;

    @NotNull
    @Size(min = 1, max = 100)
    private String locality;

    @Size(max = 100)
    @NotNull
    private String dependentLocality;

    @NotNull
    private String addressLine1;

    @NotNull
    private String addressLine2;

    @Size(max = 9)
    @NotNull
    private String postalCode;

    @NotNull
    private String lat;

    @NotNull
    private String lng;


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
