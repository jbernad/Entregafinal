package com.example.jorgebr.pglfinal.Validaciones;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by jorge on 10/03/2018.
 */

public class Validaciones {
    /***** VALIDAR EMAIL *****/
    public static boolean validaEmail(String email) {
        //Expresión regular para validar un Email
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*********FUNCIÓN QUE COMPRUEBA SI UN TELEFONO ES VÁLIDO******************/
    public static boolean Comprueba_Tfno (String entrada){
        if (Pattern.matches("[0-9]{9}",entrada))  //cumple patrón
            return true;
        else
            return false;
    }

    /*DNI */
    public static boolean Comprueba_dni (String entrada){
        if (Pattern.matches("[0-9]{8}[A-Za-z]{1}",entrada))  //cumple patrón
            return true;
        else
            return false;
    }



    /*FECHA */
    public static boolean Compruebafecha (String entrada){
        if (Pattern.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])",entrada))  //cumple patrón
            return true;
        else
            return false;
    }

    /*******************FUNCIÓN PARA EVITAR CADENA SÓLO DE ESPACIOS*************************************************/
    public static boolean sonEspacios(String cad)
    {
        for(int i = 0; i<cad.length(); i++) {
            if (cad.charAt(i) != ' ')  //buscamos caracter distinto de espacio
                return false;
        }
        return true;  //sólo espacios
    }
}
