#Just a Python Environment for interactive testing purposes
FROM python:3.8

RUN mkdir /application
WORKDIR "/application"

RUN pip install --upgrade pip
RUN pip install kafka-python

COPY kafka_producer_interactive.py .
CMD python

# to build/rebuild it:
# docker build --no-cache -t unict/fake_producer:0.0.1 -f Dockerfile .

# to run it:
# docker run -it --network order-service_default unict/fake_producer:0.0.1
#    >>> import kafka_producer_interactive as kpi        
#    >>> send = kpi.producer(topic='orders')        
#    >>> send('order_validation', value={'myKeyStr': 'asd'}) 
