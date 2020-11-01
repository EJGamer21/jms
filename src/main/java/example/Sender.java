package example;

import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class Sender {

    public static void main(String[] args) throws JMSException {
        JmsConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:5672");
        Connection connection = factory.createConnection("admin", "password");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("myQueue");

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        String text;

        do {
            System.out.println("Enter new message: ");
            text = new Scanner(System.in).nextLine();
            TextMessage message = session.createTextMessage(text);

            producer.send(message);

            if (text.equalsIgnoreCase("EXIT")) {
                System.out.println("Cya boi");
            }
        } while (!text.equalsIgnoreCase("EXIT"));

        producer.close();
        session.close();
        connection.close();
        System.exit(1);
    }
}