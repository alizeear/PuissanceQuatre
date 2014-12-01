package puissanceQuatre;

public class Joueur {

	private int _numero;
	private int _niveau;

	public Joueur(int numero){
		this._numero = numero;
	}

	void joueCoup(int i,int j,Jeu jeu){
		jeu.getTab()[i][j] = _numero;
	}

	public void retireCoup(int col, int ligne, Jeu jeu) {
		jeu.getTab()[col][ligne]=0;
	}

	// GET & SET

	public int getNumero() {
		return _numero;
	}

	public void setNumero(int _numero) {
		this._numero = _numero;
	}

	public int getNiveau() {
		return _niveau;
	}

	public void setNiveau(int _niveau) {
		this._niveau = _niveau;
	}


}
