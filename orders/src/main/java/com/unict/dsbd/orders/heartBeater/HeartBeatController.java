/*

                    +++     How To use this class   +++

    - Decomentare metodo "PostHeartBeatTest" nel file "orderController"
    - Decomentare tutte le righe di codice presente in questa classe

La richiesta post, se andata a buon fine, stamperà il risultato all'interno della console log.debug ogni 25 secondi.
*/
/*

package com.unict.dsbd.orders.heartBeater;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.unict.dsbd.orders.order.OrderRepository;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDatabaseUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.unict.dsbd.orders.OrdersApplication.log;

import javax.annotation.PostConstruct;

@Component
@EnableScheduling
@RestController
public class HeartBeatController {

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Value("${heartbeatHost}")
    private String heartbeatHost;

    @Value("${heartbeatPort}")
    private String heartbeatPort;
    
    @Value("${heartbeatBasePath}")
    private String heartbeatBasePath;

    private RestTemplate restTemplate = null;
    private String mongoEndpoint = null;
    private String heartbeatEndpoint = null;
    
	@PostConstruct
	public void init() {
		restTemplate = new RestTemplate();
		
		UriComponentsBuilder bb = UriComponentsBuilder.newInstance();
		bb.scheme("http").host(mongoHost).port(mongoPort);
		mongoEndpoint = bb.toUriString();
		log.debug("init() HeartBeatController: mongodb endpoint = {}", mongoEndpoint);
		
		UriComponentsBuilder bb2 = UriComponentsBuilder.newInstance();
		bb2.scheme("http").host(heartbeatHost).port(heartbeatPort);
		if(heartbeatBasePath != null && !heartbeatBasePath.trim().isEmpty())
			bb2.path(heartbeatBasePath);
		heartbeatEndpoint = bb2.toUriString();
		log.debug("init() HeartBeatController: heartbeatEndpoint endpoint = {}", heartbeatEndpoint);
	}
	
    @Scheduled(fixedRate = 25000)
    public void HeartBeat() {
        RestTemplate restTemplate = new RestTemplate();

        HeartBeat tmpHeartBeat = this.chekStatusServer();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(tmpHeartBeat.toString(), headers);
        String response = restTemplate.postForObject(heartbeatEndpoint, entity, String.class);
        log.debug("HeartBeat response = " + response);
    }

    private HeartBeat chekStatusServer() {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entityMongoTest = new HttpEntity<String>("", new HttpHeaders());
        try {

            ResponseEntity<String> result = restTemplate.exchange("http://" + this.mongoHost + ":" + this.mongoPort, HttpMethod.GET, entityMongoTest, String.class);

            if (result.getStatusCode() == HttpStatus.OK)
                return new HeartBeat("up", "up");
            else
                return new HeartBeat("up", "down");

        } catch (org.springframework.web.client.ResourceAccessException e) {
            return new HeartBeat("up", "down");
        }

    }


}
*/
