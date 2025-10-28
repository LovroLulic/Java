package entities;

public class Tramvaj extends Vehicle implements Elektricni{
    public Tramvaj(String registration, String type, String color, Integer year) {
        super(registration, type, color, year);
    }

    @Override
    public boolean jeTramvaj(){return true;}
    public boolean jeElektricni(){return true;}
    public boolean jeHibridni(){return false;}
    public boolean jePlinski(){return false;}

    @Override
    public void ispis(){
        System.out.println("===TRAMVAJ===");
        System.out.println("Registracija: " + getRegistration());
        System.out.println("Boja: " + getColor());
        System.out.println("Godina: " + getYear());
        System.out.println("Pogon: "+pogon());
    }
}
