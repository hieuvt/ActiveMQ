

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

public class TestManageQueue {

	public void sendMessageToQueue(String message, String queueName){
		try {

			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					Constants.activemqUrl);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

//			for (ScoutBean scoutBean : scoutBeans) {
//				ObjectMessage scoutBeanMessage = session.createObjectMessage();
//				scoutBeanMessage.setObject(scoutBean);
//				producer.send(scoutBeanMessage);
//			}
			
			TextMessage textMessage = session.createTextMessage("Hello world! From Producer");
			producer.send(textMessage);
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
	
	private void sendScoutBeansToQueue(List<ScoutBean> scoutBeans, String queueName) {
		try {

			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					Constants.activemqUrl);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			for (ScoutBean scoutBean : scoutBeans) {
				ObjectMessage scoutBeanMessage = session.createObjectMessage();
				scoutBeanMessage.setObject(scoutBean);
				scoutBeanMessage.setJMSCorrelationID(Integer.toString(scoutBean.getId()));
				scoutBeanMessage.setStringProperty("ceName", scoutBean.getCe());
				producer.send(scoutBeanMessage);
			}

			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
	
	public void purgeQueue(String queueName){

	}
	
	public void removeMeassage(String queueName){
		JMXServiceURL url;
	
		ObjectName name;
		try {
			url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://150.183.250.128:2011/jmxrmi");

			JMXConnector connector;
			connector = JMXConnectorFactory.connect(url, null);

			connector.connect();
			MBeanServerConnection connection;
			connection = connector.getMBeanServerConnection();
			name = new ObjectName("my-broker:BrokerName=localhost,Type=Broker");
			BrokerViewMBean mbean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
			System.out.println("Statistics for broker " + mbean.getBrokerId() + " - " + mbean.getBrokerName());
			
			System.out.println(mbean.getQueues().length);
			for (ObjectName objectName : mbean.getQueues()) {
				QueueViewMBean queueMbean = MBeanServerInvocationHandler.newProxyInstance(connection, objectName, QueueViewMBean.class, true);
				System.out.println(queueMbean.getName());
				if (queueMbean.getName().equals(queueName)) {
					System.out.println(queueMbean.getQueueSize());
//					queueMbean.purge();
//					System.out.println(queueMbean.getQueueSize());
//					queueMbean.removeMatchingMessages("JMSCorrelationID='ID:htcaas-hieu.kisti.re.kr-47981-1401861951259-1:1:1:1:1'");
//					queueMbean.removeMessage("ID:htcaas-hieu.kisti.re.kr-47981-1401861951259-1:1:1:1:1");
//					System.out.println(((ScoutBean)queueMbean.browseMessages().get(0)).toString());
					queueMbean.removeMatchingMessages("ceName='darthvader'");
				}
			}
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}
	public static void main(String[] args){
		TestManageQueue testManageQueue = new TestManageQueue();
		List<ScoutBean> scoutBeans = new ArrayList<ScoutBean>();
		for (int i = 0; i < 10; i++){
			ScoutBean scoutBean = new ScoutBean();
			scoutBean.setId(i);
			scoutBean.setCe("darthvader");
			scoutBeans.add(scoutBean);
		}
		String queueName = "TEST.FOO";
		String message = "Hello from producer";
//		testManageQueue.sendMessageToQueue(message, queueName);
		testManageQueue.sendScoutBeansToQueue(scoutBeans, queueName);

//		testManageQueue.purgeQueue(queueName);
//		testManageQueue.removeMeassage(queueName);
	}


}
