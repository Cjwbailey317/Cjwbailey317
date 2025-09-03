<template>
  <div class="home">
    <div id="heading-line">
      <h1>
        Welcome to MyFriendJoe! Lets Build Your Coffee Catalog!
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
    </div>

    <!-- Add Coffee Shop -->
    <router-link v-if="isLoggedIn" to="/add-coffee-shop" class="add-button">+ Add New Coffee Shop </router-link>

    <!-- Login prompt -->
    <p id="login-message" v-if="!isLoggedIn">
      Welcome!<br />
      <router-link v-bind:to="{ name: 'login' }">Login</router-link> to create your list of coffee shops.
    </p>

    <!-- Search bar -->
    <search-bar @search="handleSearch" />
    
    <!-- View toggle buttons -->
    <div class="view-toggle" v-if="coffeeShops.length > 0">
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

    <!-- <h2 v-if="coffeeShops.length > 0">Nearby Coffee Shops</h2> -->

    <!-- Coffee Shop List -->
    <coffee-shop-list
      v-if="!isLoading"
      v-bind:shops="coffeeShops"
      v-bind:cardView="cardView"
    />
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import CoffeeShopList from "../components/CoffeeShopList.vue";
import CoffeeShopService from "../services/CoffeeShopService.js";
import SearchBar from "../components/SearchBar.vue"; 

export default {
  name: "HomeView",
  components: {
    LoadingSpinner,
    CoffeeShopList,
    SearchBar
  },
  data() {
    return {
      isLoading: false,
      cardView: true,
      coffeeShops: [],
      searchQuery: '',
      CoffeeShopService: null
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },
  mounted() {
    
    this.fetchCoffeeShops();
  },
  methods: {
    handleSearch({ field, query }) {
      this.searchQuery = query;
      this.fetchCoffeeShops(field, query);
    },
    fetchCoffeeShops(field = '', query = '') {
      this.isLoading = true;

      let params = {};
      if (field && query) {
        params[field] = query;
      }

      CoffeeShopService.getAll({ params })
        .then(response => {
          this.coffeeShops = response.data;
        })
        .catch(error => {
          console.error("Failed to load coffee shops", error);
        })
        .finally(() => {
          this.isLoading = false;
        });
    }
  }
};
</script>

<style scoped>
#spinner {
  color: green;
}

.view-toggle {
  margin: 1rem 0;
}

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #fcfcfc;
  border-radius: 3px;
  cursor: pointer;
}

.view-icon.active {
  background-color: #90ee90;
}

.view-icon:not(.active):hover {
  color: rgb(129, 129, 194);
  background-color: rgba(255, 255, 255, 0.7);
}
.add-button {
  background-color: #916412;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  text-decoration: none;
}

.add-button:hover {
  background-color: #218838;
}

.home {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem 1rem;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #ebe2d1;
  background-color: #664404;
}
#heading-line h1 {
  font-size: 2rem;
  margin-bottom: 1rem;
  line-height: 1.4;
}

#login-message {
  background-color: #fff3cd;
  border: 1px solid #ffeeba;
  padding: 1rem;
  border-radius: 4px;
  margin-bottom: 1.5rem;
  color: #856404;
}
body {
  background-color: #664404;
  margin: 0;
  font-family: 'Segoe UI', sans-serif;
}


</style>