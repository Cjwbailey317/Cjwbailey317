<template>
  <div class="add-coffee-shop">
    <h2>Add a New Coffee Shop</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="name">Name:</label>
        <input id="name" v-model="name" required />
      </div>

      <div class="form-group">
        <label for="description">Description:</label>
        <textarea id="description" v-model="description" />
      </div>

      <div class="form-group">
        <label for="location">Location:</label>
        <input id="location" v-model="location" required />
      </div>

      <button type="submit">Add Coffee Shop</button>
    </form>
  </div>
</template>

<script>
import CoffeeShopService from "../services/CoffeeShopService.js";

export default {
  name: "AddCoffeeShop",
  data() {
    return {
      name: "",
      description: "",
      location: ""
    };
  },
  methods: {
    async submitForm() {
      const newShop = {
        name: this.name,
        description: this.description,
        location: this.location
      };

      try {
        await CoffeeShopService.addCoffeeShop(newShop);
        alert("Coffee Shop added")
        this.$router.push({ name: 'home' }); 
      } catch (error) {
        console.error("Error adding coffee shop:", error);
        alert("Failed to add coffee shop.");
      }
    }
  }
};
</script>

<style scoped>
.form-group {
  margin-bottom: 1rem;
}

input,
textarea {
  width: 100%;
  padding: 0.5rem;
  margin-top: 0.25rem;
}

button {
  background-color: #90ee90;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  cursor: pointer;
  border-radius: 4px;
}

button:hover {
  background-color: #218838;
}
</style>