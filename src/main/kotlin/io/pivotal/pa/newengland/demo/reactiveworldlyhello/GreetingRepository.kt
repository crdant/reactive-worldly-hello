package io.pivotal.pa.newengland.demo.reactiveworldlyhello

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.ReactiveRedisConnection
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Indexed
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@RedisHash("greetings")
data class Greeting (
  @Id
  val id: String? = null,
  val language: String? = null,
  val text: String? = null
)

@Repository
interface GreetingRepository : CrudRepository<Greeting, String> {
  fun findByLanguage(language: String): List<Greeting>

}

@Configuration
@EnableRedisRepositories(basePackageClasses = arrayOf(GreetingRepository::class))
class RedisConfig {

  @Bean
  fun redisConnectionFactory(): RedisConnectionFactory {
    return LettuceConnectionFactory()
  }

  @Bean
  fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
    return LettuceConnectionFactory()
  }

  @Bean
  fun reactiveRedisConnection(redisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisConnection {
    return redisConnectionFactory.reactiveConnection
  }
}

