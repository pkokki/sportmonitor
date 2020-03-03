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
    #sparkContainer {
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
    #myContainer p {
        color:                  red;
        background:             white;
    }`);

    var zNode = document.createElement('div');
    zNode.innerHTML = '<button id="sparkBtn1" type="button">Snapshot</button>';
    zNode.setAttribute('id', 'sparkContainer');
    document.body.appendChild(zNode);
    document.getElementById("sparkBtn1").addEventListener ("click", process, false);

    function process() {
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

            var event = {
                id: parseInt(href.substring(href.lastIndexOf('-')+1)),
                betRadarId: statsHref ? parseInt(statsHref.substring(statsHref.lastIndexOf('/')+1)) : null,
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
                    type: 'GNG',
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

        if (events.length) {
            var requestData = {
                stamp: moment().unix(),
                events: events
            };
            send('http://localhost:8080/coupon', requestData);
        }
    }

    function parseEventDate(strDate) {
        // 13/03 10:00
        var date = moment(strDate,"DD/MM hh:mm").unix();
        return date;
    }

    function send(url, requestData) {
        console.log(url, requestData);
        GM_xmlhttpRequest({
                method: 'POST',
                url: url,
                headers: { 'Content-Type': 'text/plain' },
                data: JSON.stringify(requestData),
                onload: function(response) {
                    //console.log(url, requestData);
                },
                onerror: function(response) {
                    console.error(url, response);
                }
            });
    }
})();