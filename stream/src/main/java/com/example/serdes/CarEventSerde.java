package com.example.serdes;

import com.example.CarEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CarEventSerde implements Serde<CarEvent> {

    private CarEventSerializer serializer = new CarEventSerializer();
    private CarEventDeserializer deserializer = new CarEventDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        serializer.configure(configs, isKey);
        deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer<CarEvent> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<CarEvent> deserializer() {
        return deserializer;
    }
}
