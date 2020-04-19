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

//import javafx.scene.image.ImageView;


/** La classe Jeu a deux fonctions 
 *  (1) Gérer les aspects du jeu : condition de défaite, victoire, nombre de vies
 *  (2) Gérer les coordonnées des entités du monde : déplacements, collisions, perception des entités, ... 
 *
 * @author freder
 */
public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 12;
    public static final int SIZE_Y = 12;

    private Pacman pm;
    private Fantome f;
    private SuperGomme sg;
    private FantomeBG fBG;
    private Pac_Gommes gomme;


    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées
    
    // Initialisation de la Vie, du Nombre de Gomme et du Score
    public int NbVie = 3;
    public int nbGomme=0;
    public int score=0;
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
    
    public HashMap<Entite, Point> getMap() {
        return map;
    }
    
    //Permet de placer notre Pacman, les 2 Fantomes, les Gommes, les SuperGommes et les murs dans grilleEntite[][] et dans la map
    private void initialisationDesEntites() {
      
        pm = new Pacman(this);
        grilleEntites[6][5] = pm;
        map.put(pm, new Point(6,5));

        f = new Fantome(this);
        grilleEntites[4][5] = f;
        map.put(f, new Point(4, 5));
        
        fBG = new FantomeBG(this);
        grilleEntites[9][8] = fBG;
        map.put(fBG, new Point(9, 8));
        
        sg=new SuperGomme(this);
        grilleEntites[1][1] = sg;
        map.put(sg, new Point(1, 1));
        grilleEntites[10][1] = sg;
        map.put(sg, new Point(10, 1));
        grilleEntites[1][10] = sg;
        map.put(sg, new Point(1, 10));
        grilleEntites[10][10] = sg;
        map.put(sg, new Point(10,10));
        
        afficheMurs();
        InitGomme();    
    }
    
    //Initialise grilleEntites[][] et la map en mettant des gommes partout où il n'y pas d'Entité 
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
    
