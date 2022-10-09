<template>
  <div class="form-signin w-100 m-auto">

    <img class="mb-4" src="../assets/logo.png" alt="" width="72" height="57">
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <div class="form-floating">
      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
      v-model="state.form.email">

      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" @keyup.enter="submit()" v-model="state.form.password">
      <label for="floatingPassword">Password</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me" @keyup.enter="submit()"> Remember me
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" @click="submit">Sign in</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>

</div>

</template>

<script>
import router from '@/router'
import store from '@/store'
import axios from 'axios'
import { reactive } from 'vue'
// import { useStore } from 'vuex'

export default {
  setup() {
    const state = reactive({
      form: { // 객체추가
        email: '',
        password: ''
      }
    })

    // const store = useStore()

    const submit = () => {
      axios.post('/api/account/login', state.form).then((res) => {
        store.commit('setAccount', res.data)
        sessionStorage.setItem('id', res.data)
        console.log(res)
        router.push({ path: '/' })
        window.alert('로그인하셨습니다')
      }).catch(() => {
        window.alert('로그인정보없습니다')
      })
    }

    return { state, submit }
  }
}
</script>
<!-- 로그인하면 res를 콘솔로 찍고
그안에 res값을
store에 넣어줌 (첫번째인자로 setAccount string넣어주고 두번째 res.data) -->

<!-- sessionStorage에 id데이터값보관 -->
<!-- 실패경우 catch로 불일치일경우 경고창 -->

<style scoped>
/* html,
body {
  height: 100%;
}

body {
  display: flex;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
} */

.form-signin {
  max-width: 330px;
  padding: 15px;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
