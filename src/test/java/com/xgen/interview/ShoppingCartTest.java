package com.xgen.interview;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A test suite for {@link ShoppingCart} with 100% code-coverage as of 29/01/2022 4:58pm
 * @since 1.0.0
 * */
public class ShoppingCartTest {

    private static final String LS = System.lineSeparator();

    @Test
    public void addItemTest() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("banana", 1);

        assertEquals("banana - 1 - €2.00" + LS + "Total: €2.00"
                + LS, cart.toString());
    }

    @Test
    public void addMultipleDistinctItemsTest() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("banana", 2);
        cart.addItem("apple", 3);

       assertEquals("banana - 2 - €4.00" + LS + "apple - 3 - €3.00"
               + LS + "Total: €7.00" + LS, cart.toString());
    }

    @Test
    public void addAdditionalItemsTest() {
        ShoppingCart cart = new ShoppingCart(new Pricer());

        cart.addItem("banana", 1);
        cart.addItem("banana", 3);

        assertTrue(cart.toString().contains("banana - 4 - €8.00"));
    }


    @Test
    public void addItemWithoutListedPrice() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("weird_item", 1);
        cart.addItem("weirder_item", 3);

        assertEquals("weird_item - 1 - €0.00" + LS
                + "weirder_item - 3 - €0.00" + LS + "Total: €0.00" + LS, cart.toString());
    }

    @Test
    public void alternativeReceiptTest() {
        ShoppingCart cart = new ShoppingCart(new Pricer(), ShoppingCart.ReceiptType.PRICE_AMOUNT_TITLE);

        cart.addItem("banana", 1);
        cart.addItem("banana", 3);

        assertEquals("€8.00 - banana - 4" + LS + "Total: €8.00" + LS, cart.toString());
    }

    @Test
    public void printReceiptTest() {
        ShoppingCart cart = new ShoppingCart();

        final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutput));

        cart.addItem("banana", 3);
        cart.addItem("apple", 3);

        cart.printReceipt();

        assertEquals(cart.toString(), testOutput.toString());
    }
}


