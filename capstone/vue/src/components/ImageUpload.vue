<template>
  <div>
    <div>
        <input type="file" @change="uploadFile" ref="file">
        <button @click="submitFile">Upload!</button>
    </div>
  </div>
</template>

<script>
import fileUploadService from "@/services/FileUploadService.js";

export default {
name: "image-upload"
,
props:["hazardId"],
data(){
    return{
        image:null
    }
},
methods:{
    uploadFile() {
        this.image = this.$refs.file.files[0];
      },
    submitFile() {
        const formData = new FormData();
        formData.append('file', this.image);
        formData.append('hazardId', this.hazardId);
        console.log(this.hazardId);
        fileUploadService.uploadPhoto(formData).then((response) => {
            alert(response.status);
        }).catch((error) => {
            alert(error);
        })
      }

},
}
</script>

<style>

</style>