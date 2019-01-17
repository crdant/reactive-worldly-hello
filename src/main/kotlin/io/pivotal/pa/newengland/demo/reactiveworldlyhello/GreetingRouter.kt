package io.pivotal.pa.newengland.demo.reactiveworldlyhello

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

@Configuration
class Greeter () {
  @Value("\${defaultLanguage}")
  var defaultLanguage: String? = null

  @Autowired
  lateinit var repository: GreetingRepository

  fun defaultLangauge(request: ServerRequest): Mono<ServerResponse> {
    return ServerResponse
      .ok()
      .contentType(MediaType.TEXT_PLAIN)
      .body(
        BodyInserters.fromObject("The default language is ${defaultLanguage}")
      )
  }

  fun greet(request: ServerRequest): Mono<ServerResponse> {
    val language = request.pathVariable("language")
    val greeting = repository.findByLanguage(if (language.length != 0) language else defaultLanguage!!).first();

    return ServerResponse
      .ok()
      .contentType(MediaType.TEXT_PLAIN)
      .body(
        BodyInserters.fromObject(greeting.text!!)
      )
  }
}


@Configuration
class GreetingRouter {

  @Autowired
  lateinit var greeter : Greeter

  @Bean
  fun router() = router {
    (accept(TEXT_HTML) or accept(TEXT_HTML)).nest {
      GET("/", greeter::defaultLangauge)
      GET("/greeting/{language}", greeter::greet)
    }
  }

}
