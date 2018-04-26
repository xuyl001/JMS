package com.xuyl.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * @author Administrator
 * ��Ϣ������
 */
public class JMSProducer {
	
	private static final String  USERNAME = ActiveMQConnection.DEFAULT_USER; //Ĭ�ϵ������û���
	private static final String  PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//Ĭ�ϵ���������
	private static final String  BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//Ĭ�ϵ����ӵ�ַ
	private static final int SENDNUM = 10;//������Ϣ������
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;//���ӹ���  ����connection 
		Connection connection = null;//����
		Session session ;//�Ự  ���ܻ��߷�����Ϣ���߳�
		Destination destination;//��ϢĿ�ĵ�ַ
		MessageProducer messageProducer;//��Ϣ��������
		
		//ʵ�������ӹ���
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		try {
			connection = connectionFactory.createConnection();//ͨ�����ӹ��� ��������
			connection.start();//��������
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);//��һ�������ǿ�������
			//ǿתĿ�ĵ�
			destination = session.createQueue("FirstQueue1");//������Ϣ����
			messageProducer = session.createProducer(destination);//������Ϣ������
			sendMessage(session, messageProducer);//������Ϣ
			session.commit();//��Ϊ������ ��Ҫ�ύ
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @param session
	 * @param messageProducer
	 * @throws Exception
	 * @Method ������Ϣ
	 */
	public static void sendMessage (Session session,MessageProducer messageProducer) throws Exception{
		
		for(int i = 0; i<JMSProducer.SENDNUM; i++){
		  //��������Ϣ
		  TextMessage message = session.createTextMessage("ActiveMq ���͵���Ϣ" + i);//
		  System.out.println("���͵���Ϣ��" + "ActiveMq���͵���Ϣ" + i);
		  //������Ϣ
		  messageProducer.send(message);
		}
	}
}



