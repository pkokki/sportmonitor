// ==UserScript==
// @name         stoiximan_upcoming24H
// @namespace    http://panos.net/
// @version      0.1
// @require      https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js
// @description  try to take over the world!
// @author       You
// @match        https://www.stoiximan.gr/Upcoming24H/Soccer-FOOT/
// @grant        GM_xmlhttpRequest
// @grant        GM_addStyle
// @connect      localhost
// ==/UserScript==

(function() {
    'use strict';

    GM_addStyle (`
    .spark_container {
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
    .spark_button {
        cursor:                 pointer;
        margin:                 5px;
        padding:                5px;
    }`);
    function addButton(container, text, handler) {
        var button = document.createElement('button');
        //button.setAttribute('id', id);
        button.classList.add('spark_button');
        button.innerText = text;
        container.appendChild(button);
        button.addEventListener ("click", handler, false);
        //document.getElementById(id).addEventListener ("click", handler, false);
    }

    var debug = true,
        pauseProcess = false,
        radarCache = [];

    var container = document.createElement('div');
    container.classList.add('spark_container');
    document.body.appendChild(container);
    addButton(container, 'Snapshot', snapshot);
    addButton(container, 'Process', process);
    addButton(container, 'Pause', toggleProcess);
    addButton(container, 'Cache', showRadarCache);

    window.addEventListener("message", receiveMessage, false);

    function toggleProcess(ev) {
        pauseProcess = !pauseProcess;
        ev.target.innerText = pauseProcess ? 'Play' : 'Pause';
    }

    function showRadarCache() {
        console.log(radarCache.length, radarCache);
    }

    function receiveMessage(event) {
        if (event.origin !== "https://s5.sir.sportradar.com") return;
        var data = event.data;
        //log('RECEIVE MESSAGE', data.type, data);
        switch (data.type) {
            case 'spm_begin':
                processBeginMessage(data.sourceUrl);
                break;
            case 'spm_end':
                processEndMessage(data.sourceUrl, data.requests);
                break;
            case 'spm_fetch':
                processFetchMessage(data.sourceUrl, data.radarUrl, data.json);
                break;
        }
    }

    function processFetchMessage(sourceUrl, radarUrl, radarJson) {
        //log('processFetchMessage', sourceUrl, radarUrl, radarJson);
        if (!radarCache.includes(radarUrl)) {
            radarCache.push(radarUrl);
            var json = Object.assign({
                "radarUrl": radarUrl
            }, radarJson);
            send('http://localhost:8080/sportradar', json);
        }
    }

    function processBeginMessage(sourceUrl) {
        //log('processBeginMessage', sourceUrl);
    }

    function processEndMessage(sourceUrl, requests) {
        //log('processEndMessage', sourceUrl, requests, radarCache.length);
    }

    function log() {
        if (debug) console.log(... [... arguments]);
    }

    function snapshot() {
        processEvents(false);
    }
    function process() {
        processEvents(true);
    }
    function processEvents(sendToHost) {
        var events = prepareCouponEvents();
        console.log('process', events);
        if (sendToHost && events.length) {
            var currentStamp = moment().unix();
            send('http://localhost:8080/coupon', {
                stamp: currentStamp,
                events: events
            });
            clickStatistics(events);
        }
    }

    function clickStatistics(initialEvents) {
        function appendStatistics() {
            if (eventsToClick.length) {
                if (pauseProcess) {
                    setTimeout(appendStatistics, 15000);
                }
                else {
                    var event = eventsToClick.shift();
                    log('appendStatistics: ', event.title, ', remaining events=', eventsToClick.length, ', captured messages=', radarCache.length);
                    var eventTime = new Date(event.eventTime*1000).getHours();
                    if (event.stats && event.live && eventTime >= 15) {
                        var statsAnchor = window.document.body.querySelector('a[href="' + event.stats + '"]');
                        if (statsAnchor) {
                            statsAnchor.target = 'statsWin';
                            statsAnchor.click();
                            setTimeout(appendStatistics, 15000);
                            return;
                        } else {
                            console.warn('appendStatistics: Anchor', event.stats, 'not found!');
                            appendStatistics();
                        }
                    } else {
                        appendStatistics();
                    }
                }
            }
        }
        var eventsToClick = initialEvents.slice();
        appendStatistics();
    }

    function prepareCouponEvents() {
        var tableRows = window.document.body.querySelector('div#js-content .tab-content table tbody').children;
        var events = [];
        for (var i = 0; i < tableRows.length; i++) {
            var infoCell = tableRows[i].querySelector('td.event-info');
            var prim = tableRows[i].querySelectorAll('td.primary-market');
            var r1 = prim[0].querySelector('a');
            var rX = prim[1].querySelector('a');
            var r2 = prim[2].querySelector('a');
            var sels = tableRows[i].querySelectorAll('td.secondary-market div.market-selection');
            var o = null, u = null, gg = null, ng = null;
            for (var j = 0; j < sels.length; j++) {
                var ms = sels[j];
                if (ms.querySelector('label').innerText == 'O ') o = ms.querySelector('a');
                if (ms.querySelector('label').innerText == 'U ') u = ms.querySelector('a');
                if (ms.querySelector('label').innerText == 'GG ') gg = ms.querySelector('a');
                if (ms.querySelector('label').innerText == 'NG ') ng = ms.querySelector('a');
            }

            var href = infoCell.querySelector('a.event-title').getAttribute('href');
            var statsHref = infoCell.querySelector('.event-facts a.stats') ? infoCell.querySelector('.event-facts a.stats').getAttribute('href') : null;
            var info = infoCell.querySelector('a.event-title span').getAttribute('title').split(' - ');
            var country = info[0];
            var league = info[1];
            var betRadarId = statsHref ? parseInt(statsHref.substring(statsHref.lastIndexOf('/')+1)) : null;

            if (betRadarId && r1 && r1.getAttribute('data-selnid') && rX && rX.getAttribute('data-selnid') && r2 && r2.getAttribute('data-selnid')) {
                var event = {
                    id: parseInt(href.substring(href.lastIndexOf('-')+1)),
                    betRadarId: betRadarId,
                    title: infoCell.querySelector('a.event-title').innerText.trim(),
                    href: href,
                    country: country,
                    league: league,
                    eventTime: parseEventDate(infoCell.querySelector('.event-facts span.date').innerText.trim()),
                    stats: statsHref,
                    live: !!(infoCell.querySelector('.event-facts span.live')),
                    markets: [{
                        id: parseInt(r1.getAttribute('data-marketid')),
                        type: 'MRES',
                        selections: [{
                            id: parseInt(r1.getAttribute('data-selnid')),
                            type: '1',
                            price: parseFloat(r1.innerText)
                        }, {
                            id: parseInt(rX.getAttribute('data-selnid')),
                            type: 'X',
                            price: parseFloat(rX.innerText)
                        }, {
                            id: parseInt(r2.getAttribute('data-selnid')),
                            type: '2',
                            price: parseFloat(r2.innerText)
                        }
                                    ]
                    }
                             ]};
                if (o && u) {
                    event.markets.push({
                        id: parseInt(o.getAttribute('data-marketid')),
                        type: 'HCTG',
                        handicap: 2.5,
                        selections: [{
                            id: parseInt(o.getAttribute('data-selnid')),
                            type: 'O',
                            price: parseFloat(o.innerText)
                        }, {
                            id: parseInt(u.getAttribute('data-selnid')),
                            type: 'U',
                            price: parseFloat(u.innerText)
                        }
                                    ]
                    });
                }
                if (gg && ng) {
                    event.markets.push({
                        id: parseInt(gg.getAttribute('data-marketid')),
                        type: 'BTSC',
                        selections: [{
                            id: parseInt(gg.getAttribute('data-selnid')),
                            type: 'Y',
                            price: parseFloat(gg.innerText)
                        }, {
                            id: parseInt(ng.getAttribute('data-selnid')),
                            type: 'N',
                            price: parseFloat(ng.innerText)
                        }
                                    ]
                    });
                }
                events.push(event);
            }
        }
        return events;
    }

    function parseEventDate(strDate) {
        // 13/03 10:00
        var date = moment(strDate,"DD/MM hh:mm").unix();
        return date;
    }

    function send(url, requestData) {
        log('send', url, requestData);
        GM_xmlhttpRequest({
                method: 'POST',
                url: url,
                headers: { 'Content-Type': 'text/plain' },
                data: JSON.stringify(requestData),
                onload: function(response) {
                },
                onerror: function(response) {
                    console.error(url, response);
                }
            });
    }
})();