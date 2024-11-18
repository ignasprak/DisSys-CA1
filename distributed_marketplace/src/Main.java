import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Marketplace marketplace = new Marketplace();

        // Pre-register sellers with stock
        Seller seller1 = new Seller("Irish Wholesale");
        marketplace.registerSeller(seller1);

        // Simulate buyer actions
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Buyer ann = new Buyer("Ann Bakery");
        ann.joinMarket(marketplace);
        ann.setBuyingPreferences("Irish Wholesale", "sugar", 1);
        executor.submit(ann);

        Buyer bert = new Buyer("Bert Takeaway");
        bert.joinMarket(marketplace);
        bert.setBuyingPreferences("Irish Wholesale", "oil", 1);
        executor.submit(bert);

        Buyer charlie = new Buyer("Charlie Fries");
        charlie.joinMarket(marketplace);
        charlie.setBuyingPreferences("Irish Wholesale", "potato", 1);
        executor.submit(charlie);

        Buyer dominic = new Buyer("Dominic Gardens");
        dominic.joinMarket(marketplace);
        dominic.setBuyingPreferences("Irish Wholesale", "flower", 1);
        executor.submit(dominic);
    }
}
