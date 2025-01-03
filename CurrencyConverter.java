import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class CurrencyConverter {
    public static double getExchangeRate(String baseCurrency, String targetCurrency) {
        String apiKey = "31f17f731e04b4b0acfe6fe0";
        if (apiKey.equals("YOUR_API_KEY")) {
            System.out.println("Please replace 'YOUR_API_KEY' with your actual API key.");
            return -1;
        }
        String urlString = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String responseString = response.toString();
            int startIndex = responseString.indexOf(targetCurrency);
            if (startIndex == -1) {
                throw new Exception("Exchange rate for " + targetCurrency + " not available.");
            }
            startIndex = responseString.indexOf(":", startIndex) + 1;
            int endIndex = responseString.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = responseString.indexOf("}", startIndex);
            }

            String rateString = responseString.substring(startIndex, endIndex).trim();
            return Double.parseDouble(rateString);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }
    public static void convertCurrency() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the base currency (e.g., USD, EUR, GBP): ");
            String baseCurrency = scanner.nextLine().toUpperCase();
            System.out.print("Enter the target currency (e.g., USD, EUR, GBP): ");
            String targetCurrency = scanner.nextLine().toUpperCase();
            System.out.print("Enter the amount in " + baseCurrency + ": ");
            double amount = scanner.nextDouble();
            double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
            if (exchangeRate == -1) {
                return;
            }
            double convertedAmount = amount * exchangeRate;
 System.out.printf("\n%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount,
  targetCurrency);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        convertCurrency();
    }
}
