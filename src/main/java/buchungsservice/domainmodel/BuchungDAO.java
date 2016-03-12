package buchungsservice.domainmodel;

import java.util.List;

public interface BuchungDAO {
	
	public void insertBuchung(int zug_id, int platz_id);
	public List<Buchung> getBuchungen(int from_id);
}
