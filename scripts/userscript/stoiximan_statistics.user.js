// ==UserScript==
// @name         stoiximan_statistics
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://s5.sir.sportradar.com/stoiximan/*
// ==/UserScript==

(function() {
    'use strict';
    var targetWindow = window.eval('window'),
        windowOpener = window.eval('parent.opener'),
        totalRadarRequests = 0,
        pendingRadarRequests = 0,
        debug = false,
        finished = false,
        postedKeys = [],
        menuTexts = ['Επισκόπηση', 'Σύγκριση', 'Πρόγραμμα', 'Βαθμολογίες'],
        menuItems = getMenuItems();

    if (!windowOpener) {
        console.log('NO TARGET OPENER!');
    }
    targetWindow.fetch = createFetchInterceptor(targetWindow.fetch);
    postToOpener({
        type:'spm_begin',
        sourceUrl: window.location.href
    });
    setTimeout(clickNextMenuItem, 2000);
    setTimeout(isFinished, 10000);

    function getMenuItems() {
        var menuItems = [];
        var elems = document.querySelectorAll('li.menuitem .btn-top-menu');
        for (var i = 0; i< elems.length; i++) {
            var elem = elems[i];
            if (!elem.classList.contains('active') && menuTexts.includes(elem.innerText)) {
                menuItems.push(elem);
            }
        }
        return menuItems;
    }
    function clickNextMenuItem() {
        if (menuItems.length) {
            var elem = menuItems.shift();
            elem.click();
            setTimeout(clickNextMenuItem, 2000);
        }
    }

    function createFetchInterceptor(nativeFetch) {
        return function() {
            onFetchRequest(arguments[0]);
            return nativeFetch.apply(this, arguments).then(function(response) {
                onFetchResponse(response.clone());
                return response;
            });
        }
    }

    function onFetchRequest(url) {
        if (debug) console.log('FETCH', url);
        if (isRadar(url)) {
            finished = false;
            ++totalRadarRequests;
            ++pendingRadarRequests;
        }
    }

    function onFetchResponse(response) {
        if (debug) console.log('FETCH RESPONSE', response);
        var responseUrl = response.url;
        if (isRadar(responseUrl)) {
            /*
            if (response.status == 200) {
                response.json().then(json => {
                    if (debug) console.log('FETCH JSON RESPONSE', responseUrl, json);
                    postToOpener({
                        type: 'spm_fetch',
                        sourceUrl: window.location.href,
                        radarUrl: responseUrl,
                        json: json
                    });
                });
            }
            */
            --pendingRadarRequests;
            isFinished(true);
        }
    }

    function postToOpener(msg) {
        if (windowOpener) {
            windowOpener.postMessage(msg, 'https://www.stoiximan.gr/');
        }
    }

    function isFinished(fromFetchHandler) {
        if (!finished && pendingRadarRequests == 0) {
            finished = true;
            totalRadarRequests = postFetchData();
            if (debug) console.log('FETCH FINISH', totalRadarRequests);
            postToOpener({
                type:'spm_end',
                sourceUrl: window.location.href,
                requests: totalRadarRequests
            });
        }
    }

    function postFetchData() {
        var reactElement = document.getElementById('sr-app');
        if (debug) console.log('reactElement', reactElement);
        var reactRootContainer = reactElement._reactRootContainer;
        if (debug) console.log('reactRootContainer', reactRootContainer._internalRoot.current);
        var base = reactRootContainer._internalRoot.current;
        while(base) {
            try {
                var state = base.pendingProps.store.getState();
                var fetchedData = state.fetchedData || {};
                var keys = Object.keys(fetchedData);
                if (keys.length) {
                    for (var i = 0; i < keys.length; i++) {
                        var key = keys[i];
                        if (!postedKeys.includes(key)) {
                            var json = fetchedData[key];
                            if (debug) console.log('fetchedData', key, json);
                            postToOpener({
                                type: 'spm_fetch',
                                sourceUrl: window.location.href,
                                radarUrl: key,
                                json: json
                            });
                        }
                    }
                    return keys.length;
                }
            } catch(e) {}
            base = base.child;
        }
    }

    function isRadar(url) {
        return url.indexOf('gismo') > 0 && url.indexOf('match_info') == -1;
    }
})();