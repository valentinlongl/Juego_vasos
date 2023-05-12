import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Interfaz {//esta clase genera e imprime los string necesarios para mostrar el estilo de juego como cajas en consola

    String[] vasoVacioGrafico = new String[]{//array de strings necesarias para mostrar un vaso vacío boca arriba
            "║         ║",
            "╚╗       ╔╝",
            " ╚╗     ╔╝ ",
            "  ║     ║  ",
            "  ╚═════╝  "
    };

    String[] vasoVolteadoGrafico = new String[]{//array de strings necesarias para mostrar un vaso vacío boca abajo
            "  ╔═════╗  ",
            "  ║     ║  ",
            " ╔╝     ╚╗ ",
            "╔╝       ╚╗",
            "║         ║"
    };

    String [] vasoPelotaGrafico = new String[]{//array de strings necesarias para mostrar un vaso con pelota boca arriba
            "║         ║",
            "╚╗       ╔╝",
            " ╚╗     ╔╝ ",
            "  ║  ●  ║  ",
            "  ╚═════╝  "
    };

    public void drawBox(String[] lines){
        /*
        dibuja todos los elementos del array de strings lines como una línea dentro de una caja
        buscar la línea más larga
        */
        int maxLength = 0;
        for (String line:
             lines) {
            if (maxLength < line.length()){
                maxLength = line.length();
            }
        }

        //establecer el ancho de la caja según el largo de las líneas
        String menuLine = "";
        for (int i = 0; i < maxLength + 2; i++) {
            menuLine = menuLine + "═";
        }

        //imprimir la caja en consola
        System.out.println("╔" + menuLine + "╗");
        for (String line:
             lines) {

            int endLength = maxLength - line.length();//calcula el espacio en blanco después de cada línea
            String endSpace = "";
            for (int i = 0; i < endLength; i++) {
                endSpace = endSpace + " ";
            }

            System.out.println("║ " + line + endSpace + " ║");
        }
        System.out.println("╚" + menuLine + "╝");
    }

    public void drawVasos(Vaso[] vasos, boolean[] flipped){
        /*
        este método recibe un array de vasos y un array que indica en que orientación mostrar el vaso
        la función genera a partir del array de vasos y de sus estados todas las líneas necesarias para
        imprimir en consola una caja que contenga una representación de la realidad que se le pasó
        */


        String[] lines = new String[5];//la caja contendrá siempre 5 líneas, ya que es el alto del gráfico de los vasos

        //inicializa las líneas como vacías
        for (int i = 0; i < lines.length; i++) {
            lines[i] = "";
        }


        for (int i = 0; i < lines.length; i++) {//para cada línea

            //noinspection DuplicatedCode
            for (int j = 0; j < vasos.length; j++) {//para cada vaso

                if (flipped[j]) {//si el vaso esta boca arriba

                    if (vasos[j].TienePelota()){//si el vaso tiene pelota
                        lines[i] = lines[i] + vasoPelotaGrafico[i];//agrega a la línea de la caja la primera línea del gráfico del vaso con pelota

                    }else {//si el vaso esta vacío
                        lines[i] = lines[i] + vasoVacioGrafico[i];//agrega a la línea de la caja la primera línea del gráfico del vaso sin pelota
                    }
                    if (!(j == vasos.length -1)){//si no es el ultimo vaso
                        lines[i] = lines[i] + "   ";//agrega un espacio a la línea de la caja para separar los vasos entre sí
                    }

                } else {//si el vaso esta boca abajo

                    lines[i] = lines[i] + vasoVolteadoGrafico[i];//agrega a la línea de la caja la primera línea del gráfico del vaso boca abajo

                    if (!(j == vasos.length -1)){//si es el ultimo vaso
                        lines[i] = lines[i] + "   ";//agrega un espacio a la línea de la caja para separar los vasos entre sí
                    }

                }

            }

        }

        drawBox(lines);//llama al método drawBox y le pasa las líneas para que dibuje los vasos
    }

    public void drawVasos(Vaso[] vasos, boolean[] flipped, String appendix, boolean appendixPossition){
        /*
        este método recibe un array de vasos, un array que indica en que orientación mostrar el vaso,
        un apéndice de texto y la posición de dicho apéndice
        la función genera a partir del array de vasos y de sus estados todas las líneas necesarias para
        imprimir en consola una caja que contenga una representación de la realidad que se le pasó junto con el apéndice
        */

        //su funcionamiento es muy similar al de drawVasos sin apéndice por lo que no se explicará

        String[] lines = new String[6];

        for (int i = 0; i < lines.length; i++) {
            lines[i] = "";
        }

        for (int i = 0; i < lines.length; i++) {

            if (appendixPossition && i == 0){
                lines[0] = appendix;
                continue;
            } else if (!appendixPossition && i == 5) {
                lines[5] = appendix;
                continue;
            }

            //noinspection DuplicatedCode
            for (int j = 0; j < vasos.length; j++) {

                if (flipped[j]) {

                    if (appendixPossition) {

                        if (vasos[j].TienePelota()){
                            lines[i] = lines[i] + vasoPelotaGrafico[i-1];
                        }else {
                            lines[i] = lines[i] + vasoVacioGrafico[i-1];
                        }
                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }

                    } else {

                        if (vasos[j].TienePelota()){
                            lines[i] = lines[i] + vasoPelotaGrafico[i];
                        }else {
                            lines[i] = lines[i] + vasoVacioGrafico[i];
                        }
                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }

                    }

                } else {

                    if (appendixPossition) {
                        lines[i] = lines[i] + vasoVolteadoGrafico[i-1];

                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }
                    } else {
                        lines[i] = lines[i] + vasoVolteadoGrafico[i];

                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }
                    }

                }

            }

        }
        drawBox(lines);
    }

    public void drawVasos(Vaso[] vasos, boolean[] flipped, String appendix1, String appendix2, boolean appendixPossition){
        /*
        este método recibe un array de vasos, un array que indica en que orientación mostrar el vaso,
        dos apéndices de texto y la posición de dichos apéndices
        la función genera a partir del array de vasos y de sus estados todas las líneas necesarias para
        imprimir en consola una caja que contenga una representación de la realidad que se le pasó junto con ambos apéndices
        */

        //su funcionamiento es muy similar al de drawVasos sin apéndice por lo que no se explicará


        String[] lines = new String[7];

        for (int i = 0; i < lines.length; i++) {
            lines[i] = "";
        }

        for (int i = 0; i < lines.length; i++) {

            if (appendixPossition && i == 0){
                lines[0] = appendix1;
                continue;
            } else if (appendixPossition && i == 1) {
                lines[1] = appendix2;
                continue;
            } else if (!appendixPossition && i == 5) {
                lines[5] = appendix1;
                continue;
            } else if (!appendixPossition && i == 6) {
                lines[6] = appendix2;
                continue;
            }

            //noinspection DuplicatedCode
            for (int j = 0; j < vasos.length; j++) {

                if (flipped[j]) {

                    if (appendixPossition) {

                        if (vasos[j].TienePelota()){
                            lines[i] = lines[i] + vasoPelotaGrafico[i-2];
                        }else {
                            lines[i] = lines[i] + vasoVacioGrafico[i-2];
                        }
                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }

                    } else {

                        if (vasos[j].TienePelota()){
                            lines[i] = lines[i] + vasoPelotaGrafico[i];
                        }else {
                            lines[i] = lines[i] + vasoVacioGrafico[i];
                        }
                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }

                    }

                } else {

                    if (appendixPossition) {
                        lines[i] = lines[i] + vasoVolteadoGrafico[i-2];

                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }
                    } else {
                        lines[i] = lines[i] + vasoVolteadoGrafico[i];

                        if (!(j == vasos.length -1)){
                            lines[i] = lines[i] + "   ";
                        }
                    }

                }

            }

        }
        drawBox(lines);
    }

    public void drawMovement(Vaso[] vasos, int fromIndex, int toIndex, boolean[] flipped){
        /*
        este método recibe un array de vasos, un array que indica en que orientación mostrar el vaso,
        y dos valores que indican un movimiento de vasos
        la función genera a partir del array de vasos, de sus estados y de la información del movimiento
        todas las líneas necesarias para imprimir en consola una caja que contenga
        un esquema con flecha del movimiento que se le pasó
        */

        /*
        parte de su funcionamiento es muy similar al de drawVasos sin apéndice por lo que no se explicará
        el método dibuja las flechas sobre los vasos pasándolas como apéndice a drawVasos, esta parte se explicará
         */


        //calcula la distancia (y dirección con el signo del resultado) de índices entre los vasos que se movieron
        int indexDistance = toIndex - fromIndex;

        //calcula los caracteres que debe ocupar la flecha para señalar ambos vasos
        int arrowDisance = 1 + 14 * abs(indexDistance);

        //calcula el espacio que debe haber antes de la flecha para que esta apunte a los vasos correctos
        int aheadSize = ((min(fromIndex, toIndex)) * 14) +5;

        String arrow = "";//inicializa la linea superior de la flecha

        String arrowSecondLine = "";//inicializa la linea inferior de la flecha


        for (int i = 0; i < arrowDisance - 2; i++) {//repetir tantas veces como caracteres que debe ocupar la flecha para señalar ambos vasos -2
            arrow = arrow + "─";//agregar a la línea superior de la flecha un caractér más de distancia
            arrowSecondLine = arrowSecondLine + " ";//agregar a la linea inferior de la flecha un espacio mas para que ambas puntas apunten al vaso correcto
        }

        arrow = "┌" + arrow;//agrega la curva de la flecha al inicio de la linea superior
        arrow = arrow + "┐";//agrega la curva de la flecha al final de la linea superior
        arrowSecondLine = "↓" + arrowSecondLine + "↓";//agrega ambas puntas de la flecha en los extremos de la linea inferior

        //agrega los espacios en blanco necesarios al inicio
        for (int i = 0; i < aheadSize; i++) {
            arrow = " " + arrow;
            arrowSecondLine = " " + arrowSecondLine;
        }


        drawVasos(vasos, flipped, arrow, arrowSecondLine, true);//dibuja los vasos y pasa como apéndice la flecha

    }

}
