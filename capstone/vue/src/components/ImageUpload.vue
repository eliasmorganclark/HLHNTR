<template>
  <div>
    <div>
        <input type="file" @change="uploadFile" ref="file">
        <input type="number" @change="updateId" ref="hazardId">
        <button @click="submitFile">Upload!</button>
    </div>
  </div>
</template>

<script>
import fileUploadService from "@/services/FileUploadService.js";

export default {
name: "image-upload"
,
data(){
    return{
        image:null,
        hazardId: null
    }
},
methods:{
    uploadFile() {
        this.image = this.$refs.file.files[0];
      },
    updateId() {
        this.hazardId = this.$refs.hazardId.value;
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