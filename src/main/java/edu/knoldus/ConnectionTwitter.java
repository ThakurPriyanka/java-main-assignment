package edu.knoldus;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class ConnectionTwitter {
    /**
     * @return twitter . Twitter object
     */
    public Twitter getConnectionTwitter() {
        Config conf = ConfigFactory.load();
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(conf.getString("CONSUMER_KEY"),
                conf.getString("CONSUMER_SECRET"));
        twitter.setOAuthAccessToken(new AccessToken(
                conf.getString("ACCESS_KEY"),
                conf.getString("ACCESS_SECRET")));
        return twitter;
    }
}
