package edu.knoldus.operation;

import twitter4j.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


public class Operation {
    private Twitter twitter;

    public Operation(Twitter twitter) {
        this.twitter = twitter;
    }

    public CompletableFuture<Stream<String>> getTweetWithLimit() {
        return CompletableFuture.supplyAsync(() -> {
            Stream<String> tweetData = null;
            try {
                String hashTag = "modi";
                Integer count = 100;
                Query query = new Query(hashTag);
                query.setCount(count);
                query.resultType(Query.RECENT);
                QueryResult result;
                do {
                    result = twitter.search(query);
                    List<Status> tweets = result.getTweets();
                    System.out.println(tweets);
                    tweetData = tweets.stream().map(tweet -> {
                        System.out.println(tweet.getCreatedAt());
                        return tweet.getText();
                    });
                    System.out.println(tweetData);
                    return tweetData;
                } while ((query = result.nextQuery()) != null);
            } catch (TwitterException te) {
                System.out.println(te.getMessage());
            }
            return tweetData;
        });


    }

    public CompletableFuture<List<Status>> getTweetWithNewerToOlder() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> latestTweets = new ArrayList<>();
            try {
                String hashTag = "modi";
                Integer count = 100;
                Query query = new Query(hashTag);
                query.setCount(count);
                query.resultType(Query.RECENT);
                QueryResult result = this.twitter.search(query);
                result.getTweets().sort(Comparator.comparing(status ->
                        status.getCreatedAt().getTime()));
                latestTweets.addAll(result.getTweets());
            } catch (TwitterException e) {
                System.out.println("error occured " + e.getMessage());
            }
            return latestTweets;
        });
    }
    public CompletableFuture<List<Status>> getRetweetCount() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> latestTweets = new ArrayList<>();
            try {
                String hashTag = "modi";
                Integer count = 100;
                Query query = new Query(hashTag);
                query.setCount(count);
                query.resultType(Query.RECENT);
                    QueryResult queryResult = this.twitter.search(query);
                    queryResult.getTweets().sort((statusFirst, statusSecond) ->
                            statusSecond.getRetweetCount() - statusFirst.getRetweetCount());
                    latestTweets.addAll(queryResult.getTweets());
                } catch (TwitterException e) {
                    System.out.println("error occured " + e.getMessage());
                }
                return latestTweets;
            });
}


    public CompletableFuture<List<Status>> getTotalLike() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> latestTweets = new ArrayList<>();
            try {
                String hashTag = "modi";
                Integer count = 100;
                Query query = new Query(hashTag);
                query.setCount(count);
                query.resultType(Query.RECENT);
                QueryResult queryResult = this.twitter.search(query);
                queryResult.getTweets().sort((firstStatus, secondStatus) ->
                        secondStatus.getFavoriteCount() - firstStatus.getFavoriteCount());
                latestTweets.addAll(queryResult.getTweets());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return latestTweets;
        });
    }

    public CompletableFuture<List<Status>> getForDate() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> latestTweets = new ArrayList<>();
            try {
                String hashTag = "modi";
                String date = "2018-03-10";
                Integer count = 100;
                Query query = new Query(hashTag);
                query.setUntil(date);
                query.setCount(count);
                query.resultType(Query.RECENT);
                QueryResult queryResult = this.twitter.search(query);
                latestTweets.addAll(queryResult.getTweets());
                System.out.println("number of tweets on given date are : " + latestTweets.size());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return latestTweets;
        });
    }
}
