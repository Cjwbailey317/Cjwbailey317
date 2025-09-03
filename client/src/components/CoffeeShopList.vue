<template>
    <div class="coffee-shop-list">
        <div v-if="shops.length === 0">
            <p>No joe found. Try again!</p>
        </div>

        <div v-else>
            <div v-if="cardView" class="card-view">
                <h3>Coffee Shops</h3>
                <coffee-shop-card
                v-for="shop in shops"
                v-bind:shop="shop"
                v-bind:key="shop.id"
                />
            </div>
            <div v-else>
        <h3>Coffee Shops</h3>
        <table class="table-view">
          <thead>
            <tr>
              <th>Name</th>
              <th>Location</th>
              <th>Rating</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="shop in shops" v-bind:key="shop.id">
              <td>{{ shop.name }}</td>
              <td>{{ shop.location }}</td>
              <td>{{ shop.rating || 'N/A' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

  
  <script>
  import CoffeeShopCard from './CoffeeShopCard.vue';
  export default {
    name: 'CoffeeShopList',
    props: {
        shops: {
            type: Array,
            required: true
        },
        cardView: {
            type: Boolean,
            default: true
        }
    },
    components: {
        CoffeeShopCard
    } 
    };
  </script>
  
  <style scoped>
  .card-view {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.table-view {
  width: 100%;
  border-collapse: collapse;
}

.table-view th,
.table-view td {
  padding: 0.5rem;
  border: 1px solid #ccc;
  text-align: left;
}

.coffee-shop-list {
  display: grid;
  grid-template-columns: fr;  
  gap: 1.5rem;                            
  margin-top: 2rem;                       
}

.coffee-card {
  background-color: #fffdf9;
  border: 1px solid #e0dcd5;
  border-radius: 8px;
  padding: 1rem;
}



.coffee-card h3 {
  margin-top: 0;
  color: #4e342e;
  font-size: 1.2rem;
}

.coffee-card p {
  margin: 0.5rem 0;
  color: #5c4a3d;
}

  </style>