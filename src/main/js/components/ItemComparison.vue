<template>
  <div class="item-comparison">
    <h1>TERRARIA World Cup</h1>
    <h2>32강</h2>
    <div class="comparison-container">
      <ItemCard :item="itemA" @select="selectItem" />
      <div class="vs">VS</div>
      <ItemCard :item="itemB" @select="selectItem" />
    </div>
    <button @click="nextRound">다음</button>
  </div>
</template>

<script>
import ItemCard from './ItemCard.vue';

export default {
  name: 'ItemComparison',
  components: {
    ItemCard,
  },
  data() {
    return {
      itemA: {
        id: 1,
        name: '아이템 A',
        image: 'https://placehold.co/150x150',
        stats: ['공격력: 50', '속도: 보통'],
      },
      itemB: {
        id: 2,
        name: '아이템 B',
        image: 'https://placehold.co/150x150',
        stats: ['공격력: 45', '속도: 빠름'],
      },
    };
  },
  methods: {
    selectItem(item) {
      console.log('Selected item:', item);
      // Logic to handle item selection
    },
    nextRound() {
      console.log('Next round');
      // Logic to load next items
    },
  },
};
</script>

<style scoped>
.item-comparison {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.comparison-container {
  display: flex;
  align-items: center;
  margin: 20px 0;
}

.vs {
  font-size: 2em;
  margin: 0 20px;
  font-weight: bold;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 1.2em;
  cursor: pointer;
  background-color: #5a5a5a;
  color: white;
  border: none;
  border-radius: 5px;
}
</style>
