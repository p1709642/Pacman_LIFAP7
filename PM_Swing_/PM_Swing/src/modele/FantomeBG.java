/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Random;

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
        switch (r.nextInt(2)) {
            case 0:
                d = Direction.droite;
                break;
            case 1:
                d = Direction.haut;
                break;
            case 2:
                d = Direction.gauche;
                break;
            case 3:
                d = Direction.haut;
                break;
        }
    }
}
