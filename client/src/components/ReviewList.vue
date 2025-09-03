<template>
    <div class="review-list">
      <ul v-if="reviews.length > 0">
        <li v-for="review in reviews" :key="review.id">
          <strong>User {{ review.userId }} said:</strong>
           <!-- Consider adding a Username for the review -->
          <p>{{ review.comment }}</p>
        </li>
      </ul>
      <p v-else>No reviews yet.</p>
    </div>
  </template>
  
  <script>
  export default {
    name: "ReviewList",
    props: {
      reviews: {
        type: Array,
        required: true
      }
    }
  };
  </script>
  
  <style scoped>
  .review-list {
    margin-top: 2rem;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    padding: 1rem;
    border-bottom: 1px solid #ccc;
  }
  
  strong {
    font-weight: bold;
  }
  
  p {
    margin: 0.5rem 0;
  }
  </style>