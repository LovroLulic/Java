package app;
import entities.*;

import java.math.BigDecimal;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {


    static int isVehicleUsed(Vehicle v, Route[] routes, int brojRuta){
        if(v==null){ return -1; }

        for(int i=0;i<brojRuta;i++){
            Vehicle rvozilo=routes[i].getVehicle();

            if(rvozilo==v) {return i;}

            if(rvozilo!=null && rvozilo.getRegistration().equals(v.getRegistration())){return i;}
        }
        return -1;
    }

    static void dostupnostVozila(Vehicle[] vehicles, int brojVozila, Route[] routes, int brojRuta){
        for(int i=0;i<brojVozila;i++){

            int indexLinije=isVehicleUsed(vehicles[i],routes,brojRuta);

            if(indexLinije!=-1){
                Route r=routes[indexLinije];

                vehicles[i].ispis();
                System.out.println("⚠\uFE0F NEDOSTUPAN - Linija "+r.getPocetnastanica()+" - "
                        +r.getKrajnastanica());
                System.out.println();
            }
            else if(indexLinije==-1){
                vehicles[i].ispis();
                System.out.println("✅ DOSTUPAN");
                System.out.println();
            }
        }
    }

    private static User login(Scanner scanner, User korisnik) {
        System.out.print("Unesite ime i prezime: ");
        String name= scanner.nextLine();
        while(!korisnik.provjeriImePrezime(name)){
            System.out.print("Neispravno uneseno ime i prezime. Pokusajte ponovo: ");
            name= scanner.nextLine();
        }
        System.out.print("Broj godina: ");
        int age= scanner.nextInt();
        while(!korisnik.provjeriGodine(age)){
            System.out.print("Neispravna godina. Pokusajte ponovo: ");
            age= scanner.nextInt();
        }
        scanner.nextLine();
        System.out.print("Unesite username: ");
        String nameID= scanner.nextLine();
        System.out.print("Unesite email adresu: ");
        String email= scanner.nextLine();
        while(!korisnik.provjeriMail(email)){
            System.out.print("Neispravna email adresa. Pokusajte ponovo: ");
            email= scanner.nextLine();
        }
        korisnik =new User(name,age,nameID,email);
        return korisnik;
    }

    private static boolean provjeraAdmin(User korisnik, Scanner scanner) {
        String lozinka;
        if(!korisnik.getNameID().equals("llulic")){
            System.out.println("⚠\uFE0F Greska! Korisnik "+ korisnik.getNameID()+" nije autoriziran za ovu opciju.");
            return true;
        }
        System.out.print("Unesite lozinku za administratora ("+ korisnik.getNameID()+"): ");
        lozinka= scanner.nextLine();
        if(!"admin123".equals(lozinka)){
            System.out.println("⚠\uFE0F Greska! Lozinka nije ispravna.");
            return true;
        }
        System.out.println("✅ Lozinka je ispravna.");
        return false;
    }

    private static int procesDodavanjaLinije(int brojRuta, Route[] routes, Scanner scanner, int brojVozila, Vehicle[] vehicles,CijenaKarte cjenik) {
        if (brojRuta < routes.length) {
            System.out.print("Koliko novih linija zelite dodati? ");
            int brojLinijaZaDodavanje = scanner.nextInt();
            scanner.nextLine();
            if (brojLinijaZaDodavanje > routes.length - brojRuta) {
                System.out.println("⚠\uFE0F Greska! Nemate dovoljno vozila za nove linije.");
                return brojRuta;
            }


            brojRuta = dodavanjeLinija(brojLinijaZaDodavanje, scanner, brojVozila, vehicles, routes, brojRuta,cjenik);
            System.out.println();
        }
        return brojRuta;
    }

    private static int procesDodavanjaVozila(int brojVozila, Vehicle[] vehicles, Scanner scanner, int brojVozilaKopijaPocetno) {
        if (brojVozila < vehicles.length) {
            System.out.print("Koliko novih vozila zelite dodati? ");
            int brojVozilaZaDodavanje = scanner.nextInt();
            scanner.nextLine();
            if (brojVozilaZaDodavanje > vehicles.length - brojVozila) {
                System.out.println("⚠\uFE0F Greska! Nemate dovoljno mjesta za nove vozila.");
                return brojVozila;
            }


            brojVozila = dodavanjeVozila(brojVozilaZaDodavanje, brojVozilaKopijaPocetno, scanner, vehicles, brojVozila);
            System.out.println();
        }
        return brojVozila;
    }

    private static int dodavanjeLinija(int brojLinijaZaDodavanje, Scanner scanner, int brojVozila, Vehicle[] vehicles, Route[] routes, int brojRuta, CijenaKarte cjenik) {
        for(int i = 0; i< brojLinijaZaDodavanje; i++){
            System.out.print("Pocetna stanica: ");
            String pocetnastanica= scanner.nextLine();
            System.out.print("Krajnja stanica: ");
            String krajnjastanica= scanner.nextLine();

            System.out.print("Kilometraza: ");
            BigDecimal kilometers= scanner.nextBigDecimal();
            scanner.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            System.out.print("Datum (dd.MM.yyyy): ");
            LocalDate datum=LocalDate.parse(scanner.nextLine(),formatter);

            System.out.print("Vrijeme polaska: ");
            String vrijemepolaska= scanner.nextLine();

            Vehicle odabranovozilo=null;

            while(odabranovozilo==null){
                System.out.print("Unesite registraciju vozila za taj smjer: ");
                String registracijazasmjer= scanner.nextLine();
                for(int j = 0; j< brojVozila; j++){
                    if(vehicles[j].getRegistration().equalsIgnoreCase(registracijazasmjer)){
                        odabranovozilo= vehicles[j];
                        break;
                    }
                }
                if(odabranovozilo==null){
                    System.out.println("⚠\uFE0F Greska! Vozilo sa registracijom "+registracijazasmjer+" ne postoji.");
                    continue;
                }
            }

            routes[brojRuta]=Route.builder(odabranovozilo,datum)
                    .time(vrijemepolaska)
                    .pocetnastanica(pocetnastanica)
                    .krajnastanica(krajnjastanica)
                    .kilometers(kilometers)
                    .cjenik(cjenik)
                    .build();
            brojRuta++;
            System.out.println("✅ Linija uspjesno dodana!");



        }
        return brojRuta;
    }

    private static int dodavanjeVozila(int brojVozilaZaDodavanje, int brojVozilaKopijaPocetno, Scanner scanner, Vehicle[] vehicles, int brojVozila) {
        for(int i = 1; i< brojVozilaZaDodavanje +1; i++){
            System.out.print("Unesite registraciju "+(brojVozila+1)+". vozila: ");
            String registration= scanner.nextLine();
            System.out.print("Unesite tip vozila (Bus/Tramvaj): ");
            String model= scanner.nextLine();
            while(!model.equals("Bus") && !model.equals("Tramvaj")){
                System.out.print("⚠\uFE0F Greska! Pogresan tip vozila! Unesite tip vozila (Bus/Tramvaj): ");
                model= scanner.nextLine();
            }
            System.out.print("Unesite boju: ");
            String color= scanner.nextLine();
            System.out.print("Unesite godinu proizvodnje: ");
            int year= scanner.nextInt();
            scanner.nextLine();
            if(model.equals("Bus")){
                vehicles[brojVozila]=new Bus(registration,model,color,year);
                brojVozila++;
            }
            else if(model.equals("Tramvaj")){
                vehicles[brojVozila]=new Tramvaj(registration,model,color,year);
                brojVozila++;
            }

        }
        System.out.println("✅ Uspjesno dodano vozilo.");
        return brojVozila;
    }

    private static void ispisiVozila(Vehicle[] vehicles, int brojVozila, Route[] routes, int brojRuta) {
        System.out.println("=== VOZILA ===");
        System.out.println();
        dostupnostVozila(vehicles, brojVozila, routes, brojRuta);
    }

    private static void ispisiLinije(int brojRuta, Route[] routes) {
        System.out.println("=== LINIJE ===");
        System.out.println();
        for(int i = 0; i< brojRuta; i++){
            routes[i].ispis();
            System.out.println();
        }
    }

    private static void pronadiKilometrazu(Scanner scanner, int brojRuta, Route[] routes) {
        int odabir2;
        while(true) {

            System.out.println("1)Najkraca linija\n2)Najduza linija\n3)Izlaz");
            odabir2 = scanner.nextInt();
            if (odabir2==1) {
                BigDecimal najkraca = new BigDecimal(1000);
                int index = -1;
                for (int i = 0; i < brojRuta; i++) {
                    if (routes[i].getKilometers().compareTo(najkraca) < 0) {
                        najkraca = routes[i].getKilometers();
                        index = i;
                    }
                }
                System.out.println();
                System.out.println("Najkraca linija: ");
                System.out.println();
                routes[index].ispis();
                System.out.println();
                continue;
            }

            else if (odabir2==2) {
                BigDecimal najduzna = new BigDecimal(0);
                int index = -1;
                for (int i = 0; i < brojRuta; i++) {
                    if (routes[i].getKilometers().compareTo(najduzna) > 0) {
                        najduzna = routes[i].getKilometers();
                        index = i;
                    }
                }
                System.out.println();
                System.out.println("Najduza linija: ");
                System.out.println();
                routes[index].ispis();
                System.out.println();
                continue;
            }

            else if(odabir2==3){
                break;
            }
        }
    }

    private static void pronadiStanice(Scanner scanner, int brojRuta, Route[] routes) {
        System.out.print("Unesite polaziste: ");
        String polaziste = scanner.nextLine();
        boolean postoji=false;
        for (int i = 0; i < brojRuta; i++) {
            if (polaziste.equalsIgnoreCase(routes[i].getPocetnastanica())) {
                System.out.println();
                routes[i].ispis();
                postoji=true;
                System.out.println();
            }

        }
        if(!postoji){
            System.out.println("⚠\uFE0F Greska! Nema linije koja krece iz "+polaziste);
        }
        return;
    }

    private static void pronadiRegistraciju(Scanner scanner, int brojRuta, Route[] routes) {
        System.out.print("Unesite registraciju: ");
        String registracijavozila = scanner.nextLine();
        boolean postoji=false;
        for (int i = 0; i < brojRuta; i++) {
            if (registracijavozila.equalsIgnoreCase(routes[i].getVehicle().getRegistration())) {
                System.out.println();
                routes[i].ispis();
                postoji=true;
                System.out.println();
            }

        }
        if(!postoji){
            System.out.println("⚠\uFE0F Greska! Vozilo sa registracijom "+registracijavozila+" ne postoji.");
        }
        return;
    }

    private static void pronadiGodinuProizvodnje(Scanner scanner, Vehicle[] vehicles, int brojVozila) {
        int odabir;
        while(true) {
            System.out.println("1)Najnovije vozilo\n2)Najstarije vozilo\n3)Izlaz");
            odabir= scanner.nextInt();
            scanner.nextLine();
            if (odabir == 1) {
                Vehicle najnovijevozilo= vehicles[0];
                for(int i = 0; i< brojVozila; i++){
                    if(vehicles[i].getYear()>najnovijevozilo.getYear()){
                        najnovijevozilo= vehicles[i];
                    }
                }
                System.out.println("===Najnovije vozilo===\n");
                najnovijevozilo.ispis();
                System.out.println("-----------------------------");
                System.out.println();
            }
            else if (odabir == 2) {
                Vehicle najstarijevozilo= vehicles[0];
                for(int i = 0; i< brojVozila; i++){
                    if(vehicles[i].getYear()<najstarijevozilo.getYear()){
                        najstarijevozilo= vehicles[i];
                    }
                }
                System.out.println("===Najstarije vozilo===\n");
                najstarijevozilo.ispis();
                System.out.println("-----------------------------");
                System.out.println();
            }
            else if(odabir==3){
                break;
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

        User korisnik = new User("",0,"","");
        Vehicle[] vehicles=new Vehicle[10];

        vehicles[0]=new Tramvaj("ZG19055A","Tramvaj","Plava",2019);

        vehicles[1]=new Bus("ZG23045B","Bus","Plava",2012);

        vehicles[2]=new Tramvaj("ZG01045C","Tramvaj","Bijela",2023);

        vehicles[3]=new Bus("ZG01045D","Bus","Bijela",2023);

        vehicles[4]=new Bus("ZG01045E","Bus","Crvena",2023);

        vehicles[5]=new Tramvaj("ZG01045F","Tramvaj","Crvena",2023);

        vehicles[6]=new Bus("ZG01045G","Bus","Crvena",2023);

        int brojVozila=7;
        int brojVozilaKopijaPocetno=7;


        Route[] routes=new Route[10];
        CijenaKarte cjenik=new CijenaKarte(new BigDecimal("2.00"),
                new BigDecimal("1.00"), new BigDecimal("1.5"),
                new BigDecimal("4.5"), new BigDecimal("2.5"), new BigDecimal("3"));


        routes[0]=Route.builder(vehicles[0],LocalDate.of(2025,12,23))
                .time("19:04")
                .pocetnastanica("Velika Gorica")
                .krajnastanica("Aerodrom")
                .kilometers(new BigDecimal("6.24"))
                .cjenik(cjenik)
                .build();


        routes[1]=Route.builder(vehicles[2],LocalDate.of(2025,9,12))
                .time("18:45")
                .pocetnastanica("Glavni Kolodvor")
                .krajnastanica("Vrapce")
                .kilometers(new BigDecimal("8.5"))
                .cjenik(cjenik)
                .build();


        routes[2]=Route.builder(vehicles[5],LocalDate.of(2026,1,30))
                .time("09:20")
                .pocetnastanica("Prisavlje")
                .krajnastanica("Mihaljevac")
                .kilometers(new BigDecimal("7.5"))
                .cjenik(cjenik)
                .build();


        int brojRuta=3;
        String lozinka;


        //Korisnik
        korisnik = login(scanner, korisnik);

        System.out.println();
        System.out.println("Dobrodosao "+korisnik.getName()+" ("+korisnik.getNameID()+", "+korisnik.getAge()+")");
        System.out.println();


        do{
            System.out.println("1) Unos vozila i linije");
            System.out.println("2) Pretrazivanje");
            System.out.println("3) Izlaz");

            int odabir=scanner.nextInt();
            scanner.nextLine();

            if(odabir==1){
                if (provjeraAdmin(korisnik, scanner)) continue;

                System.out.println("\n=== UNOS VOZILA I LINIJA ===");


                while(true) {
                    System.out.println("Trenutno stanje vozila: " + brojVozila + "/" + vehicles.length);
                    System.out.println("Trenutno stanje linija: " + brojRuta + "/" + routes.length);
                    System.out.println();
                    System.out.println("1)Unos novog vozila\n2)Unos novih linija\n3)Izlaz");

                    odabir=scanner.nextInt();
                    scanner.nextLine();

                    if (odabir == 1) {
                        brojVozila = procesDodavanjaVozila(brojVozila, vehicles, scanner, brojVozilaKopijaPocetno);
                    }
                    else if (odabir == 2) {
                        brojRuta = procesDodavanjaLinije(brojRuta, routes, scanner, brojVozila, vehicles,cjenik);
                    }
                    else if(odabir==3){
                        break;
                    }
                }
            }

            else if(odabir==2){
                if(brojRuta==0){
                    System.out.println("⚠\uFE0F Nema vozila i linija.");
                    continue;
                }
                while(true) {
                    System.out.println("Pretrazivanje po:\n1)Registracija\n2)Polaziste\n3)Kilometraza\n4)Prikaz linija\n5)Vozila\n6)Godine Proizvodnje\n7)Izlaz");
                    int odabir2;
                    odabir2 = scanner.nextInt();
                    scanner.nextLine();

                    if (odabir2==1) {
                        pronadiRegistraciju(scanner, brojRuta, routes);
                    }

                    else if (odabir2==2) {
                        pronadiStanice(scanner, brojRuta, routes);
                    }
                    else if (odabir2==3) {
                        pronadiKilometrazu(scanner, brojRuta, routes);
                    }
                    else if(odabir2==4){
                        ispisiLinije(brojRuta, routes);
                    }
                    else if(odabir2==5){
                        ispisiVozila(vehicles, brojVozila, routes, brojRuta);
                    }
                    else if(odabir2==6){
                        pronadiGodinuProizvodnje(scanner, vehicles, brojVozila);
                    }
                    else if(odabir2==7){
                        break;
                    }
                }
            }

            else if(odabir==3){
                System.out.println("Hvala na koristenju!");
                break;
            }

        }while(true);

        scanner.close();
    }




}
