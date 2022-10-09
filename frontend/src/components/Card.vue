<template>
<div class="card shadow-sm">
  <span class="img" :style="{backgroundImage: `url(${item.imgPath})`}"/>

              <div class="card-body">
                <p class="card-text"><span>{{ item.name }} &nbsp;</span>
                </p>

                <div class="d-fle
                x justify-content-between align-items-center">

                  <button type="button" class="btn btn-primary" @click="addToCart(item.id)">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </button>

                  <small class="price text-muted">{{ lib.getNumberFormatted(item.price) }}원</small>
                </div>
              </div>
            </div>
</template>

<script>

import lib from '@/scripts/lib'
import axios from 'axios'

export default {
  name: 'Card',
  props: {
    item: Object
  },
  setup() {
    const addToCart = (itemId) => { // 상단 click 연동addToCart메서드 만듬
      // itemId인자로 받아서 백앤드 api post로 보냄
      axios.post(`/api/cart/items/${itemId}`).then(() => {
        console.log('success')
      })
    }
    return { lib, addToCart }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .card-text{
    text-align: left;
    }

    .card .img {
  display: inline-block;
  width: 100%;
  height: 250px;
  background-size: cover;
  background-position: center;
}
/* .card .card-body .price {
  text-decoration: line-through;
} */
</style>
