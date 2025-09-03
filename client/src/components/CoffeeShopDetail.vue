<template>
  <div class="coffee-shop-detail" v-if="shop">
    <h1>{{ shop.name }}</h1>
    <p><strong>Location:</strong> {{ shop.location }}</p>
    <p><strong>Rating:</strong> {{ reviewRating || 'No rating yet' }}</p>
    <!-- Consider adding average rating -->

    <!-- Favorite Button for Authenticated Users -->
    <button
      v-if="isLoggedIn"
      v-on:click="toggleFavorite"
      v-bind:class="{ favorited: isFavorite }"
    >
      {{ isFavorite ? 'Remove from Favorites' : 'Add to Favorites' }}
    </button>

    <!-- Review Section -->
    <div class="reviews">
      <h2>Reviews</h2>

      <review-list v-bind:reviews="reviews" />

      <review-form
        v-if="isLoggedIn"
        v-bind:coffeeShopId="shop.id"
        @review-submitted="handleReviewAdded"
      />
    </div>

    <!-- Back to Home -->
    <router-link to="/" class="back-link">← Back to list</router-link>
  </div>

  <div v-else>
    <p>Loading coffee shop details...</p>
  </div>
</template>

<script>
import CoffeeShopService from "../services/CoffeeShopService.js";
import ReviewForm from "../components/ReviewForm.vue";
import ReviewList from "../components/ReviewList.vue";


export default {
  name: "CoffeeShopDetail",
  components: {
    ReviewForm,
    ReviewList
  },
  data() {
    return {
      shop: null,
      isFavorite: false,
      reviews: []
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
    reviewRating() {
      if (this.reviews.length > 0) {
        return this.reviews[0].rating; 
      }
      return null; 
    }
  },
  mounted() {
    this.fetchShop().then(() => {
      this.fetchReviews();
    });
  },
  methods: {
    fetchShop() {
      const shopId = this.$route.params.id;
      return CoffeeShopService.getById(shopId)
        .then((response) => {
          this.shop = response.data;
        })
        .then(() => {
          return CoffeeShopService.getFavorites();
        })
        .then((response) => {
          this.isFavorite = response.data.some(fav => fav.id === this.shop.id);
        })
        .catch((error) => {
          console.error("Failed to fetch shop details or favorites", error);
        });
      },
    fetchReviews() {
      CoffeeShopService.getReviewsByCoffeeShopId(this.shop.id)
        .then((response) => {
          this.reviews = response.data;
        })
        .catch((error) => {
          console.error("Failed to fetch reviews", error);
        });
      },
    handleReviewAdded(newReview) {
      if (this.reviews) {
        this.reviews.push(newReview);
      }
    },
    toggleFavorite() {
      if (this.isFavorite) {
        CoffeeShopService.deleteFavorite(this.shop.id)
          .then(() => {
            this.isFavorite = false;
            alert("Coffee shop removed from favorites.");
          })
          .catch((error) => {
            console.error("Failed to remove favorite", error);
          });
      } else {
        CoffeeShopService.addFavorite(this.shop.id)
          .then(() => {
            this.isFavorite = true;
            alert("Coffee Shop added to favorites!");
          })
          .catch((error) => {
            console.error("Failed to add favorite", error);
          });
      }
    }
  }
};
</script>

<style scoped>
.coffee-shop-detail {
  padding: 1rem;
  background-color: #664404; 
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  color: #ebe2d1; 
}

.coffee-shop-detail h1 {
  font-size: 2rem;
  color: #ffffff; 
  margin-bottom: 1rem;
}

button {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background-color: #90ee90; 
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button.favorited {
  background-color: #ec8686;
}

.back-link {
  display: inline-block;
  margin-top: 1rem;
  color: #0077cc;
  text-decoration: none;
}

.reviews {
  margin-top: 2rem;
  padding-top: 1rem;
  border-top: 1px solid #ebe2d1; 
}

.reviews h2 {
  margin-bottom: 0.5rem;
  color: #fcfcfc; 
}
</style>