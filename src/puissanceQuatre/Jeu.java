package puissanceQuatre;

import java.util.ArrayList;
import java.util.Collections;

public class Jeu{

    private int tab[][];
    private int derniereColonne;
    private int nbrNoeuds;

    public Jeu() {
        tab = new int[6][7];
    }

    // ******************** afficher ********************************
    void afficher() {
        System.out.println("*************** JEU ****************");
        for (int ligne = 6 - 1; ligne > -1; ligne--) {
            for (int colonne = 0; colonne < 7; colonne++) {
                System.out.print(tab[ligne][colonne] + " ");
            }
            System.out.println();
        }
    }

    // ******************** jouerCoup ********************************
    public int jouerCoup(int colonne, int numeroJoueur) {
        for (int ligne = 0; ligne < 6; ligne++) {
            if (tab[ligne][colonne] == 0) {
                tab[ligne][colonne] = numeroJoueur;
                return ligne;
            }
        }
        return -1;
    }

    // ******************** annulerCoup ********************************
    private void annulerCoup(int i) {
        int ligne = 6 - 1;
        while (tab[ligne][i] == 0) {
            ligne--;
        }
        this.tab[ligne][i] = 0;

    }

    // ******************** placeDispo ********************************
    public boolean placeDispo(int colonne) {
        boolean dispo = false;
        for (int ligne = 0; ligne < 6; ligne++) {
            if (tab[ligne][colonne] == 0) {
                dispo = true;
            }
        }
        return dispo;
    }

    // ******************** cherche ********************************
    public int cherche(int numeroJoueur, int nombre) {
        int compteur = 0;
        for (int ligne = 0; ligne < 6; ligne++) {
            compteur += chercheAlignes(0, ligne, 1, 0, numeroJoueur, nombre);
        }
        for (int col = 0; col < 7; col++) {
            compteur += chercheAlignes(col, 0, 0, 1, numeroJoueur, nombre);
        }
        for (int col = 0; col < 7; col++) {
            compteur += chercheAlignes(col, 0, 1, 1, numeroJoueur, nombre);
            compteur += chercheAlignes(col, 0, -1, 1, numeroJoueur, nombre);
        }
        for (int ligne = 0; ligne < 6; ligne++) {
            compteur += chercheAlignes(0, ligne, 1, 1, numeroJoueur, nombre);
            compteur += chercheAlignes(7 - 1, ligne, -1, 1, numeroJoueur, nombre);
        }
        return compteur;
    }

