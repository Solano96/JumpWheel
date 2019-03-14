package com.jumpwheel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;
import com.badlogic.gdx.utils.Disposable;

public class ClienteTCP implements Disposable{

    private int posicionx;      // Posición x del jugador
    private int posiciony;      // Posición y del jugador

    private int posAdvesariox;  // Posición x del adversario
    private int posAdvesarioy;  // Posición y del adversario

    private Socket client;
    private ClientThread thread;
    private Semaphore sem1, mutex, mutex2;

    public ClienteTCP(){
        this.create();
        sem1 = new Semaphore(1);
        mutex = new Semaphore(1);
        mutex2 = new Semaphore(1);
    }

    // Almacenar la posición del jugador
    public void setPosicion(int posicionx, int posiciony){
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.posicionx = posicionx;
        this.posiciony = posiciony;
        mutex.release();
        sem1.release();  // La hebra cliente ya puede enviar la posición actualizada al servidor
    }

    // Obtener la posición del adversario
    public Pair<Integer, Integer> getPositionAdversario(){
        try {
            mutex2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Pair<Integer, Integer> posicion = new Pair<Integer, Integer>(posAdvesariox, posAdvesarioy);
        mutex2.release();
        return posicion;
    }

    public void create(){
        thread = new ClientThread();
        thread.start();
    }

    @Override
    public void dispose() {
        try{
            thread.join();
        }catch (InterruptedException e){}

    }

    public class ClientThread extends Thread{
        private int posx, posy;

        public ClientThread(){
            posx  = 0;
            posy = 0;
        }

        @Override
        public void run() {

            byte[] buferEnvio = new byte[256];
            byte[] buferRecibido = new byte[256];
            int bytesRecibidos;

            try {
                client = new Socket("192.168.43.30", 8988);
                OutputStream outputStream;
                InputStream inputStream;
                outputStream = client.getOutputStream();
                inputStream = client.getInputStream();

                do {
                    sem1.acquire();             // Adquiere el control de flujo despues de que el player haga setPosicion
                        mutex.acquire();
                            posx = posicionx;
                            posy = posiciony;
                        mutex.release();

                    //  Envio mi posicion al servidor
                    /**************************************************************************************/
                        buferEnvio = (String.valueOf(posx) + ";" + String.valueOf(posy)).getBytes();

                        outputStream.write(buferEnvio, 0, buferEnvio.length);
                        outputStream.flush();

                    /**************************************************************************************/

                    //  Recibo la posicion del adversario
                    /**************************************************************************************/

                        bytesRecibidos = inputStream.read(buferRecibido);
                        String mensajeRecibido = new String(buferRecibido, 0, bytesRecibidos);

                    /**************************************************************************************/

                    // Actualizamos la posición del adversario
                        String[] posAdver = mensajeRecibido.split(";");
                        mutex2.acquire();
                            posAdvesariox = Integer.parseInt(posAdver[0]);
                            posAdvesarioy = Integer.parseInt(posAdver[1]);
                        mutex2.release();

                } while (posx != -1);
                client.close();

            } catch (UnknownHostException e) {
            System.err.println("Error: equipo desconocido.");
            } catch (IOException e) {
                System.err.println("Error: no se pudo establecer la conexión.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}