import { createStore } from 'vuex'

const store = createStore({
  state() {
    return {
      account: { // account추가
        id: 0
      }
    }
  },
  mutations: {
    setAccount(state, payload) { // setAccount메서드생성
      state.account.id = payload// state.account.id 의 값은 payload로 받음
      // id값이 호출되면 도출하겠다
    }
  }
})

export default store
