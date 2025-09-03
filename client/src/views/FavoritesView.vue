<template>
    <div class="favorites-view">
      <h1>Your Favorite Coffee Shops</h1>
      
      <!-- Loading Spinner -->
      <loading-spinner v-bind:spin="isLoading" v-if="isLoading" />
      
      <!-- Error Message (if any issue fetching favorites) -->
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  
      <!-- No Favorites State -->
      <p v-if="!isLoading && !errorMessage && favorites.length === 0">
        You haven’t added any favorites yet.
      </p>
  
      <!-- Coffee Shop List (display only when favorites are available) -->
      <coffee-shop-list
        v-if="!isLoading && favorites.length > 0"
        v-bind:shops="favorites"
        v-bind:cardView="cardView"
      />
  
      <!-- View Toggle (card / table) -->
      <div v-if="favorites.length > 0" class="view-toggle">
        <font-awesome-icon
          v-bind:class="{ 'view-icon': true, active: cardView }"
          v-on:click="cardView = true"
          icon="fa-solid fa-grip"
          title="View tiles"
        />
        <font-awesome-icon
          v-bind:class="{ 'view-icon': true, active: !cardView }"
          v-on:click="cardView = false"
          icon="fa-solid fa-table"
          title="View table"
        />
      </div>
    </div>
  </template>
  
  <script>
  import CoffeeShopList from "../components/CoffeeShopList.vue";
  import LoadingSpinner from "../components/LoadingSpinner.vue";
  import CoffeeShopService from "../services/CoffeeShopService.js";
  
  export default {
    name: "FavoritesView",
    components: {
      CoffeeShopList,
      LoadingSpinner
    },
    data() {
      return {
        favorites: [],
        isLoading: false,
        cardView: true,
        errorMessage: ''
      };
    },
    mounted() {
      this.fetchFavorites();
    },
    methods: {
      fetchFavorites() {
        this.isLoading = true;
        this.errorMessage = '';  
  
        CoffeeShopService.getFavorites()
          .then((response) => {
            this.favorites = response.data;
          })
          .catch((error) => {
            this.errorMessage = "Unable to load your favorites. Please try again.";
            console.error("Failed to load favorites", error);
          })
          .finally(() => {
            this.isLoading = false;
          });
      }
    }
  };
  </script>
  
  <style scoped>
  .favorites-view {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem 1rem;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #ebe2d1;
  background-color: #664404;
}

#spinner {
  color: lightgreen;
  margin-left: 0.5rem;
  vertical-align: middle;
}

.error {
  color: #ffadad;
  font-weight: bold;
  margin-top: 1rem;
}

.view-toggle {
  margin: 1rem 0;
  display: flex;
  gap: 0.5rem;
}

.view-icon {
  font-size: 1.2rem;
  padding: 0.4rem;
  color: #ebe2d1;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.view-icon.active {
  background-color: #a2d2a2; 
}

.view-icon:not(.active):hover {
  color: #81a3c2;
  background-color: rgba(255, 255, 255, 0.2);
}
  </style>