package entities;

public interface Elektricni {
    boolean jeElektricni();
    boolean jeHibridni();
    boolean jePlinski();
    boolean jeTramvaj();
    default String pogon(){
        if(jeHibridni()) return "Hibrid";
        if(jePlinski()) return "Plinski";
        return jeElektricni()?"Elektrican":"Dizel";
    }
}
