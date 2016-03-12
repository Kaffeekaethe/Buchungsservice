package buchungsservice.domainmodel;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import buchungsservice.App;

public class QueueFeeder {
	
	public static void feedBuchungsQueue(int zug, int platz) {
		try {

			
			Queue queue = new Queue("buchungsEreignisse");
			App.admin.declareQueue(queue);
			
			TopicExchange exchange = new TopicExchange("buchungen");
			App.admin.declareExchange(exchange);
			
			//Binding zu allen Routen mit "buchungen"
		    App.admin.declareBinding(
		            BindingBuilder.bind(queue).to(exchange).with("buchungen.*"));
		    		
			JSONObject buchung = new JSONObject();
			try {
				buchung.put("zug", zug);
				buchung.put("platz", platz);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			App.template.convertAndSend("buchungen", "buchungen.general", buchung.toString());
			System.out.println("Send: " + buchung.toString());

		} catch (AmqpException e) {
			System.out.println(e);
		}

		/*
		 * 
		 * Alter Code fuer RabbitMQ ConnectionFactory factory = new
		 * ConnectionFactory();
		 * factory.setHost("#{systemEnvironment['RABBITMQ_SERVER']}");
		 * Connection connection; try { connection = factory.newConnection();
		 * Channel channel = connection.createChannel();
		 * channel.queueDeclare("buchungsEreignisse", false, false, false,
		 * null); JSONObject buchung = new JSONObject(); try {
		 * buchung.put("zug", zug); buchung.put("platz", platz);
		 * channel.basicPublish("", "buchungsEreignisse", null,
		 * buchung.toString().getBytes()); System.out.println(" [x] Sent '" +
		 * buchung.toString() + "'"); } catch (JSONException e) {
		 * System.out.println("Buchung konnte nicht in Queue gesendet werden");
		 * e.printStackTrace(); }
		 * 
		 * 
		 * channel.close(); connection.close(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (TimeoutException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

}
