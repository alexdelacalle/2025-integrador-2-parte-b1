package es.upm.grise.profundizacion.order;

public interface Item {
	
	Product getProduct();
	int getQuantity();
	void setQuantity(int i);
	double getPrice();

}
