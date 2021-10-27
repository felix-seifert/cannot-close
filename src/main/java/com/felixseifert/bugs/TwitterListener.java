package com.felixseifert.bugs;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ApplicationScoped
public class TwitterListener {

    private static final Logger LOGGER = Logger.getLogger(TwitterListener.class);

    @Inject
    TwitterClient twitterClient;

    private Stream<String> tweetStream;

    public void startListener(@Observes StartupEvent startupEvent) throws IOException, URISyntaxException {
        final Map<String, String> rules =
                Map.of("(entity:\\\"New York\\\" OR #NewYork OR \\\"New York\\\") lang:en -is:retweet", "NYC");

        final List<String> existingRules = twitterClient.getExistingRules();
        if (!existingRules.isEmpty()) twitterClient.deleteRules(existingRules);
        twitterClient.createRules(rules);

        tweetStream = twitterClient.connectStream(1024);
        tweetStream.forEach(LOGGER::info);
    }

    public void stopListener(@Observes ShutdownEvent shutdownEvent) {
        LOGGER.info("STOPPED");
        tweetStream.close();
    }
}