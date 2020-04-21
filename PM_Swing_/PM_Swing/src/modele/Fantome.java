/*


    Fait par ALI MROIVILI Djaloud à 100%

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;
import java.awt.Point;

import java.util.Random;

/**
 *
 * @author freder
 */
public class Fantome extends Entite {

    private Random r = new Random();

    public Fantome(Jeu _jeu) {
        super(_jeu);

    }

     @Override
    public void choixDirection() {
        
        //On récupère la position du Pacman
        Pacman pm = this.jeu.getPacman();
        Point PositonPacman = this.jeu.getMap().get(pm);
        double x_p = PositonPacman.getX();
        double y_p = PositonPacman.getY();
        
        //On récupère la position du Fantome
        Point PositionFantome = this.jeu.getMap().get(this);
        double x_f = PositionFantome.getX();
        double y_f = PositionFantome.getY();
        
        double x_d = x_f - x_p;
        double y_d = y_f - y_p;
        
        int Deplacment;
        
        if((Math.abs(x_d)<Math.abs(y_d)) && x_d!=0 && x_d<0)
            Deplacment=0;
        else if ((Math.abs(x_d)<Math.abs(y_d)) && x_d!=0 && x_d>0)
            Deplacment=1;
        else if ((Math.abs(y_d)<Math.abs(x_d)) && y_d!=0 && y_d<0)
            Deplacment=2;
        else if ((Math.abs(y_d)<Math.abs(x_d)) && y_d!=0 && y_d>0)
            Deplacment=3;
        else if((Math.abs(x_d)<Math.abs(y_d)) && x_d==0 && y_d<0)
            Deplacment=4;
        else if ((Math.abs(x_d)<Math.abs(y_d)) && x_d==0 && y_d>0)
            Deplacment=5;
        else if ((Math.abs(y_d)<Math.abs(x_d)) && y_d==0 && x_d<0)
            Deplacment=6;
        else
            Deplacment=7;
                  
        switch(Deplacment) {
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
    }}
