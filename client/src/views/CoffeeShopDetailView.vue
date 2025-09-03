<template>
    <div class="coffee-shop-detail-view">
      <coffee-shop-detail v-bind:shopId="shopId" />
    </div>
  </template>
  
  <script>
  import CoffeeShopDetail from '../components/CoffeeShopDetail.vue';
  
  export default {
    name: 'CoffeeShopDetailView',
    components: {
      CoffeeShopDetail
    },
    computed: {
      shopId() {
        return this.$route.params.id;
      }
    }
  };
  </script>
  
  <style scoped>
  .coffee-shop-detail-view {
    padding: 1rem;
  }
  </style>