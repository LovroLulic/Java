package entities;

public class Vehicle {
    private String registration;
    private String type;
    private String color;
    private Integer year;

    public Vehicle(String registration, String type, String color, Integer year) {
        this.registration = registration;
        this.type = type;
        this.color = color;
        this.year = year;
    }

    public String getRegistration() {
        return registration;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public Integer getYear() {
        return year;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void ispis(){
        System.out.println("Registracija: " + registration + ", Model: "
                + type + ", Boja: " + color + ", Godina proizvodnje: " + year);

        //Nije bitno jel preko gettera ili direktno jer se
        //i dalje nalazimo u istoj klasi.
    }
}
