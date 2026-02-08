<template>
  <div class="item-card" @click="selectItem">
    <img :src="item.image" :alt="item.name" />
    <h3>{{ item.name }}</h3>
    <ul>
      <li v-for="(stat, index) in item.stats" :key="index">{{ stat }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'ItemCard',
  props: {
    item: {
      type: Object,
      required: true,
    },
  },
  methods: {
    selectItem() {
      this.$emit('select', this.item);
    },
  },
};
</script>

<style scoped>
.item-card {
  border: 2px solid #5a5a5a;
  border-radius: 10px;
  padding: 20px;
  width: 250px;
  cursor: pointer;
  transition: transform 0.2s;
}

.item-card:hover {
  transform: scale(1.05);
  border-color: #a0a0a0;
}

img {
  width: 100%;
  height: auto;
  border-radius: 5px;
}

h3 {
  margin: 10px 0;
}

ul {
  list-style: none;
  padding: 0;
  text-align: left;
}
</style>
