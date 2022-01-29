package com.xgen.interview;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * An implementation of {@link IShoppingCart}
 */
public class ShoppingCart implements IShoppingCart {

    // LinkedHashMap maintains insertion order, unlike HashMap.
    private final LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();

    private final Pricer pricer;
    private final ReceiptType receiptType;

    /**
     * This represents the order in which the receipt contents will be printed.
     *
     * <p> The naming convention includes the order of the values baked in.</p>
     *
     * <p> The provided string represents how the recript entry will look in the final String.</p>
     * */
    public enum ReceiptType {
        TITLE_AMOUNT_PRICE("%s - %s - €%.2f%n"),
        PRICE_AMOUNT_TITLE("€%.2f - %s - %s%n");

        private final String format;

        ReceiptType(String format) {
            this.format = format;
        }
    }

    public ShoppingCart() {
        this(new Pricer(), ReceiptType.TITLE_AMOUNT_PRICE);
    }

    public ShoppingCart(Pricer pricer) {
        this(pricer, ReceiptType.TITLE_AMOUNT_PRICE);
    }

    public ShoppingCart(Pricer pricer, ReceiptType receiptType) {
        this.pricer = pricer;
        this.receiptType = receiptType;
    }

    /** 
     * Adds a specified amount of an item to the shopping cart.
     * 
     * @implNote If the item already exists in the shopping cart, the quantity is increased
     *           by the specified found, rather than overriding which is the default
     *           behaviour of {@link LinkedHashMap#put(Object, Object)}
     * */
    public void addItem(String item, int amount) {
        contents.merge(item, amount, Integer::sum);
    }


    /**
     * Prints {@link ShoppingCart#toString} to STDOUT
     * */
    public void printReceipt() {
        System.out.println(this);
    }

    /**
     * Creates a String-version of the contents of the shopping cart.
     *
     * <p> The formatting of the string is dependent on the {@link ReceiptType}.</p>
     *
     * @return A string-version of the contents of the shopping cart.
     * @since 1.0.0
     * */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        int total = 0;

        // For every item in the cart.
        for (String item : contents.keySet()) {
            // If it's null, assume it's free and unbox to an integer.
            int itemUnitPrice = Optional.ofNullable(pricer.getPrice(item))
                    .orElse(0);

            int itemPrice = itemUnitPrice * contents.get(item); // Multiple by the amount of said item.
            total += itemPrice;

            // The formatting is specified by the enum. Depending on which enum is selected
            // the order in which the parameters are provided may need to be changed, hence
            // this switch.
            switch (receiptType) {
                case TITLE_AMOUNT_PRICE:
                    builder.append(String.format(receiptType.format, item, contents.get(item), itemPrice/100f));
                    break;

                case PRICE_AMOUNT_TITLE:
                    builder.append(String.format(receiptType.format, itemPrice/100f, item, contents.get(item)));
                    break;
            }
        }

        builder.append(String.format("Total: €%.2f%n", total/100f));
        return builder.toString();
    }

}
