package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import idao.PortefeuilleGIDAO;
import model.PortefeuilleG;

public class PortefeuilleGDAO implements PortefeuilleGIDAO {

	private DataSource dataSourceTopaze;
	private DataSource dataSourceWiseam;
	
	
	public DataSource getDataSourceTopaze() {
		return dataSourceTopaze;
	}


	public void setDataSourceTopaze(DataSource dataSourceTopaze) {
		this.dataSourceTopaze = dataSourceTopaze;
	}


	public DataSource getDataSourceWiseam() {
		return dataSourceWiseam;
	}


	public void setDataSourceWiseam(DataSource dataSourceWiseam) {
		this.dataSourceWiseam = dataSourceWiseam;
	}

	public void insert(PortefeuilleG portefeuilleG) {
		// TODO Auto-generated method stub
		
	}

	public void insertPortefeuilleG(PortefeuilleG portefeuilleG){

		String sql = "INSERT INTO PORTEFEUILLEG " +
				"(NOM, NUMCONTRAT, DEVISE, ACTIFNET, VL," + 
				"DATEMAJ, FORMEJURIDIQUEPTF, DEPOSITAIRE, VALORISATEUR, MONTANTINITIAL," + 
				"BIC, IBAN, CATAMF, DATECREATION, CLASSIFICATIONAMF," + 
				"DUREERECOMMANDEE, AFFECTATIONRESULTAT, CENTRALORDRE, REGLEMENTLIVRAISON," + 
				"FRAISENTREE, FRAISSORTIE, fDGF, fDGV, COMMENTAIREGESTION," + 
				"NIVEAURISQUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + 
		        "?, ?, ?, ?,?, ?, ?, ?, ?,?)";
		
		        Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, portefeuilleG.getNom());
			ps.setString(2, portefeuilleG.getNumContrat());
			ps.setString(3, portefeuilleG.getDevise());
			ps.setFloat(4, portefeuilleG.getActifNet());
			ps.setFloat(5, portefeuilleG.getVl());
			ps.setDate(6, portefeuilleG.getDateMAJ());
			ps.setString(7, portefeuilleG.getFormeJuridiquePTF());
			ps.setString(8, portefeuilleG.getDepositaire());
			ps.setString(9, portefeuilleG.getValorisateur());
			ps.setFloat(10, portefeuilleG.getMontantInitial());
			ps.setString(11, portefeuilleG.getBic());
			ps.setString(12, portefeuilleG.getIban());
			ps.setString(13, portefeuilleG.getCatAMF());
			ps.setDate(14, portefeuilleG.getDateCreation());
			ps.setString(15, portefeuilleG.getClassificationAMF());
			ps.setString(16, portefeuilleG.getDureeRecommandee());
			ps.setString(17, portefeuilleG.getAffectationResultat());
			ps.setString(18, portefeuilleG.getCentralOrdre());
			ps.setString(19, portefeuilleG.getReglementLivraison());
			ps.setFloat(20, portefeuilleG.getFraisEntree());
			ps.setFloat(21, portefeuilleG.getFraisSortie());
			ps.setFloat(22, portefeuilleG.getFDGF());
			ps.setFloat(23, portefeuilleG.getFDGV());
			ps.setString(24, portefeuilleG.getCommentaireGestion());
			ps.setString(25, portefeuilleG.getNiveauRisque());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public PortefeuilleG findByPortefeuilleGId(int portefeuilleGId){

		String sql = "SELECT * FROM PORTEFEUILLEG WHERE IDPORTEFG = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, portefeuilleGId);
			PortefeuilleG portefeuilleG = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				portefeuilleG = new PortefeuilleG("", "", "", 0, 0,
						null, "", "", "", 0,
						"", "", "", null, "",
						"", "", "", "",
						0, 0, 0 ,0, "",
						"");
			}
			rs.close();
			ps.close();
			return portefeuilleG;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
