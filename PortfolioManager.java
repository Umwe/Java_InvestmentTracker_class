
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PortfolioManager {
    private Map<String, Double> portfolio;
    private Map<String, Double> stockPrices;
    private ScheduledExecutorService priceUpdater;
    private AtomicBoolean running;

    public PortfolioManager() {
        portfolio = new ConcurrentHashMap<>();
        stockPrices = new ConcurrentHashMap<>();
        priceUpdater = Executors.newScheduledThreadPool(1);
        running = new AtomicBoolean(true);
    }

    // Add a stock to the portfolio
    public void addStock(String stockSymbol, double quantity) {
        portfolio.merge(stockSymbol, quantity, Double::sum);
    }

    // Remove a stock from the portfolio
    public void removeStock(String stockSymbol, double quantity) {
        portfolio.computeIfPresent(stockSymbol, (key, val) -> val - quantity <= 0 ? null : val - quantity);
    }

    // Get the total value of the portfolio
    public double getTotalValue() {
        return portfolio.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * stockPrices.getOrDefault(entry.getKey(), 0.0))
                .sum();
    }

    // Update stock prices periodically
    public void startPriceUpdater() {
        priceUpdater.scheduleAtFixedRate(this::updateStockPrices, 0, 10, TimeUnit.SECONDS);
    }

    // Stop the price updater
    public void stopPriceUpdater() {
        running.set(false);
        priceUpdater.shutdown();
    }

    // Simulate updating stock prices
    private void updateStockPrices() {
        if (!running.get()) return;
        Random random = new Random();
        stockPrices.forEach((key, value) -> {
            double change = value * (random.nextDouble() - 0.5) / 10; // +/- 5% change
            stockPrices.put(key, value + change);
        });
        System.out.println("Stock prices updated: " + stockPrices);
    }

    // Add a new stock with an initial price
    public void addNewStock(String stockSymbol, double initialPrice) {
        stockPrices.put(stockSymbol, initialPrice);
    }

    public static void main(String[] args) {
        PortfolioManager manager = new PortfolioManager();
        manager.addNewStock("AAPL", 150.0);
        manager.addNewStock("GOOG", 2500.0);
        manager.addNewStock("AMZN", 3500.0);

        manager.addStock("AAPL", 10);
        manager.addStock("GOOG", 5);
        manager.addStock("AMZN", 2);

        manager.startPriceUpdater();

        // Simulate some portfolio operations
        try {
            Thread.sleep(30000); // Let the updater run for 30 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total portfolio value: " + manager.getTotalValue());

        manager.stopPriceUpdater();
    }
}
