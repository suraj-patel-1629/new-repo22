package com.example.payment_servcie.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${rabbitmq.queue.name}")
  private String paymentResponseQueue;

  @Value("${rabbitmq.exchange.name}")
  private String paymentResponseexchange;

  @Value("${rabbitmq.routing.key.name}")
  private String paymentRoutingkey;

  

  @Bean
  public Queue queue() {
    return new Queue(paymentResponseQueue);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(paymentResponseexchange);

  }

  @Bean
  public Binding bind() {
    return BindingBuilder.bind(queue()).to(exchange()).with(paymentRoutingkey);
  }

 

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }

}
