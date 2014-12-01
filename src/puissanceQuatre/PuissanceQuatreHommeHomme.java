package puissanceQuatre;

import java.util.Scanner;

public class PuissanceQuatreHommeHomme {
	public static void main(String[] args) {
		System.out.println("********** DEBUT DU JEU **********");
		Jeu jeu = new Jeu();
		jeu.afficher();
		Joueur joueur1 = new Joueur(1);
		Joueur joueur2 = new Joueur(2);
		Scanner sc = new Scanner(System.in);

		int isWin = -1;
		Joueur joueurCourant = joueur1;

		while(isWin!=1){
			System.out.println("JOUE UN COUP : JOUEUR " + joueurCourant.getNumero());
			System.out.println("NUMERO DE  COLONNE ? ");
			int colonne = sc.nextInt();
			jeu.jouerCoup(colonne-1, joueurCourant.getNumero());
			jeu.afficher();
			isWin = jeu.cherche4();
			System.out.println("******** "+isWin);
			if(joueurCourant.getNumero()== 1) joueurCourant=joueur2;
			else joueurCourant=joueur1;
			
		}
		
		System.out.println("Bravo le joueur "+joueurCourant.getNumero()+" a gagné");
	}
}
