import axios from 'axios';

const API_BASE_URL = 'http://localhost:9000';

export default {
  getAll(filter = {}) {
    if (filter.params?.name) {
      return axios.get(`${API_BASE_URL}/coffeeshops/search`, {
        params: { name: filter.params.name }
      });
    } else if (filter.params?.location) {
      return axios.get(`${API_BASE_URL}/coffeeshops/location`, {
        params: { locationName: filter.params.location }
      });
    } else {
      return axios.get(`${API_BASE_URL}/coffeeshops`);
    }
  },
  getById(id) {
    return axios.get(`${API_BASE_URL}/coffeeshops/${id}`);
  },
  addFavorite(id) {
    return axios.post(`${API_BASE_URL}/coffeeshops/${id}/favorite?isFavorite=true`);
  },
  getFavorites() {
    return axios.get(`${API_BASE_URL}/coffeeshops/favorites`);
  },
  deleteFavorite(id) {
    return axios.delete(`${API_BASE_URL}/coffeeshops/${id}/favorite`);
  },
  addReview(coffeeShopId, review) {
    return axios.post(`${API_BASE_URL}/coffee-shops/reviews`, {
      coffeeShopId,
      comment: review.comment,
      rating: review.rating
    });
  },
  updateReview(reviewId, review) {
    return axios.put(`${API_BASE_URL}/coffee-shops/reviews/${reviewId}`, review);
  },
  deleteReview(reviewId) {
    return axios.delete(`${API_BASE_URL}/coffee-shops/reviews/${reviewId}`);
  },
  getReviewsByCoffeeShopId(coffeeShopId) {
    return axios.get(`${API_BASE_URL}/coffee-shops/${coffeeShopId}/reviews`);
  },
  addCoffeeShop(newCoffeeShop) {
    return axios.post(`${API_BASE_URL}/coffeeshops`, newCoffeeShop);
  }
}