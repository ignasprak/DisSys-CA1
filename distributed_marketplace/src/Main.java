import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Marketplace marketplace = new Marketplace();
        Scanner scanner = new Scanner(System.in);

        // Pre-register two sellers with stock
        Seller seller1 = new Seller("Irish Wholesale");
        marketplace.registerSeller(seller1);
        // Seller seller2 = new Seller("Lynn Wholesale");
        // marketplace.registerSeller(seller2);

        // Simulate buyer actions
        ExecutorService executor = Executors.newFixedThreadPool(4);
        String[] buyerNames = { "Ann", "Bert", "Charlie", "Dominic" };
        for (String buyerName : buyerNames) {
            executor.submit(() -> {
                Buyer buyer = new Buyer(buyerName);
                buyer.joinMarket(marketplace);
                buyer.tryToBuyItem(marketplace, "Irish Wholesale", "flower", 1);
            });
        }

        // Command line interface
        while (true) {
            System.out.println("\n-- ELECTRONIC FOOD MARKETPLACE --");
            System.out.println("Please select an option:");
            System.out.println("1. View all sellers");
            System.out.println("2. Register a new seller");
            System.out.println("3. View all buyers");
            System.out.println("4. Register a new buyer");
            System.out.println("5. Buy items");
            System.out.println("6. Exit\n");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    System.out.println("List of sellers:");
                    for (Seller seller : marketplace.getSellers().values()) {
                        System.out.println("\nSeller ID: " + seller.getSellerId());
                        for (Map.Entry<String, Integer> entry : seller.getStock().entrySet()) {
                            System.out.println(entry.getKey().substring(0, 1).toUpperCase()
                                    + entry.getKey().substring(1) + " \t- " + entry.getValue() + "(Kg/L)");
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter seller ID: ");
                    String sellerId = scanner.nextLine();
                    Seller seller = new Seller(sellerId);
                    marketplace.registerSeller(seller);
                    break;
                case 3:
                    System.out.println("List of buyers:");
                    for (Buyer b : marketplace.getBuyers().values()) {
                        System.out.println("Buyer ID: " + b.getBuyerId());
                    }
                    break;
                case 4:
                    System.out.print("Enter buyer ID: ");
                    String buyerId = scanner.nextLine();
                    Buyer buyer = new Buyer(buyerId);
                    buyer.joinMarket(marketplace);
                    break;
                case 5:
                    System.out.print("Enter buyer ID: ");
                    String buyerIdForBuy = scanner.nextLine();
                    System.out.println("Select a seller:");
                    int sellerIndex = 1;
                    for (Seller s : marketplace.getSellers().values()) {
                        System.out.println(sellerIndex + ". " + s.getSellerId());
                        sellerIndex++;
                    }
                    int sellerChoice = Integer.parseInt(scanner.nextLine());
                    Seller selectedSeller = null;
                    sellerIndex = 1;
                    for (Seller s : marketplace.getSellers().values()) {
                        if (sellerIndex == sellerChoice) {
                            selectedSeller = s;
                            break;
                        }
                        sellerIndex++;
                    }
                    if (selectedSeller == null) {
                        System.out.println("Invalid seller choice.");
                        break;
                    }
                    System.out.print("Enter item ID: ");
                    String itemId = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    int amount = Integer.parseInt(scanner.nextLine());

                    Buyer buyerForBuy = marketplace.getBuyer(buyerIdForBuy);
                    if (buyerForBuy != null) {
                        buyerForBuy.tryToBuyItem(marketplace, selectedSeller.getSellerId(), itemId, amount);
                    } else {
                        System.out.println("Buyer not found.");
                    }
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
}
