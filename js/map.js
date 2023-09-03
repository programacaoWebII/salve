var x = document.getElementById("map");
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
    console.log('https://www.google.com/maps/dir/?api=1&origin='+position.coords.latitude + ",%20" + position.coords.longitude+'&destination=-8.112935,%20-35.032218';)
  } else {
   console.log("Geolocation is not supported by this browser.");
  }
}

function showPosition(position) {
  
  SRRC ='https://www.google.com/maps/dir/?api=1&origin='+position.coords.latitude + ",%20" + position.coords.longitude+'&destination=-8.112935,%20-35.032218';
  x.ATTRIBUTE_NODE.setAttribute("src", SRRC);
}
getLocation()