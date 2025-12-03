
package es.upm.grise.profundizacion.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.grise.exceptions.IncorrectItemException;

class OrderTest {

    @Test
    @DisplayName("addItem: lanza IncorrectItemException si el precio es negativo")
    void addItem_throwsException_whenPriceNegative() {
        Order order = new Order();

        Item item = mock(Item.class);
        when(item.getPrice()).thenReturn(-10.0);
        when(item.getQuantity()).thenReturn(5);

        assertThrows(IncorrectItemException.class, () -> order.addItem(item));
    }

    @Test
    @DisplayName("addItem: lanza IncorrectItemException si la cantidad es <= 0")
    void addItem_throwsException_whenQuantityZeroOrNegative() {
        Order order = new Order();

        Item item = mock(Item.class);
        when(item.getPrice()).thenReturn(10.0);
        when(item.getQuantity()).thenReturn(0);

        assertThrows(IncorrectItemException.class, () -> order.addItem(item));
    }

    @Test
    @DisplayName("addItem: añade el item si no existe otro con el mismo producto")
    void addItem_addsNewItem_whenNoSameProduct() throws IncorrectItemException {
        Order order = new Order();

        Product product = mock(Product.class);
        Item item = mock(Item.class);
        when(item.getPrice()).thenReturn(10.0);
        when(item.getQuantity()).thenReturn(2);
        when(item.getProduct()).thenReturn(product);

        order.addItem(item);

        Collection<Item> items = order.getItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(item));
    }

    @Test
    @DisplayName("addItem: suma cantidades si existe item con mismo producto y mismo precio")
    void addItem_mergesQuantity_whenSameProductAndPrice() throws IncorrectItemException {
        Order order = new Order();

        Product product = mock(Product.class);

        Item existing = mock(Item.class);
        when(existing.getProduct()).thenReturn(product);
        when(existing.getPrice()).thenReturn(10.0);
        when(existing.getQuantity()).thenReturn(3);

        Item newItem = mock(Item.class);
        when(newItem.getProduct()).thenReturn(product);
        when(newItem.getPrice()).thenReturn(10.0);
        when(newItem.getQuantity()).thenReturn(2);

        order.getItems().add(existing);

        order.addItem(newItem);

        verify(existing).setQuantity(3 + 2); // Verifica que se sumó la cantidad
        assertEquals(1, order.getItems().size()); // No se añadió un nuevo item
    }

    @Test
    @DisplayName("addItem: añade nuevo item si mismo producto pero distinto precio")
    void addItem_addsNewItem_whenSameProductDifferentPrice() throws IncorrectItemException {
        Order order = new Order();

        Product product = mock(Product.class);

        Item existing = mock(Item.class);
        when(existing.getProduct()).thenReturn(product);
        when(existing.getPrice()).thenReturn(10.0);
        when(existing.getQuantity()).thenReturn(3);

        Item newItem = mock(Item.class);
        when(newItem.getProduct()).thenReturn(product);
        when(newItem.getPrice()).thenReturn(15.0);
        when(newItem.getQuantity()).thenReturn(2);

        order.getItems().add(existing);

        order.addItem(newItem);

        assertEquals(2, order.getItems().size()); // Se añadió un nuevo item
        assertTrue(order.getItems().contains(newItem));
    }
}
