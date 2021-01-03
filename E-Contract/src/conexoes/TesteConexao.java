package conexoes;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class TesteConexao {
	

	public static boolean doPing(String host) {
		return doPing(host, 3000); // 3 segundos
	}

	/**
	 * Verifica se determinado host esta atingivel
	 */
	public static boolean doPing(String host, int timeOut) {
		try {
			return InetAddress.getByName(host).isReachable(timeOut);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Cria uma comunicacao com a porta desejada, se a porta estiver disponivel
	 * returna true, caso contrário uma exception ira ocorrer e retornara false
	 */
	public static boolean isPortAvailable(int port) {
		try {
			ServerSocket srv = new ServerSocket(port);
			srv.close();
			srv = null;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Uma outra maneira de fazer o ping, dessa maneira é invocado o comando ping do
	 * sistemaOperacional e verificado na mensagem de retorno se houve faha.
	 */
	public static boolean runPing(String ipstr) {
		boolean retv = false;
		try {
			InputStream ins = Runtime.getRuntime().exec("ping -n 1 -w 2000 " + ipstr).getInputStream();
			Thread.sleep(3000);
			byte[] prsbuf = new byte[ins.available()];
			ins.read(prsbuf);
			String parsstr = new StringTokenizer(new String(prsbuf), "%").nextToken().trim();
			if (!parsstr.endsWith("100"))
				retv = true;
		} catch (Exception e) {
			retv = false;
		}
		return retv;
	}
}