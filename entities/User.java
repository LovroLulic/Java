package entities;

public class User extends Person{
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
}
