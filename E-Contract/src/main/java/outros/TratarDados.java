package main.java.outros;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TratarDados {

    String dados;

    public TratarDados(String dados)
    {
        this.dados = dados;
       
    }
    
    public String tratar(String start, String end, boolean replacer, String alvo, String trocar)
    {
        String processamento = new String();
        String replace = new String();
        String regex = start.concat("(.*?)").concat(end);
      
        if(replacer) {
             replace = dados.replace(alvo, trocar);
        }
        else
        {
        	replace = dados;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(replace);
        while(matcher.find())
        {
                processamento = matcher.group(1);
        }
        return processamento;

    }

    public String tratar(String start, String end)
    {
        String processamento = new String();
        String regex = start.concat("(.*?)").concat(end);
      
 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dados);
        while(matcher.find())
        {
                processamento = matcher.group(1);
        }
        return processamento;

    }


}
