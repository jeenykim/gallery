<template>
  <div class="orders">
    <div class="container">
      <table class="table table-bordered">
        <thead>
        <tr>
          <th>번호</th>
          <th>주문자명</th>
          <th>주소</th>
          <th>결제 수단</th>
          <th>구입 항목</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(o, idx1) in state.orders" :key="idx1">
          <!-- 전체 주문목록 v-for 사이클링 -->
          <!-- orders약자로 o -->
          <td>{{ state.orders.length - idx1 }}</td>
          <!-- 역순 -->
          <td>{{ o.name }}</td>
          <td>{{ o.address }}</td>
          <td>{{ o.payment }}</td>
          <td>
            <div v-for="(i, idx2) in o.items" :key="idx2">{{ i.name }}</div>
          </td>
          <!-- 각각 주문목록아이템 v-for 사이클링 -->
        </tr>
        </tbody>
      </table>
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
      orders: []
    })
    axios.get('/api/orders').then(({ data }) => {
      state.orders = []// 초기화

      for (const d of data) { // 구매목록리스트 for문 돌림
        if (d.items) { // 목록값이 있을때만
          d.items = JSON.parse(d.items)// json파싱
        }
        state.orders.push(d)// orders에 넣어줌
      }
    })

    return { state, lib }
  }
}
</script>

<style scoped>
 .table {
  margin-top: 30px;
}
.table > tbody {
  border-top: 1px solid #eee;
}
</style>
