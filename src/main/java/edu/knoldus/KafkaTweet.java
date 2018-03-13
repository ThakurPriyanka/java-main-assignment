package edu.knoldus;

import com.beust.jcommander.internal.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KafkaTweet {
    public void tweetOF(){

        private static final String topic = "twitter-topic";


        Properties properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");

        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        // add some track terms
        endpoint.trackTerms(Lists.newArrayList("twitterapi",
                "#modi"));
        Config conf = ConfigFactory.load();
        Authentication auth = new OAuth1(conf.getString("CONSUMER_KEY"), conf.getString("CONSUMER_SECRET"), conf.getString("ACCESS_KEY"),
                conf.getString("ACCESS_SECRET"));
        // Authentication auth = new BasicAuth(username, password);
        // Create a new BasicClient. By default gzip is enabled.
        Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
                .endpoint(endpoint).authentication(auth)
                .processor(new StringDelimitedProcessor(queue)).build();

        client.connect();
        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            KeyedMessage<String, String> message = null;
            try {
                message = new KeyedMessage<String, String>(topic, queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.send(message);
        }
        producer.close();
        client.stop();
    }
}
