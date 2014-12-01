package puissanceQuatre;

import java.util.Scanner;

public class PuissanceQuatreHommeRandom {
	public static void main(String[] args) {
		System.out.println("********** DEBUT DU JEU **********");
		Jeu jeu = new Jeu();
		jeu.afficher();
		Joueur joueur1 = new Joueur(1);
		Joueur ordinateurRandom = new Joueur(2);
		ordinateurRandom.setNiveau(1);
		Scanner sc = new Scanner(System.in);

		int isWin = -1;
		Joueur joueurCourant = joueur1;
		int colonne = 4;
		while(isWin==-1){
			if(joueurCourant.getNumero()== 1) {
				System.out.println("JOUE UN COUP : JOUEUR " + joueurCourant.getNumero());
				System.out.println("NUMERO DE  COLONNE ? ");
				colonne = sc.nextInt();
				System.out.println("****** colonne user "+colonne);
				jeu.jouerCoup(colonne-1, joueurCourant.getNumero());
				joueurCourant=ordinateurRandom;
			}
			else {
				colonne = jeu.colonneRandom();
				System.out.println("******** colonne random : "+colonne);
				jeu.jouerCoup(colonne, joueurCourant.getNumero());
				joueurCourant=joueur1;
			}
			
			jeu.afficher();
			isWin = jeu.cherche4();
			
		}
		
		if(joueurCourant.getNumero()==1) System.out.println("L'ordinateur a gagné");
		if(joueurCourant.getNumero()==2) System.out.println("Bravo vous avez gagné");

	}
    
}
