package entities;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class Route {
    private Vehicle vehicle;
    private LocalDate date;
    private String time;
    private String pocetnastanica;
    private String krajnastanica;
    private BigDecimal kilometers;

    public Route(Vehicle vehicle, LocalDate date, String time, String pocetnastanica,String krajnastanica, BigDecimal kilometers) {
        this.vehicle = vehicle;
        this.date = date;
        this.time = time;
        this.pocetnastanica = pocetnastanica;
        this.krajnastanica = krajnastanica;
        this.kilometers = kilometers;
    }


    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPocetnastanica(String pocetnastanica) {this.pocetnastanica = pocetnastanica;}

    public void setKrajnastanica(String krajnastanica) {this.krajnastanica = krajnastanica;}

    public void setKilometers(BigDecimal kilometers) { this.kilometers = kilometers; }

    
    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPocetnastanica() {return pocetnastanica;}

    public String getKrajnastanica() {return krajnastanica;}

    public BigDecimal getKilometers() { return kilometers; }

    public void ispis(){
        System.out.println("Smjer " + pocetnastanica+" - "+krajnastanica);
        System.out.println("Datum: "+date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))+ " u "+time+" sati.");
        System.out.println("Udaljenost: "+kilometers+" km.");
        vehicle.ispis();
    }
}
