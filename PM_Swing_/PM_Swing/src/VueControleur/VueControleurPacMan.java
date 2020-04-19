package VueControleur;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextComponent;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    private ImageIcon ImageSuperGomme;
    private Image image;
    private JFrame frame= new JFrame();
    private JFrame frame1= new JFrame();
    private JFrame frame2= new JFrame();
    private JFrame frameRegle= new JFrame();
    private JTextField text;
    private JTextArea score = new JTextArea();
    private JTextArea vie = new JTextArea();
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
        ImageMurs = chargerIcone("Images/Mur.jpg");
        ImageGomme = chargerIcone("Images/bonbon.png");
        ImageSuperGomme = chargerIcone("Images/superbonbon.png");
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
    
    private void placerLesComposantsGraphiques() {

        
        setTitle("PacMan");
        setSize(500, 500);
        JComponent grilleJLabels = new JPanel(new GridLayout(12, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);              
        //Met le Score dans les fenètre qui se situe en dessous du Jeu
        JPanel panelRegle=new JPanel();
        panelRegle.add(new JLabel("Vous Disposez de TROIS vie"));
        panelRegle.add(new JLabel("1 Gomme manger = Score + 10"));
        panelRegle.add(new JLabel("1 SuperGomme manger = Score + 100"));
        frameRegle.setContentPane(panelRegle);
        frameRegle.setBounds(120, 500, 300, 300);
        frameRegle.setVisible(true);
        frameRegle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        
        add(grilleJLabels);

        
    }
    //Si vous gagnez Affichage du Score/ Du nombre de Vie et d'une Image
    private void AffichageWin(){
        frameRegle.setVisible(false);
        
        frame.add(new JLabel(ImageWIN));
        frame.setBounds(520, 0, 500, 370);
        frame.setVisible(true);   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(false);  -> Si on veur Enlever la Fenetre Principale du Jeu
        
        //Affichage du Score
        JPanel panel2=new JPanel();
        panel2.add(new JLabel("Score : "));
        frame1.setContentPane(panel2);
        score.setText(String.valueOf(jeu.score));
        frame1.getContentPane().add(score, BorderLayout.CENTER);
        frame1.setBounds(0, 500, 100, 100);
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Affichage du Nombre de Vie
        JPanel panel=new JPanel();
        panel.add(new JLabel("Vie : "));
        frame2.setContentPane(panel);
	vie.setText(String.valueOf(jeu.NbVie));
        frame2.getContentPane().add(vie, BorderLayout.CENTER);
        frame2.setBounds(120, 500, 100, 100);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
    
//Si vous perdez Affichage du Score/ Du nombre de Vie et d'une Image    
    private void AffichageLoose(){
        frameRegle.setVisible(false);
        
        frame.add(new JLabel(ImageFIN));
        frame.setBounds(520, 0, 500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(false); -> Si on veur Enlever la Fenetre Principale du Jeu 
        //Affichage du Score
        JPanel panel2=new JPanel();
        panel2.add(new JLabel("Score : "));
        frame1.setContentPane(panel2);
        score.setText(String.valueOf(jeu.score));
        frame1.getContentPane().add(score, BorderLayout.CENTER);
        frame1.setBounds(0, 500, 100, 100);
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Affichage du Nombre de Vie
        JPanel panel=new JPanel();
        panel.add(new JLabel("Vie : "));
        frame2.setContentPane(panel);
	vie.setText(String.valueOf(jeu.NbVie));
        frame2.getContentPane().add(vie, BorderLayout.CENTER);
        frame2.setBounds(120, 500, 100, 100);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
                else if (jeu.getGrille()[x][y] instanceof FantomeBG) {
                    
                    tabJLabel[x][y].setIcon(icoFantome);
                }
                else if (jeu.getGrille()[x][y] instanceof SuperGomme) {
                    
                    tabJLabel[x][y].setIcon(ImageSuperGomme);
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
        if (jeu.nbGomme <= 0)
        {
           AffichageWin(); 
        }
        

        
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage(); 
    }

}
