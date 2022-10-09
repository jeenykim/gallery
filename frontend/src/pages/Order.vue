<template>
   <div class="order">
    <div class="container">
      <main>
        <div class="py-5 text-center"><h2>주문하기</h2>
          <p class="lead"></p></div>

          <div class="row g-5">
          <div class="col-md-5 col-lg-4 order-md-last"><h4
              class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-primary">구입 목록</span>
            <span
                class="badge bg-primary rounded-pill">
              {{ state.items.length }}
              <!-- 구입목록개수 -->
            </span></h4>
            <!-- 구입목록리스트-->
            <ul class="list-group mb-3">
              <li class="list-group-item d-flex justify-content-between lh-sm" v-for="(i, idx) in state.items" :key="idx">
                <!-- 목록개수만큼 보이기 -->
                <div>
                  <h6 class="my-0">{{ i.name }}</h6>
                </div>
                <!-- 구입목록명 -->
                <span class="text-muted">
                  {{ lib.getNumberFormatted(i.price) }}원
                </span>
                <!-- 가격 -->
              </li>
            </ul>

            <h3 class="text-center total-price">
              {{ lib.getNumberFormatted(computedPrice) }}원
            </h3>
            <!-- 최종가격합산 하단 computedPrice와 연동 -->
          </div>

          <div class="col-md-7 col-lg-8">
            <h4 class="mb-3">주문자 정보</h4>
            <div class="needs-validation" novalidate="">

              <div class="row g-3">
                <div class="col-12"><label for="username" class="form-label">이름</label>
                  <input type="text"
                         class="form-control"
                         id="username"
                         v-model="state.form.name">
                </div>
                <div class="col-12"><label for="address" class="form-label">주소</label>
                  <input type="text"
                         class="form-control"
                         id="address"
                         v-model="state.form.address">
                </div>
              </div>
              <hr class="my-4">

              <h4 class="mb-3">결제 수단</h4>
              <div class="my-3">
                <div class="form-check">
                  <input id="card" name="paymentMethod" type="radio" class="form-check-input"
                         value="card" v-model="state.form.payment">
                  <label class="form-check-label" for="card">신용카드
                  </label>
                </div>

                <div class="form-check">
                  <input id="bank" name="paymentMethod" type="radio" class="form-check-input"
                         value="bank" v-model="state.form.payment">
                  <label class="form-check-label" for="bank">무통장입금</label>
                </div>
              </div>

              <label for="cc-name" class="form-label">카드 번호</label>
              <input type="text"
                     class="form-control"
                     id="cc-name"
                     v-model="state.form.cardNumber">

             <hr class="my-4">

              <button class="w-100 btn btn-primary btn-lg" @click="submit()">결제하기</button>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>

</template>

<script>
import router from '@/router'
// import store from '@/store'
import axios from 'axios'
import { computed, reactive } from 'vue'
import lib from '@/scripts/lib'
// import { useStore } from 'vuex'

export default {
  setup() {
    const state = reactive({
      items: [],
      form: {
        name: '',
        address: '',
        payment: '',
        cardNumber: '',
        items: ''
      }
    })

    const load = () => { // load로 함수화함
      axios.get('/api/cart/items').then(({ data }) => {
        console.log(data)
        state.items = data
      })
    }

    const submit = () => {
      const args = JSON.parse(JSON.stringify(state.form))
      // JSON은 JavaScript Object Notation의 약자로,
      // 브라우저와 서버사이에서 오고가는 데이터의 형식이다.
      // JSON.parse():JSON 문자열의 구문을 분석하고,
      // 그 결과에서 JavaScript 값이나 객체를 생성합니다.
      // JSON.stringify( ): 자바스크립트의 값을 JSON 문자열로 변환한다.
      // 문자열화 프로세스의 작동을 변경하는 함수
      // 참조값의 연결 끊음
      args.items = JSON.stringify(state.items)
      axios.post('/api/orders', args).then(() => {
        alert('주문 완료하였습니다.')
        router.push({ path: '/orders' })
      })
    }
    const computedPrice = computed(() => {
      // 템플릿에서 사용할 복잡한 로직은 computed에서 사용
      // const 선언과 동시에 값을 할당 해야한다.
      let result = 0
      // let은 선언하고 나중에 조건값 할당 가능
      for (const i of state.items) {
        result += i.price
        // 상단 state.items를 i로 선언
        // 개수만큼 가격더한 result값 도출
      }
      return result
    })

    load()
    // 맨처음 load실행
    return { state, lib, computedPrice, submit }
  }
}
</script>

<style scoped>

</style>
