import java.util.concurrent.*;

public class Seller {
    private String sellerId;
    private ConcurrentMap<String, Integer> items;
    private String currentItem;
    private ScheduledExecutorService scheduler;
    private static final int MAX_SELLING_PERIOD = 30; // seconds

    public Seller(String sellerId) {
        this.sellerId = sellerId;
        this.items = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
        // Initialize items with some stock
        items.put("flower", 5);
        items.put("sugar", 5);
        items.put("potato", 5);
        items.put("oil", 5);
        startSelling();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void addStock(String itemId, int amount) {
        items.put(itemId, items.getOrDefault(itemId, 0) + amount);
    }

    public ConcurrentMap<String, Integer> getStock() {
        return items;
    }

    public synchronized void sellItem(String itemId, int amount, String buyerId) {
        if (items.containsKey(itemId) && items.get(itemId) >= amount) {
            items.put(itemId, items.get(itemId) - amount);
            System.out.println(buyerId + " bought " + amount + " of " + itemId + " from " + sellerId);
            notifyBuyers(itemId);
            if (items.get(itemId) == 0) {
                switchItem();
            }
        } else {
            System.out.println("Not enough stock or item not available.");
        }
    }

    private void notifyBuyers(String itemId) {
        System.out.println("Current stock of " + itemId + ": " + items.get(itemId));
    }

    private void startSelling() {
        switchItem();
        scheduler.scheduleAtFixedRate(this::switchItem, MAX_SELLING_PERIOD, MAX_SELLING_PERIOD, TimeUnit.SECONDS);
    }

    private synchronized void switchItem() {
        boolean foundNewItem = false;
        for (String item : items.keySet()) {
            if (!item.equals(currentItem) && items.get(item) > 0) {
                currentItem = item;
                System.out.println("\n---------------------------");
                System.out.println(getSellerId() + "\nNow selling: " + currentItem);
                notifyBuyers(currentItem);
                System.out.println("---------------------------");
                foundNewItem = true;
                break;
            }
        }
        if (!foundNewItem) {
            for (String item : items.keySet()) {
                if (items.get(item) > 0) {
                    currentItem = item;
                    System.out.println("\n---------------------------");
                    System.out.println(getSellerId() + "\nNow selling: " + currentItem);
                    notifyBuyers(currentItem);
                    System.out.println("---------------------------");
                    break;
                }
            }
        }
    }
}
