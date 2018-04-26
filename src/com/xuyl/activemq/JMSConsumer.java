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
public class JMSConsumer {

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
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);
		
		try {
			connection = connectionFactory.createConnection();//ͨ�����ӹ��� ��������
			connection.start();//��������
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);//����session  �����߲���Ҫ������
			//�������ӵ���Ϣ����
			destination = session.createQueue("FirstQueue1");//��Ϣ�����������߶�Ӧ
			messageConsumer = session.createConsumer(destination);//������Ϣ������
			//������Ϣ
			while(true){
				TextMessage textMessage= (TextMessage)messageConsumer.receive(100000);//100s����һ�Σ�ms����
				if(textMessage != null){
					System.out.println("���յ�����Ϣ��" + textMessage.getText() );
				}else {
					break;
				}
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
