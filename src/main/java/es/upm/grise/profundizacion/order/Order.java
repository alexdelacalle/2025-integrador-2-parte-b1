package es.upm.grise.profundizacion.order;

import java.util.ArrayList;
import java.util.Collection;

import es.upm.grise.exceptions.IncorrectItemException;

public class Order {

    private Collection<Item> items;

	/*
	 * Constructor
	 */
    public Order() {
    	
        this.items = new ArrayList<Item>();
        
    }

	/*
	 * Method to code / test
	 */
    public void addItem(Item item) throws IncorrectItemException {

        if (item.getPrice() < 0) {
        	
            throw new IncorrectItemException();
            
        }

        if (item.getQuantity() <= 0) {
        	
            throw new IncorrectItemException();
            
        }

        for (Item i : items) {
        	
            if (i.getProduct().equals(item.getProduct())) {
                
                if (Double.compare(i.getPrice(), item.getPrice()) == 0) {
                	
                    i.setQuantity(i.getQuantity() + item.getQuantity());
                    return;

                } else {
                	
                    items.add(item);
                    return;
                	
                }
            }
        }

        items.add(item);
    }
    
	/*
	 * Setters/getters
	 */
    public Collection<Item> getItems() {
    	
    	return this.items;
    	
    }

}

