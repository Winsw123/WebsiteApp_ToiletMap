class Toilet {

    constructor() {
        this.Name = "小東路地下道";
        this.Type = "All Gender";
        this.Free = true;
        //this.Fare = 0;
        this.Status = "Available";
        this.Location = { Longitude: 120.21379292529143, Latitude: 23.00158213806466 };
        //this.Description = "尿在地下道內";
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
    \"Name\": \"南一中校門口\",\
    \"Type\": \"Disabled\",\
    \"Free\": true,\
    \"Status\": \"Maintenance\",\
    \"Location\": {\
        \"Longitude\": 120.21557856948613,\
        \"Latitude\": 22.9942663239397\
    }\
}"

getToilet();

function getToilet() {
    var toilets = [];

    /*for (var i = 0; i < db.length; i++) {
        toilets.push(Toilet.set(db[i]));
    }*/

    toilets.push(new Toilet());
    toilets.push(new Toilet().set(JSON.parse(test)));
    displayToilet(toilets);
}

function displayToilet(toilets) {
    for (var i = 0; i < toilets.length; i++) {
        let name = toilets[i].Name;
        let longitude = toilets[i].Location.Longitude;
        let latitude = toilets[i].Location.Latitude;
        let temp = L.marker([latitude, longitude]).addTo(toiletLayer);
        temp.bindPopup(name + "<br>to be formatted").openPopup();
    }
}

function findToilet() {
    if (!navigator.geolocation) {
        alert('瀏覽器不支援定位功能');
        return [1, null, null];
    }
    return navigator.geolocation.getCurrentPosition(locationSuccess, locationError);
}
function addToilet() {
    document.getElementById("overlay").style.visibility = visible;
}
function closeDialog() {
    document.getElementById("overlay").style.visibility = hidden;
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