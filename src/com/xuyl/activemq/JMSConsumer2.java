package com.xuyl.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * @author Admin
 * ������
 */
public class JMSConsumer2 {

	private static final String  USERNAME = ActiveMQConnection.DEFAULT_USER; //Ĭ�ϵ������û���
	private static final String  PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//Ĭ�ϵ���������
	private static final String  BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//Ĭ�ϵ����ӵ�ַ

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;//���ӹ���  ����connection 
		Connection connection = null;//����
		Session session ;//�Ự  ���ܻ��߷�����Ϣ���߳�
		Destination destination;//��ϢĿ�ĵ�ַ
		MessageConsumer messageConsumer;//��Ϣ��������
		
		//ʵ�������ӹ���
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer2.USERNAME, JMSConsumer2.PASSWORD, JMSConsumer2.BROKEURL);
		
		try {
			connection = connectionFactory.createConnection();//ͨ�����ӹ��� ��������
			connection.start();//��������
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);//����session  �����߲���Ҫ������
			//�������ӵ���Ϣ����
			destination = session.createQueue("FirstQueue1");//��Ϣ�����������߶�Ӧ
			messageConsumer = session.createConsumer(destination);//������Ϣ������
			//������Ϣ
			messageConsumer.setMessageListener(new Listener());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
