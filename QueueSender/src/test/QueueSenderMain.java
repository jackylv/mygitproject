package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;

import com.tibco.tibjms.TibjmsConnectionFactory;
import com.tibco.tibjms.TibjmsQueue;

public class QueueSenderMain {
	public static void main(String[] args) throws Exception {

		// // ARGS 0:类型 1.参数 0:文件名,1:队列,2:服务器ip,3:用户名,4:字符集
		String type = args[0];

		if ("dzhclct".equalsIgnoreCase(type)) {
			sendMsgDzhClct(args);
		} else if ("local".equalsIgnoreCase(type)) {
			sendMsgLocal(args);
		} else if ("prod".equalsIgnoreCase(type)) {
			sendMsgProd(args);
		}else if ("input".equalsIgnoreCase(type)) {
			sendMsgInput(args);
		}
		
		
		//
		// if("local".equals(type)){
		// ip = "127.0.0.1" ;
		// user="admin" ;
		// }else if("prod".equals(type)){
		// ip = "10.2.64.44" ;
		// user="jdpt" ;
		// }else if("03013".equals(type)){
		// ip = "10.3.26.163" ;
		// user="jdywpt" ;
		// }else if("05002".equals(type)){
		// ip = "10.3.26.164" ;
		// user="jdywpt" ;
		// }else if("test".equals(type)){
		// ip = "10.2.64.44" ;
		// user="test" ;
		// }else if("input".equals(type)){
		// ip = args[3] ;
		// user = "jdywpt" ;
		// }

	}

