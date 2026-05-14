package com.publicaciones.publicaciones_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

    public static final String COLA_NOTIFICACIONES = "cola.notificaciones";
    public static final String EXCHANGE = "exchange.publicaciones";
    public static final String CLAVE_ENRUTAMIENTO = "publicacion.creada";

    @Bean
    public Queue cola() {
        return new Queue(COLA_NOTIFICACIONES, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding enlace(Queue cola, DirectExchange exchange) {
        return BindingBuilder.bind(cola).to(exchange).with(CLAVE_ENRUTAMIENTO);
    }

    @Bean
    public JacksonJsonMessageConverter converter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }
}
