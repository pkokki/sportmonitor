docker-compose up -d

Where are docker images stored?
C:\Users\Public\Documents\Hyper-V\Virtual hard disks

### timescaledb installation
https://docs.timescale.com/latest/getting-started/installation/docker/installation-docker

    docker run -d --name timescaledb -p 5432:5432 -e POSTGRES_PASSWORD=password -v /panos/docker/storage/timescaledb:/var/lib/postgresql/data timescale/timescaledb:latest-pg11

**PSQL client**: docker exec -it timescaledb psql -U postgres

    select table_schema, table_name, (xpath('/row/cnt/text()', xml_count))[1]::text::int as row_count
    from (
      select table_name, table_schema,
             query_to_xml(format('select count(*) as cnt from %I.%I', table_schema, table_name), false, true, '') as xml_count
      from information_schema.tables
      where table_schema = 'public'
    ) t;

    select l.id, MAX(e.timestamp) 
    from event_master_data l inner join event_data e on l.id = e."eventId" 
    where l.expired = false 
    group by l.id;

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
    
