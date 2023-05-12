import java.util.*;

public class Juego {

    private Scanner scanner;//almacena el scanner que se utilizara para introducir datos dentro de la clase Juego
    private Random generador;//almacena un generador de números aleatorios

    private int cantidadVasos;//almacena la configuración de cantidad de vasos en la partida

    private boolean[] volteados;//almacena el estado de como se tienen que mostrar los vasos (boca abajo o boca arriba)

    private int movimientos;//almacena la configuración de movimientos realizados a los vasos

    private int sleepTime;//almacena la configuración de tiempo de espera entre diálogos

    private String nombre;//almacena el nombre de la computadora

    private Vaso[] vasos;//almacena los objetos Vaso

    private Interfaz interfaz;//almacena el objeto interfaz con el que se dibuja en consola la mayoría de salida del juego

    public Juego(int cantidadVasos, int movimientos, String nombre, Interfaz interfaz, Scanner scanner, int actionsPerSecond){

        generador = new Random();//crea un nuevo objeto Random que generara números aleatorios

        this.cantidadVasos = cantidadVasos;
        this.movimientos = movimientos;
        this.nombre = nombre;
        this.sleepTime = 1000/actionsPerSecond;//el tiempo de espera entre cada diálogo se calcula de esta manera, ya que es medido en milisegundos

        this.interfaz = interfaz;
        this.scanner = scanner;
    }

    public int getCantidadVasos() {
        return cantidadVasos;
    }

    public void setCantidadVasos(int cantidadVasos) {
        this.cantidadVasos = cantidadVasos;
    }

    public int getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(int movimientos) {
        this.movimientos = movimientos;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int actionsPerSecond) {
        this.sleepTime = 1000/actionsPerSecond;
    }

    private void actionWait(){//espera sleepTime mlisegundos y continua la ejecucion
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("Error: error al esperar para la siguiente accion");
        }
    }

    private void generarVasos(){//genera todos los vasos del array vasos, uno de los cuales tiene asignado una pelota
        vasos = new Vaso[cantidadVasos];//cambia el tamaño del array vasos si hace falta
        volteados = new boolean[cantidadVasos];//cambia el tamaño del array volteados si hace falta

        int vasoConPelota = generador.nextInt(cantidadVasos);//decide que vaso tendrá una pelota

        for (int i = 0; i < cantidadVasos; i++) {
            volteados[i] = true;//inicializa todos los vasos como boca arriba
            if (i == vasoConPelota){
                vasos[i] = new Vaso(true);
            }else {
                vasos[i] = new Vaso(false);
            }
        }
    }

    public int[] moverVasos(){//genera un movimiento aleatorio de vasos y cambia sus posiciones en el array vasos
        int from = generador.nextInt(vasos.length);
        int to = from;

        while (from == to){
            to = generador.nextInt(vasos.length);
        }

        Vaso buffer = vasos[to];

        vasos[to] = vasos[from];
        vasos[from] = buffer;

        return new int[]{from, to};
    }

    private void voltearVaso(int i){//establece el estado del vaso i como el opuesto al que tiene (boca arriba = true/boca abajo = false)
        volteados[i] = !volteados[i];
    }

    private void voltearVaso(boolean state){//voltea todos los vasos al estado que se le pide
        for (int i = 0; i < cantidadVasos; i++) {
            volteados[i] = state;
        }
    }

    public void jugar(boolean primeraVez){

        if (primeraVez) {//explicar el juego al jugador si se llama a jugar con el parámetro primeraVez en true
            System.out.println("Hola, soy " + nombre + ".");
            actionWait();
            System.out.println("Vamos a jugar al juego de los vasos.");
            actionWait();
            System.out.println("Te lo explicare rapidamente, primero colocare una pelota en uno de " + cantidadVasos + " vasos y tu veras en cual.");
            actionWait();
            actionWait();
            actionWait();
            System.out.println("Luego moveré los vasos " + movimientos + " veces frente a ti.");
            actionWait();
            actionWait();
            System.out.println("Por ultimo deberás adivinar en que vaso esta la pelota.");
            actionWait();
            actionWait();
            System.out.print("Presiona enter para empezar.");
            scanner.nextLine();
        }

        generarVasos();

        //muestra una caja con vasos indicando donde coloco la pelota
        interfaz.drawVasos(vasos, volteados, "Colocare la pelota aquí.", true);
        actionWait();

        voltearVaso(false);//oculta el contenido de todos los vasos cuando se vuelvan a mostrar

        for (int i = 0; i < movimientos; i++) {//repetir tantas veces como movimientos de vasos haya que realizar

            int [] movement = moverVasos();//guardar los índices de los vasos intercambiados

            interfaz.drawMovement(vasos, movement[0], movement[1], volteados);//muestra una caja con el esquema del movimiento realizado
            actionWait();
            actionWait();
        }



        //muestra una caja en consola preguntando donde se encuentra la pelota
        interfaz.drawBox(new String[]{
                "en que vaso esta la pelota? (1 - " + cantidadVasos + ")"
        });

        int intentos = 0;
        boolean adivinado = false;
        while (!adivinado){
            System.out.print(">");
            int selected = scanner.nextInt();

            if (selected < 1){

                //muestra una caja indicando al jugador que el número seleccionado está fuera del rango
                interfaz.drawBox(new String[]{
                        "¿Que? ¿Como que en el vaso " + selected + "? Responde bien.",
                        "en que vaso esta la pelota? (1 - " + cantidadVasos + ")"
                });

                continue;
            }
            if (selected > cantidadVasos){

                //muestra una caja indicando al jugador que el número seleccionado está fuera del rango
                interfaz.drawBox(new String[]{
                        "Pero si solo tengo " + cantidadVasos + " vasos... Responde bien.",
                        "en que vaso esta la pelota? (1 - " + cantidadVasos + ")"
                });

                continue;
            }

            if (vasos[selected -1].TienePelota()){//si el vaso seleccionado tiene la pelota
                voltearVaso(true);//voltear todos los vasos
                intentos++;

                //muestra una caja con vasos felicitando al jugador por ganar
                interfaz.drawVasos(vasos, volteados, "Felicidades, Has ganado en " + intentos + " intentos", true);

                adivinado = true;

            }else {//si el vaso seleccionado no tiene la pelota

                if (volteados[selected-1]) {//si ya está boca arriba (ya se seleccionó previamente)

                    //muestra una caja con vasos indicando al jugador que ya selecciono ese vaso previamente
                    interfaz.drawVasos(vasos, volteados,
                            "Ya te lo dije, la pelota no esta ahi, hasta puedes ver que el vaso esta vacío",
                            "en que vaso esta la pelota? (1 - " + cantidadVasos + ")", true);

                } else {//si no se ha seleccionado aun
                    voltearVaso(selected -1);//voltea el vaso que seleccionó el jugador
                    intentos++;

                    //muestra una caja con vasos diciéndole al jugador que se equivocó
                    interfaz.drawVasos(vasos, volteados, "No, ahi no estaba la pelota.",
                            "Fijate mas cuidadosamente e intenta de nuevo.", true);
                }
            }
        }

    }

}
