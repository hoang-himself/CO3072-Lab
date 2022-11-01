#!/usr/bin/env python3

from __future__ import print_function

from kafka import KafkaConsumer

TOPIC = 'hh_python'
BROKER_URI = 'broker-1:29091'
CONSUMER_GROUP = 'hh_g1'

tracker = dict()

consumer = KafkaConsumer(
    TOPIC,
    group_id=CONSUMER_GROUP,
    bootstrap_servers=[BROKER_URI]
)

for message in consumer:
    print(f"key={message.key} value={message.value}")

    nominal_message = message.value.decode('ascii').split(' ')
    for msg in nominal_message:
        if msg in tracker.keys():
            tracker[msg] += 1
        else:
            tracker[msg] = 1

    print(tracker)
