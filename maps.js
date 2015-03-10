var northMap, southMap;
var northMapRects = [];
var southMapRects = [];
var storedMaps = new Object();


function initialize() {

  var mapOptionsNorth = {
          center: { lat: 44.395, lng: -89.765},
          zoom: 13,
          backgroundColor: "pink",
          mapTypeId: google.maps.MapTypeId.SATELLITE
          };
  var mapOptionsSouth = {
          center: { lat: 44.360, lng: -89.77},
          zoom: 13,
          backgroundColor: "pink",
          mapTypeId: google.maps.MapTypeId.SATELLITE
          };

  northMap = new google.maps.Map(document.getElementById('northMap'),
      mapOptionsNorth);
  southMap = new google.maps.Map(document.getElementById('southMap'),
      mapOptionsSouth);
  script = document.createElement('script');
  script.src = "data/first.js";
  document.getElementsByTagName('head')[0].appendChild(script);
}

//Credit for this function goes to: http://www.javascripter.net/faq/rgbtohex.htm
function rgbToHex(R,G,B) {return toHex(R)+toHex(G)+toHex(B)}
function toHex(n) {
 n = parseInt(n,10);
 if (isNaN(n)) return "00";
 n = Math.max(0,Math.min(n,255));
 return "0123456789ABCDEF".charAt((n-n%16)/16)
      + "0123456789ABCDEF".charAt(n%16);
}
function concentrationToColor (concentration) {
  var red = (concentration)/5 * 255
  var green = 255 - red
  return rgbToHex(red, green, 0)
}

function eqfeed_callback(results) {
    var heatmapRects;
    var map;

    if (results.features.length > 5){
      run = results.features[5].properties.run;
      day = results.features[5].properties.day;
      key = run.toString()+day.toString();
      if (run == 0){
        map = northMap;
        heatmapRects = northMapRects;

      }
      else {
        map = southMap;
        heatmapRects = southMapRects;    
       }

      //delete old markers
      for (var i = 0; i<heatmapRects.length; i++){
          heatmapRects[i].setMap(null);
        };
        
      //add new markers
      try {
        squares = storedMaps[key];
        for (var i = 0; i < squares.length; i++) { 
          squares[i].setMap(map);
        }
      }

      catch(err) {
        for (var i = 0; i < results.features.length; i++) {
          var coords = results.features[i].geometry.coordinates;
          var bottom_right = new google.maps.LatLng(coords[0][0], coords[0][1]);
          var top_left = new google.maps.LatLng(coords[1][0], coords[1][1]);
          var concentration = results.features[i].properties.conc;
          var bounds = new google.maps.LatLngBounds(bottom_right, top_left);

          //color scale
          var color = '#'+concentrationToColor(concentration)
          var rectangle = new google.maps.Rectangle({
            bounds: bounds,
            strokeColor: color,
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: color,
            editable: false,
            fillOpacity: 0.5
          });
          heatmapRects[i] = rectangle;
          heatmapRects[i].setMap(map);  

        }
        storedMaps[key] = heatmapRects;
      }
    }
    else {
      nmap = results.features[0].features;
      smap = results.features[1].features;
     
      for (var j = 0; j < nmap.length; j++) {
        var coords = nmap[j].geometry.coordinates;
        var bottom_right = new google.maps.LatLng(coords[0][0], coords[0][1]);
        var top_left = new google.maps.LatLng(coords[1][0], coords[1][1]);
        var concentration = nmap[j].properties.conc;
        var bounds = new google.maps.LatLngBounds(bottom_right, top_left);

        //color scale
        var color = '#'+concentrationToColor(concentration);
        var rectangle = new google.maps.Rectangle({
          bounds: bounds,
          strokeColor: color,
          strokeOpacity: 0.8,
          strokeWeight: 2,
          fillColor: color,
          editable: false,
          fillOpacity: 0.5
        });
        northMapRects[j] = rectangle;
        northMapRects[j].setMap(northMap);  

      }
      for (var i = 0; i < smap.length; i++) {
        var coords = smap[i].geometry.coordinates;
        var bottom_right = new google.maps.LatLng(coords[0][0], coords[0][1]);
        var top_left = new google.maps.LatLng(coords[1][0], coords[1][1]);
        var concentration = smap[i].properties.conc;
        var bounds = new google.maps.LatLngBounds(bottom_right, top_left);

        //color scale
        var color = '#'+concentrationToColor(concentration);
        var rectangle = new google.maps.Rectangle({
          bounds: bounds,
          strokeColor: color,
          strokeOpacity: 0.8,
          strokeWeight: 2,
          fillColor: color,
          editable: false,
          fillOpacity: 0.5
        });
        southMapRects[i] = rectangle;
        southMapRects[i].setMap(southMap);  
      }
    }
  }

google.maps.event.addDomListener(window, 'load', initialize);
