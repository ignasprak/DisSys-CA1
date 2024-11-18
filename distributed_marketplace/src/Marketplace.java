import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Marketplace {
    private ConcurrentMap<String, Seller> sellers;
    private ConcurrentMap<String, Buyer> buyers;

    public Marketplace() {
        this.sellers = new ConcurrentHashMap<>();
        this.buyers = new ConcurrentHashMap<>();
    }

    public void registerSeller(Seller seller) {
        sellers.put(seller.getSellerId(), seller);
        // System.out.println("Seller " + seller.getSellerId() + " registered.");
    }

    public void registerBuyer(Buyer buyer) {
        buyers.put(buyer.getBuyerId(), buyer);
        // System.out.println("Buyer " + buyer.getBuyerId() + " registered.");
    }

    public Seller getSeller(String sellerId) {
        return sellers.get(sellerId);
    }

    public Buyer getBuyer(String buyerId) {
        return buyers.get(buyerId);
    }

    public ConcurrentMap<String, Seller> getSellers() {
        return sellers;
    }

    public ConcurrentMap<String, Buyer> getBuyers() {
        return buyers;
    }
}
