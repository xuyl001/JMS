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
 * 消息生产者
 */
public class JMSProducer {
	
	private static final String  USERNAME = ActiveMQConnection.DEFAULT_USER; //默认的连接用户名
	private static final String  PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认的连接密码
	private static final String  BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认的连接地址
	private static final int SENDNUM = 10;//发送消息的数量
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;//连接工厂  生产connection 
		Connection connection = null;//连接
		Session session ;//会话  接受或者发送消息的线程
		Destination destination;//消息目的地址
		MessageProducer messageProducer;//消息的生产者
		
		//实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		try {
			connection = connectionFactory.createConnection();//通过连接工厂 创建连接
			connection.start();//启动连接
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);//第一个参数是开启事务
			//强转目的地
			destination = session.createQueue("FirstQueue1");//创建消息队列
			messageProducer = session.createProducer(destination);//创建消息生产者
			sendMessage(session, messageProducer);//发送消息
			session.commit();//因为有事务 需要提交
			
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
	 * @Method 发送消息
	 */
	public static void sendMessage (Session session,MessageProducer messageProducer) throws Exception{
		
		for(int i = 0; i<JMSProducer.SENDNUM; i++){
		  //创建的消息
		  TextMessage message = session.createTextMessage("ActiveMq 发送的消息" + i);//
		  System.out.println("发送的消息：" + "ActiveMq发送的消息" + i);
		  //发送消息
		  messageProducer.send(message);
		}
	}
}



