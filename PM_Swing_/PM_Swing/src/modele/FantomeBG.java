/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Point;
import java.util.Random;
import java.util.HashMap;
import java.lang.Math;
/**
 *
 * @author freder
 */
public class FantomeBG extends Entite {

    private Random r = new Random();

    public FantomeBG(Jeu _jeu) {
        super(_jeu);

    }


     @Override
    public void choixDirection() {
        
        // développer une stratégie plus détaillée (utiliser regarderDansLaDirection(Entité, Direction) , ajouter murs, etc.)
        /*switch (r.nextInt(4)) {
            case 0:
                d = Direction.droite;
                break;
            case 1:
                d = Direction.bas;
                break;
            
            case 2:
                d = Direction.haut;
                break;
            
            case 3:
                d = Direction.gauche;
                break;
        }*/
        Pacman pm = this.jeu.getPacman();
        Point posPac = this.jeu.getMap().get(pm);
        double x_p = posPac.getX();
        double y_p = posPac.getY();
         
        Point posFan = this.jeu.getMap().get(this);
        double x_f = posFan.getX();
        double y_f = posFan.getY();
        
        double x_d = x_f - x_p;
        double y_d = y_f - y_p;
        
        int choixDir;
        
        if((Math.abs(x_d)<Math.abs(y_d)) && x_d!=0 && x_d<0)
            choixDir=0;
        else if ((Math.abs(x_d)<Math.abs(y_d)) && x_d!=0 && x_d>0)
            choixDir=1;
        else if ((Math.abs(y_d)<Math.abs(x_d)) && y_d!=0 && y_d<0)
            choixDir=2;
        else if ((Math.abs(y_d)<Math.abs(x_d)) && y_d!=0 && y_d>0)
            choixDir=3;
        else if((Math.abs(x_d)<Math.abs(y_d)) && x_d==0 && y_d<0)
            choixDir=4;
        else if ((Math.abs(x_d)<Math.abs(y_d)) && x_d==0 && y_d>0)
            choixDir=5;
        else if ((Math.abs(y_d)<Math.abs(x_d)) && y_d==0 && x_d<0)
            choixDir=6;
        else/* ((Math.abs(y_d)<Math.abs(x_d)) && y_d==0 && x_d>0)*/
            choixDir=7;
            
        
        
        switch(choixDir) {
            case 0:
                d = Direction.droite;
                break;
            case 1:
                d = Direction.gauche;
                break;
            case 2:
                d = Direction.haut;
                break;
            case 3:
                d= Direction.bas;
                break;
            case 4:
                d=Direction.bas;
                break;
            case 5:
                d=Direction.haut;
                break;
            case 6:
                d=Direction.droite;
                break;
            case 7:
                d=Direction.gauche;
                break;
            default:
                switch (r.nextInt(4)) {
                    case 0:
                        d = Direction.droite;
                        break;
                    case 1:
                        d = Direction.bas;
                        break;

                    case 2:
                        d = Direction.haut;
                        break;

                    case 3:
                        d = Direction.gauche;
                        break;
                }
        }
    }
}
