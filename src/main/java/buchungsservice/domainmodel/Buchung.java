package buchungsservice.domainmodel;

public class Buchung {

	// Im Gegensatz zum Platz beim Service braucht man hier die ID, damit nicht
	// immer alle Events abgefragt werden. Der Klient weiﬂ dann, bis wohin er
	// schonmal alles bekommen hat
	private int platz_id;
	private int zug_id;
	private int id;

	public Buchung(int id, int zug_id, int platz_id) {
		this.platz_id = platz_id;
		this.zug_id = zug_id;
		this.id = id;
	}

	public int getZugID() {
		return this.zug_id;
	}

	public int getPlatzID() {
		return this.platz_id;
	}

	public int getID() {
		return this.id;
	}
}
