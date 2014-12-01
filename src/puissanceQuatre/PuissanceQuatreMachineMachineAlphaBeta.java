package puissanceQuatre;

import java.util.Scanner;

public class PuissanceQuatreMachineMachineAlphaBeta {

    public static void main(String[] args) {

        System.out.println("********** DEBUT DU JEU **********");
        Jeu jeu = new Jeu();
        jeu.afficher();
        Joueur ordinateur = new Joueur(1);
        Joueur ordinateurAlphaBeta = new Joueur(2);
        ordinateur.setNiveau(4);
        ordinateurAlphaBeta.setNiveau(4);
        Scanner sc = new Scanner(System.in);

        int isWin = -1;
        Joueur joueurCourant = ordinateur;
        int colonne = 4;
        while (isWin == -1) {
            if (joueurCourant.getNumero() == 1) {

                colonne = jeu.colonne(ordinateur);
                System.out.println("Choix colonne MinMax: " + colonne);
                jeu.jouerCoup(colonne, joueurCourant.getNumero());
                joueurCourant = ordinateurAlphaBeta;
            } else {

                colonne = jeu.alphabeta(joueurCourant.getNumero(), ordinateurAlphaBeta);
                System.out.println("Choix colonne Alpha Beta: " + colonne);
                jeu.jouerCoup(colonne, joueurCourant.getNumero());
                joueurCourant = ordinateur;
            }

            jeu.afficher();
            isWin = jeu.cherche4();

        }

        if (joueurCourant.getNumero() == 1) {
            System.out.println("Alpha Beta a gagné");
        }
        if (joueurCourant.getNumero() == 2) {
            System.out.println("MinMax a gagné");
        }

    }
}
