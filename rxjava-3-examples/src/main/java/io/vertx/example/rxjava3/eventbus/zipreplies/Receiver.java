package io.vertx.example.rxjava3.eventbus.zipreplies;

import io.vertx.example.util.Runner;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.eventbus.EventBus;
import java.security.SecureRandom;

import java.util.Random;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Receiver extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runClusteredExample(Receiver.class);
  }


  @Override
  public void start() throws Exception {

    Random random1 = new SecureRandom();
    EventBus eb = vertx.eventBus();

    eb.consumer("heatsensor1").
        toFlowable().
        subscribe(message -> {
          message.reply(9 + random1.nextInt(5));
        });

    eb.consumer("heatsensor2").
      toFlowable().
        subscribe(message -> {
          message.reply(10 + random1.nextInt(3));
        });
  }
}
