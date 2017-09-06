package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.orm.hibernate5.HibernateTemplate;

import idao.PortefeuilleIDAO;
import model.Portefeuille;

public class PortefeuilleDAO implements PortefeuilleIDAO {

	private DataSource dataSourceTopaze;
	private DataSource dataSourceWiseam;
	
	private HibernateTemplate hibernateWiseam;
	private HibernateTemplate hibernateTopaze;
	
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

	
	public HibernateTemplate getHibernateWiseam() {
		return hibernateWiseam;
	}


	public void setHibernateWiseam(HibernateTemplate hibernateWiseam) {
		this.hibernateWiseam = hibernateWiseam;
	}


	public HibernateTemplate getHibernateTopaze() {
		return hibernateTopaze;
	}


	public void setHibernateTopaze(HibernateTemplate hibernateTopaze) {
		this.hibernateTopaze = hibernateTopaze;
	}


	public void insert(Portefeuille portefeuille) {
		// TODO Auto-generated method stub
		
	}

	public void insertPortefeuille(Portefeuille portefeuille){

		String sql = "INSERT INTO PORTEFEUILLE " +
				"(NOM, NUMCONTRAT, DEVISE, ACTIFNET, VL," + 
				"DATEMAJ, FORMEJURIDIQUEPTF, DEPOSITAIRE, VALORISATEUR, MONTANTINITIAL," + 
				"BIC, IBAN, CATAMF, DATECREATION, CLASSIFICATIONAMF," + 
				"DUREERECOMMANDEE, AFFECTATIONRESULTAT, CENTRALORDRE, REGLEMENTLIVRAISON," + 
				"FRAISENTREE, FRAISSORTIE, fDGF, fDGV, COMMENTAIREGESTION," + 
				"NIVEAURISQUE, IDAR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + 
		        "?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, portefeuille.getNom());
			ps.setString(2, portefeuille.getNumContrat());
			ps.setString(3, portefeuille.getDevise());
			ps.setFloat(4, portefeuille.getActifNet());
			ps.setFloat(5, portefeuille.getVl());
			ps.setDate(6, portefeuille.getDateMAJ());
			ps.setString(7, portefeuille.getFormeJuridiquePTF());
			ps.setString(8, portefeuille.getDepositaire());
			ps.setString(9, portefeuille.getValorisateur());
			ps.setFloat(10, portefeuille.getMontantInitial());
			ps.setString(11, portefeuille.getBic());
			ps.setString(12, portefeuille.getIban());
			ps.setString(13, portefeuille.getCatAMF());
			ps.setDate(14, portefeuille.getDateCreation());
			ps.setString(15, portefeuille.getClassificationAMF());
			ps.setString(16, portefeuille.getDureeRecommandee());
			ps.setString(17, portefeuille.getAffectationResultat());
			ps.setString(18, portefeuille.getCentralOrdre());
			ps.setString(19, portefeuille.getReglementLivraison());
			ps.setFloat(20, portefeuille.getFraisEntree());
			ps.setFloat(21, portefeuille.getFraisSortie());
			ps.setFloat(22, portefeuille.getFDGF());
			ps.setFloat(23, portefeuille.getFDGV());
			ps.setString(24, portefeuille.getCommentaireGestion());
			ps.setString(25, portefeuille.getNiveauRisque());
			ps.setInt(26, portefeuille.getIdAR());
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

	public Portefeuille findByPortefeuilleId(int portefeuilleGId){

		String sql = "SELECT * FROM PORTEFEUILLE WHERE IDPORTEFG = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, portefeuilleGId);
			Portefeuille portefeuille = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				portefeuille = new Portefeuille("", "", "", 0, 0,
						null, "", "", "", 0,
						"", "", "", null, "",
						"", "", "", "",
						0, 0, 0 ,0, "",
						"",0);
			}
			rs.close();
			ps.close();
			return portefeuille;
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
