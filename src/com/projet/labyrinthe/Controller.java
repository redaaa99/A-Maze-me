package com.projet.labyrinthe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private CheckBox imparfait;
    @FXML
    private ToggleGroup Grp1,Grp,Grp2; //les groupes de radiobuttons
    @FXML
    private Label generationTime,solveTime; //les label de temps de generation/resolution
    @FXML
    private Pane mainPane; // l'afficheur
    @FXML
    private TextField tailleTextField; //Taille

    private Group tileGroup = new Group(); // groupe de tout les cellules du labyrinthe


    public static int animationSpeed=0;
    public static boolean cleared = false;
    final public static double SCENEWIDTH = 675;
    final public static double SCENEHEIGHT = 675;
    public static int mazeWidth = 5;
    public static int mazeHeight = 5;
    public static double cellWidth = SCENEWIDTH / ((mazeWidth%2==1)?mazeWidth:mazeWidth+1);
    public static double cellHeight = SCENEHEIGHT / ((mazeHeight%2==1)?mazeHeight:mazeHeight+1);
    public static Player player; // joueur vert
    public static FinishCell finishCell; //Arrivé bleue
    public static List<Cell> grid = new ArrayList<>(); // le labyrinthe :D
    public static AbstractSolver solver; // L'objet qui resout le labyrinthe
    public static boolean wallGrise = false; // true si labyrinthe imparfait


    /*
    * Fonction Appelé au demarrage de l'application
    */
    @FXML
    public void initialize(){
        mainPane.setStyle("-fx-background-color:white;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"); // effets css
        mainPane.getChildren().addAll(tileGroup); // on ajoute le groupe de cellules au panel
        tailleTextField.setText(String.valueOf(mazeWidth));
   }
    /*
    * Listener au clique sur le bouton de generation
    */
    @FXML
    private void handleGenerateClick(ActionEvent event)
    {
        generateMaze(); // xD
    }
    /*
    * Listener au clique sur le checkbox de labyrinthe imparfait
    */
    @FXML
    private void handleImparfaitClick(ActionEvent event) {
        wallGrise = !wallGrise; // Si imparfait on ne peut pas utilisé le WallFollower
        if(wallGrise)
        {
            RadioButton selectedRadioButton = (RadioButton) Grp1.getToggles().get(4); // 4 c'est le WallFollower
            selectedRadioButton.setDisable(true);
            selectedRadioButton.setSelected(false);
        }else
        {
            RadioButton selectedRadioButton = (RadioButton) Grp1.getToggles().get(4);
            selectedRadioButton.setDisable(false);
        }
    }
    /*
    * Listener au clique sur le bouton About
    */
    @FXML
    private  void handleAboutClick(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // infos
        alert.setTitle("About");
        alert.getDialogPane().setPrefSize(600,700);
        alert.setHeaderText("Realisé par:");
        alert.setContentText(" Meskali Reda \n Lagrini Youness \n Mastane Jihane" +
                "\n Se deplacer en utilisant Z Q S D " +
                "\n (Attention clavier azerty)\n\n\n" +
                "MIT License\n" +
                "\n" +
                "Copyright (c) 2018 Reda Meskali\n" +
                "\n" +
                "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                "of this software and associated documentation files (the \"Software\"), to deal\n" +
                "in the Software without restriction, including without limitation the rights\n" +
                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                "copies of the Software, and to permit persons to whom the Software is\n" +
                "furnished to do so, subject to the following conditions:\n" +
                "\n" +
                "The above copyright notice and this permission notice shall be included in all\n" +
                "copies or substantial portions of the Software.\n" +
                "\n" +
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                "SOFTWARE.\n\n\n" +
                "Github: https://github.com/redaaa99/mazes");
        alert.showAndWait();
    }
    /*
    * Listener pour les touches de clavier
    */
    @FXML
    public void keyListener(KeyEvent event){
        player.moveTo(event.getCode(),grid); // deplacer le joueur
        checkWin(); // et voir si il a gagné
    }
    /*
    * Listener au clique sur le bouton d'effacement
    */
    @FXML
    private void handleClearClick(ActionEvent event)
    {
        clearMaze(); // ...
    }
    /*
    * Listener au clique sur le bouton de resolution
    */
    @FXML
    private void handleSolveClick(ActionEvent event) {
        clearPathMaze(grid,tileGroup);
        solveMaze();
    }

    /*
    * Fonction d'initialisation pour la generation
    */
    private void generateMaze() {
        clearMaze(); // On efface
        updateGrid(); // on lit la taille et tout pour intialisé
        long startTime = System.currentTimeMillis(); // start
        grid = getSelectedGenerator();
        if(imparfait.isSelected())
        {
            makeItImperfect();
        }
        player = new Player(0,0);
        finishCell = new FinishCell(mazeWidth-1,mazeHeight-1);
        long timer = System.currentTimeMillis()-startTime; // end
        generationTime.setText(timer+" ms");
        DrawMaze(); // Dessin
    }
    /*
    * Fonction d'initialisation pour la resolution
    */
    private void solveMaze(){
        long startTime = System.currentTimeMillis();
        animationSpeed = getSelectedSpeed();
        grid=getSelectedSolver();
        long timer = System.currentTimeMillis()-startTime;
        solveTime.setText(timer+" ms");
    }
    /*
    * Choix de l'animation
    */
    private int getSelectedSpeed() {
        RadioButton selectedRadioButton = (RadioButton) Grp2.getSelectedToggle();
        String choice = selectedRadioButton.getText();
        switch (choice)
        {
            case "Avec":
                return 2;
            case "Sans":
               return 0;
            default:
                return 0;
        }
    }
    /*
    * Choix du resolveur
    */
    private List<Cell> getSelectedSolver() {
        RadioButton selectedRadioButton = (RadioButton) Grp1.getSelectedToggle();
        String choice;
        if(selectedRadioButton!=null) // par defaut BFS
        {
            choice = selectedRadioButton.getText();
        }else
        {
            choice = "BFS";
        }
        switch (choice) // Choix
        {
            case "Wall Follower": // Suis toujours le mur de droite
                WallFollower wallFollower = new WallFollower(mazeWidth,mazeHeight,player.getCol(),player.getRow()
                        ,finishCell.getRow(),finishCell.getCol(),grid,tileGroup);
                solver = wallFollower;
                return wallFollower.getMaze();
            case "BFS": // Largeur
                BFSSolver bfsSolver = new BFSSolver(mazeWidth,mazeHeight,player.getCol(),player.getRow()
                        ,finishCell.getRow(),finishCell.getCol(),grid,tileGroup);
                solver = bfsSolver;
                return bfsSolver.getMaze();
            case "A*": // plus courte distance
                AStarSolver aStarSolver = new AStarSolver(mazeWidth,mazeHeight,player.getCol(),player.getRow()
                        ,finishCell.getRow(),finishCell.getCol(),grid,tileGroup);
                solver = aStarSolver;
                return aStarSolver.getMaze();
            case "DFS": // profondeur
                DFSSolver dfsSolver= new DFSSolver(mazeWidth,mazeHeight,player.getCol(),player.getRow()
                        ,finishCell.getRow(),finishCell.getCol(),grid,tileGroup);
                solver = dfsSolver;
                return dfsSolver.getMaze();
            case "Dijkstra": // profondeur
                DijkstraSolver dijkstraSolver= new DijkstraSolver(mazeWidth,mazeHeight,player.getCol(),player.getRow()
                        ,finishCell.getRow(),finishCell.getCol(),grid,tileGroup);
                solver = dijkstraSolver;
                return dijkstraSolver.getMaze();
            default:
                WallFollower wallFollower4 = new WallFollower(mazeWidth,mazeHeight,player.getCol(),player.getRow()
                        ,finishCell.getRow(),finishCell.getCol(),grid,tileGroup);
                solver = wallFollower4;
                return wallFollower4.getMaze();
        }
    }
    /*
    * Choix du generateur
    */
    private List<Cell> getSelectedGenerator() {
        RadioButton selectedRadioButton = (RadioButton) Grp.getSelectedToggle();
        String choice = selectedRadioButton.getText();
        switch (choice)
        {
            case "Prim":
                return new PrimGenerator(mazeWidth,mazeHeight,tileGroup).generateMaze().getMaze();
            case "DFS":
                return new DFSBackTrackerGenerator(mazeWidth,mazeHeight,tileGroup).generateMaze().getMaze();
            case "Kruskal":
                return new KruskalMazeGenerator(mazeWidth,mazeHeight,tileGroup).generateMaze().getMaze();
            case "Eller's":
                return new EllersGen(mazeWidth,mazeHeight,tileGroup).generateMaze().getMaze();
            case "Hunt And Kill":
                return new HuntAndKillGenerator(mazeWidth,mazeHeight,tileGroup).generateMaze().getMaze();
            default:
                return new DFSBackTrackerGenerator(mazeWidth,mazeHeight,tileGroup).generateMaze().getMaze();
        }
    }
    /*
    * Affichage du labyrinthe
    */
    public void DrawMaze() {
        cleared=false;
        String couleur;
        for (int x = 0; x < grid.size(); x++) {
                float top=0; // largeur des murs ...
                float bottom=0;
                float left=0;
                float right=0;
                if(grid.get(x).getPosWalls(0)) // top ... etc
                {
                    top=0.5F;
                }
                if(grid.get(x).getPosWalls(3))
                {
                    left=0.5F;
                }
                if(grid.get(x).getPosWalls(1))
                {
                    right=0.5F;
                }
                if(grid.get(x).getPosWalls(2))
                {
                    bottom=0.5F;
                }
                if(!grid.get(x).getSolvedVisited()){couleur="#F00";}else{couleur="#FFF";}
                grid.get(x).setStyle("-fx-border-width: "+top+" "+right+" "+bottom+" "+left+"; " +
                        "-fx-border-color: black black black black;\n" +
                        "-fx-background-color: "+couleur+";"); // On applique le CSS
                tileGroup.getChildren().add(grid.get(x)); // On ajout la cellule au groupe
        }
        tileGroup.getChildren().add(player); // et le joueur
        tileGroup.getChildren().add(finishCell); // finalement l'arrivée
    }
    /*
    * Donne l'index de la cellule dans la liste en fonction de la ligne/colonne
    */
    public static int getIndex(int i, int j) {
        return (i<0 || j<0 || j>mazeHeight-1 || i>mazeHeight-1) ?  -1 : (j + i*mazeWidth); // renvoie l'index
    }
    /*
    * Reinitialise les parametres du labyrinthe
    */
    private void updateGrid() {
        int w;
        int h;
        if(isInteger(tailleTextField.getText()) && isInteger(tailleTextField.getText()))
        {
            // Valider l'input avec la taille min = 6 et max = 401
            w = Integer.parseInt(tailleTextField.getText());
            h = Integer.parseInt(tailleTextField.getText());
            if(h<6 && h>401){ h = 5;}
            if(w<6 && w>401){ w = 5;}
        }else
        {
            w = 5;
            h = 5;
            tailleTextField.setText(String.valueOf(w));
            tailleTextField.setText(String.valueOf(h));
        }
        mazeWidth = w;
        mazeHeight = h;
        mazeWidth =  (mazeWidth%2==1)?mazeWidth:mazeWidth+1;
        mazeHeight= (mazeHeight%2==1)?mazeHeight:mazeHeight+1;
        cellWidth = SCENEWIDTH / (mazeWidth);
        cellHeight = SCENEHEIGHT / (mazeHeight);
        clearMaze();
    }
    /*
    * Evident ...
    */
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    /*
    * Efface l'affichage
    */
    private void clearMaze() {
        if(!cleared) {
            if(solver!=null) {
                if(solver.getAt()!=null)
                {
                    solver.getAt().stop(); // Arreter l'animation au cas ou ...
                }
            }
            grid.clear();
            tileGroup.getChildren().clear();
            solveTime.setText("");
            generationTime.setText("");
            cleared = true;
        }
    }
    /*
     *
     */
    private void clearPathMaze(List<Cell> grid,Group tilegroup) {
        if(solver!=null) {
            if(solver.getAt()!=null)
            {
                solver.getAt().stop(); // Arreter l'animation au cas ou ...
            }
        }
   	 for (int x = 0; x < grid.size(); x++) {
   		   grid.get(x).setVisited(true);
           grid.get(x).setProcessed(false);
           String helper1 , helper2;
           helper2 = tilegroup.getChildren().get(x).getStyle();
           helper2 = tilegroup.getChildren().get(x).getStyle();
           helper2 = helper2.replace("#0b7c7c", "#FFF");
           tilegroup.getChildren().get(x).setStyle(helper2);
           helper1 = tilegroup.getChildren().get(x).getStyle();
           helper1 = helper1.replace("#a3c9f8", "#FFF");
           tilegroup.getChildren().get(x).setStyle(helper1);
	  }
}
    /*
    * Gagné ?
    */
    public void checkWin() {
        if(player.getRow()==finishCell.getRow() && player.getCol()==finishCell.getCol()) // Si oui on passe au niveau suivant
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informations");
            alert.setHeaderText("Niveau reussi!");
            mazeWidth+=20;
            mazeHeight+=20; //+20 sur chaque niveau
            alert.setContentText("Prochain niveau: "+mazeHeight);
            tailleTextField.setText(String.valueOf(mazeWidth));
            tailleTextField.setText(String.valueOf(mazeHeight));
            updateGrid();
            generateMaze();
            alert.showAndWait();
        }
    }
    /*
    * Le monde n'est pas parfait ...
    */
    public void makeItImperfect() {
        for(int i=0 ; i<(grid.size()/10) ; i++) { // on enleve des murs aleatoires pour avoir plusieurs chemins à la cellule de destination
            int row = (int) ((Math.random() * (mazeHeight-2 - 1)) + 1);
            int col = (int) ((Math.random() * (mazeWidth-2 - 1)) + 1);
            Cell current = grid.get(getIndex(row,col));
            current.removeWalls(grid.get(getIndex(row-1,col)));
            current.removeWalls(grid.get(getIndex(row+1,col)));
            current.removeWalls(grid.get(getIndex(row,col-1)));
            current.removeWalls(grid.get(getIndex(row,col+1)));
        }
    }
}
