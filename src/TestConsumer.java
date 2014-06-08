

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TestConsumer implements ExceptionListener{

	
	
	public static void main(String[] args){
		TestConsumer testConsumer = new TestConsumer();
		while (true){
			try {
				 
	            // Create a ConnectionFactory
	            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.activemqUrl);

	            // Create a Connection
	            Connection connection = connectionFactory.createConnection();
	            connection.start();

	            connection.setExceptionListener(testConsumer);

	            // Create a Session
	            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	            // Create the destination (Topic or Queue)
	            Destination destination = session.createQueue("TEST.FOO");

	            // Create a MessageConsumer from the Session to the Topic or Queue
	            MessageConsumer consumer = session.createConsumer(destination);

	            // Wait for a message
	            Message message = consumer.receive(1000);

//	            if (message instanceof TextMessage) {
//	                TextMessage textMessage = (TextMessage) message;
//	                String text = textMessage.getText();
//	                System.out.println("Received: " + text);
//	            } else {
//	                System.out.println("Received: " + message);
//	            }
	            
	            if (message instanceof ObjectMessage){
	            	ObjectMessage scoutBeanMessage = (ObjectMessage) message;
	            	ScoutBean scoutBean = (ScoutBean) scoutBeanMessage.getObject();
	            	System.out.println("ScoutId: " + scoutBean.getId() + " Dest: " + scoutBean.getCe());
	            } else {
	            	System.out.println("Received: " + message);
	            }

	            consumer.close();
	            session.close();
	            connection.close();
	        } catch (Exception e) {
	            System.out.println("Caught: " + e);
	            e.printStackTrace();
	        }
		}
		}
		

	@Override
	public void onException(JMSException arg0) {
		// TODO Auto-generated method stub
		System.out.println("JMS Exception occured.  Shutting down client.");
	}
}
