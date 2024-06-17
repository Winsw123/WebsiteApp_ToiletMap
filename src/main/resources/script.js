class Toilet {
    constructor() {
        this.name = "小東路地下道";
        this.type = "All Gender";
        this.isFree = true;
        this.longitude = 120.21379292529143;
        this.latitude = 23.00158213806466;
        this.isAvailable = true;
        this.isClean = true;
        this.isPaper = true;
        this.isSoap = true;
    }
    //后端GET的json的变数大小写和前端不一，需进行转换才存取，否则无法覆盖建构子预设值
    set(json) {
        this.name = json.name || this.name;
        this.type = json.type || this.type;
        this.isFree = json.free !== undefined ? json.free : this.isFree;
        this.longitude = json.longitude || this.longitude;
        this.latitude = json.latitude || this.latitude;
        this.isAvailable = json.available !== undefined ? json.available : this.isAvailable;
        this.isClean = json.clean !== undefined ? json.clean : this.isClean;
        this.isPaper = json.paper !== undefined ? json.paper : this.isPaper;
        this.isSoap = json.soap !== undefined ? json.soap : this.isSoap;
        return this;
    }
}


const map = L.map('map').setView([23, 120.21], 15);
const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a> <span aria-hidden="true">|</span> <a href="https://github.com/lynntsuiplus/pd2ToiletProject">GitHub Repo</a>'
}).addTo(map);

var markerLayer = L.featureGroup().addTo(map);
var toiletLayer = L.featureGroup().addTo(map);
var formLayer = L.featureGroup().addTo(map);

var current;

var test = "{\
    \"name\": \"南一中校門口\",\
    \"type\": \"Disabled\",\
    \"isFree\": false,\
    \"longitude\": 120.21557856948613,\
    \"latitude\": 22.9942663239397,\
    \"isAvailable\" : false,\
    \"isClean\" : false,\
    \"isPaper\" : false,\
    \"isSoap\" : false\
}";
var test2 = "{\
    \"values\": [114, 514]\
}";
var test3 = [];

var toiletsAll = [];
//一开始先获取后台所有已存取的厕所并显示在页面
getToiletLocation();

//获取所有厕所资讯并显示在页面
async function getToiletLocation() {
    //let toiletsAll = [];
    let url = "http://localhost:8080/api/getToiletLocation"; // 后端的URL
    fetch(url).then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('無法連絡伺服器，請回報開發者');
        }).then((json) => {
            var jsonArray = json;
            console.log('Raw data from server:', json); 
            for (var i = 0; i < jsonArray.length; i++) {
                let toilet = new Toilet();
                toilet.set(jsonArray[i]);
                toiletsAll.push(toilet);
                //displayToilet(toiletsAll, null);
            }
            console.log('Toilets after processing:', toiletsAll); 
            displayToilet(toiletsAll, null);
        }).catch((error) => {
            alert(error);
        });
/*
    try {
        let response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Unable to contact server, please report to the developer');
        }

        let json = await response.json();

        if (json.length === 0) {
            throw new Error('No toilet locations found');
        }

        console.log('Raw data from server:', json); // 输出从服务器获取的原始数据

        for (let i = 0; i < json.length; i++) {
            let toilet = new Toilet();
            toilet.set(json[i]);
            toiletsAll.push(toilet);
        }

        console.log('Toilets after processing:', toiletsAll); // 输出处理后的卫生间数据数组

        // 显示卫生间位置数据到界面
        displayToilet(toiletsAll, null);

    } catch (error) {
        alert(error.message);
    }
        */
    //toiletsAll.push(new Toilet()); // test
    //toiletsAll.push(new Toilet().set(JSON.parse(test))); // test
    //displayToilet(toiletsAll, null);
}

//计算User和厕所之间的距离
function getDistance() {
    let distance = [];
    for (var i = 0; i < toiletsAll.length; i++) { //test
        distance.push(Math.round(map.distance(L.latLng(toiletsAll[i].latitude, toiletsAll[i].longitude),current.getLatLng())));
    }
    return distance;
}
function displayToilet(toilets, distance) {
    var f = 0;
    if (distance != null && Array.isArray(distance) && distance.length == toilets.length) {
        f = 1;
    }
    for (var i = 0; i < toilets.length; i++) {
        let longitude = toilets[i].longitude;
        let latitude = toilets[i].latitude;
        let temp = L.marker([latitude, longitude]).addTo(toiletLayer);
        temp.bindPopup(formatPopup(toilets[i], f ? distance[i] : -1));
    }
}

