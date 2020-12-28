# File/Module name: kafka_producer_interactive.py 
# # Install dependencies with: pip3 install kafka-python [bpython (optional)] 
from kafka import KafkaProducer 
import json 
""" 
# Just a KafkaProducer for interactive testing purposes
Usage example:
    /code # bpython        
    bpython version 0.20.1 on top of Python 3.9.0 /usr/local/bin/python        
    >>> import kafka_producer_interactive as kpi        
    >>> send = kpi.producer(topic='mailing')        
    >>> send('myKey', value={'myKeyStr': 'asd'}) 
"""
def producer(broker='kafka:9092', topic='my-topic'): 
    p = KafkaProducer(bootstrap_servers=[broker], 
        value_serializer=lambda x: json.dumps(x).encode('utf-8'))

    def send_and_flush(key: str, value: dict, *args, **kwargs):
        p.send(topic, key=key.encode('utf-8'), value=value, *args, **kwargs) 
        p.flush()
        
    return send_and_flush
