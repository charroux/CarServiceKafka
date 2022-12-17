package com.example.partitioner;

import org.apache.kafka.streams.processor.StreamPartitioner;

import java.util.Map;

public class OfficePartitioner implements StreamPartitioner {

    @Override
    public Integer partition(String topic, Object key, Object value, int numPartitions) {
        if(key!=null && key instanceof String){
            String keyS = (String)key;
            if(keyS.equals("Office Paris")) return 0;
            if(keyS.equals("Office Nice")) return 1;
        }
        return 0;
    }
}
