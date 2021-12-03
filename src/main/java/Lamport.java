import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Algoritmo de Lamport
//Panaderia con N clientes

public class Lamport {
    private static final int N = 5;
    private volatile static int numeros[] = new int[N]; // inicializa a 0
    private volatile static boolean eligiendoNum[] = new boolean[N]; // inicializa a falso

    public static Thread crearHilo(final int i){
        Thread th1 = new Thread(new Runnable(){

            @Override
            public void run() {

                // calcular el numero de turno
                eligiendoNum[i] = true;
                numeros[i] = 1 + maximo(numeros);
                eligiendoNum[i] = false;
                Random rd = new Random();
                //comparar el turno con todos los hilos
                for(int j=0; j<N;j++){
                    while(eligiendoNum[j]); // se espera a que haya elegido

                    while( (numeros[j] != 0) && ( (numeros[j]< numeros[i]) || ( (numeros[j] == numeros[i]) && (j<i) ) ) );
                    /* Se espera si:
                     * -el numero es mayor que el nuestro (i)
                     * -el numero es igual al nuestro pero nuestro indice (i) es mayor
                     *  * la primera parte es para evitarle consultar todo esto si ya es igual a 0
                     */
                }
                System.out.println("(" + i + ")"+rd.nextInt(10 - 0 + 1 )+" Medialunas! ");
                System.out.println("(" + i + ")"+rd.nextInt(10 - 0 + 1 )+" Tortas negras! ");
                System.out.println("(" + i + ")"+rd.nextInt(10 - 0 + 1 ) +" Vigilantes");


                numeros[i]=0;
                System.out.println("(" + i + ")Esperando...");



            }
        },"Hilo"+ i);
        return th1;
    }
    // Fin Thread

    public static int maximo(int[] numeros){ // Calcular maximo
        int maxi = 0;
        for (int i=0;i<numeros.length;i++){
            maxi += numeros[i];
        }
        return maxi;

    }

    public static void main(String[] args) {
        List<Thread> lista = new ArrayList<Thread>();
        for (int i=0;i<N;i++){
            Thread th =crearHilo(i);
            lista.add(th);
        }

        for (int i=0;i<N;i++){
            Thread th=lista.get(i);
            th.start();

        }


    }

}