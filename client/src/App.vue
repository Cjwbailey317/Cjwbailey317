<template>
  <div id="app">
    <nav>
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link v-bind:to="{ name: 'favorites' }" v-if="isLoggedIn">Favorites</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-if="!isLoggedIn">Login</router-link>
      <router-link v-bind:to="{ name: 'register' }" v-if="!isLoggedIn">Register</router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="isLoggedIn">Logout</router-link>
    </nav>

    <router-view />
  </div>
</template>

<script>
export default {
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    }
  }
};
</script>

<style scoped>
nav {
  background-color:n#664404;
  padding: 1rem;
}

nav a {
  background-color:#664404;
  margin-right: 1rem;
  text-decoration: none;
  color: #d3c1a0;
}
body {
  background-color: #664404;
  margin: 0;
  font-family: 'Segoe UI', sans-serif;
}
</style>