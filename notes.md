docker-compose up -d

Where are docker images stored?
C:\Users\Public\Documents\Hyper-V\Virtual hard disks

### timescaledb installation
https://docs.timescale.com/latest/getting-started/installation/docker/installation-docker

    docker run -d --name timescaledb -p 5432:5432 -e POSTGRES_PASSWORD=password -v /panos/docker/storage/timescaledb:/var/lib/postgresql/data timescale/timescaledb:latest-pg11

**PSQL client**: docker exec -it timescaledb psql -U postgres

    SELECT table_schema, table_name, (XPATH('/row/cnt/text()', XML_COUNT))[1]::text::int AS row_count
    FROM (
      SELECT table_name, table_schema,
             QUERY_TO_XML(FORMAT('SELECT count(*) AS cnt FROM %I.%I', table_schema, table_name), false, true, '') AS xml_count
      FROM information_schema.tables
      WHERE table_schema = 'public'
    ) t;

    SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE';
    
    SELECT column_name FROM information_schema.columns WHERE table_schema='public' AND table_name='country';
    
    SELECT t.table_name, c.column_name 
    FROM information_schema.tables AS t
    LEFT JOIN information_schema.columns AS c ON t.table_schema=c.table_schema AND t.table_name=c.table_name AND c.column_name='source_id'
    WHERE t.table_schema='public' AND t.table_type='BASE TABLE';

### Kafka cheat sheet 
    root@localhost:/opt/kafka_2.11-0.10.1.0#

List Kafka topics

    bin/kafka-topics.sh --zookeeper localhost:2181 --list

Show the Kafka topic details

    bin/kafka-topics.sh --zookeeper localhost:2181 --topic OVERVIEWS --describe

Change the retention time of a Kafka topic (86400000 = 1day)

    bin/kafka-configs.sh --zookeeper localhost:2181 --alter --entity-type topics --entity-name OVERVIEWS --add-config retention.ms=86400000

Purge a Kafka topic (set retention time to 1 second)

    bin/kafka-configs.sh --zookeeper localhost:2181 --alter --entity-type topics --entity-name 4584120 --add-config retention.ms=1000
    ... wait a minute ...
    bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic 4584120 --delete-config retention.ms

List Kafka topics (with configuration values) that have specific configuration overrides

    bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topics-with-overrides

Delete a Kafka topic

    bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic 4584120
    bin/zookeeper-shell.sh localhost:2181 rmr /brokers/topics/4584120

Get the earliest offset still in a topic

    bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic OVERVIEWS --time -2

Get the latest offset still in a topic

    bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic OVERVIEWS --time -1
    
List the consumer groups known to Kafka

    bin/kafka-consumer-groups.sh --new-consumer --bootstrap-server localhost:9092 --list

View the details of a consumer group

    bin/kafka-consumer-groups.sh --zookeeper localhost:2181 --describe --group <group name>
    
How to seek Kafka consumer offsets by timestamp:

* https://medium.com/@werneckpaiva/how-to-seek-kafka-consumer-offsets-by-timestamp-de351ba35c61
* https://kafka.apache.org/23/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
