/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7_pcd;

import java.util.Random;

/**
 *
 * @author usuario
 */
public class EstudiantePracticas implements Runnable {

    private Despacho mi_Despacho;
    private CanvasGenerador cv;

    public EstudiantePracticas(Despacho mi_Despacho, CanvasGenerador cv) {
        this.mi_Despacho = mi_Despacho;
        this.cv = cv;
    }

    @Override
    public void run() {
        try {
            System.out.println("Soy un Estudiante de Practicas con id: " + Thread.currentThread().getId());
            cv.representaPracticas((int) Thread.currentThread().getId());
            Random aleatorio = new Random();
            Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
            String Quien = mi_Despacho.atiendePracticas((int) Thread.currentThread().getId());

            if (Quien.equals("p1")) {
                cv.insertaProfesorPracticas1((int) Thread.currentThread().getId(), 'p');
            } else if (Quien.equals("p2")) {
                cv.insertaProfesorPracticas2((int) Thread.currentThread().getId(), 'p');
            }

            System.out.println("Quien [" + (int) Thread.currentThread().getId() + "]:" + Quien);
            Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
            mi_Despacho.salePracticas((int) Thread.currentThread().getId(), Quien);
            if (Quien.equals("p1")) {
                cv.borraProfesorPracticas1();
            } else if (Quien.equals("p2")) {
                cv.borraProfesorPracticas2();
            }

        } catch (InterruptedException e) {
            System.out.println("El run falla en la practica: " + e);
        }

    }

}
