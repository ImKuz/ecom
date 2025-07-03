package io.kuz.ecom.gateway.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "grpc")
public class GrpcChannelProperties {

    private Map<String, ChannelConfig> channel;

    public ChannelConfig getConfig(String name) {
        return channel.get(name);
    }

    public Map<String, ChannelConfig> getChannel() {
        return channel;
    }

    public void setChannel(Map<String, ChannelConfig> channel) {
        this.channel = channel;
    }

    public static class ChannelConfig {
        private String host;
        private int port;

        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }

        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
    }
}
