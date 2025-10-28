package entities;

import java.math.BigDecimal;

public record CijenaKarte(
        BigDecimal cijena,
        BigDecimal studentskaCijena,
        BigDecimal umirovljenickaCijena,
        BigDecimal nocnaCijena,
        BigDecimal studentskaNocna,
        BigDecimal umirovljenickaNocna)

{

    public static boolean jeNocna(String vrijeme){
        String[] podijeljen=vrijeme.split(":");
        int sat=Integer.parseInt(podijeljen[0]);
        int minuta=Integer.parseInt(podijeljen[1]);

        return(sat==23 && minuta>=30) ||
                (sat>=0 && sat<5) ||
                (sat==5 && minuta>=30);
    }


}
