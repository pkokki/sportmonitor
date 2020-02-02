// ==UserScript==
// @name         stlive00
// @namespace    http://panos.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @include      https://www.stoiximan.gr/live/*
// @grant        GM_xmlhttpRequest
// @grant        GM_addStyle
// @connect      localhost
// ==/UserScript==

(function() {
    'use strict';
    GM_addStyle (`
    #myContainer {
        position:               absolute;
        top:                    0;
        left:                   0;
        font-size:              16px;
        background:             orange;
        border:                 3px outset black;
        margin:                 3px;
        opacity:                0.9;
        z-index:                1100;
        padding:                5px 20px;
    }
    #sparkBtn1 {
        cursor:                 pointer;
    }
    #sparkBtn2 {
        cursor:                 pointer;
    }
    #myContainer p {
        color:                  red;
        background:             white;
    }`);

    var lastRadarEvents = {};
    var activeRadarEvents = [];
    var sparkTimer = null, activeRadarTimer = null;
    var captureXHR = false;
    var zNode = document.createElement ('div');
    zNode.innerHTML = '<button id="sparkBtn1" type="button">Snapshot</button>&nbsp;<button id="sparkBtn2" type="button">Start</button>&nbsp;<button id="sparkBtn3" type="button">Capture XHR</button>';
    zNode.setAttribute ('id', 'myContainer');
    document.body.appendChild (zNode);

    //--- Activate the newly added button.
    document.getElementById ("sparkBtn1").addEventListener (
        "click", processMatches_v2, false
    );
    document.getElementById ("sparkBtn2").addEventListener (
        "click", toggleSparkProcess, false
    );
    document.getElementById ("sparkBtn3").addEventListener (
        "click", toggleCaptureXHR, false
    );

    function toggleCaptureXHR() {
        captureXHR = !captureXHR;
        document.getElementById ("sparkBtn3").innerText = captureXHR ? "Stop XHR" : "Capture XHR";
        if (captureXHR) {
            processMatches_v2(true);
            activeRadarTimer = setInterval(processActiveRadarEvents, 4000);
        }
        else {
            clearInterval(activeRadarTimer);
        }
    }

    function toggleSparkProcess() {
        if (sparkTimer) {
            document.getElementById ("sparkBtn2").innerText = "Start";
            clearInterval(sparkTimer);
            sparkTimer = null;
            if (captureXHR) toggleCaptureXHR();
        }
        else {
            sparkTimer = setInterval(processMatches_v2, 5000);
            document.getElementById ("sparkBtn2").innerText = "Stop";
            if (!captureXHR) toggleCaptureXHR();
        }
    }

    function processActiveRadarEvents() {
        //console.log('processActiveRadarEvents', captureXHR, activeRadarEvents, lastRadarEvents);
        var sb = window.eval('sportsbook');
        if (sb.mainlive && sb.mainlive.event) {
            var events = [prepareEvent(sb.mainlive.event)];
            sendLiveRequest(events);
        }

        if (captureXHR) {
            var now = new Date().getTime();
            var limit = now - 60000;
            var link = activeRadarEvents.find(v => lastRadarEvents[v] === undefined || (lastRadarEvents[v] > 0 && lastRadarEvents[v] < limit));
            if (link) {
                lastRadarEvents[link] = now;
                selectMatch(link);
            }
        }
    }

   function selectMatch(targetLink) {
        var sportContainers = document.querySelectorAll("div.sport-container");
        if (sportContainers.length) {
            sportContainers.forEach(sportContainer => {
                var sportTitle = sportContainer.querySelector("div.sport-title").innerText.trim();
                if (sportTitle == 'ΠΟΔΟΣΦΑΙΡΟ') {
                    var leagues = sportContainer.querySelectorAll("div.sports-body");
                    if (leagues.length) {
                        leagues.forEach(league => {
                            var matches = league.querySelectorAll("div.event-FOOT");
                            if (matches.length) {
                                matches.forEach(match => {
                                    var a = match.querySelector("a.title");
                                    var href = a.getAttribute("href");
                                    if (targetLink == href) {
                                        console.log('selectMatch', targetLink);
                                        a.click();
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    }

    function processMatches_v2(noSend) {
        console.log('processMatches');
        var sb = window.eval('sportsbook');
        if (sb) {
            var events = [];
            var radarEvents = [];
            var sports = sb.liveoverview ? sb.liveoverview.sports() : sb.liveevents.sports();
            sports.forEach(sport => {
                if (sport.id == 'FOOT') {
                    var regions = sport.regions();
                    regions.forEach(region => {
                        var events = region.events();
                        events.forEach(src => {
                            var ev = prepareEvent(src);
                            events.push(ev);
                            if (ev.betRadarId && ev.liveEventLink) {
                                radarEvents.push(ev.liveEventLink);
                            }
                        });
                    });
                }
            });
            activeRadarEvents.splice(0, activeRadarEvents.length, ...radarEvents);
            if (!!noSend) {
                sendLiveRequest(events);
            }
        } else {
             console.log('processMatches: No sport book found.');
        }
    }

    function sendLiveRequest(events) {
        var request = {
            events: events
        };
        console.log('Sending data', events);
        GM_xmlhttpRequest({
            method: 'POST',
            url: 'http://localhost:8080/live',
            headers: { 'Content-Type': 'text/plain' },
            data: JSON.stringify(request),
            onload: function(response) {
                console.log('sendLiveRequest', response.responseText, events);
            },
            onerror: function(response) {
                console.error(response);
            }
        });
    }

    function prepareEvent(src) {
        var ev = {
            timestamp: new Date().valueOf(),
            id: src.id,
            regionId: src.regionId,
            regionName: src.regionName,
            leagueId: src.leagueId,
            leagueName: src.leagueName,
            betRadarId: src.betRadarId,
            betRadarLink: src.betRadarLink(),
            shortTitle: src.shortTitle,
            title: src.title,
            startTime: src.startTime(),
            startTimeTicks: src.startTimeTicks(),
            clockTime: src.clockTime(),
            isSuspended: src.isSuspended(),
            liveEventLink: src.liveEventLink(),
            homeTeam: src.teams ? src.teams.home : null,
            homeScore: src.score.home(),
            homeRedCards: src.footballStats.redcards.home(),
            awayTeam: src.teams ? src.teams.away : null,
            awayScore: src.score.away(),
            awayRedCards: src.footballStats.redcards.away(),
            markets: []
        };

        src.markets().forEach(srcMarket => {
            var market = {
                id: srcMarket.id,
                type: srcMarket.type,
                handicap: srcMarket.handicap(),
                description: srcMarket.description(),
                isSuspended: srcMarket.isSuspended(),
                selections: []
            };
            srcMarket.selections().forEach(srcSel => {
                var selection = {
                    id: srcSel.id,
                    description: srcSel.description(),
                    price: srcSel.price()
                };
                market.selections.push(selection);
            });
            ev.markets.push(market);
        });
        return ev;
    }

    function sendSportRadar(response) {
        if (captureXHR && response.readyState == 4 && response.status == 200 && response.responseURL.indexOf('sportradar.com') > 0) {
            GM_xmlhttpRequest({
                method: 'POST',
                url: 'http://localhost:8080/sportradar',
                headers: { 'Content-Type': 'text/plain' },
                data: response.responseText,
                onload: function(response) {
                    console.log('XHR', response.responseURL, response.responseText);
                },
                onerror: function(response) {
                    console.error(response);
                }
            });
        }
    }

    // https://gist.github.com/indiejoseph/3047593
    function Interceptor(nativeOpenWrapper, nativeSendWrapper) {
        XMLHttpRequest.prototype.open = function () {
            // Code here to intercept XHR
            return nativeOpenWrapper.apply(this, arguments);
        }
        XMLHttpRequest.prototype.send = function () {
            this.onloadend = function() {
                sendSportRadar(this);
            }
            return nativeSendWrapper.apply(this, arguments);
        }
    }

    Interceptor(window.eval('XMLHttpRequest.prototype.open'), window.eval('XMLHttpRequest.prototype.send'));

})();