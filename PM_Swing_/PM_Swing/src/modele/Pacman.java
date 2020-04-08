/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Point;
import java.util.Random;



/**
 *
 * @author fred
 */
public class Pacman extends Entite {


    protected int life = 3;
    
    
    public Point positionPacman()
    {
        return position;
    }

    public Pacman(Jeu _jeu) {
        super(_jeu);
        d = Direction.droite;

    }
    
    public void setDirection(Direction _d) {
        d = _d;
    }

    @Override
    public void choixDirection() {
        
    }

    Point getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Point position(int i, int i0) {
        position.x=i;
        position.y=i0;
        return position;
    }

}
