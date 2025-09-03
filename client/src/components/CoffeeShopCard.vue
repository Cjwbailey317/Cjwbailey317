<template>
    <div class="coffee-shop-card">
      <h3 v-bind:title="shop.name">{{ shop.name }}</h3>
      <p><strong>Location:</strong> {{ shop.location }}</p>
      <p><strong>Rating:</strong> {{ shop.rating || 'No rating yet' }}</p>
  
      <!-- Optional: Link to details page -->
      <router-link
        v-bind:to="{ name: 'coffee-shop-detail', params: { id: shop.id } }"
        class="details-link"
      >
        View Details
      </router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CoffeeShopCard',
    props: {
      shop: {
        type: Object,
        required: true
      }
    }
  };
  </script>
  
  <style scoped>
  .coffee-shop-card {
    border: 1px solid #d4d4d4;
    border-radius: 6px;
    padding: 1rem;
    background-color: #916412;
    width: 250px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .coffee-shop-card h3 {
    margin: 0 0 0.5rem;
  }
  
  .details-link {
    display: inline-block;
    margin-top: 0.5rem;
    color: #f8a808;
    text-decoration: none;
  }
  
  .details-link:hover {
    text-decoration: underline;
  }
  
  .coffee-shop-card:hover {
    border-color: #a0f0a0;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}
  </style>