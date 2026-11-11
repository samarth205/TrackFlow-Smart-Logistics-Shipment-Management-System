package com.trackflow.config;
import com.trackflow.security.JwtService; import org.springframework.context.annotation.Configuration; import org.springframework.messaging.*; import org.springframework.messaging.simp.config.MessageBrokerRegistry; import org.springframework.messaging.simp.config.ChannelRegistration; import org.springframework.messaging.support.ChannelInterceptor; import org.springframework.web.socket.config.annotation.*; import java.security.Principal;
@Configuration @EnableWebSocketMessageBroker public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
 private final JwtService jwt;
 public WebSocketConfig(JwtService jwt){this.jwt=jwt;}
 public void configureMessageBroker(MessageBrokerRegistry r){r.enableSimpleBroker("/topic");r.setApplicationDestinationPrefixes("/app");}
 public void registerStompEndpoints(StompEndpointRegistry r){r.addEndpoint("/ws").setAllowedOriginPatterns("*");}
 public void configureClientInboundChannel(ChannelRegistration registration){registration.interceptors(new ChannelInterceptor(){
  public Message<?> preSend(Message<?> message,MessageChannel channel){var a=org.springframework.messaging.simp.stomp.StompHeaderAccessor.wrap(message);if(org.springframework.messaging.simp.stomp.StompCommand.CONNECT.equals(a.getCommand())){var h=a.getFirstNativeHeader("Authorization");if(h==null||!h.startsWith("Bearer "))throw new MessageDeliveryException("Authentication required");try{String email=jwt.subject(h.substring(7));a.setUser((Principal)()->email);}catch(Exception e){throw new MessageDeliveryException("Invalid token");}}else if(org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE.equals(a.getCommand())&&a.getUser()==null)throw new MessageDeliveryException("Authentication required");return org.springframework.messaging.support.MessageBuilder.createMessage(message.getPayload(),a.getMessageHeaders());}
 });}
}
