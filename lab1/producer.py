#!/usr/bin/env python3

from __future__ import print_function

from time import sleep
from kafka import KafkaProducer

BROKER_URI = 'broker-1:29091'
TOPIC = 'hh_python'

def on_send_success(record_metadata):
    print(record_metadata.topic)
    print(record_metadata.partition)
    print(record_metadata.offset)

def on_send_error(ex):
    print(ex)

producer = KafkaProducer(bootstrap_servers=[BROKER_URI])
producer.flush(timeout=1000)

with open('input.txt', 'r') as f:
    for line in f:
        producer.send(TOPIC, value=line[:-1].encode('ascii')).add_callback(on_send_success).add_errback(on_send_error)
        sleep(3)
