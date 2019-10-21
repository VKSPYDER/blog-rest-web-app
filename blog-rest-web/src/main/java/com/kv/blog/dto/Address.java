package com.kv.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URI;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private String phone;
    private URI webSite;
    private Geo geo;


    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getzipcode() {
        return zipcode;
    }

    public void setzipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public URI getWebSite() {
        return webSite;
    }

    public void setWebSite(URI webSite) {
        this.webSite = webSite;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getStreet().equals(address.getStreet()) &&
                getSuite().equals(address.getSuite()) &&
                getCity().equals(address.getCity()) &&
                getzipcode().equals(address.getzipcode()) &&
                getPhone().equals(address.getPhone()) &&
                getWebSite().equals(address.getWebSite()) &&
                getGeo().equals(address.getGeo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getSuite(), getCity(), getzipcode(), getPhone(), getWebSite(), getGeo());
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", phone='" + phone + '\'' +
                ", webSite=" + webSite +
                ", geo=" + geo +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Geo {
        private String lat;
        private String lng;

        public Geo() {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Geo)) return false;

            Geo geo = (Geo) o;

            if (getLat() != null ? !getLat().equals(geo.getLat()) : geo.getLat() != null) return false;
            return getLng() != null ? getLng().equals(geo.getLng()) : geo.getLng() == null;
        }

        @Override
        public int hashCode() {
            int result = getLat() != null ? getLat().hashCode() : 0;
            result = 31 * result + (getLng() != null ? getLng().hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Geo{" +
                    "lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }
    }
}
