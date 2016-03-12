package buchungsservice;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import buchungsservice.api.BuchungsserviceAPI;
import buchungsservice.domainmodel.BuchungDAO;


@SpringBootApplication
public class App {
	
	public static BuchungDAO buchungDAO;
	public static CachingConnectionFactory rabbitConnFactory;
	public static RabbitAdmin admin;
	public static RabbitTemplate template;
	
	public static void main(String[] args) {
		//Starten der App und des Datenbankmodells
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

		buchungDAO = (BuchungDAO) context.getBean("buchungDAO");
		rabbitConnFactory = (CachingConnectionFactory) context.getBean("rabbitConnFactory");
		admin = new RabbitAdmin(App.rabbitConnFactory);
		template = new RabbitTemplate(App.rabbitConnFactory);

		SpringApplication.run(BuchungsserviceAPI.class, args);
	}
}