    // ******************** chercheAlignes ********************************
    private int chercheAlignes(int oCol, int oLigne, int dCol, int dLigne, int joueur, int nombre) {
        int compteurJeton = 0;
        int compteurAlignes = 0;
        int curCol = oCol;
        int curRow = oLigne;
        int precedent = -1;
        while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)) {
            if (tab[curRow][curCol] != joueur) {
                if ((compteurJeton == nombre) && (precedent == 0 || tab[curRow][curCol] == 0)) {
                    compteurAlignes++;
                }
                compteurJeton = 0;
                precedent = tab[curRow][curCol];
            } else {
                compteurJeton++;
            }
            curCol += dCol;
            curRow += dLigne;
        }
        return compteurAlignes;
    }

    // ******************** cherche4 ********************************
    public int cherche4() {
        int vainqueur = -1;
        for (int ligne = 0; ligne < 6; ligne++) {
            vainqueur = cherche4alignes(0, ligne, 1, 0);
            if (vainqueur != -1) {
                return vainqueur;
            }
        }

        for (int col = 0; col < 7; col++) {
            vainqueur = cherche4alignes(col, 0, 0, 1);
            if (vainqueur != -1) {
                return vainqueur;
            }
        }

        for (int col = 0; col < 7; col++) {

            vainqueur = cherche4alignes(col, 0, 1, 1);
            if (vainqueur != -1) {
                return vainqueur;
            }
            vainqueur = cherche4alignes(col, 0, -1, 1);
            if (vainqueur != -1) {
                return vainqueur;
            }
        }

        for (int ligne = 0; ligne < 6; ligne++) {
            vainqueur = cherche4alignes(0, ligne, 1, 1);

            if (vainqueur != -1) {
                return vainqueur;
            }
            vainqueur = cherche4alignes(7 - 1, ligne, -1, 1);
            if (vainqueur != -1) {
                return vainqueur;
            }
        }
        return -1;
    }

    // ******************** cherche4alignes ********************************
    private int cherche4alignes(int oCol, int oLigne, int dCol, int dLigne) {
        int couleur = 0;
        int compteur = 0;
        int curCol = oCol;
        int curRow = oLigne;

        while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)) {
            if (tab[curRow][curCol] != couleur) {
                couleur = tab[curRow][curCol];
                compteur = 1;
            } else {
                compteur++;
            }
            if ((couleur != 0) && (compteur == 4)) {
                return couleur;
            }
            curCol += dCol;
            curRow += dLigne;
        }
        return -1;
    }

    // ******************** isTermine ********************************
    public boolean isTermine() {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                if (this.tab[j][i] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // ******************** getNbrNoeuds ********************************
    public int getNbrNoeuds() {
        return nbrNoeuds;
    }

    // ******************** getDerniereColonne ********************************
    public int getDerniereColonne() {
        return derniereColonne;
    }

    // ******************** colonne ********************************
    public int colonne(Joueur joueur) {
        if (getNbrJeton() > 1) {
            this.nbrNoeuds = 0;
            int max = -10000000;
            ArrayList<Integer> choix = new ArrayList<Integer>();
            int colonne = -1;
            int profondeur = joueur.getNiveau();

            for (int i = 0; i < 7; i++) {
                if (placeDispo(i)) {
                    this.jouerCoup(i, joueur.getNumero());
                    int evaluation = this.min(profondeur - 1, joueur.getNumero(), joueur);

                    if (evaluation > max) {
                        max = evaluation;
                        choix.clear();
                        choix.add(i);
                    } else if (evaluation == max) {
                        choix.add(i);
                    }
                    this.annulerCoup(i);
                }
            }
            Collections.shuffle(choix);
            colonne = choix.get(0);
            return colonne;
        } else {
            this.derniereColonne = 7 / 2;
            return 7 / 2;
        }
    }

    // ******************** max ********************************

    private int max(int profondeur, int joueur, Joueur joueurEnCours) {
        if (joueur == 1) {
            joueur = 2;
        } else {
            joueur = 1;
        }
        if (profondeur == 0 || this.isTermine() || this.cherche4() != -1) {
            int eval = this.eval2(joueurEnCours, profondeur);
            return eval;
        }
        int max = -10000000;
        for (int i = 0; i < 7; i++) {
            if (placeDispo(i)) {
                this.jouerCoup(i, joueur);
                int evaluation = this.min(profondeur - 1, joueur, joueurEnCours);

                if (evaluation > max) {
                    max = evaluation;
                }
                this.annulerCoup(i);
            }

        }
        return max;
    }

    // ******************** min ********************************
    private int min(int profondeur, int joueur, Joueur joueurEnCours) {
        if (joueur == 1) {
            joueur = 2;
        } else {
            joueur = 1;
        }
        if (profondeur == 0 || this.isTermine() || this.cherche4() != -1) {
            int eval = this.eval2(joueurEnCours, profondeur);
            return eval;
        }
        int min = 10000000;
        for (int i = 0; i < 7; i++) {
            if (placeDispo(i)) {
                this.jouerCoup(i, joueur);
                int evaluation = this.max(profondeur - 1, joueur, joueurEnCours);

                if (evaluation < min) {
                    min = evaluation;
                }
                this.annulerCoup(i);
            }

        }
        return min;
    }

    // ******************** eval2 ********************************
    private int eval2(Joueur joueur, int profondeur) {
        int vainqueur = this.cherche4();
        int niveau = 1;
        if (vainqueur == joueur.getNumero()) {
            return 100000 - (niveau - profondeur);
        } else if (vainqueur == -1) {
            int adversaire;
            if (joueur.getNumero() == 1) {
                adversaire = 0;
            } else {
                adversaire = 1;
            }
            int eval = cherche(joueur.getNumero(), 3) * 100 - cherche(adversaire, 3) * 100 + cherche(joueur.getNumero(), 2) * 50 - cherche(adversaire, 2) * 50;
            return eval;

        } else {
            return -100000 + (niveau - profondeur);
        }

    }

    // ******************** RANDOM ********************************
    public int colonneRandom() {
        int colonne = -1;
        int min = 0;
        int max = 6;
        colonne = (int) (Math.random() * (max - min)) + min;

        return colonne;
    }
    // ******************** FIN RANDOM ********************************        

    // ********** APLHA BETA *******************
    public int alphabeta(int joueur, Joueur joueurEnCours) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        if (getNbrJeton() > 1) {
            this.nbrNoeuds = 0;
            int max = -10000000;
            ArrayList<Integer> choix = new ArrayList<Integer>();
            int colonne = -1;
            int profondeur = joueurEnCours.getNiveau();

            for (int i = 0; i < 7; i++) {
                if (placeDispo(i)) {
                    this.jouerCoup(i, joueur);

                    int evaluation = this.minAlphaBeta(profondeur - 1, joueur, joueurEnCours, alpha, beta);

                    if (alpha < evaluation) {
                        alpha = evaluation;
                        choix.clear();
                        choix.add(i);
                    } else if (evaluation == alpha) {
                        choix.add(i);
                    }

                    this.annulerCoup(i);
                }
            }
            colonne = choix.get(0);
            return colonne;
        } else {
            this.derniereColonne = 7 / 2;
            return 7 / 2;
        }
    }

    ;
        
        // ******************** maxAplhaBeta ********************************
	private int maxAplhaBeta(int profondeur, int joueur, Joueur joueurEnCours, int alpha, int beta) {
        if (joueur == 1) {
            joueur = 2;
        } else {
            joueur = 1;
        }
        if (profondeur == 0 || this.isTermine() || this.cherche4() != -1) {
            int eval = this.eval2(joueurEnCours, profondeur);
            return eval;
        }

        for (int i = 0; i < 7; i++) {
            if (placeDispo(i)) {
                this.jouerCoup(i, joueur);
                int max = this.minAlphaBeta(profondeur - 1, joueur, joueurEnCours, alpha, beta);

                this.annulerCoup(i);

                if (alpha < max) {
                    alpha = max;
                }
                if (beta <= alpha) {
                    return alpha;
                }
            }
        }
        return alpha;
    }

    // ******************** minAlphaBeta ********************************

    private int minAlphaBeta(int profondeur, int joueur, Joueur joueurEnCours, int alpha, int beta) {
        if (joueur == 1) {
            joueur = 2;
        } else {
            joueur = 1;
        }
        if (profondeur == 0 || this.isTermine() || this.cherche4() != -1) {
            int eval = this.eval2(joueurEnCours, profondeur);
            return eval;
        }
        for (int i = 0; i < 7; i++) {
            if (placeDispo(i)) {
                this.jouerCoup(i, joueur);
                int min = this.maxAplhaBeta(profondeur - 1, joueur, joueurEnCours, alpha, beta);

                this.annulerCoup(i);

                if (beta > min) {
                    beta = min;
                }
                if (beta <= alpha) {

                    return beta;
                }
            }
        }
        return beta;
    }
        // ********** FIN APLHA BETA *******************

    // ******************** getNbrJetons ********************************
    public int getNbrJeton() {
        int compteur = 0;
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                if (this.tab[j][i] != 0) {
                    compteur++;
                }
            }
        }
        return compteur;
    }

	//************************** GET & SET **************************
    public int[][] getTab() {
        return tab;
    }

    public void setTab(int[][] tab) {
        this.tab = tab;
    }

    int trouverPosLigne(int i) {
        int j = 5;
        while (tab[i - 1][j] != 0) {
            j--;
        }
        return j;
    }

    //http://icwww.epfl.ch/~sam/ipo/series/serie07/ex6/solution_print.html
    private boolean cherchequatrealignes(int oCol, int oLigne, int dCol, int dLigne, int couleur) {

        int compteur = 0;

        int curCol = oCol;
        int curRow = oLigne;

        while ((curCol >= 0) && (curCol < 6) && (curRow >= 0) && (curRow < 7)) {
            if (tab[curRow][curCol] != couleur) {
                couleur = tab[curRow][curCol];
                compteur = 1;
            } else {
                compteur++;
            }

            if ((couleur != 0) && (compteur == 4)) {
                return true;
            }

            curCol += dCol;
            curRow += dLigne;
        }
        return false;
    }

    //http://icwww.epfl.ch/~sam/ipo/series/serie07/ex6/solution_print.html
    public boolean verifie(int couleur) {
        // Horizontal
        for (int ligne = 0; ligne < 6; ligne++) {
            if (cherchequatrealignes(0, ligne, 1, 0, couleur)) {
                return true;
            }
        }

        // Vertical
        for (int col = 0; col < 7; col++) {
            if (cherchequatrealignes(col, 0, 0, 1, couleur)) {
                return true;
            }
        }

        // Diagonals
        for (int col = 0; col < 7; col++) {
            if (cherchequatrealignes(col, 0, 1, 1, couleur)) {
                return true;
            }
            if (cherchequatrealignes(col, 0, -1, 1, couleur)) {
                return true;
            }
        }
        for (int ligne = 0; ligne < 6; ligne++) {
            if (cherchequatrealignes(0, ligne, 1, 1, couleur)) {
                return true;
            }
            if (cherchequatrealignes(7 - 1, ligne, -1, 1, couleur)) {
                return true;
            }
        }
        return false;
    }

}
