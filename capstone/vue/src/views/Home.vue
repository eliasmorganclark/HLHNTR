<template>
  <div class="home">
    <!-- <h1>Home</h1>
    <p>Map placeholder</p> -->
    <div id="sidebar">
      <h2> HELP SAVE NORTHEAST OHIO </h2>
      <p> LIST HERE !!!!!!!!!!!!!!!!!!!!!!!!!!! </p>
      </div>
      <div id="mapbox">
    <Map />
    </div>
  </div>
</template>

<script>
import Map from '../components/Map.vue';

export default {
  components: { Map },

     name: "home"

};
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Kanit:ital,wght@0,100;0,400;0,900;1,100;1,400;1,900&display=swap');

.home {
  font-family: 'Kanit', sans-serif;
  display: flex;
  flex-direction: row;
;
}
#sidebar {
  max-width: 500px;
  border-left: 10px solid black;
  border-right: 10px solid black;
  padding: 20px;
background-color: #999;
color: #000;

flex-grow: 1;
}
#mapbox {
flex-grow: 5;

}
h2 {
  font-size: 30px;
  
}






</style>