package NO2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Morpion {
	static int[] row = new int[9];
	static boolean joueur = true;
	static boolean isJvJ = true;
	static boolean isGameRunning = true;
	static Computer Harold = new Computer();
	
	public static void main (String[] args) {
		
		for (int i = 0; i < 9 ; i++ ) {
			row[i] = 0;
		}
		
		nettoyer();
		initialiserLeJeu();
		
		boolean isCoupValide = true;
		String str = "";
		while ((!isJoueurGagne(row,1) && !isJoueurGagne(row,2)) && !isGrillePleine() && isGameRunning) {
			
			nettoyer();
			
			if (!isCoupValide) {
				System.out.println("Veuillez rejouer, vous avez saisi des coordonnées incorrectes.\n");
			}
			
			afficherGrille(row);
			
			if (isJvJ) {
				System.out.println("C'est au J"+joueur(joueur)+" de jouer.");
			}
			else {
				if (joueur) {
					System.out.println("C'est à votre tour de jouer.");
				}
				else {
					System.out.println("C'est à "+Harold.name+" de jouer.");
					Harold.play(row);
					
					//Pause de 3s
					try {
						Thread.sleep(3000);
					}
					catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					joueur = !joueur;
					continue;
				}
			}
			
			
			Scanner source = new Scanner(System.in);
			str = source.nextLine();
			if (str.toLowerCase().equals("quitter") || str.toLowerCase().equals("'quitter'")) {
				break;
			}
			
			if (jouer(str)) {
				joueur = !joueur;
				isCoupValide=true;
			}
			else {
				isCoupValide=false;
			}
		}
		
		nettoyer();
		
		afficherGrille(row);
		
		if (isJoueurGagne(row,1) || isJoueurGagne(row,2)) {
			if (isJvJ) {
				System.out.println("Le joueur "+joueur(!joueur)+" a gagné !");
			}
			else {
				if (isJoueurGagne(row,1)) {
					System.out.println("Vous avez gagné !");
					System.out.println(Harold.name+" : Je n'étais pas à la hauteur, on s'en refait une ?");
				}
				else {
					System.out.println(Harold.name+" a gagné !");
				}
			}
		}
		else if (isGrillePleine()) {
			System.out.println("Match nul ! Vous avez rempli la grille de jeu sans qu'un joueur ne gagne.");
		}
		else if (str.toLowerCase().equals("quitter") || str.toLowerCase().equals("'quitter'") || !isGameRunning) {
			System.out.println("Vous avez mis fin au jeu !");
		}
		else {
			System.out.println("Une erreur est survenue...");
		}
	}
	
	private static void afficherGrille (int[] row) {
		int index = 0;
		int index2 = 1;
		String toPrint = "|";
		
		System.out.println("Pour jouer, veuillez saisir la coordonnée de la colonne puis celle de la ligne de la cellule que vous souhaitez jouer.\nPar exemple : 'a1'.\nPour quitter le jeu, vous pouvez saisir 'quitter'.");
		
		for (int cell : row) {
			if (cell == 1) {
				toPrint+="X";
			}
			else if (cell == 2) {
				toPrint+="O";
			}
			else {
				toPrint+=" ";
			}
			toPrint += "|";
			if (index == 2) {
				System.out.println(toPrint+" "+index2);
				toPrint = "|";
				index=0;
				index2++;
			}
			else {
				index+=1;
			}
		}
		System.out.println(" a b c");
	}
	
	static boolean isJoueurGagne (int[] row,int player) {
		int[][] lib = new int[8][9];
		lib[0]=new int[] {1,3,3,3,1,3,3,3,1};
		lib[1]=new int[] {3,3,1,3,1,3,1,3,3};
		lib[2]=new int[] {1,3,3,1,3,3,1,3,3};
		lib[3]=new int[] {3,1,3,3,1,3,3,1,3};
		lib[4]=new int[] {3,3,1,3,3,1,3,3,1};
		lib[5]=new int[] {1,1,1,3,3,3,3,3,3};
		lib[6]=new int[] {3,3,3,1,1,1,3,3,3};
		lib[7]=new int[] {3,3,3,3,3,3,1,1,1};
		
		int score = 0;
		for (int[] winRow : lib) {
			for (int i = 0 ; i < 9 ; i++) {
				if (row[i] == winRow[i]*player) {
					score +=1;
				}
			}
			if (score == 3) {
				return true;
			}
			else {
				score = 0;
			}
		}
		return false;
	}
	
	private static void afficherIntTableau (int[] array) {
		String tabString = "";
		for (int tab : array) {
			tabString+=tab;
		}
		System.out.println(tabString);
	}
	
	static boolean jouer (String args) {
		boolean statut = false;
		List<String> position = new ArrayList<String>();
		position.add("a1");
		position.add("b1");
		position.add("c1");
		position.add("a2");
		position.add("b2");
		position.add("c2");
		position.add("a3");
		position.add("b3");
		position.add("c3");
		
		if (position.indexOf(args)!=-1) {
			if (row[position.indexOf(args)]==0) {
			row[position.indexOf(args)]=joueur(joueur);
			statut = true;
			}
		}
		
		return statut;
	}
	
	private static int joueur(boolean player) {
		if (player) {
			return 1;
		}
		else {
			return 2;
		}
	}
	
	private static boolean isGrillePleine () {
		boolean retour = true;
		for (int i : row) {
			if (i==0) {
				retour=false;
			}
		}
		return retour;
	}
	
	private static void initialiserLeJeu() {
		System.out.println("Veuillez indiquer le mode de jeu souhaité par '1' ou '2':\n-JvJ (Joueur contre joueur)\n-JvC (Joueur contre ordinateur)");
		System.out.println("Si vous souhaitez quitter ou annuler la partie indiquez le par 'quitter'.");
		boolean flag = true;
		while (flag) {
			Scanner source = new Scanner(System.in);
			String str = source.nextLine();
			flag = false;
			if (str.equals("1")||str.equals("'1'")) {
				isJvJ = true;
			}
			else if (str.equals("2")||str.equals("'2'")) {
				isJvJ = false;
				Harold.saluer();
			}
			else if (str.toLowerCase().indexOf("quitter")!=-1||str.toLowerCase().indexOf("annuler")!=-1||str.toLowerCase().indexOf("arreter")!=-1) {
				isGameRunning = false;
				break;
			}
			else {
				flag = true;
				System.out.println("Erreur, veuillez rentrer une réponse correcte.");
			}
		}
		
	}
	
	static void nettoyer() {
		for (int i = 0; i < 20; i++) {
			System.out.println("\n");
		}
	}
}

