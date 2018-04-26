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
 * 消费者
 */
public class JMSConsumer {

	private static final String  USERNAME = ActiveMQConnection.DEFAULT_USER; //默认的连接用户名
	private static final String  PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认的连接密码
	private static final String  BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认的连接地址

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;//连接工厂  生产connection 
		Connection connection = null;//连接
		Session session ;//会话  接受或者发送消息的线程
		Destination destination;//消息目的地址
		MessageConsumer messageConsumer;//消息的消费者
		
		//实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);
		
		try {
			connection = connectionFactory.createConnection();//通过连接工厂 创建连接
			connection.start();//启动连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);//创建session  消费者不需要加事务
			//创建连接的消息队列
			destination = session.createQueue("FirstQueue1");//消息队列与生产者对应
			messageConsumer = session.createConsumer(destination);//创建消息消费者
			//接收消息
			while(true){
				TextMessage textMessage= (TextMessage)messageConsumer.receive(100000);//100s接收一次（ms级）
				if(textMessage != null){
					System.out.println("接收到的消息：" + textMessage.getText() );
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
