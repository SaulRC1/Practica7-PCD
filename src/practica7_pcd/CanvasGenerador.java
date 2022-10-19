/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7_pcd;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class CanvasGenerador extends Canvas {
    private ArrayList<String> Cola_de_Espera;

    private String profesorPracticas1;
    private String profesorPracticas2;
    private String profesorTeoria;

    public CanvasGenerador() {
        super();
        Cola_de_Espera = new ArrayList<>();

        profesorPracticas1 = null;
        profesorPracticas2 = null;
        profesorTeoria = null;
        setBackground(Color.DARK_GRAY);
        this.setSize(1280, 768);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Image offscreen = createImage(this.getWidth(), this.getHeight());
        Graphics bg = offscreen.getGraphics();
        int separacion = 0;

        bg.setColor(Color.white);
        Font f1 = new Font("Courier", Font.BOLD, 30);
        Font f2 = new Font("Courier", Font.BOLD, 15);
        bg.setFont(f1);
        bg.drawString("Profesor Practicas [1]", 150, 80);
        bg.drawString("Profesor Practicas[2]", 150, 180);
        bg.drawString("Profesor Teoria", 150, 280);
        bg.fillRect(50, 50, 50, 50);
        bg.fillRect(50, 150, 50, 50);
        bg.fillRect(50, 250, 50, 50);

        for (int i = 0; i < 20; i++) {
            bg.fillRect(separacion + 50, 400, 50, 50);
            separacion += 60;
        }
        
        
        /*profesorPracticas1 = "P[1]";
        profesorPracticas2 = "P[2]";
        profesorTeoria = "T[1]";*/
        
        
        bg.setFont(f2);
        bg.setColor(Color.black);
        separacion = 0;
        for (int i = 0; i < Cola_de_Espera.size(); i++) {
            bg.drawString(Cola_de_Espera.get(i), separacion + 60, 420);
            separacion += 60;
        }
        
        if(profesorPracticas1 != null){
            bg.drawString(profesorPracticas1, 65, 80);
        }
        
        if(profesorPracticas2 != null){
            bg.drawString(profesorPracticas2, 65, 180);
        }
        
        if(profesorTeoria != null){
            bg.drawString(profesorTeoria, 65, 275);
        }
        
        bg.setColor(Color.white);
        bg.setFont(f1);
        bg.drawString("Cola de Espera", 400, 500);

        g.drawImage(offscreen, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public synchronized void representaPracticas(int idPracticas) {
        Cola_de_Espera.add("P[" + idPracticas + "]");
        repaint();
    }

    public synchronized void representaTeoria(int idTeoria) {
        Cola_de_Espera.add("T[" + idTeoria + "]");
        repaint();
    }

    /*public void borraEspera(){
        Cola_de_Espera.remove(0);
    }*/
    public synchronized void insertaProfesorPracticas1(int id, char tipo) {

        if (tipo == 't') {
            profesorPracticas1 = "T[" + id + "]";
            Cola_de_Espera.remove("T[" + id + "]");
        } else {
            profesorPracticas1 = "P[" + id + "]";
            Cola_de_Espera.remove("P[" + id + "]");
        }
        
        repaint();

    }
    
    public synchronized void insertaProfesorPracticas2(int id, char tipo) {

        if (tipo == 't') {
            profesorPracticas2 = "T[" + id + "]";
            Cola_de_Espera.remove("T[" + id + "]");
        } else {
            profesorPracticas2 = "P[" + id + "]";
            Cola_de_Espera.remove("P[" + id + "]");
        }
        
        repaint();

    }
    
    public synchronized void borraProfesorPracticas1(){
        profesorPracticas1 = null;
        repaint();
    }
    
    public synchronized void borraProfesorPracticas2(){
        profesorPracticas2 = null;
        repaint();
    }
    
    public synchronized void borraProfesorTeoria(){
        profesorTeoria = null;
        repaint();
    }

    public synchronized void insertaTeoria(int id) {
        profesorTeoria = "T[" + id + "]";
        Cola_de_Espera.remove("T[" + id + "]");
        repaint();
    }
    
}
