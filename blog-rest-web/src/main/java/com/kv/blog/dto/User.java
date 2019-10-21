package com.kv.blog.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Long id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private Company company;

    public User() {
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

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getId().equals(user.getId())) return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (!getusername().equals(user.getusername())) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getAddress() != null ? !getAddress().equals(user.getAddress()) : user.getAddress() != null) return false;
        return getCompany() != null ? getCompany().equals(user.getCompany()) : user.getCompany() == null;
    }

    @Override
    public int hashCode() {
        int result = (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getusername() != null ? getusername().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getCompany() != null ? getCompany().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", company=" + company +
                '}';
    }
}
