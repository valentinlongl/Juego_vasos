import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        //se crea una instancia de la clase Interfaz que se va a encargar de dibujar en consola la mayoría de la salida del juego
        Interfaz interfaz = new Interfaz();

        //se crea el Scanner que se utilizara dentro de la clase Juego
        Scanner scannerInterno = new Scanner(System.in);

        //se crea el Scanner que se utilizara fuera de la clase Juego
        Scanner scanner = new Scanner(System.in);

        //se crea una instancia de la clase Juego encargado de la mayoría de la lógica del juego
        Juego juego = new Juego(3, 5, "José", interfaz, scannerInterno,1);


        boolean jugando = true;
        boolean primeraVez = true;
        while (jugando){
            juego.jugar(primeraVez);
            primeraVez = false;

            //muestra una caja que hace de menú para el jugador
            interfaz.drawBox(new String[]{
                    "Quieres jugar nuevamente? (si - no - ajustes)",
                    "utiliza ajustes para cambiar la cantidad de vasos o movimientos y volver a jugar"
            });

            System.out.print(">");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("s") || input.equals("si") || input.equals("y") || input.equals("yes")){//si se selecciona seguir jugando
                continue;
            }

            if (input.equals("ajustes") || input.equals("setting")){

                interfaz.drawBox(new String[]{//si se selecciona ajustes
                        ("Cambiar cantidad de vasos (entero mayor a 1) - actual: " + juego.getCantidadVasos())
                });

                System.out.print(">");
                input = scanner.nextLine();

                try {
                    if (Integer.valueOf(input) > 1) {
                        juego.setCantidadVasos(Integer.valueOf(input));
                    } else {
                        throw new Exception("Cantidad de vasos debe ser mayor a 1");
                    }
                }catch (Exception e){
                    interfaz.drawBox(new String[]{
                            "Valor introducido erróneamente, revirtiendo al anterior."
                    });
                }

                interfaz.drawBox(new String[]{
                        ("Cambiar cantidad de movimientos (entero mayor a 0) - actual: " + juego.getMovimientos())
                });

                System.out.print(">");
                input = scanner.nextLine();

                try {
                    if (Integer.valueOf(input) > 0) {
                        juego.setMovimientos(Integer.valueOf(input));
                    } else {
                        throw new Exception("Cantidad de movimientos debe ser mayor a 0");
                    }
                }catch (Exception e){
                    interfaz.drawBox(new String[]{
                            "Valor introducido erróneamente, revirtiendo al anterior."
                    });
                }

                interfaz.drawBox(new String[]{
                        ("Cambiar velocidad de juego (entero mayor a 0) - actual: aprox " + (1000/ juego.getSleepTime()))
                });

                System.out.print(">");
                input = scanner.nextLine();

                try {
                    if (Integer.valueOf(input) > 0) {
                        juego.setSleepTime(Integer.valueOf(input));
                    }else {
                        throw new Exception("Velocidad de juego debe ser mayor a 0");
                    }
                }catch (Exception e){
                    interfaz.drawBox(new String[]{
                            "Valor introducido erróneamente, revirtiendo al anterior."
                    });
                }
                continue;
            }else {
                jugando = false;
            }

        }
    }
}
