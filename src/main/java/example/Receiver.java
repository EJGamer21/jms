package example;

import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;

public class Receiver {

    public static void main(String[] args) throws JMSException {
        JmsConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:5672");
        Connection connection = factory.createConnection("admin", "password");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("myQueue");

        MessageConsumer consumer = session.createConsumer(queue);

        String text;
        do {
            Message message = consumer.receive();
            text = ((TextMessage) message).getText();

            System.out.println("New message received: " + text);

            if (text.equalsIgnoreCase("EXIT")) {
                System.out.println("Cya boi");
            }
        } while (!text.equalsIgnoreCase("EXIT"));

        consumer.close();
        session.close();
        connection.close();
        System.exit(1);
    }
}
