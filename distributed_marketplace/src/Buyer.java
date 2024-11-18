import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Buyer implements Runnable {
    private String buyerId;
    private Marketplace marketplace;
    private String sellerId;
    private String itemId;
    private int amount;
    private int stockLimit = 5;
    private int currentStock = 2; // Start with no stock
    private ScheduledExecutorService scheduler;

    public Buyer(String buyerId) {
        this.buyerId = buyerId;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void joinMarket(Marketplace marketplace) {
        this.marketplace = marketplace;
        marketplace.registerBuyer(this);
        startStockDecrement();
    }

    public void leaveMarket(Marketplace marketplace) {
        // ...existing code...
        System.out.println("Buyer " + buyerId + " left the market.");
    }

    public void tryToBuyItem(Marketplace marketplace, String sellerId, String itemId, int amount) {
        Seller seller = marketplace.getSeller(sellerId);
        if (seller != null) {
            seller.sellItem(itemId, amount, buyerId);
        } else {
            System.out.println("Seller not found.");
        }
    }

    // BUYER BEHAVIOUR

    public void setBuyingPreferences(String sellerId, String itemId, int amount) {
        this.sellerId = sellerId;
        this.itemId = itemId;
        this.amount = amount;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void run() {
        while (true) {
            if (currentStock < 3) {
                Seller seller = marketplace.getSeller(sellerId);
                if (seller != null && seller.getStock().getOrDefault(itemId, 0) > 0
                        && itemId.equals(seller.getCurrentItem())) {
                    tryToBuyItem(marketplace, sellerId, itemId, amount);
                    currentStock += amount;
                    System.out.println(buyerId + ": \t\t" + itemId + ": " + currentStock + "\n");
                }
            }
            try {
                Thread.sleep(5000); // Wait for 5 seconds before trying again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void startStockDecrement() {
        scheduler.scheduleAtFixedRate(() -> {
            if (currentStock > 0) {
                currentStock--;
                System.out.println(buyerId + " is low on " + itemId);
                System.out.println(buyerId + " current stock of " + itemId + " is " + currentStock);
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}