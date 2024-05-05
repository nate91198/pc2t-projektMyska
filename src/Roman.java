
public class Roman extends Kniha{
	private String zanr;

	public String getZanr() {
		return zanr;
	}

	public void setZanr(String zanr) {
		this.zanr = zanr;
	}

	public Roman(String nazevKnihy, String autor, int rokVydani, boolean dostupnost, String zanr) {
		super(nazevKnihy, autor, rokVydani, dostupnost);
		this.zanr = zanr; //ZANRY: KOMEDIE, DRAMA, AKCNI, HORROR, FANTASY
	}

	public void setVhodneProRocnik(int vhodneProRocnik) {
		assert true;
	}

	public int getVhodneProRocnik() {
		return -1;
	}
}
