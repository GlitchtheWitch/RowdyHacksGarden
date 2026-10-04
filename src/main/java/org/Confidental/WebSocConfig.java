package org.Confidental;
//Web Socket Configuration Java File

//imports
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerCofigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocConfig implements WebSocketMessageBrokerConfigurer{
    //
     @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
         config.enableSimpleBroker("/topic");
         config.setApplicationDestinationPrefixes("/app");
     }

     @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
         registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
     }

}