//前端展示厕所资讯的格式
function formatPopup(toilet, toiletDist) {
    let name = toilet.name;
    let type;
    switch (toilet.type) {
        case "All Gender": type = "不分性別"; break;
        case "Male": type = "男廁"; break;
        case "Female": type = "女廁"; break;
        case "Disabled": type = "無障礙廁所"; break;
        default: type = toilet.type;
    }
    let free = toilet.isFree ? "" : "<b>需付費使用</b><br>";
    let available = toilet.isAvailable ? "" : "<b>目前不可用</b><br>";
    let clean = toilet.isClean ? "乾淨整潔" : "整潔欠佳";
    let paper = toilet.isPaper ? "有衛生紙" : "沒有衛生紙";
    let soap = toilet.isSoap ? "有肥皂" : "沒有肥皂";
    let dist = "";
    if (toiletDist >= 0) dist = "距離：" + toiletDist + "m";
    var format = "\
        <h2>" + name + "</h2>\
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

//定位User并计算和所有厕所之间的距离
function locationSuccess(position) {
    var longitude = position.coords.longitude, latitude = position.coords.latitude;
    markerLayer.getLayers().forEach((item) => {
        markerLayer.removeLayer(item);
    });
    current = L.marker([latitude, longitude]).addTo(markerLayer);
    current._icon.classList.add("icon_red");
    map.flyTo([latitude, longitude], 15);
    current.bindPopup("現在位置").openPopup();
    calcDistance();
}

function calcDistance() {
    var distance = getDistance();
    displayToilet(toiletsAll, distance);
}

//定位请求页面
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

//前端新增厕所页面
function addToilet() {
    document.getElementById("overlay").style.visibility = "visible";
    document.getElementById("menu").style.visibility = "visible";
}

//前端显示页面
function form() {
    formLayer.getLayers().forEach((item) => {
        formLayer.removeLayer(item);
    });
    var marker = L.marker(map.getCenter()).addTo(formLayer);
    marker.bindPopup(makeForm()).openPopup();
    closeDialog();
}

//新增厕所的输入页面格式
function makeForm() {
    var format = "\
        <div class='form'>\
            <label for='name'>廁所名稱：</label>\
            <input type='text' id='form_name' name='name'><br>\
            <label for='type'>廁所種類：</label>\
            <select id='form_type' name='type'>\
                <option value='All Gender'>不分性別</option>\
                <option value='Male'>男廁</option>\
                <option value='Female'>女廁</option>\
                <option value='Disabled'>無障礙</option>\
            </select><br>\
            <input type='checkbox' id='form_isFree' name='isFree' value='isFree' checked>\
            <label for='isFree'>免費使用</label>\
            <input type='checkbox' id='form_isAvailable' name='isAvailable' value='isAvailable' checked>\
            <label for='isAvailable'>目前可用</label>\
            <input type='checkbox' id='form_isClean' name='isClean' value='isClean' checked>\
            <label for='isClean'>乾淨整潔</label><br>\
            <input type='checkbox' id='form_isPaper' name='isPaper' value='isPaper'>\
            <label for='isPaper'>有衛生紙</label>\
            <input type='checkbox' id='form_isSoap' name='isSoap' value='isSoap'>\
            <label for='isSoap'>有肥皂</label><br>\
            <input type='text' id='form_latitude' name='name' value="+ map.getCenter().lat + " hidden>\
            <input type='text' id='form_longitude' name='name' value="+ map.getCenter().lng + " hidden>\
            <input type='button' value='送出' onclick='submit()'>\
        </div>";
    return format;
}

//上传，POST
function submit() {
    var form_name = document.getElementById("form_name").value;
    var form_type = document.getElementById("form_type").value;
    var form_isFree = document.getElementById("form_isFree").checked;
    var form_isAvailable = document.getElementById("form_isAvailable").checked;
    var form_isClean = document.getElementById("form_isClean").checked;
    var form_isPaper = document.getElementById("form_isPaper").checked;
    var form_isSoap = document.getElementById("form_isSoap").checked;
    var form_longitude = document.getElementById("form_longitude").value;
    var form_latitude = document.getElementById("form_latitude").value;

    if (form_name === "") {
        alert("請輸入廁所名稱");
        return;
    }

    let url = "http://localhost:8080/api/addToiletLocation"; // 填写后端接口的URL
    fetch(url, {
        method: "POST",
        body: JSON.stringify({
            name: form_name,
            type: form_type,
            isFree: form_isFree,
            longitude: form_longitude,
            latitude: form_latitude,
            isAvailable: form_isAvailable,
            isClean: form_isClean,
            isPaper: form_isPaper,
            isSoap: form_isSoap
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then((response) => {
        if (response.ok) {
            alert("儲存成功");
        } else {
            throw new Error('無法連絡伺服器，請回報開發者');
        }
    }).catch((error) => {
        alert(error);
    });
    /*test3.push(new Toilet().set({ 
        name: v("form_name"),
        type: v("form_type"),
        isFree: c("form_isFree"),
        longitude: v("form_longitude"),
        latitude: v("form_latitude"),
        isAvailable: c("form_isAvailable"),
        isClean: c("form_isClean"),
        isPaper: c("form_isPaper"),
        isSoap: c("form_isSoap")
    }));*/
    refresh(); 
}

//格式转换
function v(id){
    return document.getElementById(id).value;
}

//格式转换
function c(id){
    return document.getElementById(id).checked;
}

//关闭图示
function closeDialog() {
    document.getElementById("overlay").style.visibility = "hidden";
    document.getElementById("menu").style.visibility = "hidden";
}

//刷新页面
function refresh(){
    alert('資料上傳成功');
    formLayer.getLayers().forEach((item) => {
        formLayer.removeLayer(item);
    });
    getToiletLocation();
    //displayToilet(test3, null); 
}