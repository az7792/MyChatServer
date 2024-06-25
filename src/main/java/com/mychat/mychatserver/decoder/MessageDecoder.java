package com.mychat.mychatserver.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;
import com.mychat.mychatserver.entity.Message;

public class MessageDecoder implements Decoder.Text<Message> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message decode(String s) throws DecodeException {
        try {
            return objectMapper.readValue(s, Message.class);
        } catch (Exception e) {
            throw new DecodeException(s, "Unable to decode text to Message", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }
}
