public abstract class Kniha implements Comparable<Kniha> {
	private String nazevKnihy;
	private String autor;
	private int rokVydani;
	private boolean dostupnost;

	public String getNazevKnihy() {
		return nazevKnihy;
	}

	public void setNazevKnihy(String nazevKnihy) {
		this.nazevKnihy = nazevKnihy;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getRokVydani() {
		return rokVydani;
	}

	public void setRokVydani(int rokVydani) {
		this.rokVydani = rokVydani;
	}
	
	public void changeDostupnost() {
		if (dostupnost == true) {
			dostupnost = false;
		}
		else if (dostupnost == false) {
			dostupnost = true;
		}
	}
	
	public Boolean getDostupnost() {
		return dostupnost;
	}

	public void setDostupnost(boolean dostupnost) {
		this.dostupnost = dostupnost;
	}
	
	public Kniha(String nazevKnihy, String autor, int rokVydani, boolean dostupnost) {
		super();
		this.nazevKnihy = nazevKnihy;
		this.autor = autor;
		this.rokVydani = rokVydani;
		this.dostupnost = dostupnost;
	}
	
	public int compareTo(Kniha other) {
        return this.nazevKnihy.compareTo(other.getNazevKnihy());
    }

	public abstract void setZanr(String zanr);

	public abstract void setVhodneProRocnik(int vhodneProRocnik);

	public abstract String getZanr();

	public abstract int getVhodneProRocnik();
}
