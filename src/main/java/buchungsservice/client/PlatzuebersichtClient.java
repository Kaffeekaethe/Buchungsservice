package buchungsservice.client;

import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;

public class PlatzuebersichtClient {

	// TODO: Enum für den Status
	private static RestTemplate restTemplate = new RestTemplate();

	public static boolean getStatus(int zug, int platz) {
		try {
			System.out.println(String.format("Sende Statusanfrage für Zug %d, Platz %d", zug, platz));

			InstanceInfo nextServerInfo = DiscoveryManager.getInstance().getDiscoveryClient()
					.getNextServerFromEureka("platzuebersichtsservice", false);
			System.out.println(String.format("Gefunden: %s, Adresse: %s", nextServerInfo.getAppName(),
					nextServerInfo.getHomePageUrl()));

			String url_str = String.format("http://%s:%s", nextServerInfo.getIPAddr(), nextServerInfo.getPort());
			String response = restTemplate.getForObject(url_str + "/status?zug={zug}&platz={platz}&lock=true",
					String.class, zug, platz);

			// Wenn das Ergebnis 0 ist --> platz ist frei --> return true
			// TODO: Enum verwenden um Lesbarkeit zu verbessern
			// TODO: Wohin kommt diese Umwandlung? Liegt die beim Client oder
			// bei Service?
			if (Integer.parseInt(response) == 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
