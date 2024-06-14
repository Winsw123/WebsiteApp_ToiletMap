class Toilet {
    constructor() {
        this.name = "小東路地下道";
        this.type = "All Gender";
        this.isFree = true;
        this.Longitude = 120.21379292529143;
        this.Latitude = 23.00158213806466;
        this.Status = {
            isAvailable: true,
            isClean: true,
            isPaper: true,
            isSoap: true
        };
        this.set = function set(json) {
            return Object.assign(new Toilet(), json);
        }
    }
}

const map = L.map('map').setView([23, 120.21], 15);
const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a> <span aria-hidden="true">|</span> <a href="https://github.com/lynntsuiplus/pd2ToiletProject">GitHub Repo</a>'
}).addTo(map);

var markerLayer = L.featureGroup().addTo(map);
var toiletLayer = L.featureGroup().addTo(map);

var test = "{\
    \"name\": \"南一中校門口\",\
    \"type\": \"Disabled\",\
    \"isFree\": false,\
    \"Longitude\": 120.21557856948613,\
    \"Latitude\": 22.9942663239397,\
    \"Status\": {\
        \"isAvailable\" : false,\
        \"isClean\" : false,\
        \"isPaper\" : false,\
        \"isSoap\" : false\
    }\
}";

var toiletsAll = [];
getToiletLocation();

function getToiletLocation() {

    /*TBD: fetch data from database
    
    for (var i = 0; i < data.length; i++) {
        toilets.push(Toilet.set(data[i]));
    }*/

    toiletsAll.push(new Toilet()); // test
    toiletsAll.push(new Toilet().set(JSON.parse(test))); // test
    displayToilet(toiletsAll, null);
}

function getDistance() {
    var distance = [];
    /*TBD: calculate distance in back end
    
    for (var i = 0; i < data.length; i++) {
        distance.push(data[i]);
    }*/
    distance = [114, 514]; // test
    return distance;
}

function displayToilet(toilets, distance) {
    var f = 0;
    if (distance != null && Array.isArray(distance) && distance.length == toilets.length) {
        f = 1;
    }
    for (var i = 0; i < toilets.length; i++) {
        let longitude = toilets[i].Longitude;
        let latitude = toilets[i].Latitude;
        let temp = L.marker([latitude, longitude]).addTo(toiletLayer);
        temp.bindPopup(formatPopup(toilets[i], f ? distance[i] : -1));
    }
}

function formatPopup(toilet, toiletDist) {
    let name = toilet.name;
    let type = toilet.type;
    let free = toilet.isFree ? "" : "<b>需付費使用</b><br>";
    let available = toilet.Status.isAvailable ? "" : "<b>目前不可用</b><br>";
    let clean = toilet.Status.isClean ? "乾淨整潔" : "整潔欠佳";
    let paper = toilet.Status.isPaper ? "有衛生紙" : "沒有衛生紙";
    let soap = toilet.Status.isSoap ? "有肥皂" : "沒有肥皂";
    let dist = "";
    if (toiletDist >= 0) dist = "距離：" + toiletDist;
    var format = "<h2>" + name + "</h2>\
                    種類：" + type + "<br>\
                    " + free + available + dist + "<hr>\
                    " + clean + "<br>\
                    " + paper + "<br>\
                    " + soap;
    return format;
}

function findToilet() {
    if (!navigator.geolocation) {
        alert('瀏覽器不支援定位功能');
        return [1, null, null];
    }
    return navigator.geolocation.getCurrentPosition(locationSuccess, locationError);
}

function locationSuccess(position) {
    var longitude = position.coords.longitude, latitude = position.coords.latitude;
    markerLayer.getLayers().forEach((item) => {
        markerLayer.removeLayer(item);
    });
    var marker = L.marker([latitude, longitude]).addTo(markerLayer);
    marker._icon.classList.add("icon_red");
    map.flyTo([latitude, longitude], 15);
    marker.bindPopup("現在位置").openPopup();
    calcDistance();
}

function calcDistance() {
    var distance = getDistance();
    displayToilet(toiletsAll, distance);
}

function locationError(err) {
    switch (err.code) {
        case err.PERMISSION_DENIED:
            alert("請開啟瀏覽器的定位功能");
            break;
        case err.POSITION_UNAVAILABLE:
            alert("無法取得定位資料");
            break;
        case err.TIMEOUT:
            alert("無法取得定位資料");
            break;
        case err.UNKNOWN_ERROR:
            alert("未知錯誤，請回報開發者");
            break;
    }
    return [2, null, null];
}

function addToilet() {
    //document.getElementById("overlay").style.visibility = visible;
    alert("功能開發中");
}
function closeDialog() {
    document.getElementById("overlay").style.visibility = hidden;
}
