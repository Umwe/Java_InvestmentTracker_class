# Java_InvestmentTracker_class
This class will handle complex tasks such as tracking stock prices, calculating the portfolio's total value, and simulating market changes. We'll use Java's concurrency features to periodically update stock prices in the background.


## Key Features:
Concurrent Portfolio Management: Uses ConcurrentHashMap for thread-safe operations on the portfolio and stock prices.
Scheduled Price Updates: Periodically updates stock prices using a scheduled executor service.
Dynamic Portfolio Operations: Supports adding and removing stocks, and recalculating the total portfolio value based on current prices.
## Explanation:
- addStock: Adds a specified quantity of a stock to the portfolio. If the stock already exists, it increments the quantity.
- removeStock: Removes a specified quantity of a stock from the portfolio. If the quantity falls to zero or below, it removes the stock.
- getTotalValue: Calculates the total value of the portfolio by summing up the values of all stocks, using their current prices.
- startPriceUpdater: Starts the background task that periodically updates stock prices.
- stopPriceUpdater: Stops the background task.
- updateStockPrices: Simulates stock price changes by applying random variations to current prices.
This class demonstrates several complex programming concepts, including concurrency, scheduled tasks, and real-time data updates.
