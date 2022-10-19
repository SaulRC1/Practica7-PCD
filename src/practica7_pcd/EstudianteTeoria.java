/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7_pcd;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class EstudianteTeoria extends Thread {
    
    
    private Despacho mi_Despacho;
    private CanvasGenerador cv;
    
    public EstudianteTeoria(Despacho D, CanvasGenerador cv){
        mi_Despacho = D;
        this.cv = cv;
    }
    
    @Override
    public void run(){
        try {
            
            System.out.println("Soy un Estudiante de Teoria con id: " + this.getId());
            cv.representaTeoria((int) this.getId());
            Random aleatorio = new Random();
            Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
            String Quien = mi_Despacho.atiendeTeoria((int) this.getId());
            
            if(Quien.equals("p1")){
                cv.insertaProfesorPracticas1((int) this.getId(), 't');
            } else if(Quien.equals("p2")){
                cv.insertaProfesorPracticas2((int) this.getId(), 't');
            } else if(Quien.equals("t")){
                cv.insertaTeoria((int) this.getId());
            }
            
            System.out.println("Quien [" + (int) this.getId() + "]:" + Quien);
            Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
            mi_Despacho.saleTeoria((int) this.getId(), Quien);
            if(Quien.equals("p1")){
                cv.borraProfesorPracticas1();
            } else if(Quien.equals("p2")){
                cv.borraProfesorPracticas2();
            } else if(Quien.equals("t")){
                cv.borraProfesorTeoria();
            }
            
        } catch (InterruptedException e) {       
            System.out.println("El run falla en la teoria" + e.getMessage());
        }
    }
    
    
}
