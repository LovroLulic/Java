package entities;

import java.util.Random;

public class Bus extends Vehicle implements Elektricni{
    private final int pogon;
    public Bus(String registration, String type, String color, Integer year) {
        super(registration, type, color, year);
        Random random=new Random();
        int rndm=random.nextInt(1,5);  //1-benzinski/dizel, 2-elektrican, 3-hibrid, 4-plinski
        this.pogon=rndm;
    }


    @Override
    public boolean jeElektricni() {
        return pogon==2;
    }
    @Override
    public boolean jeHibridni(){
        return pogon==3;
    }
    @Override
    public boolean jePlinski(){
        return pogon==4;
    }
    @Override
    public boolean jeTramvaj(){return false;}

    @Override
    public void ispis(){
        System.out.println("===AUTOBUS===");
        System.out.println("Registracija: "+getRegistration());
        System.out.println("Boja: "+getColor());
        System.out.println("Godina: "+getYear());
        System.out.println("Pogon: "+pogon());
    }


}
