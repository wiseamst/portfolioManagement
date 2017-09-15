package todelete;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class RunXMLjax {

	public static void main(String[] args) {

		  Customer customer = new Customer();
		  customer.setId(100);
		  customer.setCin(200);
		  customer.setName("mkyong");
		  customer.setAge(29);

		  try {

			File file = new File("C:\\Users\\stagiaire4\\Desktop\\fileaJAX.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(customer, System.out);

		      } catch (JAXBException e) {
			e.printStackTrace();
		      }

		}
}
