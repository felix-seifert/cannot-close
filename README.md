# cannot-close

This application serves as an example for the GitHub
issue [Cannot Stop Application Once It Is Started](https://github.com/quarkusio/quarkus/issues/20952). The problem of
the application is that it cannot be stopped once it is started. See the full details in the linked GitHub issue.

## Steps to Reproduce

The application connects to
Twitter's [Filtered Stream](https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream) API. This API
requires a Bearer token which can be retrieved
after [registration via this URL](https://developer.twitter.com/en/apply-for-access). Add your token to the
file [application.properties](src/main/resources/application.properties).

Start the application (dev mode and production mode seem to have the same issue). Usually, stopping the application
should be possible via `Ctrl + C`. However, it continues to run. To see that the application continues to run, even
after closing the terminal, you can see the Tweet counter on
the [Twitter Development Portal](https://developer.twitter.com/en/portal/dashboard) going up.

To simplify the process of stopping the application, you can start it via your IDE (I use IntelliJ). To stop it, you
have to press the stop button twice.