package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CharsetMemMain {

	public static void main(String[] args) throws IOException {
		String str = "/n中文测试，这是内部硬编码的串" + "/ntest english character";
		String strin = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "utf-8")); // 设置输入接口按中文编码
		BufferedWriter stdout = new BufferedWriter(new OutputStreamWriter(System.out, "utf-8")); // 设置输出接口按中文编码
		stdout.write("请输入:");
		stdout.flush();
		strin = stdin.readLine();
		stdout.write("这是从用户输入的串：" + strin);
		stdout.write(str);
		stdout.flush();
	}

}
