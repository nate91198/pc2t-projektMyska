
public class Ucebnice extends Kniha{
	private int vhodneProRocnik;

	public int getVhodneProRocnik() {
		return vhodneProRocnik;
	}

	public void setVhodneProRocnik(int vhodneProRocnik) {
		this.vhodneProRocnik = vhodneProRocnik;
	}

	public Ucebnice(String nazevKnihy, String autor, int rokVydani, boolean dostupnost, int vhodneProRocnik) {
		super(nazevKnihy, autor, rokVydani, dostupnost);
		this.vhodneProRocnik = vhodneProRocnik;
	}

	public void setZanr(String zanr) {
		assert true;
	}

	public String getZanr() {
		return "-";
	}
	

}
