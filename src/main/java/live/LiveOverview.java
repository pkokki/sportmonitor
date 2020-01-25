package live;

import java.io.Serializable;
import java.util.List;

public class LiveOverview implements Serializable {
    /*
    public static final DataType SELECTION_TYPE = new StructType(new StructField[]{
            new StructField("id", DataTypes.StringType, false, Metadata.empty()),
            new StructField("description", DataTypes.StringType, false, Metadata.empty()),
            new StructField("price", DataTypes.FloatType, false, Metadata.empty())
    });

    public static final DataType MARKET_TYPE = new StructType(new StructField[]{
            new StructField("id", DataTypes.StringType, false, Metadata.empty()),
            new StructField("description", DataTypes.StringType, false, Metadata.empty()),
            new StructField("isSuspended", DataTypes.BooleanType, false, Metadata.empty()),
            new StructField("type", DataTypes.StringType, false, Metadata.empty()),
            new StructField("markets", DataTypes.createArrayType(SELECTION_TYPE), false, Metadata.empty())
    });

    public static final DataType EVENT_TYPE = new StructType(new StructField[] {
            new StructField("timestamp", DataTypes.LongType, false, Metadata.empty()),
            new StructField("id", DataTypes.StringType, false, Metadata.empty()),
            new StructField("regionId", DataTypes.StringType, false, Metadata.empty()),
            new StructField("regionName", DataTypes.StringType, false, Metadata.empty()),
            new StructField("leagueId", DataTypes.StringType, false, Metadata.empty()),
            new StructField("leagueName", DataTypes.StringType, false, Metadata.empty()),
            new StructField("betRadarId", DataTypes.LongType, true, Metadata.empty()),
            new StructField("betRadarLink", DataTypes.StringType, true, Metadata.empty()),
            new StructField("shortTitle", DataTypes.StringType, true, Metadata.empty()),
            new StructField("title", DataTypes.StringType, true, Metadata.empty()),
            new StructField("startTime", DataTypes.StringType, true, Metadata.empty()),
            new StructField("startTimeTicks", DataTypes.LongType, true, Metadata.empty()),
            new StructField("clockTime", DataTypes.StringType, true, Metadata.empty()),
            new StructField("isSuspended", DataTypes.BooleanType, false, Metadata.empty()),
            new StructField("liveEventLink", DataTypes.StringType, true, Metadata.empty()),
            new StructField("homeTeam", DataTypes.StringType, true, Metadata.empty()),
            new StructField("homeScore", DataTypes.StringType, true, Metadata.empty()),
            new StructField("homeRedCards", DataTypes.IntegerType, true, Metadata.empty()),
            new StructField("awayTeam", DataTypes.StringType, true, Metadata.empty()),
            new StructField("awayScore", DataTypes.StringType, true, Metadata.empty()),
            new StructField("awayRedCards", DataTypes.IntegerType, true, Metadata.empty()),
            new StructField("markets", DataTypes.createArrayType(MARKET_TYPE), true, Metadata.empty())
    });
    public static final StructType OVERVIEW_TYPE = new StructType(new StructField[] {
            new StructField("events", DataTypes.createArrayType(EVENT_TYPE), false, Metadata.empty()),
    });
    */

    private List<Event> events;

