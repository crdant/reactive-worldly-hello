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

  fun language(request: ServerRequest): Mono<ServerResponse> {
    return ServerResponse
      .ok()
      .contentType(MediaType.TEXT_PLAIN)
      .body(
        BodyInserters.fromObject("The default language is ${defaultLanguage}")
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
      GET("/", greeter::language)
    }
  }

}
