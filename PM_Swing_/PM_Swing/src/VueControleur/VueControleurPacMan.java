package VueControleur;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.text.*;
import javax.swing.SwingUtilities;
import modele.Direction;
import modele.Fantome;
import modele.Jeu;
import modele.Pacman;
import modele.Murs;
import modele.Pac_Gommes;
import modele.*;

/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 * @author freder
 */
public class VueControleurPacMan extends JFrame implements Observer {

    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    private ImageIcon icoPacMan; // icones affichées dans la grille
    private ImageIcon icoFantome;
    private ImageIcon icoCouloir;
    private ImageIcon ImageMurs;
    private ImageIcon ImageGomme;
    private ImageIcon ImageFIN;
    private ImageIcon ImageWIN;
    private Image image;
    private JFrame frame= new JFrame();
    private JFrame frame1= new JFrame();

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associé à une icône, suivant ce qui est présent dans la partie modèle)


    public VueControleurPacMan(int _sizeX, int _sizeY) {

        sizeX = _sizeX;
        sizeY = _sizeY;

        chargerLesIcones();
        placerLesComposantsGraphiques();

        ajouterEcouteurClavier();

    }
    

    private void ajouterEcouteurClavier() {

        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                
                switch(e.getKeyCode()) {  // on écoute les flèches de direction du clavier
                    case KeyEvent.VK_LEFT : jeu.getPacman().setDirection(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : jeu.getPacman().setDirection(Direction.droite); break;
                    case KeyEvent.VK_DOWN : jeu.getPacman().setDirection(Direction.bas); break;
                    case KeyEvent.VK_UP : jeu.getPacman().setDirection(Direction.haut); break;
                }
                
            }

        });

    }

    public void setJeu(Jeu _jeu) {
        jeu = _jeu;
    }

    private void chargerLesIcones() {
        icoPacMan = chargerIcone("Images/Pacman.png");
        icoCouloir = chargerIcone("Images/Vide.png");
        icoFantome = chargerIcone("Images/Fantom.png");
        ImageMurs = chargerIcone("Images/OR.jpg");
        ImageGomme = chargerIcone("Images/bonbon.png");
        ImageFIN = chargerIcone("Images/GameOver.jpg");
        ImageWIN = chargerIcone("Images/victoire.jpg");
    }

    private ImageIcon chargerIcone(String urlIcone) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurPacMan.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return new ImageIcon(image);
    }
    
    /*public void paintComponent(Graphics g)
    {
        try{
            Image img =ImageIO.read(new File ("Images/bonbon.png"));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        }catch (IOException e)
        {
            e.printStackTrace();
            System.out.print("ERREUR");
        }
    }
    */
    
    private void placerLesComposantsGraphiques() {

        
        setTitle("PacMan");
        setSize(400, 500);
         // permet de terminer l'application à la fermeture de la fenêtre
        JComponent grilleJLabels = new JPanel(new GridLayout(15, 15));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        
     //   JLabel label;
     //   label = new JLabel(jeu.NbVie);
	/*frame1.setBounds(400, 0, 500, 370);
        frame1.add(label);
        frame1.setVisible(true);*/
        // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille
        tabJLabel = new JLabel[sizeX][sizeY];
        

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {

                JLabel jlab = new JLabel();
                tabJLabel[y][x] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
                
            }

        }
        //grilleJLabels.
        
        
        //Text titre = new Text(100,30,"Pacman");
        add(grilleJLabels);
        //label.setBounds(0,0, 1, 1);
        //label.setOpaque (true);
        //add(label);

    }

    private void AffichageWin(){
        frame.add(new JLabel(ImageWIN));
        frame.setBounds(400, 0, 500, 370);
        frame.setVisible(true);
        //setVisible(false);
        //frame.setSize(400,500);                
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}
    
    private void AffichageLoose(){
        frame.add(new JLabel(ImageFIN));
        frame.setBounds(400, 0, 500, 500);
        frame.setVisible(true);
        //frame.setSize(400,500);                
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (jeu.getGrille()[x][y] instanceof Pacman) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                    
                    tabJLabel[x][y].setIcon(icoPacMan);
                    
                    
                    
                } else if (jeu.getGrille()[x][y] instanceof Fantome) {
                    
                    tabJLabel[x][y].setIcon(icoFantome);
                } 
                else if (jeu.getGrille()[x][y] instanceof Murs) {
                    
                    tabJLabel[x][y].setIcon(ImageMurs);
                } 
                else if (jeu.getGrille()[x][y] instanceof Pac_Gommes) {
                    
                    tabJLabel[x][y].setIcon(ImageGomme);
                } 
                    else if (jeu.getGrille()[x][y] instanceof FantomeBD) {
                    
                    tabJLabel[x][y].setIcon(icoFantome);
                } 
                else if (jeu.getGrille()[x][y] instanceof FantomeBG) {
                    
                    tabJLabel[x][y].setIcon(icoFantome);
                } 
                else if (jeu.getGrille()[x][y] instanceof FantomeHD) {
                    
                    tabJLabel[x][y].setIcon(icoFantome);
                } 
                
                
                
                
                else {
                    
                        tabJLabel[x][y].setIcon(icoCouloir);
                        
                        
                    
                }
                
                

            }
        }
        if (jeu.NbVie == 0)
        {
           AffichageLoose(); 
        }
        if (jeu.nbGomme == 0)
        {
           AffichageWin(); 
        }


    }

    @Override
    public void update(Observable o, Object arg) {
        
        
        mettreAJourAffichage();
                    
        //img.setImage(new Image("win.png") {});

        
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
       */
        
    }

}
