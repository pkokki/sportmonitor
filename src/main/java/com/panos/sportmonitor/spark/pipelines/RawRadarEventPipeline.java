package com.panos.sportmonitor.spark.pipelines;

import com.panos.sportmonitor.spark.dto.MatchDetailsType;
import com.panos.sportmonitor.spark.streams.*;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.util.TempViewHelper;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.stereotype.Service;

@Service
public class RawRadarEventPipeline {

    public static final String VIEW_MATCH_TIMELINE_TYPES = "match_timeline_types";
    public static final String VIEW_MATCH_DETAIL_TYPES = "match_detail_types";

    public void init(JavaStreamingContext streamingContext) {
        loadExistingMatchDetailTypes();
        loadExistingMatchTimelineTypes();
    }

    private void loadExistingMatchTimelineTypes() {
        Dataset<Row> ds = PostgresHelper.readQuery(SparkSession.active(), "select typeid from match_timeline_types");
        TempViewHelper.appendOrCreateView(VIEW_MATCH_TIMELINE_TYPES, ds);
    }

    private void loadExistingMatchDetailTypes() {
        Dataset<Row> ds = PostgresHelper.readQuery(SparkSession.active(), "select typeid from match_detail_types");
        TempViewHelper.appendOrCreateView(VIEW_MATCH_DETAIL_TYPES, ds);
    }

    public void run(RawRadarEventStream rawRadarEventStream) {

        MatchSituationEventStream matchSituationEventStream = rawRadarEventStream.createMatchSituationEventStream();
        matchSituationEventStream.output(matchSituationEventStream::appendToMatchSituationEventsTable);

        MatchTimelineEventStream matchTimelineEventStream = rawRadarEventStream.createMatchTimelineEventStream(VIEW_MATCH_TIMELINE_TYPES);
        matchTimelineEventStream.output(
                matchTimelineEventStream::appendToMatchTimelineTypesTable,
                matchTimelineEventStream::appendToMatchTimelineEventsTable
        );

        MatchDetailsEventStream matchDetailsEventStream = rawRadarEventStream.createMatchDetailsEventStream(VIEW_MATCH_DETAIL_TYPES);
        matchDetailsEventStream.output(
                matchDetailsEventStream::appendToMatchDetailsEventsTable,
                matchDetailsEventStream::appendToMatchDetailsTypesTable
        );
    }
}
