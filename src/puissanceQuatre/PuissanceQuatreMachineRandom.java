package puissanceQuatre;

import java.util.Scanner;

public class PuissanceQuatreMachineRandom {
	public static void main(String[] args) {
		System.out.println("********** DEBUT DU JEU **********");
		Jeu jeu = new Jeu();
		jeu.afficher();
		Joueur ordinateurRandom = new Joueur(1);
		Joueur ordinateur = new Joueur(2);
                ordinateur.setNiveau(4);
		Scanner sc = new Scanner(System.in);

		int isWin = -1;
		Joueur joueurCourant = ordinateurRandom;
		int colonne = 4;
		while(isWin==-1){
			if(joueurCourant.getNumero()== 1) {
				colonne = jeu.colonneRandom();
				System.out.println("******** colonne random : "+colonne);
				jeu.jouerCoup(colonne, joueurCourant.getNumero());
				joueurCourant=ordinateur;
			}
			else {
                            
                                colonne = jeu.colonne(ordinateur);
				System.out.println("******** colonne auto "+colonne);
				jeu.jouerCoup(colonne, joueurCourant.getNumero());
				joueurCourant=ordinateurRandom;
			}
			
			jeu.afficher();
			isWin = jeu.cherche4();
			
		}
                
		if(joueurCourant.getNumero()==1) System.out.println("L'ordinateur a gagné");
		if(joueurCourant.getNumero()==2) System.out.println("L'ordinateur random a gagné");

	}
    
}
