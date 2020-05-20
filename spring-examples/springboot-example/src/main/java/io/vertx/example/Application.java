package io.vertx.example;


import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class Application {

  @Autowired
  private StaticServer staticServer;

  public static void main(String[] args) {

    // This is basically the same example as the web-examples static site example but it's booted using
    // Spring Boot, not Vert.x
    SpringApplication.run(Application.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void deployVerticle() {
    Vertx.vertx().deployVerticle(staticServer);
  }
}