class Computer {
	String name = "Harold";
	
	Computer() {
		this.name=name;
	}
	
	void play (int[] row) {
		int[] a1 = new int[] {1,3,3,3,3,3,3,3,3};
		int[] a2 = new int[] {3,3,3,1,3,3,3,3,3};
		int[] a3 = new int[] {3,3,3,3,3,3,1,3,3};
		int[] b1 = new int[] {3,1,3,3,3,3,3,3,3};
		int[] b2 = new int[] {3,3,3,3,1,3,3,3,3};
		int[] b3 = new int[] {3,3,3,3,3,3,3,1,3};
		int[] c1 = new int[] {3,3,1,3,3,3,3,3,3};
		int[] c2 = new int[] {3,3,3,3,3,1,3,3,3};
		int[] c3 = new int[] {3,3,3,3,3,3,3,3,1};
		
		int[] coup = prochainCoupGagne(row);
		
		if (coup[0]!=0) {
			switch(coup[1]) {
				case 0:
					Morpion.jouer("a1");
					break;
				case 1:
					Morpion.jouer("b1");
					break;
				case 2:
					Morpion.jouer("c1");
					break;
				case 3:
					Morpion.jouer("a2");
					break;
				case 4:
					Morpion.jouer("b2");
					break;
				case 5:
					Morpion.jouer("c2");
					break;
				case 6:
					Morpion.jouer("a3");
					break;
				case 7:
					Morpion.jouer("b3");
					break;
				case 8:
					Morpion.jouer("c3");
					break;
			}
			if (coup[0]==1) {
				System.out.println(this.name+" : Je pare !");
			}
		}
		else {
			if (!isComparerTableauInt(row,b2)) {
				Morpion.jouer("b2");
				System.out.println(this.name+" : Je prend le centre !");
			}
			else if (!isComparerTableauInt(row,a1)) {
				Morpion.jouer("a1");
				System.out.println(this.name+" : Comment à tu pu laisser cette cellule vide ?!");
			}
			else if (!isComparerTableauInt(row,c1)) {
				Morpion.jouer("c1");
				System.out.println(this.name+" : C'est ma cellule préférée !");
			}
			else if (!isComparerTableauInt(row,c3)) {
				Morpion.jouer("c3");
				System.out.println(this.name+" : C3 est parfait ici.");
			}
			else if (!isComparerTableauInt(row,a3)) {
				Morpion.jouer("a3");
				System.out.println(this.name+" : Ce coin est à moi !");
			}
			else if (!isComparerTableauInt(row,b1)) {
				Morpion.jouer("b1");
				System.out.println(this.name+" : Je vais gagner !");
			}
			else if (!isComparerTableauInt(row,c2)) {
				Morpion.jouer("c2");
				System.out.println(this.name+" : Pourquoi pas ce coup là ...");
			}
			else if (!isComparerTableauInt(row,b3)) {
				Morpion.jouer("b3");
				System.out.println(this.name+" : Je joue ici !");
			}
			else if (!isComparerTableauInt(row,a2)) {
				Morpion.jouer("a2");
				System.out.println(this.name+" : Je pense que ce coup est le mieux.");
			}
			else {
				System.out.println(this.name+" : Bug buggg buuuuggggggggggggg !!!");
			}
		}
	}
	
