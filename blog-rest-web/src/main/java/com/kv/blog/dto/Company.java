package com.kv.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;


    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (!getName().equals(company.getName())) return false;
        if (getCatchPhrase() != null ? !getCatchPhrase().equals(company.getCatchPhrase()) : company.getCatchPhrase() != null)
            return false;
        return getBs() != null ? getBs().equals(company.getBs()) : company.getBs() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getCatchPhrase() != null ? getCatchPhrase().hashCode() : 0);
        result = 31 * result + (getBs() != null ? getBs().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", catchPhrase='" + catchPhrase + '\'' +
                ", bs='" + bs + '\'' +
                '}';
    }
}
