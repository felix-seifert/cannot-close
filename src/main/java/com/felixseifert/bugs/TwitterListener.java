package com.felixseifert.bugs;

import io.quarkus.runtime.Startup;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jboss.logging.Logger;

@Startup
@ApplicationScoped
public class TwitterListener {

    private static final Logger LOGGER = Logger.getLogger(TwitterListener.class);

    @Inject
    TwitterClient twitterClient;

    @PostConstruct
    public void startListener() throws IOException, URISyntaxException {
        final Map<String, String> rules =
                Map.of("(entity:\\\"New York\\\" OR #NewYork OR \\\"New York\\\") lang:en -is:retweet", "NYC");

        final List<String> existingRules = twitterClient.getExistingRules();
        if (!existingRules.isEmpty()) twitterClient.deleteRules(existingRules);
        twitterClient.createRules(rules);

        twitterClient.connectStream(1024).forEach(LOGGER::info);
    }
}