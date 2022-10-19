/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7_pcd;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class Despacho {

    boolean profesor_teoria_libre = true;
    boolean profesor_practica1_libre = true;
    boolean profesor_practica2_libre = true;
    private int nPracticas = 0;

    private Lock mutex = new ReentrantLock();
    private Condition practicas = mutex.newCondition();
    private Condition teoria = mutex.newCondition();

    public String atiendeTeoria(int id) {
        mutex.lock();
        String Quien = "";
        try {
            System.out.println("nPracticas:" + nPracticas);
            while (!profesor_practica1_libre && !profesor_practica2_libre && !profesor_teoria_libre || (nPracticas > 0 && !profesor_teoria_libre)) {
                teoria.await();
            }

            if (profesor_teoria_libre) {
                profesor_teoria_libre = false;
                Quien = "t";
            } else if (profesor_practica1_libre) {
                profesor_practica1_libre = false;
                Quien = "p1";
            } else if (profesor_practica2_libre) {
                profesor_practica2_libre = false;
                Quien = "p2";
            }
            
            System.out.println("Soy atendido para la teoria con id[" + id + "] por [" + Quien + "]");
        } catch (Exception e) {
            System.out.println("El hilo ha fallado: " + e.getMessage());
        } finally {
            mutex.unlock();
        }

        return Quien;

    }

    public String atiendePracticas(int id) {
        mutex.lock();
        nPracticas++;
        String Quien = "";
        try {

            while (!profesor_practica1_libre && !profesor_practica2_libre) {
                practicas.await();
            }

            if (profesor_practica1_libre) {
                profesor_practica1_libre = false;
                Quien = "p1";
            } else if (profesor_practica2_libre) {
                profesor_practica2_libre = false;
                Quien = "p2";
            }

            System.out.println("Soy atendido para las practicas con id[" + id + "] por [" + Quien + "]");
        } catch (InterruptedException e) {
            System.out.println("El hilo ha fallado: " + e.getMessage());
        } finally {
            mutex.unlock();
        }

        return Quien;
    }

    public void salePracticas(int id, String Quien) {
        mutex.lock();
        nPracticas--;
        try {
            System.out.println("Salgo de ser atendido por las practicas con id: " + id);
            if ("p1".equals(Quien)) {
                System.out.println("p1 liberado");
                profesor_practica1_libre = true;

            } else if ("p2".equals(Quien)) {
                System.out.println("p2 liberado");
                profesor_practica2_libre = true;
            }
            if(nPracticas > 0){
                practicas.signal();
            } else {
                teoria.signal();
            }
            
        } catch (Exception e) {
        } finally {
            mutex.unlock();
        }

    }

    public void saleTeoria(int id, String Quien) {

        mutex.lock();

        try {
            System.out.println("Salgo de ser atendido por la teoria con id: " + id);
            if ("p1".equals(Quien)) {
                System.out.println("p1 liberado");
                profesor_practica1_libre = true;

            } else if ("p2".equals(Quien)) {
                System.out.println("p2 liberado");
                profesor_practica2_libre = true;

            } else if ("t".equals(Quien)) {
                System.out.println("t liberado");
                profesor_teoria_libre = true;

            }

            teoria.signal();

        } catch (Exception e) {
        } finally {
            mutex.unlock();
        }

    }

}