    List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public static class Selection implements Serializable {
        private String id;
        private String description;
        private Float price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }
    }

    public static class Market implements Serializable {
        private String id;
        private String description;
        private String type;
        private Boolean isSuspended;
        private List<Selection> selections;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getIsSuspended() {
            return isSuspended;
        }

        public void setIsSuspended(Boolean suspended) {
            isSuspended = suspended;
        }

        public List<Selection> getSelections() {
            return selections;
        }

        public void setSelections(List<Selection> selections) {
            this.selections = selections;
        }
    }

    public static class Event implements Serializable {
        private String id;
        private Long timestamp;
        private String regionId;
        private String regionName;
        private String leagueId;
        private String leagueName;
        private Long betRadarId;
        private String betRadarLink;
        private String clockTime;
        private String shortTitle;
        private String title;
        private String startTime;
        private Long startTimeTicks;
        private Boolean isSuspended;
        private String liveEventLink;
        private String homeTeam;
        private String homeScore;
        private Integer homeRedCards;
        private String awayTeam;
        private String awayScore;
        private Integer awayRedCards;
        private List<Market> markets;

        public Long getTimestamp() { return timestamp; }
        public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getRegionId() {
            return regionId;
        }
        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getRegionName() { return regionName; }
        public void setRegionName(String regionName) { this.regionName = regionName; }

        public String getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(String leagueId) {
            this.leagueId = leagueId;
        }

        public String getLeagueName() {
            return leagueName;
        }

        public void setLeagueName(String leagueName) {
            this.leagueName = leagueName;
        }

        public Long getBetRadarId() {
            return betRadarId;
        }

        public void setBetRadarId(Long betRadarId) {
            this.betRadarId = betRadarId;
        }

        public String getBetRadarLink() {
            return betRadarLink;
        }

        public void setBetRadarLink(String betRadarLink) {
            this.betRadarLink = betRadarLink;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public void setShortTitle(String shortTitle) {
            this.shortTitle = shortTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public Long getStartTimeTicks() {
            return startTimeTicks;
        }

        public void setStartTimeTicks(Long startTimeTicks) {
            this.startTimeTicks = startTimeTicks;
        }

        public Boolean getIsSuspended() {
            return isSuspended;
        }

        public void setIsSuspended(Boolean suspended) {
            isSuspended = suspended;
        }

        public String getLiveEventLink() {
            return liveEventLink;
        }

        public void setLiveEventLink(String liveEventLink) {
            this.liveEventLink = liveEventLink;
        }

        public String getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(String homeTeam) {
            this.homeTeam = homeTeam;
        }

        public String getHomeScore() {
            return homeScore;
        }

        public void setHomeScore(String homeScore) {
            this.homeScore = homeScore;
        }

        public int getHomeRedCards() {
            return homeRedCards;
        }

        public void setHomeRedCards(int homeRedCards) {
            this.homeRedCards = homeRedCards;
        }

        public String getAwayTeam() {
            return awayTeam;
        }

        public void setAwayTeam(String awayTeam) {
            this.awayTeam = awayTeam;
        }

        public int getAwayRedCards() {
            return awayRedCards;
        }

        public void setAwayRedCards(int awayRedCards) {
            this.awayRedCards = awayRedCards;
        }

        public List<Market> getMarkets() {
            return markets;
        }

        public void setMarkets(List<Market> markets) {
            this.markets = markets;
        }

        public String getClockTime() {
            return clockTime;
        }

        public void setClockTime(String clockTime) {
            this.clockTime = clockTime;
        }

        public String getAwayScore() {
            return awayScore;
        }

        public void setAwayScore(String awayScore) {
            this.awayScore = awayScore;
        }

        @Override public String toString() {
            return "Event(id = " + id + " " + clockTime + " " + shortTitle + " " + homeScore + "-" + awayScore + ")";
        }
    }

    /**
     * User-defined data type for storing an event information as state in mapGroupsWithState.
     */
    public static class EventInfo implements Serializable {
        private int numEvents = 0;
        private long startTimestampMs = -1;
        private long endTimestampMs = -1;

        public int getNumEvents() { return numEvents; }
        public void setNumEvents(int numEvents) { this.numEvents = numEvents; }

        public long getStartTimestampMs() { return startTimestampMs; }
        public void setStartTimestampMs(long startTimestampMs) {
            this.startTimestampMs = startTimestampMs;
        }

        public long getEndTimestampMs() { return endTimestampMs; }
        public void setEndTimestampMs(long endTimestampMs) { this.endTimestampMs = endTimestampMs; }

        public long calculateDuration() { return endTimestampMs - startTimestampMs; }

        @Override public String toString() {
            return "EventInfo(numEvents = " + numEvents +
                    ", timestamps = " + startTimestampMs + " to " + endTimestampMs + ")";
        }
    }

    /**
     * User-defined data type representing the update information returned by mapGroupsWithState.
     */
    public static class EventUpdate implements Serializable {
        private String id;
        private long durationMs;
        private int numEvents;
        private boolean expired;

        public EventUpdate() { }

        public EventUpdate(String id, long durationMs, int numEvents, boolean expired) {
            this.id = id;
            this.durationMs = durationMs;
            this.numEvents = numEvents;
            this.expired = expired;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public long getDurationMs() { return durationMs; }
        public void setDurationMs(long durationMs) { this.durationMs = durationMs; }

        public int getNumEvents() { return numEvents; }
        public void setNumEvents(int numEvents) { this.numEvents = numEvents; }

        public boolean isExpired() { return expired; }
        public void setExpired(boolean expired) { this.expired = expired; }

        @Override public String toString() {
            return "EventUpdate(id = " + id + ", numEvents = " + numEvents +
                    ", durationMs = " + durationMs + ", expired = " + expired + ")";
        }
    }
}
