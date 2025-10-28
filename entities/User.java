package entities;

public final class User extends Person implements Provjera {
    private String nameID;
    private String email;

    public User(String name, int age, String nameID, String email) {
        super(name, age);
        this.nameID = nameID;
        this.email = email;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameID() {
        return nameID;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public void ispis(){
        System.out.println("Ime: " + getName() + ", Godine: " + getAge() +
                ", Username: "+nameID+", Email: "+email+".");
    }

    @Override
    public boolean provjeriImePrezime(String name) {
        if(name.equals("")){
            return false;
        }
        return true;
    }

    @Override
    public boolean provjeriGodine(int godina) {
        if(godina <100 && godina>10){
            return true;
        }
        return false;
    }

    @Override
    public boolean provjeriMail(String email) {
        if(!email.contains("@")){
            return false;
        }
        return true;
    }
}
