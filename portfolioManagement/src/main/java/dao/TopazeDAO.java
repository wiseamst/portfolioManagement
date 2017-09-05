package dao;

public class TopazeDAO {

	public void req_tw_instrument() {
		String req= "SELECT NBINS,COINS,TYINS,NAINS,ISIN,TICKER,TYCLA,COCCY,COGEO FROM topazeweb.tw_instrument;";
	}
	
	public void req_tw_portfolio() {
		String req= "SELECT NBINS,NBENT,NBCTR,COENT,TYENT,NAENT,TYCTR,ISIN,DASTA,COCCY,CODOM,COCTD,NBBEN,EMAIL,COADM,COCGP,AMNAV,DANAV FROM  topazeweb.tw_portfolio;";		
	}
	
	public void req_tw_position() {
		String req= "SELECT NBINS,NBPOS,QTPOS,VAPOS,WGPOS FROM topazeweb.tw_position;";		
	}
	public void req_tw_price() {
		String req= "SELECT NBINS,DAPRI,CLOSE FROM topazeweb.tw_price;";		
	}
	public void req_tw_return() {
		String req= "SELECT NBINS,DARET,PRICE,VARET FROM topazeweb.tw_return;";		
	}
}
