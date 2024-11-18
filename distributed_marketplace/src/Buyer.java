public class Buyer {
    private String buyerId;

    public Buyer(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void joinMarket(Marketplace marketplace) {
        marketplace.registerBuyer(this);
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
}