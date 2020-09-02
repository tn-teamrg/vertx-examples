package io.vertx.example.reactivex.http.client.reduce;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.vertx.core.http.HttpMethod;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.http.HttpClient;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Client extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  @Override
  public void start() throws Exception {
    HttpClient client = vertx.createHttpClient();

    Maybe<String> maybe = client.rxRequest(HttpMethod.GET, 8080, "localhost", "/")

      // Connect
      .flatMapMaybe(req -> req

        // Send request
        .rxSend()

        // Status code check and -> Observable<Buffer>
        .flatMapPublisher(resp -> {
          if (resp.statusCode() != 200) {
            return Flowable.error(new RuntimeException("Wrong status code " + resp.statusCode()));
          }
          return Flowable.just(Buffer.buffer()).mergeWith(resp.toFlowable());
        })

        // Reduce all buffers in a single buffer
        .reduce(Buffer::appendBuffer)

        // Turn in to a string
        .map(buffer -> buffer.toString("UTF-8"))
      );

    maybe.subscribe(data -> System.out.println("Server content " + data), Throwable::printStackTrace);
  }
}
