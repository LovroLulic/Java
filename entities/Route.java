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
    private CijenaKarte cjenik;

    private Route(Vehicle vehicle, LocalDate date, String time, String pocetnastanica,String krajnastanica, BigDecimal kilometers, CijenaKarte cjenik) {
        this.vehicle = vehicle;
        this.date = date;
        this.time = time;
        this.pocetnastanica = pocetnastanica;
        this.krajnastanica = krajnastanica;
        this.kilometers = kilometers;
        this.cjenik=cjenik;
    }

    public static Builder builder(Vehicle vehicle, LocalDate date){
        return new Builder(vehicle,date);
    }

    public static class Builder{
        private final Vehicle vehicle;
        private final LocalDate date;
        private String time="00:00";
        private String pocetnastanica="Nepoznata";
        private String krajnastanica="Nepoznata";
        private BigDecimal kilometers=BigDecimal.ZERO;
        private CijenaKarte cjenik;

        private Builder(Vehicle vehicle, LocalDate date){
            this.vehicle=vehicle;
            this.date=date;
        }

        public Builder time(String time){
            this.time=time;
            return this;
        }

        public Builder pocetnastanica(String pocetnastanica){
            this.pocetnastanica=pocetnastanica;
            return this;
        }

        public Builder krajnastanica(String krajnastanica){
            this.krajnastanica=krajnastanica;
            return this;
        }

        public Builder kilometers(BigDecimal kilometers){
            this.kilometers=kilometers;
            return this;
        }

        public Builder cjenik(CijenaKarte cjenik){
            this.cjenik=cjenik;
            return this;
        }
        public Route build(){
            return new Route(vehicle,date,time,pocetnastanica,krajnastanica,kilometers,cjenik);
        }
    }
    
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
        System.out.println("----------------------------------");
        System.out.println("Smjer " + pocetnastanica+" - "+krajnastanica);
        System.out.println("Datum: "+date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))+ " u "+time+" sati.");
        System.out.println("Udaljenost: "+kilometers+" km.");
        System.out.println();
        boolean nocna=CijenaKarte.jeNocna(time);
        if(nocna){
            System.out.println("⚠\uFE0F NOCNA VOZNJA (23:30 - 05:30)");
            System.out.println("Cijena: "+cjenik.nocnaCijena()+"€");
            System.out.println("(Studentski popust: "+cjenik.studentskaNocna()+"€, umirovljenicki popust: "+cjenik.umirovljenickaNocna()+"€)");
            System.out.println();
        }
        else{
            System.out.println("Cijena: "+cjenik.cijena()+"€");
            System.out.println("(Studentski popust: "+cjenik.studentskaCijena()+"€, umirovljenicki popust: "+cjenik.umirovljenickaCijena()+"€)");
            System.out.println();
        }
        vehicle.ispis();

        System.out.println("----------------------------------");
    }
}
