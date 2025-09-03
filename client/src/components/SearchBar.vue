<template>
  <div class="search-bar">
    <div class="search-controls">
      <select v-model="field">
        <option value="name">Name</option>
        <option value="location">Location</option>
      </select>
      <input 
        v-model="query" 
        type="text" 
        :placeholder="`Search by ${field}...`"
        @input="search"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      query: '',
      field: 'name'
    };
  },
  methods: {
    search() {
      this.$emit('search', { field: this.field, query: this.query });
    }
  }
};
</script>

<style scoped>
.search-bar {
  margin: 1rem 0;
}
.search-controls {
  display: flex;
  gap: 0.5rem;
}
select,
input {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
input {
  flex: 1;
  background-color: rgb(230, 205, 137);
}
</style>