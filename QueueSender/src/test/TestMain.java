package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.tibco.tibjms.TibjmsConnectionFactory;
import com.tibco.tibjms.TibjmsQueue;

public class TestMain {
	public static void main(String[] args) throws Exception {
		
//		if( "tdxd".equals(args[0]) ){
//			sendMsgProd(TypeEnumProd.投递下段);
//		}else if( "tdfk".equals(args[0]) ){
//			sendMsgProd(TypeEnumProd.投递反馈);
//		}else if( "ld".equals(args[0]) ){
//			sendMsgProd(TypeEnumProd.路单接收);
//		}else if( "ff".equals(args[0]) ){
//			sendMsgProd(TypeEnumProd.封发);
//		}else if( "sj".equals(args[0]) ){
//			sendMsgProd(TypeEnumProd.收寄);
//		}
		
		String fileName = args[0] ;
		String queue = args[1] ;
		String type = args[2] ;
		
		String path = "/home/nodemanager/"+fileName ;
		
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;
		List<String> contentList = readFileContentList( fileName ,"GBK");
		try {
//			TextMessage msg;
			BytesMessage msg;
			
			String ip = "127.0.0.1" ;
			String user="admin"  ;
			
			if("local".equals(type)){
				ip = "127.0.0.1" ;
				user="admin" ;
			}else if("prod".equals(type)){
				ip = "10.2.64.44" ;
				user="jdpt" ;
			}else if("03013".equals(type)){
				ip = "10.3.26.163" ;
				user="jdywpt" ;
			}else if("05002".equals(type)){
				ip = "10.3.26.164" ;
				user="jdywpt" ;
			}else if("test".equals(type)){
				ip = "10.2.64.44" ;
				user="test" ;
			}else if("input".equals(type)){
				ip = args[3] ;
				user = "jdywpt" ;
			}
			ConnectionFactory factory = new TibjmsConnectionFactory(ip);
			connection = factory.createConnection(user, user);
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			destination = new TibjmsQueue(queue);
			msgProducer = session.createProducer(null);
			for (int i = 0; i < contentList.size(); i++) {
				msg = session.createBytesMessage() ;
				msg.writeBytes( contentList.get(i).getBytes("GBK")  ) ;
				msgProducer.send(destination, msg);
			}
			connection.close();
			System.out.println("Published message finished, message count is:"+contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void sendMsg(TypeEnum type) throws Exception {
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;
		List<String> contentList = readFileContent(type.context,"UTF-8");
		try {
			TextMessage msg;
			ConnectionFactory factory = new TibjmsConnectionFactory("192.101.1.198");
			connection = factory.createConnection("emsco", "emsco");
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			destination = new TibjmsQueue(type.getQueue());
			msgProducer = session.createProducer(null);
			for (int i = 0; i < contentList.size(); i++) {
				msg = session.createTextMessage();
				msg.setText(contentList.get(i));
				msgProducer.send(destination, msg);
			}
			connection.close();
			System.out.println("Published message finished, message count is:"+contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void sendMsgProd(TypeEnumProd type) throws Exception {
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;
		List<String> contentList = readFileContent(type.context,"UTF-8");
		try {
			TextMessage msg;
			ConnectionFactory factory = new TibjmsConnectionFactory("10.2.64.44");
			connection = factory.createConnection("jdpt", "jdpt");
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			destination = new TibjmsQueue(type.getQueue());
			msgProducer = session.createProducer(null);
			for (int i = 0; i < contentList.size(); i++) {
				msg = session.createTextMessage();
				msg.setText(contentList.get(i));
				msgProducer.send(destination, msg);
			}
			connection.close();
			System.out.println("Published message finished, message count is:"+contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	enum TypeEnum {
		封车("1.dat", "TST.EMSCO.JDPT"), 发运_路单信息("2.dat", "TST.EMSCO.JDPT"), 发运_路单邮袋关系信息("3.dat", "TST.EMSCO.JDPT"), 发运_揽投部发运信息("4.dat", "TST.EMSCO.JDPT"), 封发_邮袋信息("5.dat", "TST.EMSCO.JDPT"), 封发_邮件封发信息("6.dat", "TST.EMSCO.JDPT"), 收寄_邮件收寄信息("7.dat",
				"TST.EMSCO.JDPT"), 收寄_一票多件信息("8.dat", "TST.EMSCO.JDPT"), 收寄_邮件收寄费用信息("9.dat", "TST.EMSCO.JDPT"), 收寄_全名址录入信息("10.dat", "TST.EMSCO.JDPT");
		private String context;
		private String queue;

		public String getQueue() {
			return queue;
		}

		public void setQueue(String queue) {
			this.queue = queue;
		}

		public String getContext() {
			return this.context;
		}

		private TypeEnum(String context, String queue) {
			this.context = context;
			this.queue = queue;
		}

		public static void main(String[] args) {
			for (TypeEnum name : TypeEnum.values()) {
				System.out.println(name + " : " + name.getContext());
			}
		}
	}
	
	
	enum TypeEnumProd {
		投递下段("TDXD.dat", "JDPT.EMSCO.TDXD"), 投递反馈("TDFK.dat", "JDPT.EMSCO.TDFK"), 路单接收("LD.dat", "JDPT.EMSCO.LTLD"), 封发("FF.dat", "JDPT.EMSCO.LTFF"), 收寄("SJ.dat", "JDPT.EMSCO.ITEM");
		private String context;
		private String queue;

		public String getQueue() {
			return queue;
		}

		public void setQueue(String queue) {
			this.queue = queue;
		}

		public String getContext() {
			return this.context;
		}

		private TypeEnumProd(String context, String queue) {
			this.context = context;
			this.queue = queue;
		}

	}

	private static List<String> readFileContent(String fileName, String charSet) throws Exception {
		List<String> resultList = new ArrayList<String>();
		InputStream is=TestMain.class.getClassLoader().getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charSet));
		String line = "";
		StringBuffer sb = new StringBuffer() ;
		while ((line = br.readLine()) != null) {
//			resultList.add(line);
			sb.append(line).append("\n") ;
		}
		
		resultList.add(sb.toString());
		System.out.println(resultList.get(0));
		return resultList;
	}
	
	private static List<String> readFileContentList(String fileName, String charSet) throws Exception {
		List<String> resultList = new ArrayList<String>();
		InputStream is= new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charSet));
		String line = "";
		StringBuffer sb = new StringBuffer() ;
		while ((line = br.readLine()) != null) {
			resultList.add(line);
			sb.append(line).append("\n") ;
		}
		
//		resultList.add(sb.toString());
		System.out.println(resultList.size()+"-->"+resultList.get(0));
		return resultList;
	}
}
