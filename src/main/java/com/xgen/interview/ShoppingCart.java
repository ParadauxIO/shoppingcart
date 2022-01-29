package com.xgen.interview;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * An implementation of {@link IShoppingCart}
 */
public class ShoppingCart implements IShoppingCart {

    private final LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();
    private final Pricer pricer;

    public ShoppingCart() {
        this.pricer = new Pricer();
    }

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
    }

    public void addItem(String item, int amount) {
        if (!contents.containsKey(item)) {
            contents.put(item, amount);
        } else {
            int existing = contents.get(item);
            contents.put(item, existing + amount);
        }
    }

    public void printReceipt() {
        int total = 0;

        for (String item : contents.keySet()) {
            // If it's null, assume it's free and unbox to an integer.
            int itemUnitPrice = Optional.ofNullable(pricer.getPrice(item))
                    .orElse(0);

            int itemPrice = itemUnitPrice * contents.get(item);

            total += itemPrice;

            System.out.printf("%s - %s - €%.2f%n", item, contents.get(item), itemPrice/100f);
        }

        System.out.printf("Total: €%.2f%n", total/100f);
    }

}