	// java -jar sendq.jar local aaa.txt,queue,charset
	public static void sendMsgLocal(String[] args) {
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;

		// 0:文件名,1:队列,2:字符集
		String[] inputArgs = args[1].split(",");
		String fileName = inputArgs[0];
		String queue = inputArgs[1];
		
		String charset = "";
		if (inputArgs.length > 2) {
			charset = inputArgs[2];
		}

		if (StringUtils.isBlank(charset)) {
			charset = "UTF-8";
		}

//		String path = "/home/nodemanager/" + fileName;
		try {
			// TextMessage msg;
			BytesMessage msg;

			String ip = "127.0.0.1";
			String user = "admin";
			List<String> contentList = readFileContentList(fileName,charset );
			ConnectionFactory factory = new TibjmsConnectionFactory(ip);
			connection = factory.createConnection(user, user);
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			destination = new TibjmsQueue(queue);
			msgProducer = session.createProducer(null);
			for (int i = 0; i < contentList.size(); i++) {
				msg = session.createBytesMessage();
				msg.writeBytes(contentList.get(i).getBytes( charset ));
				msgProducer.send(destination, msg);
			}
			connection.close();
			System.out.println("Published message finished, message count is:" + contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// java -jar sendq.jar input aaa.txt,queue,ip,user,UTF-8
	public static void sendMsgInput(String[] args) {
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;

		// 0:文件名,1:队列,2:服务器ip,3:用户名,4:字符集
		String[] inputArgs = args[1].split(",");
		try {
			BytesMessage msg;

			String fileName = inputArgs[0];
			String queue = inputArgs[1];
			String ip =inputArgs[2];
			String user = inputArgs[3];
			
			String charset = "";
			if (inputArgs.length > 4) {
				charset = inputArgs[4];
			}

			if (StringUtils.isBlank(charset)) {
				charset = "UTF-8";
			}
			
//			String path = "/home/nodemanager/" + fileName;

			List<String> contentList = readFileContentList(fileName, charset );
			ConnectionFactory factory = new TibjmsConnectionFactory(ip);
			connection = factory.createConnection(user, user);
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			destination = new TibjmsQueue(queue);
			msgProducer = session.createProducer(null);
			for (int i = 0; i < contentList.size(); i++) {
				msg = session.createBytesMessage();
				msg.writeBytes(contentList.get(i).getBytes(charset));
				msgProducer.send(destination, msg);
			}
			connection.close();
			System.out.println("Published message finished, message count is:" + contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// java -jar sendq.jar prod aaa.txt,queue
	public static void sendMsgProd(String[] args) {
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;

		// 0:文件名,1:队列,2:服务器ip,3:用户名,4:字符集
		String[] inputArgs = args[1].split(",");

		String fileName = inputArgs[0];
		String queue = inputArgs[1];

//		String path = "/home/nodemanager/" + fileName;
		try {
			BytesMessage msg;

			String ip = "10.2.64.44";
			String user = "jdpt";

			List<String> contentList = readFileContentList(fileName, "utf-8");
			ConnectionFactory factory = new TibjmsConnectionFactory(ip);
			connection = factory.createConnection(user, user);
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			destination = new TibjmsQueue(queue);
			msgProducer = session.createProducer(null);
			for (int i = 0; i < contentList.size(); i++) {
				msg = session.createBytesMessage();
				msg.writeBytes(contentList.get(i).getBytes("utf-8"));
				msgProducer.send(destination, msg);
			}
			connection.close();
			System.out.println("Published message finished, message count is:" + contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// java -jar sendq.jar dzhclct aaa.txt,queue,ip,user,GBK
	public static void sendMsgDzhClct(String[] args) {
		try {
			// // ARGS 0:类型 1.参数 0:文件名,1:队列,2:服务器ip,3:用户名,4:字符集

			BytesMessage msg;
			Connection connection = null;
			Session session = null;
			MessageProducer msgProducer = null;
			Destination destination = null;

			String[] inputArgs = args[1].split(",");

			String fileName = inputArgs[0];
			String queue = inputArgs[1];
			String ip = inputArgs[2];
			String user = inputArgs[3];

			String charset = "";
			if (inputArgs.length > 4) {
				charset = inputArgs[4];
			}

			if (StringUtils.isBlank(charset)) {
				charset = "GBK";
			}

			// java -jar senddzhclct.jar del-GBK.txt CUS.INT.CL1 10.3.37.48
			ConnectionFactory factory = new TibjmsConnectionFactory(ip);
			connection = factory.createConnection(user, user);
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			destination = new TibjmsQueue(queue);
			msgProducer = session.createProducer(null);
			SimpleDateFormat form = new SimpleDateFormat("yyyyMMddHHmmss");

			List<String> contentList = readFileContentList(fileName, charset);

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < contentList.size(); i++) {
				sb.append(contentList.get(i));
				if (i != contentList.size() - 1) {
					sb.append("\n");
				}
			}
			System.out.println(sb.toString());
			msg = session.createBytesMessage();

			msg.setStringProperty("CUSTOMER_MSGSRC", "PEO600");
			msg.setStringProperty("CUSTOMER_MSGDST", "EMS");
			msg.setStringProperty("CUSTOMER_MSGTYPE", "PCLT");
			msg.setStringProperty("CUSTOMER_MSGTIME", form.format(new Date()));
			msg.setStringProperty("CUSTOMER_MSGSIZE", "1");
			msg.setStringProperty("CUSTOMER_MSGSERNO", "12345678");

			msg.writeBytes(sb.toString().getBytes(charset));
			msgProducer.send(destination, msg);

			connection.close();
			System.out.println("Published message finished, message count is:" + contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMsg(TypeEnum type) throws Exception {
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;
		List<String> contentList = readFileContent(type.context, "UTF-8");
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
			System.out.println("Published message finished, message count is:" + contentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMsgProd(TypeEnumProd type) throws Exception {
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		Destination destination = null;
		List<String> contentList = readFileContent(type.context, "UTF-8");
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
			System.out.println("Published message finished, message count is:" + contentList.size());
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
		InputStream is = QueueSenderMain.class.getClassLoader().getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charSet));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			// resultList.add(line);
			sb.append(line).append("\n");
		}

		resultList.add(sb.toString());
		System.out.println(resultList.get(0));
		return resultList;
	}

	private static List<String> readFileContentList(String fileName, String charSet) throws Exception {
		List<String> resultList = new ArrayList<String>();
		InputStream is = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charSet));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			resultList.add(line);
			sb.append(line).append("\n");
		}

		// resultList.add(sb.toString());
		System.out.println(resultList.size() + "-->" + resultList.get(0));
		return resultList;
	}
}
