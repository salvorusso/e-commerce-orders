/*

                    +++     How To use this class   +++

    - Decomentare metodo "PostHeartBeatTest" nel file "orderController"
    - Decomentare tutte le righe di codice presente in questa classe

La richiesta post, se andata a buon fine, stamperà il risultato all'interno della console log.debug ogni 25 secondi.
*/


/*package com.unict.dsbd.orders.heartBeater;

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
import org.springframework.data.mongodb.MongoDatabaseUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.unict.dsbd.orders.OrdersApplication.log;

@Component
@EnableScheduling
@RestController
public class HeartBeatController {

    private int mongoPort = 27017;

    @Scheduled(fixedRate = 25000)
    public void HeartBeat() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:9080/orders/ping";

        HeartBeat tmpHeartBeat = this.chekStatusServer();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(tmpHeartBeat.toString(), headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        log.debug("HeartBeat responce = " + response);
    }

    private HeartBeat chekStatusServer(){

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entityMongoTest = new HttpEntity<String>("", new HttpHeaders());
        try {
            restTemplate.getForObject("http://127.0.0.1:" + this.mongoPort , String.class, entityMongoTest);
        } catch (org.springframework.web.client.ResourceAccessException e) {
            return new HeartBeat("up", "down");
        }
        return new HeartBeat("up", "up");
    }


}*/
