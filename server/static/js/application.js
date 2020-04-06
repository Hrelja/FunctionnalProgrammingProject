$(document).ready(function(){
    //connect to the socket server.
    var socket = io.connect('http://' + document.domain + ':' + location.port + '/test');
    var alerts_received = [];
    var markers = []
       // Styles a map in night mode.
       var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 40.674, lng: -73.945},
        zoom: 9,
        styles: [
          {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
          {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
          {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
          {
            featureType: 'administrative.locality',
            elementType: 'labels.text.fill',
            stylers: [{color: '#d59563'}]
          },
          {
            featureType: 'poi',
            elementType: 'labels.text.fill',
            stylers: [{color: '#d59563'}]
          },
          {
            featureType: 'poi.park',
            elementType: 'geometry',
            stylers: [{color: '#263c3f'}]
          },
          {
            featureType: 'poi.park',
            elementType: 'labels.text.fill',
            stylers: [{color: '#6b9a76'}]
          },
          {
            featureType: 'road',
            elementType: 'geometry',
            stylers: [{color: '#38414e'}]
          },
          {
            featureType: 'road',
            elementType: 'geometry.stroke',
            stylers: [{color: '#212a37'}]
          },
          {
            featureType: 'road',
            elementType: 'labels.text.fill',
            stylers: [{color: '#9ca5b3'}]
          },
          {
            featureType: 'road.highway',
            elementType: 'geometry',
            stylers: [{color: '#746855'}]
          },
          {
            featureType: 'road.highway',
            elementType: 'geometry.stroke',
            stylers: [{color: '#1f2835'}]
          },
          {
            featureType: 'road.highway',
            elementType: 'labels.text.fill',
            stylers: [{color: '#f3d19c'}]
          },
          {
            featureType: 'transit',
            elementType: 'geometry',
            stylers: [{color: '#2f3948'}]
          },
          {
            featureType: 'transit.station',
            elementType: 'labels.text.fill',
            stylers: [{color: '#d59563'}]
          },
          {
            featureType: 'water',
            elementType: 'geometry',
            stylers: [{color: '#17263c'}]
          },
          {
            featureType: 'water',
            elementType: 'labels.text.fill',
            stylers: [{color: '#515c6d'}]
          },
          {
            featureType: 'water',
            elementType: 'labels.text.stroke',
            stylers: [{color: '#17263c'}]
          }
        ]
      });
    
    //receive details from server
    socket.on('alert', function(msg) {
        alerts_received.push(msg.alerts);
        alert_string = '';
        for (var i = 0; i < alerts_received.length; i++){
        var contentString = '<div id="content">'+
            '<div id="siteNotice">'+
            '</div>'+
            '<h1 id="firstHeading" class="firstHeading">'+ 'Drone number : ' + alerts_received[i]['DroneID'] +'</h1>'+
            '<div id="bodyContent">'+
            '<p>'+ 'Plate ID : ' + alerts_received[i]['Plate ID'] + '</br>'
            + 'Registration State : ' + alerts_received[i]['Registration State'] + '</br>'
            + 'Plate Type : ' + alerts_received[i]['Plate Type'] + '</br>'
            + 'Issue Date : ' + alerts_received[i]['Issue Date'] + '</br>'
            + 'Violation Code : ' + alerts_received[i]['Violation Code'] + '</br>'
            + 'Vehicle Body Type : ' + alerts_received[i]['Vehicle Body Type'] + '</br>'
            + 'Vehicle Make : ' + alerts_received[i]['Vehicle Make'] + '</br>'
            + 'Issuing Agency : ' + alerts_received[i]['Issuing Agency'] + '</br>'
            + 'Street Code1 : ' + alerts_received[i]['Street Code1'] + '</br>'
            + 'Violation Location : ' + alerts_received[i]['Violation Location'] + '</br>'
            + 'House Number : ' + alerts_received[i]['House Number'] + '</br>'
            + 'Street Name : ' + alerts_received[i]['Street Name'] + '</br>'
            + 'Vehicle Color : ' + alerts_received[i]['Vehicle Color'] + '</br>'
            + 'Latitude : ' + alerts_received[i]['Latitude'] + '</br>'
            + 'Longitude : ' + alerts_received[i]['Longitude'] + '</br>'
            + 'DroneID : ' + alerts_received[i]['DroneID'] + '</br>'
            + 'Alert : ' + alerts_received[i]['Alert'] + '</br>'
            + 'Battery : ' + alerts_received[i]['Battery'] + '</br>'
            +'</p>'+
            '</div>'+
            '</div>';

            var marker = new google.maps.Marker({
                position: {lat: alerts_received[i]['Latitude'], lng: alerts_received[i]['Longitude']},
                map: map,
            });
            var infowindow = new google.maps.InfoWindow();
            google.maps.event.addListener(marker, 'click', function() {

    
            infowindow.setContent(contentString);
            infowindow.open(map, marker);
            });
    
            alert_string = alert_string +
            '<tr>'+
              '<th scope="row">'+i+'</th>'+
              '<td>'+alerts_received[i]["Plate ID"]+'</td>'
              +'<td>'+alerts_received[i]['Registration State']+'</td>'
              +'<td>'+alerts_received[i]['Plate Type']+'</td>'
              +'<td>'+alerts_received[i]['Issue Date']+'</td>'
              +'<td>'+alerts_received[i]['Violation Code']+'</td>'
              +'<td>'+alerts_received[i]['Vehicle Body Type']+'</td>'
              +'<td>'+alerts_received[i]['Vehicle Make']+'</td>'
              +'<td>'+alerts_received[i]['Issuing Agency']+'</td>'
              +'<td>'+alerts_received[i]['Street Code1']+'</td>'
              +'<td>'+alerts_received[i]['Violation Location']+'</td>'
              +'<td>'+alerts_received[i]['House Number']+'</td>'
              +'<td>'+alerts_received[i]['Street Name']+'</td>'
              +'<td>'+alerts_received[i]['Vehicle Color']+'</td>'
              +'<td>'+alerts_received[i]['Latitude']+'</td>'
              +'<td>'+alerts_received[i]['Longitude']+'</td>'
              +'<td>'+alerts_received[i]['DroneID']+'</td>'
              +'<td>'+alerts_received[i]['Alert']+'</td>'
              +'<td>'+alerts_received[i]['Battery']+'</td>'
            +'</tr>'
        }
        $('#alerts').html(alert_string);
    });
});