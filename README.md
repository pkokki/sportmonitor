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