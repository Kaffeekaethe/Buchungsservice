package buchungsservice.api;

import java.util.List;
import java.util.Random;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import buchungsservice.App;
import buchungsservice.client.PlatzuebersichtClient;
import buchungsservice.domainmodel.Buchung;
import buchungsservice.domainmodel.QueueFeeder;

@Configuration
@EnableAutoConfiguration
@RestController
// API mit Registrierung bei Eureka
@EnableEurekaClient
public class BuchungsserviceAPI {

	// home
	@RequestMapping("/")
	public String home() {
		return "Ich bin ein Buchungsservice";
	}

	// buche einen Zug
	@RequestMapping("/buche")
	public String buchung(@RequestParam(value = "zug") int zug, @RequestParam(value = "platz") int platz) {
		boolean status = PlatzuebersichtClient.getStatus(zug, platz);
		System.out.println(String.format("Statusabfrage fuer Zug %d, Platz %d gesendet. Ergebnis: %s", zug, platz, status));
		if (status) {
			Random r = new Random();
			if (r.nextInt(5) != 1) {
				App.buchungDAO.insertBuchung(zug, platz);
				String response = String.format("Platz gebucht: Zug %d, Platz %d", zug, platz);
				QueueFeeder.feedBuchungsQueue(zug, platz);
				System.out.println(response);
				return response;
			} else {
				String response = "Buchung fehlgeschlagen";
				System.out.println(response);
				return response;
			}

		}
		return String.format("Platz %d in zug %d war nicht mehr frei", platz, zug);
	}

	@RequestMapping("/buchungen")
	public List<Buchung> buchungen(@RequestParam(value = "id") int id) {
		System.out.println(String.format("Anfrage fuer alle Buchungen ab ID %d erhalten", id));
		return App.buchungDAO.getBuchungen(id);
	}

}