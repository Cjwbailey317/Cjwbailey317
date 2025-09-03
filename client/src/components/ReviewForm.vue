<template>
  <div class="review-form">
    <h2>Leave a Review</h2>
    <form @submit.prevent="submitReview">
      <textarea
        v-model="review.comment"
        placeholder="Write your review here..."
        required
      ></textarea>

      <div class="rating">
        <label for="rating">Rating:</label>
        <select v-model="review.rating" required>
          <option value="" disabled>Select a rating</option>
          <option v-for="n in 5" :key="n" :value="n">{{ n }}</option>
        </select>
      </div>

      <button type="submit" :disabled="isSubmitting">Submit Review</button>
    </form>
  </div>
</template>

<script>
import CoffeeShopService from "../services/CoffeeShopService.js";

export default {
  name: "ReviewForm",
  props: {
    coffeeShopId: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      review: {
        comment: '',
        rating: null
      },
      isSubmitting: false
    };
  },
  methods: {
    async submitReview() {
      this.isSubmitting = true;
      try {
        const response = await CoffeeShopService.addReview(this.coffeeShopId, this.review);

        this.$emit('review-submitted', response.data);

        alert("Review submitted!");

        this.review.comment = '';
        this.review.rating = null;
      } catch (error) {
        console.error("Failed to submit review:", error);
      } finally {
        this.isSubmitting = false;
      }
    }
  }
};
</script>

<style scoped>
.review-form {
  padding: 1rem;
  background-color: #f9f9f9;
  border-radius: 6px;
}

textarea {
  width: 100%;
  height: 150px;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
  resize: vertical;
}

select {
  padding: 0.5rem;
  margin-top: 0.5rem;
  border-radius: 4px;
}

button {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background-color: #0077cc;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
