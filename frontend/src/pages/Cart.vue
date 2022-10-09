<template>
  <div class="cart">
    <div class="container">
      <ul>
        <li v-for="(i, idx) in state.items" :key="idx">
          <!-- item 약자 i index의 약자idx로 state에 있는 items를 반복 -->
          <!--하단 구입선택한 items내용들 -->
          <img :src='i.imgPath'/>
          <!-- 그림 -->
          <span class="name">{{ i.name }}</span>
          <!-- 이름 -->
          <!-- <span class="price">{{ lib.getNumberFormatted(i.price) }}원</span> -->
          <span class="price">{{ lib.getNumberFormatted(i.price) }}원</span>
          <!-- 가격 -->
          <i class="fa fa-trash" @click="remove(i.id)"></i>
          <!-- 휴지통 하단remove와 연동 -->
        </li>
      </ul>

      <router-link to="/order" class="btn btn-primary">구입하기</router-link>
    </div>
</div>

</template>

<script>
// import router from '@/router'
// import store from '@/store'
import axios from 'axios'
import { reactive } from 'vue'
import lib from '@/scripts/lib'
// import { useStore } from 'vuex'

export default {
  setup() {
    const state = reactive({
      items: []
    })

    const load = () => { // load로 함수화함
      axios.get('/api/cart/items').then(({ data }) => {
        console.log(data)
        state.items = data
      })
    }

    const remove = (itemId) => {
      axios.delete(`/api/cart/items/${itemId}`).then(() => {
        load()
        // 지운 결과값의 load잡아옴
        // 문자열 생성시 따옴표 대신 백틱(`)을 사용한다.
      })
    }
    // items에 itemId지우고 결과값 도출

    load()
    // 맨처음 load실행
    return { state, lib, remove }
  }
}
</script>

<style scoped>
 .cart ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
.cart ul li {
  border: 1px solid rgb(255, 255, 255);
  margin-top: 25px;
  margin-bottom: 25px;
}
.cart ul li img {
  width: 150px;
  height: 150px;
}
.cart ul li .name {
  margin-left: 25px;
}
.cart ul li .price {
  margin-left: 25px;
}
.cart ul li i {
  float: right;
  font-size: 20px;
  margin-top: 65px;
  margin-right: 50px;
}
.cart .btn {
  width:150px;
  display:block;
  margin:0 auto;
  padding:5px 12px;
  font-size: 18px;
  outline: none;
}

</style>
