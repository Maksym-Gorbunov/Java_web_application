package microservices.web_application.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Car implements Serializable {
    @JsonProperty("licensenumber")
    private String licensenumber;

    @JsonProperty("make")
    private String make;

    @JsonProperty("color")
    private String color;
    public static final long serialVersionUID = 11L;

    public Car(String licensenumber, String make, String color) {
        this.licensenumber = licensenumber;
        this.make = make;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Car obj2 = (Car) obj;
        return getLicensenumber().equals(obj2.getLicensenumber());
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(getLicensenumber());
    }
    @Override
    public String toString() {
        return "{\n"
                +"\"licensenumber\" : \"" + getLicensenumber() + "\",\n"
                + "\"make\" : \"" + getMake() + "\",\n"
                + "\"color\" : \"" + getColor() + "\",\n"
                + "}";
    }

    // Getters & Setters
    public String getLicensenumber() {
        return licensenumber;
    }
    public void setLicensenumber(String licensenumber) {
        this.licensenumber = licensenumber;
    }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}