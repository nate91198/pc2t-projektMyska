import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
	private Connection conn;

	public boolean connect() {
		conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:knihy.db");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public boolean createTable() {
		if (conn == null)
			return false;
		String sql = "CREATE TABLE IF NOT EXISTS knihy (" + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ "nazevKnihy TEXT NOT NULL, " + "autor TEXT NOT NULL, " + "rokVydani INTEGER, " + "zanr TEXT, "
				+ "vhodneProRocnik INTEGER, " + "dostupnost BOOLEAN NOT NULL)";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public void insertRecord(ArrayList<Kniha> knihy) {
		String sql = "INSERT INTO knihy (nazevKnihy, autor, rokVydani, zanr, vhodneProRocnik, dostupnost) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement statementInsert = conn.prepareStatement(sql);
			for (Kniha kniha1 : knihy) {
				statementInsert.setString(1, kniha1.getNazevKnihy());
				statementInsert.setString(2, kniha1.getAutor());
				statementInsert.setInt(3, kniha1.getRokVydani());
				statementInsert.setString(4, kniha1.getZanr());
				statementInsert.setInt(5, kniha1.getVhodneProRocnik());
				statementInsert.setBoolean(6, kniha1.getDostupnost());
				statementInsert.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\n\033[32mZáznamy byly úspěšně vloženy do databáze!\033[0m");
	}

	public void loadData(ArrayList<Kniha> knihy) {
		String url = "jdbc:sqlite:knihy.db";

		try (Statement statement = conn.createStatement()) {
			String query = "SELECT * FROM knihy";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String nazevKnihy = resultSet.getString("nazevKnihy");
				String autor = resultSet.getString("autor");
				int rokVydani = resultSet.getInt("rokVydani");
				String zanr = resultSet.getString("zanr");
				int vhodneProRocnik = resultSet.getInt("vhodneProRocnik");
				boolean dostupnost = resultSet.getBoolean("dostupnost");
				if (zanr.equals("-")) {
					knihy.add(new Ucebnice(nazevKnihy, autor, rokVydani, dostupnost, vhodneProRocnik));
				} else if (vhodneProRocnik == -1) {
					knihy.add(new Roman(nazevKnihy, autor, rokVydani, dostupnost, zanr));
				}
			}

		} catch (SQLException e) {
			System.out.println("Nastala chyba při práci s databází: " + e.getMessage());
		}
	}
	public boolean dropTable() {
		if (conn == null)
			return false;

        try (Statement statement = conn.createStatement()) {
            String query = "DROP TABLE IF EXISTS knihy";
            statement.executeUpdate(query);
            return true;
            } catch (SQLException e) {
            System.out.println("Nastala chyba při práci s databází: " + e.getMessage());
        }
        return false;
	}

}