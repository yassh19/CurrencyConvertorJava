package Project;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
public class CurrencyConvertor {

	public static void main(String[] args) throws IOException {
		
		Boolean running = true;
		do {
			HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();
		
			currencyCodes.put(1, "USD");
			currencyCodes.put(2, "CAD");
			currencyCodes.put(3, "EUR");
			currencyCodes.put(4, "HKD");
			currencyCodes.put(5, "INR");
		
			Integer from, to;
			String fromCode, toCode;
			double amount;
			
			Scanner sc = new Scanner(System.in);
		
			System.out.println("Welcome to currency converter!");
		
			System.out.println("Currency to convert from ?");
			System.out.println(" 1:USD(US Dollar) \n 2:CAD(Canadian Dollar) \n 3:EUR(Euro) \n 4:HKD(Hong Kong Dollar) \n 5:INR(Indian Rupee)");
			from = sc.nextInt();
			while(from<1 || from>5) {
				System.out.println("Please select a valid currency(1-5)");
				System.out.println(" 1:USD(US Dollar) \n 2:CAD(Canadian Dollar) \n 3:EUR(Euro) \n 4:HKD(Hong Kong Dollar) \n 5:INR(Indian Rupee)");
				from = sc.nextInt();
			}
			fromCode = currencyCodes.get(from);
		

			System.out.println("Currency converting to ?");
			System.out.println("1:USD(US Dollar) \n 2:CAD(Canadian Dollar) \n 3:EUR(Euro) \n 4:HKD(Hong Kong Dollar) \n 5:INR(Indian Rupee)");
			to = sc.nextInt();
			while(to<1 || to>5) {
				System.out.println("Please select a valid currency(1-5)");
				System.out.println(" 1:USD(US Dollar) \n 2:CAD(Canadian Dollar) \n 3:EUR(Euro) \n 4:HKD(Hong Kong Dollar) \n 5:INR(Indian Rupee)");
				to = sc.nextInt();
			}
			toCode = currencyCodes.get(to);
		
			System.out.println("Amount you want to convert:");
			amount = sc.nextFloat();
		
			sendHttpGETRequest(fromCode, toCode, amount);
		
			System.out.println("Would you like to make another conversion");
			System.out.println(" 1:Yes \n 2:No");
			if(sc.nextInt() != 1) {
				running = false;
			}
		}while(running);
		
		System.out.println("Thank You for using currency convertor!");
	}
	
	private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
		
		DecimalFormat f = new DecimalFormat("00.00");
		
		String GET_URL = "https://api.frankfurter.app/latest?from="+fromCode+"&to="+toCode;
		URL url = new URL(GET_URL);
		
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestMethod("GET");
		int responseCode = httpURLConnection.getResponseCode();
		
		if(responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}in.close();
			
			JSONObject obj = new JSONObject(response.toString());
			Double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);
			System.out.println(obj.getJSONObject("rates"));
			System.out.println(exchangeRate);
			System.out.println();
			System.out.println(f.format(amount) + fromCode + "=" + f.format(amount*exchangeRate) + toCode);
		}
		else {
			System.out.println("GET request failed");
		}
	}

}
