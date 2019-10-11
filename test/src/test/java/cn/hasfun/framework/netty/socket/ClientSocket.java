package cn.hasfun.framework.netty.socket;

import java.io.*;
import java.net.Socket;

public class ClientSocket {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 9999);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        while (true) {
            String str = bufferedReader.readLine();
            bufferedWriter.write(str);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        }
    }
}
