/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Point;
import static modele.Jeu.SIZE_X;

/**
 *
 * @author thoma
 */
public class SuperGomme extends Entite{
    

    public SuperGomme(Jeu _jeu) {
        super(_jeu);
        d = Direction.droite;

    }
        public void setDirection(Direction _d) {
        d = _d;
    }


    @Override
    public void choixDirection() {

    }


    
}
