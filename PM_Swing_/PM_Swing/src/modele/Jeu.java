/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;
import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Pac_Gommes;

/** La classe Jeu a deux fonctions 
 *  (1) Gérer les aspects du jeu : condition de défaite, victoire, nombre de vies
 *  (2) Gérer les coordonnées des entités du monde : déplacements, collisions, perception des entités, ... 
 *
 * @author freder
 */
public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 15;
    public static final int SIZE_Y = 15;

    private Pacman pm;
    private Fantome f;
    private Pac_Gommes gomme;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées
    
    private int NbVie = 3;
    // TODO : ajouter les murs, couloir, PacGums, et adapter l'ensemble des fonctions (prévoir le raffraichissement également du côté de la vue)
    
    
    public Jeu() {
        
        
        initialisationDesEntites();
    }
    
    public Entite[][] getGrille() {
        return grilleEntites;
    }
    
    public Pacman getPacman() {
        return pm;
    }
    
    private void initialisationDesEntites() {
        
        pm = new Pacman(this);
        grilleEntites[4][1] = pm;
        map.put(pm, new Point(4,1));
    

        f = new Fantome(this);
        grilleEntites[1][1] = f;
        map.put(f, new Point(1, 1));
        
        afficheMurs();
        InitGomme();       
    }
    
    //public Point LastPoint();
    
    public void InitGomme()
    {
        int nbGomme=0;
       gomme= new Pac_Gommes(this);
        for(int i=0; i<SIZE_X;i++)
        {
          for(int j=0; j<SIZE_X;j++)
          {
              if(grilleEntites[i][j]==null)
              {
                  grilleEntites[i][j]=gomme;
                  map.put(gomme, new Point(i,j));
                  nbGomme++;
              }             
          }
        }
        //System.out.print(nbGomme);
    }
    
    
    

    
    
    public void afficheMurs()
    {
        Murs m;
        m = new Murs(this);
        for (int i=0; i< SIZE_X;i++)
        {
        grilleEntites[0][i] = m;
        map.put(m, new Point(0,i));
        grilleEntites[i][0] = m;
        map.put(m, new Point(i,0));
        grilleEntites[i][SIZE_X-1] = m;
        map.put(m, new Point(i,SIZE_X-1));
        grilleEntites[SIZE_X-1][i] = m;
        map.put(m, new Point(SIZE_X-1,i));
        }
        for (int j=2; j< 10-2;j++)
        {
        grilleEntites[j][2] = m;
        map.put(m, new Point(j,2));
        grilleEntites[5][j] = m;
        map.put(m, new Point(5,j));
        grilleEntites[4][j] = m;
        map.put(m, new Point(4,j));
        grilleEntites[j][7] = m;
        map.put(m, new Point(j,7));

        }
       grilleEntites[2][4] = m;
       map.put(m, new Point(2,4));
       grilleEntites[2][5] = m;
       map.put(m, new Point(2,5));
       grilleEntites[7][4] = m;
       map.put(m, new Point(7,4));
       grilleEntites[7][5] = m;
       map.put(m, new Point(7,5));
        
        
    }
    
    
    
    
    /** Permet a une entité  de percevoir sont environnement proche et de définir sa strétégie de déplacement 
     * (fonctionalité utilisée dans choixDirection() de Fantôme)
     */
    public Object regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    /** Si le déclacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        
        boolean retour;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);
       
        if (Cadre(pCible) && (objetALaPosition(pCible)instanceof Pac_Gommes || objetALaPosition(pCible)==null) && objetALaPosition(pCourant)instanceof Pacman) { // a adapter (collisions murs, etc.)
            //if ((map.get(pm)) == map.get(gomme))
            {
                Point tampon =pCible;
                grilleEntites[tampon.x][tampon.y]=null;
                //System.out.print(map.get(pm));
                //System.out.print(map.get(f));
                deplacerEntite(pCourant, pCible, e);
            
            }
            retour = true;
        }
        
        else if (Cadre(pCible) && objetALaPosition(pCible)==null && (objetALaPosition(pCible)instanceof Pac_Gommes || objetALaPosition(pCible)==null) && objetALaPosition(pCourant)instanceof Fantome) {
            deplacerEntite(pCourant, pCible, e);
            retour = true;
        }
        else if(Cadre(pCible) && (objetALaPosition(pCible)instanceof Pac_Gommes || objetALaPosition(pCible)==null) && objetALaPosition(pCourant)instanceof Fantome)
        {
            deplacerEntiteFantome(pCourant, pCible, e);
            retour = true;
        }
        
        else if ((objetALaPosition(pCourant)instanceof Pacman && objetALaPosition(pCible)instanceof Fantome) || (objetALaPosition(pCible)instanceof Pacman && objetALaPosition(pCourant)instanceof Fantome))
        {
          //  do
        //{
            System.out.print(NbVie);
            NbVie--;
            
            
            pCourant = map.get(e);
            
            pCible = calculerPointCible(pCourant, d);
            if(NbVie>0)
            {
             deplacerEntiteColision(pCourant, pCible, e/*,d*/);
            }
            return false;
             //deplacerEntiteColision(pCourant, pCible, e/*,d*/);

        //}while(NbVie!=0);
         //deplacerEntite(pCourant, pCible, e);

         //   retour = true;
        }
      
        else {
            retour = false;
        }        
    
        return retour;
    }
    
    
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }
    
    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }
    private void deplacerEntiteFantome(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = gomme;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }
       private void deplacerEntiteColision(Point pCourant, Point pCible, Entite e/*, Direction d*/) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = null;
        map.put(e, pCible);
            grilleEntites[2][1] = pm;
            map.put(pm, new Point(2,1));
            
            grilleEntites[10][1] = f;
            map.put(f, new Point(10,1));
        
       // deplacerEntite(e,d);
    } 
    
    
    

    private boolean Cadre (Point p) {
        return  p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }



    private Object objetALaPosition(Point p) {
        Object retour = null;
        
        if (Cadre(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }
    
    /**
     * Un processus est créé et lancé, celui-ci execute la fonction run()
     */
    public void start() {

        new Thread(this).start();

    }

    @Override
    public void run() {

        while (true) {
            
            for (Entite e : map.keySet()) { // déclenchement de l'activité des entités, map.keySet() correspond à la liste des entités
                e.run(); 
            }

            setChanged();
            notifyObservers(); // notification de l'observer pour le raffraichisssement graphique

            try {
                Thread.sleep(500); // pause de 0.5s
            } catch (InterruptedException ex) {
                Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
