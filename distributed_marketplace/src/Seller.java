import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Seller {
    private String sellerId;
    private ConcurrentMap<String, Integer> items;
    private String name;

    public Seller(String sellerId) {
        this.sellerId = sellerId;
        this.items = new ConcurrentHashMap<>();
        // Initialize items with some stock
        items.put("flower", 5);
        items.put("sugar", 5);
        items.put("potato", 5);
        items.put("oil", 5);
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getName() {
        return name;
    }

    public void addStock(String itemId, int amount) {
        items.put(itemId, items.getOrDefault(itemId, 0) + amount);
    }

    public ConcurrentMap<String, Integer> getStock() {
        return items;
    }

    public synchronized void sellItem(String itemId, int amount, String buyerId) {
        // ...existing code...
        if (items.containsKey(itemId) && items.get(itemId) >= amount) {
            items.put(itemId, items.get(itemId) - amount);
            System.out.println(buyerId + " bought " + amount + " of " + itemId + " from " + sellerId);
            notifyBuyers(itemId);
        } else {
            System.out.println("Not enough stock or item not available.");
        }
    }

    private void notifyBuyers(String itemId) {
        // Notify all buyers about the current stock of the item
        System.out.println("Current stock of " + itemId + ": " + items.get(itemId));
    }

}
