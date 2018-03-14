package edu.knoldus;

import edu.knoldus.operation.Operation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        ConnectionTwitter connectionObject = new ConnectionTwitter();
        twitter4j.Twitter twitter = connectionObject.getConnectionTwitter();
        Operation twitterobject = new Operation(twitter);
        try {
//            CompletableFuture<Stream<String>> resultTweet = twitterobject.getTweetWithLimit();
//            resultTweet.thenAccept(tweets -> tweets.forEach(System.out::println));
//            CompletableFuture<List<Status>> resultOldNew = twitterobject.getTweetWithNewerToOlder();
//            resultOldNew.thenAccept(tweets -> tweets.forEach(System.out::println));
//            CompletableFuture<Stream<Integer>> resultRetweet = twitterobject.getRetweetCount();
//            resultRetweet.thenAccept(tweets -> tweets.forEach(System.out::println));
//
//            CompletableFuture<Stream<Integer>> resultTotalLike = twitterobject.getTotalLike();
//            resultTotalLike.thenAccept(tweets -> tweets.forEach(System.out::println));
            CompletableFuture<List<Status>> resultDate = twitterobject.getForDate();
            resultDate.thenAccept(tweets -> tweets.forEach(System.out::println));
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
