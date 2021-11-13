package main.java.outros;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class GetData {
	
	
	
	
	
	public GetData() {
	
		
	
	}
	
	
	
	
	public String getHora() {

	    
	    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

	 }
	public String getData() {

		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    
	}
	
	public String getDataHora() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		return sdf.format(new Date());
	}
	
	
	public String getDataHoraMinus(int minus) {
	    return LocalDateTime.now().minusMinutes(minus).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

	}
	
	
	public String getHoraLog() {
		LocalDateTime date = LocalDateTime.now();

	    return date.getHour() + "-" + date.getMinute() + "-" + date.getSecond() ;
	    
	  
	 }
	
	
	public String getTempoNet() {
		try {
		    String ntpServer = "a.st1.ntp.br";
		 
		    NTPUDPClient timeClient = new NTPUDPClient();
		    InetAddress inetAddress = InetAddress.getByName(ntpServer);
		    TimeInfo timeInfo = timeClient.getTime(inetAddress);
		    long returnTime = timeInfo.getReturnTime();
		    Date time = new Date(returnTime);
		    LocalDateTime date =
		    	    LocalDateTime.ofInstant(Instant.ofEpochMilli(returnTime), ZoneId.systemDefault());
		    return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + "-" + date.getHour() + ":" + date.getMinute() + ":" + date.getSecond() ;
		    
		    
		} catch (UnknownHostException ex) {
		    ex.printStackTrace();
		    return "Não foi possivel buscar a hora na internet";
		}   catch (IOException ex) {
		    ex.printStackTrace();
		    return "Não foi possivel buscar a hora na internet";

		}
	}
	

	
	public String getDataLog() {
		LocalDateTime date = LocalDateTime.now();

	    return date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();
	
	}
	
	public int getMes() {
		LocalDateTime date = LocalDateTime.now();

	    return date.getMonthValue();
	
	}
	
	
	public int  getAnoAtual(){
		LocalDateTime date = LocalDateTime.now();

	    return date.getYear();

	}
	
	public String getDataNet() {
		try {
		    String ntpServer = "a.st1.ntp.br";
		 
		    NTPUDPClient timeClient = new NTPUDPClient();
		    InetAddress inetAddress = InetAddress.getByName(ntpServer);
		    TimeInfo timeInfo = timeClient.getTime(inetAddress);
		    long returnTime = timeInfo.getReturnTime();
		    Date time = new Date(returnTime);
		    LocalDateTime date =
		    	    LocalDateTime.ofInstant(Instant.ofEpochMilli(returnTime), ZoneId.systemDefault());
		    return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() ;
		    
		    
		} catch (UnknownHostException ex) {
		    ex.printStackTrace();
		    return "Não foi possivel buscar a hora na internet";
		}   catch (IOException ex) {
		    ex.printStackTrace();
		    return "Não foi possivel buscar a hora na internet";

		}
	}

}