//Permet après colision entre le Pacman et un Fantome/FantomeBG: 
//- De ré-initialiser grilleEntites[][] en supprimant les Entités Pacman/Fantome/FantomeBG de la map
//- Met a jour le nombre de gomme présente dans grilleEntites[][]
public void InitPos()
    {
       int me=0; //Variable tampon pour récuperer le nombre de gomme
       for(int i=0; i<SIZE_X;i++)
        {
          for(int j=0; j<SIZE_X;j++)
          {
              if( grilleEntites[i][j]==fBG || grilleEntites[i][j]==f)
              {
                  grilleEntites[i][j]=gomme;
              }
              if(grilleEntites[i][j]==pm)
              {
                 grilleEntites[i][j]=null; 
              }       
              if(grilleEntites[i][j]==gomme)
              {
                  me++;
              }
             
          }
        }
        
        nbGomme=me;
    }
    
    


    
    //Place les différents Murs dans grilleEntite[][] et dans la map
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
 
        //Bloc 1
        grilleEntites[2][2] = m;
        map.put(m, new Point(2,2));
        grilleEntites[3][2] = m;
        map.put(m, new Point(3,2));
        grilleEntites[4][2] = m;
        map.put(m, new Point(4,2));
        grilleEntites[2][3] = m;
        map.put(m, new Point(2,3));
        grilleEntites[3][3] = m;
        map.put(m, new Point(3,3));
        
        //Bloc 2
        grilleEntites[6][2] = m;
        map.put(m, new Point(6,2));
        grilleEntites[7][2] = m;
        map.put(m, new Point(7,2));
        grilleEntites[8][2] = m;
        map.put(m, new Point(8,2));
        grilleEntites[9][2] = m;
        map.put(m, new Point(9,2));
        grilleEntites[8][3] = m;
        map.put(m, new Point(8,3));
        grilleEntites[8][4] = m;
        map.put(m, new Point(8,4));
        grilleEntites[7][4] = m;
        map.put(m, new Point(7,4));
        grilleEntites[7][5] = m;
        map.put(m, new Point(7,5));
        
        //Bloc 3
        grilleEntites[5][4] = m;
        map.put(m, new Point(5,4));
        grilleEntites[5][5] = m;
        map.put(m, new Point(5,5));
        grilleEntites[5][6] = m;
        map.put(m, new Point(5,6));
        grilleEntites[5][7] = m;
        map.put(m, new Point(5,7));
        grilleEntites[6][7] = m;
        map.put(m, new Point(6,7));
        
        //Bloc 4
        grilleEntites[10][4] = m;
        map.put(m, new Point(10,4));
        grilleEntites[10][5] = m;
        map.put(m, new Point(10,5));
        grilleEntites[10][6] = m;
        map.put(m, new Point(10,6));
        grilleEntites[10][7] = m;
        map.put(m, new Point(10,7));
        
        //Bloc 5
        grilleEntites[8][7] = m;
        map.put(m, new Point(8,7));
        grilleEntites[8][8] = m;
        map.put(m, new Point(8,8));
        grilleEntites[8][9] = m;
        map.put(m, new Point(8,9));
        grilleEntites[9][9] = m;
        map.put(m, new Point(9,9));
        grilleEntites[7][9] = m;
        map.put(m, new Point(7,9));
        
        //Bloc 6
        grilleEntites[2][9] = m;
        map.put(m, new Point(2,9));
        grilleEntites[3][9] = m;
        map.put(m, new Point(3,9));
        grilleEntites[4][9] = m;
        map.put(m, new Point(4,9));
        grilleEntites[5][9] = m;
        map.put(m, new Point(5,9));
        grilleEntites[2][8] = m;
        map.put(m, new Point(2,8));
        
        //Bloc 7
        grilleEntites[2][5] = m;
        map.put(m, new Point(2,5));
        grilleEntites[2][6] = m;
        map.put(m, new Point(2,6));
        grilleEntites[2][7] = m;
        map.put(m, new Point(2,7));
        grilleEntites[3][5] = m;
        map.put(m, new Point(3,5));
        grilleEntites[3][6] = m;
        map.put(m, new Point(3,6));
        grilleEntites[3][7] = m;
        map.put(m, new Point(3,7));
        
    }
    
    
    
    
    /** Permet a une entité  de percevoir sont environnement proche et de définir sa strétégie de déplacement 
     * (fonctionalité utilisée dans choixDirection() de Fantôme)
     */
    public Object regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    /** 
     * 
     * Permet de faire déplacer les différents Entités selon des condition très percise
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        
        boolean retour;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);
        //Si le pacman rencontre une gomme: donc nbGomme diminue et le score augmente. si plus de gomme Arret du jeu
        if (Cadre(pCible) && (objetALaPosition(pCible)instanceof Pac_Gommes) && objetALaPosition(pCourant)instanceof Pacman) { // a adapter (collisions murs, etc.)
                Point tampon =pCible;
                grilleEntites[tampon.x][tampon.y]=null;
                nbGomme--;
                score=score+10;
                System.out.print("Score" + score);
                if (nbGomme<=0)
                {
                    Arret(pCourant, pCible, e);
                }
                deplacerEntite(pCourant, pCible, e);
            retour = true;
        }
        //Si le pacman rencontre une SuperGomme: le score augmente. si plus de gomme Arret du jeu
        else if (Cadre(pCible) && (objetALaPosition(pCible)instanceof SuperGomme) && objetALaPosition(pCourant)instanceof Pacman) { // a adapter (collisions murs, etc.)
                Point tampon =pCible;
                grilleEntites[tampon.x][tampon.y]=null;
                score=score+100;
                System.out.print("Score" + score);
                
                if (nbGomme<=0)
                {
                    Arret(pCourant, pCible, e);
                }
                deplacerEntite(pCourant, pCible, e);
            retour = true;
        }
        //si le Pacman ne recontre pas d'Entité, il se déplace juste 
        else if (Cadre(pCible) && (objetALaPosition(pCible) == null) && objetALaPosition(pCourant)instanceof Pacman) {
            deplacerEntite(pCourant, pCible, e);
            retour = true;
            }
        //si Fantome/FantomeBG rencontre une Gomme il se déplace sur la Gomme
        else if(Cadre(pCible) && objetALaPosition(pCible)instanceof Pac_Gommes && (objetALaPosition(pCourant)instanceof Fantome || objetALaPosition(pCourant)instanceof FantomeBG ))
        {
            deplacerEntiteFantome(pCourant, pCible, e);
            retour = true;
        }
        //si Fantome/FantomeBG rencontre une SuperGomme il se déplace sur la SuperGomme
        else if(Cadre(pCible) && objetALaPosition(pCible)instanceof SuperGomme && (objetALaPosition(pCourant)instanceof Fantome  || objetALaPosition(pCourant)instanceof FantomeBG))
        {
            deplacerEntiteSuperFantome(pCourant, pCible, e);
            retour = true;
        }
        //si le Fantome/FantomeBG ne recontre pas d'Entité, il se déplace juste 
         else if(Cadre(pCible) && objetALaPosition(pCible)==null && (objetALaPosition(pCourant)instanceof Fantome || objetALaPosition(pCourant)instanceof FantomeBG))
        {
            deplacerEntiteFantome2(pCourant, pCible, e);
            retour = true;
        }
         
        //si le Fantome rencontre le Pacman et inverssement: On perd une vie, si plus de vie -> Arret du Jeu
        else if ((objetALaPosition(pCourant)instanceof Pacman && objetALaPosition(pCourant)instanceof Fantome ) || (objetALaPosition(pCible)instanceof Pacman && objetALaPosition(pCourant)instanceof Fantome ))
        {
            
            //System.out.print(NbVie);
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
        //si le FantomeBG rencontre le Pacman et inverssement: On perd une vie, si plus de vie -> Arret du Jeu           
        else if ((objetALaPosition(pCourant)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeBG) || (objetALaPosition(pCible)instanceof Pacman && objetALaPosition(pCourant)instanceof FantomeBG))
        {
            System.out.print(nbGomme);
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
    
    //Les différents deplacerEntités permettants de gerer les colisions entre les Entités
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
    private void deplacerEntiteSuperFantome(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = sg;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }
        private void deplacerEntiteFantome2(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }
       private void deplacerEntiteColision(Point pCourant, Point pCible, Entite e) {
        RespawnFantome();
    } 

    
    private void deplacerEntiteColisionBG(Point pCourant, Point pCible, Entite e) {
        RespawnFantome();
    } 
              

    //Après colision entre un Fantome/FantomeBG et le Pacman: on ré-initialise les position du Pacman/Fantome/FantomeBG
    private void RespawnFantome()
    {   
        InitPos();
        grilleEntites[6][5] = pm;
        map.put(pm, new Point(6,5));
    

        grilleEntites[5][1] = f;
        map.put(f, new Point(5, 1));
        
        grilleEntites[5][10] = fBG;
        map.put(fBG, new Point(5, 10));
        
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
