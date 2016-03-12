package buchungsservice.domainmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class JdbcBuchungDAO implements BuchungDAO {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	} 
	public void insertBuchung(int zug_id, int platz_id) {
		
		String sql = "INSERT INTO buchung " + "(zug_id, platz_id) VALUES (?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, zug_id);
			ps.setInt(2, platz_id);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public List<Buchung> getBuchungen(int from_id) {
		String sql = "SELECT * FROM buchung WHERE id > ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, from_id);
			List<Buchung> buchungen = new ArrayList<Buchung>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				buchungen.add(new Buchung(rs.getInt("ID"), rs.getInt("ZUG_ID"), rs.getInt("PLATZ_ID")));
			}
			rs.close();
			ps.close();
			return buchungen;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
