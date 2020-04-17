/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.ImageView;
import modele.Pac_Gommes;
//import javafx.scene.image.ImageView;


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
    private FantomeHD fHD;
    private FantomeBD fBD;
    private FantomeBG fBG;
    private Pac_Gommes gomme;
    private Point pacman;
    private Point Ghost;
    private Point GhostHD;
    private Point GhostBD;
    private Point GhostBG;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées
    
    public int NbVie = 3;
    public int nbGomme=0;
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
        grilleEntites[7][7] = pm;
        map.put(pm, new Point(7,7));
    

        f = new Fantome(this);
        grilleEntites[1][1] = f;
        map.put(f, new Point(1, 1));
        
        fHD = new FantomeHD(this);
        grilleEntites[13][1] = fHD;
        map.put(fHD, new Point(13, 1));
        
        fBG = new FantomeBG(this);
        grilleEntites[1][13] = fBG;
        map.put(fBG, new Point(1, 13));
        
        fBD = new FantomeBD(this);
        grilleEntites[13][13] = fBD;
        map.put(fBD, new Point(13, 13));
        
        
        afficheMurs();
        InitGomme();    
        initPosEntite();
    }
    
    //public Point LastPoint();
    
    public void InitGomme()
    {
        
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
    
    
    public void initPosEntite()
    {
            pacman=map.get(pm);
            Ghost=map.get(f);
            GhostHD=map.get(fHD);
            GhostBG=map.get(fBG);
            GhostBD=map.get(fBD);
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
        for (int j=0; j< 5;j++)
        {
        grilleEntites[7][1+j] = m;
        map.put(m, new Point(7,1+j));
        grilleEntites[1+j][7] = m;
        map.put(m, new Point(1+j,7));
        grilleEntites[7][13-j] = m;
        map.put(m, new Point(7,13-j));
        grilleEntites[13-j][7] = m;
        map.put(m, new Point(13-j,7));

        }
       grilleEntites[2][4] = m;
       map.put(m, new Point(2,4));
       grilleEntites[2][5] = m;
       map.put(m, new Point(2,5));
       grilleEntites[2][3] = m;
       map.put(m, new Point(2,3));
       grilleEntites[2][2] = m;
       map.put(m, new Point(2,2));
       grilleEntites[3][2] = m;
       map.put(m, new Point(3,2));
       grilleEntites[4][2] = m;
       map.put(m, new Point(4,2));
       grilleEntites[5][2] = m;
       map.put(m, new Point(5,2));
       grilleEntites[4][5] = m;
       map.put(m, new Point(4,5));
       grilleEntites[4][4] = m;
       map.put(m, new Point(4,4));
       grilleEntites[5][4] = m;
       map.put(m, new Point(5,4));
       
       
       
       grilleEntites[2][12] = m;
       map.put(m, new Point(2,12));
       grilleEntites[2][11] = m;
       map.put(m, new Point(2,11));
       grilleEntites[2][10] = m;
       map.put(m, new Point(2,10));
       grilleEntites[2][9] = m;
       map.put(m, new Point(2,9));
       grilleEntites[3][12] = m;
       map.put(m, new Point(3,12));
       grilleEntites[4][12] = m;
       map.put(m, new Point(4,12));
       grilleEntites[5][12] = m;
       map.put(m, new Point(5,12));
       grilleEntites[4][10] = m;
       map.put(m, new Point(4,10));
       grilleEntites[4][9] = m;
       map.put(m, new Point(4,9));
       grilleEntites[5][10] = m;
       map.put(m, new Point(5,10));
       
        
       grilleEntites[12][2] = m;
       map.put(m, new Point(12,2));
       grilleEntites[11][2] = m;
       map.put(m, new Point(11,2));
       grilleEntites[10][2] = m;
       map.put(m, new Point(10,2));
       grilleEntites[9][2] = m;
       map.put(m, new Point(9,2));
       grilleEntites[12][3] = m;
       map.put(m, new Point(12,3));
       grilleEntites[12][4] = m;
       map.put(m, new Point(12,4));
       grilleEntites[12][5] = m;
       map.put(m, new Point(12,5));
       grilleEntites[10][4] = m;
       map.put(m, new Point(10,4));
       grilleEntites[9][4] = m;
       map.put(m, new Point(9,4));
       grilleEntites[10][5] = m;
       map.put(m, new Point(10,5));
       
       grilleEntites[12][12] = m;
       map.put(m, new Point(12,12));
       grilleEntites[12][11] = m;
       map.put(m, new Point(12,11));
       grilleEntites[12][10] = m;
       map.put(m, new Point(12,10));
       grilleEntites[12][9] = m;
       map.put(m, new Point(12,9));
       grilleEntites[11][12] = m;
       map.put(m, new Point(11,12));
       grilleEntites[10][12] = m;
       map.put(m, new Point(10,12));
       grilleEntites[9][12] = m;
       map.put(m, new Point(9,12));
       grilleEntites[10][10] = m;
       map.put(m, new Point(10,10));
       grilleEntites[10][9] = m;
       map.put(m, new Point(10,9));
       grilleEntites[9][10] = m;
       map.put(m, new Point(9,10));
       
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
       
        if (Cadre(pCible) && (objetALaPosition(pCible)instanceof Pac_Gommes) && objetALaPosition(pCourant)instanceof Pacman) { // a adapter (collisions murs, etc.)
                Point tampon =pCible;
                grilleEntites[tampon.x][tampon.y]=null;
                nbGomme--;
                //System.out.print(nbGomme);
                if (nbGomme<0)
                {
                    Arret(pCourant, pCible, e);
                }
                deplacerEntite(pCourant, pCible, e);
            retour = true;
        }
        else if (Cadre(pCible) && (objetALaPosition(pCible) == null) && objetALaPosition(pCourant)instanceof Pacman) {
            deplacerEntite(pCourant, pCible, e);
            retour = true;
            }
        else if (Cadre(pCible) && objetALaPosition(pCible)==null && (objetALaPosition(pCible)instanceof Pac_Gommes || objetALaPosition(pCible)==null) && (objetALaPosition(pCourant)instanceof Fantome ||objetALaPosition(pCourant)instanceof FantomeHD || objetALaPosition(pCourant)instanceof FantomeBG || objetALaPosition(pCourant)instanceof FantomeBD)) {

            deplacerEntite(pCourant, pCible, e);
            retour = true;
        }
        else if(Cadre(pCible) && objetALaPosition(pCible)instanceof Pac_Gommes && (objetALaPosition(pCourant)instanceof Fantome ||objetALaPosition(pCourant)instanceof FantomeHD || objetALaPosition(pCourant)instanceof FantomeBG || objetALaPosition(pCourant)instanceof FantomeBD))
        {
            deplacerEntiteFantome(pCourant, pCible, e);
            retour = true;
        }
         else if(Cadre(pCible) && objetALaPosition(pCible)==null && (objetALaPosition(pCourant)instanceof Fantome ||objetALaPosition(pCourant)instanceof FantomeHD || objetALaPosition(pCourant)instanceof FantomeBG || objetALaPosition(pCourant)instanceof FantomeBD))
        {
            deplacerEntiteFantome2(pCourant, pCible, e);
            retour = true;
        }
        
        else if ((objetALaPosition(pCourant)instanceof Pacman && objetALaPosition(pCourant)instanceof Fantome ) || (objetALaPosition(pCible)instanceof Pacman && objetALaPosition(pCourant)instanceof Fantome ))
        {
            
            System.out.print(NbVie);
            NbVie--;
            pCourant = map.get(e);
            pCible = calculerPointCible(pCourant, d);
            if(NbVie>0)
            {
             deplacerEntiteColision(pCourant, pCible, e/*,d*/);
            }
            else
            {   
                Arret(pCourant, pCible, e);    
            }
             retour = true;
        }
    else if ((objetALaPosition(pCourant)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeHD) || (objetALaPosition(pCible)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeHD))
        {

            System.out.print(NbVie);
            NbVie--;
            pCourant = map.get(e);
            pCible = calculerPointCible(pCourant, d);
            if(NbVie>0)
            {
             deplacerEntiteColisionHD(pCourant, pCible, e/*,d*/);
            }
            else
            {   
                Arret(pCourant, pCible, e);    
            }
             retour = true;
        }
                
    else if ((objetALaPosition(pCourant)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeBG) || (objetALaPosition(pCible)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeBG))
        {
            System.out.print(NbVie);
            NbVie--;
            pCourant = map.get(e);
            pCible = calculerPointCible(pCourant, d);
            if(NbVie>0)
            {
             deplacerEntiteColisionBG(pCourant, pCible, e/*,d*/);
            }
            else
            {   
                Arret(pCourant, pCible, e);    
            }
             retour = true;
        }
                        
    else if ((objetALaPosition(pCourant)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeBG) || (objetALaPosition(pCible)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeBG))
        {
            System.out.print(NbVie);
            NbVie--;
            pCourant = map.get(e);
            pCible = calculerPointCible(pCourant, d);
            if(NbVie>0)
            {
             deplacerEntiteColisionBG(pCourant, pCible, e/*,d*/);
            }
            else
            {   
                Arret(pCourant, pCible, e);    
            }
             retour = true;
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
        private void deplacerEntiteFantome2(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }
       private void deplacerEntiteColision(Point pCourant, Point pCible, Entite e/*, Direction d*/) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = null;
        map.put(e, pCible);
            grilleEntites[7][7] = pm;
            map.put(pm, new Point(7,7));
            
            grilleEntites[1][1] = f;
            map.put(f, new Point(1,1));
        
       // deplacerEntite(e,d);
    } 
    private void deplacerEntiteColisionHD(Point pCourant, Point pCible, Entite e/*, Direction d*/) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = null;
        map.put(e, pCible);
            grilleEntites[7][7] = pm;
            map.put(pm, new Point(7,7));
            
            grilleEntites[13][1] = fHD;
            map.put(fHD, new Point(13,1));
        
        //RespawnFantome(pCourant,pCible,e);
       // deplacerEntite(e,d);
    } 
    
    private void deplacerEntiteColisionBG(Point pCourant, Point pCible, Entite e/*, Direction d*/) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = null;
        map.put(e, pCible);
            grilleEntites[7][7] = pm;
            map.put(pm, new Point(7,7));
            
            grilleEntites[1][13] = fBG;
            map.put(fBG, new Point(1,13));
        
       // deplacerEntite(e,d);
    } 
              
    private void deplacerEntiteColisionBD(Point pCourant, Point pCible, Entite e/*, Direction d*/) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = null;
        map.put(e, pCible);
            grilleEntites[7][7] = pm;
            map.put(pm, new Point(7,7));
            
            grilleEntites[13][13] = fBD;
            map.put(fBD, new Point(13,13));
        
       // deplacerEntite(e,d);
    } 
    
    private void RespawnFantome(Point pCourant, Point pCible, Entite e/*, Direction d*/)
    {
        System.out.print(pacman);
        grilleEntites[pacman.x][pacman.x] = pm;
        //System.out.print(map.get(pm));
        //map.put(pm, map.get(pm));
        grilleEntites[Ghost.x][Ghost.x] = pm;
        //map.put(f, map.get(f));
        grilleEntites[GhostHD.x][GhostHD.x] = pm;
        //map.put(fHD, map.get(fHD));
        grilleEntites[GhostBD.x][GhostBD.x] = pm;
        //map.put(fBD, map.get(fBD));
        grilleEntites[GhostBG.x][GhostBG.x] = null;
        //map.put(fBG, map.get(fBG));
                
        grilleEntites[7][7] = pm;
        map.put(pm, new Point(7,7));
        
        grilleEntites[1][1] = f;
        map.put(f, new Point(1, 1));
        
        grilleEntites[13][1] = fHD; 
        map.put(fHD, new Point(13, 1)); 
               
        grilleEntites[1][13] = fBG;
        map.put(fBG, new Point(1, 13));
        
        grilleEntites[13][13] = fBD;
        map.put(fBD, new Point(13,13));
    }
                     
       private void Arret(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = null;
        //pm=null;
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
