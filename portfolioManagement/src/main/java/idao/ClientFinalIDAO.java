package idao;

import model.ClientFinal;

public interface ClientFinalIDAO {

	public void checkFictif();
	
	public void insertWiseamClient(ClientFinal clientFinal);
	
	public ClientFinal findByClientFinalId(int clientId);
}
