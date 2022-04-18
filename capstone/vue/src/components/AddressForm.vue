// Cannot figure out how to import this to master form

<template>
<div class = "address-form-container">
    <form v-on:submit.prevent="saveReport">
      <label for="house-number">House Number</label>
      <input v-model="address.houseNumber" id="houser-number" type="text" />

      <label for="street-name">Street Name</label>
      <input v-model="address.streetName" id="street-name" type="text" />

      <label for="city">City</label>
      <input v-model="address.city" id="city" type="text" />

      <label for="state">State</label>
      <input v-model="address.state" id="state" type="text" />

      <label for="zip-code">Zip Code</label>
      <input v-model="address.zip" id="zip-code" type="text" />

      <input type="submit" value="Submit Report" />
      <input type="button" v-on:click.prevent="clearForm" value="Clear Form" />
    </form>
    <input type="button" value="Drop a pin" v-on:click.prevent="$emit('drop-pin',true)" />
</div>
</template>

<script>
export default {
  name: "address-form",
  emits: ["drop-pin"],
  data() {
    return {
      address: {
        houseNumber: "",
        streetName: "",
        city: "",
        state: "",
        zip: "",
      },
    };
  },
  methods: {
    clearForm() {
      this.address = {};
    },
  },
};
</script>

<style></style>
