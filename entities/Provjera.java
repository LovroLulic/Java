package entities;

public sealed interface Provjera permits User {
    boolean provjeriImePrezime(String imeprezime);
    boolean provjeriGodine(int godina);
    boolean provjeriMail(String mail);
}
