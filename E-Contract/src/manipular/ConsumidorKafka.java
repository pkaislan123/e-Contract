package manipular;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;




	public class ConsumidorKafka {

		public ConsumidorKafka(){
			Properties properties = new Properties();
		    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ConsumidorKafka.class.getSimpleName());
		    
	        
	        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
	        List topics = new ArrayList();
	        topics.add("teste");
	        kafkaConsumer.subscribe(topics);
	        try{
	            while (true){
	            	ConsumerRecords<String, String> records = kafkaConsumer.poll(10);
	            	if(!records.isEmpty())
	            	    System.out.println("Records found: " + records.count());
	            	  
	            	  for(ConsumerRecord record : records){
	            	    System.out.println("Processing likes");
	            	    System.out.println("Key: " + record.key());
	            	    System.out.println("Value: " + record.value());
	            	  }
	            }
	        }catch (Exception e){
	            System.out.println(e.getMessage());
	        }finally {
	            kafkaConsumer.close();
	        }
	        
	    }
	}