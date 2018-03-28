package edu.knoldus.operation;


import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

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

    /**
     *
     * @return . get tweet with in limit
     */

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

    /**
     * @return . list of likes from higher to lower
     */
    public CompletableFuture<List<Status>> getRetweetCount() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> latestTweets = new ArrayList<>();
            try {
                Query query = new Query("modi");
                QueryResult result;
                do {
                    result = twitter.search(query);
                    result.getTweets().sort((statusFirst, statusSecond) ->
                            statusSecond.getRetweetCount() - statusFirst.getRetweetCount());
                    latestTweets.addAll(result.getTweets());
                } while ((query = result.nextQuery()) != null);
            } catch (TwitterException te) {
                System.out.println(te.getMessage());
            }
            return latestTweets;
        });

    }

    /**
     * @return . list of favorite tweet in descending order
     */

    public CompletableFuture<List<Status>> getTotalLike() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> latestTweets = new ArrayList<>();
            try {
                String hashTag = "modi";
                Integer count = 100;
                Query query = new Query(hashTag);
                query.setCount(count);
                query.resultType(Query.RECENT);
                QueryResult result = this.twitter.search(query);
                result.getTweets().sort((firstStatus, secondStatus) ->
                        secondStatus.getFavoriteCount() - firstStatus.getFavoriteCount());
                latestTweets.addAll(result.getTweets());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return latestTweets;
        });
    }

    /**
     * @return . list of tweet for a given date
     */
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
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return latestTweets;
        });
    }
}