	boolean isComparerTableauInt(int[] tab1, int[] tab2) {
		boolean result = false;
		int i = 0;
		for (int tab : tab1) {
			if (tab == tab2[i]) {result = true;}
			if (tab == tab2[i]+1) {result = true;}
			i += 1;
		}
		return result;
	}
	
	int[] prochainCoupGagne (int[] row) {
		int[] winRow1 = new int[row.length];
		int[] winRow2 = new int[row.length];
		int[] result = new int[2];
		int prochainCoupGagne = 0;
		int coupGagnant=-1;
		
		//Remplissage du tableau winRow avec row
		int index = 0;
		for (int cellule : row) {
			winRow1[index] = cellule;
			winRow2[index] = cellule;
			index+=1;
		}
		index=0;
		
		//Vérification d'un coup gagnant
		for (int i = 0; i < row.length; i++) {
			winRow1[i] = 1;
			winRow2[i] = 2;
			if (row[i]==0) {
				if (Morpion.isJoueurGagne(winRow2,2)) {
					prochainCoupGagne = 2;
					coupGagnant = i;
					System.out.println(this.name+" : Je vois la victoire.");
					
					result[0]=prochainCoupGagne;
					result[1]=coupGagnant;
					return result;
				}
				else if (Morpion.isJoueurGagne(winRow1,1)) {
					prochainCoupGagne = 1;
					coupGagnant = i;
				}
			}
			//Remise à zero du tableau winRow
			for (int cellule : row) {
				winRow1[index] = cellule;
				winRow2[index] = cellule;
				index+=1;
			}
			index=0;
		}
		
		result[0]=prochainCoupGagne;
		result[1]=coupGagnant;
		return result;
	}
	
	void saluer () {
		Morpion.nettoyer();
		System.out.println("Saluuuttt ! Moi c'est "+this.name+" ! Je suis là pour faire cette partie avec toi,\nbonne chance !");
		System.out.println("\nAppuyez sur la touche 'Entrée' pour lancer la partie.");
		Scanner source = new Scanner(System.in);
		String str = source.nextLine();
	}
}