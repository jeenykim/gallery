<template>
  <header>
  <div class="collapse bg-dark" id="navbarHeader">
    <div class="container">
      <div class="row">
        <div class="col-sm-8 col-md-7 py-4">
          <h4 class="text-white">사이트맵</h4>

          <ul class="list-unstyled">
            <li>
            <router-link to='/' class="text-white" >메인화면</router-link>
             </li>

             <!-- 15 orders만들고 추가 -->
             <li v-if="$store.state.account.id">
              <!--기존 계정에 구매목록이있다면-->
              <!-- 주문내역표시 -->
              <!-- 회원아니면 안보임 -->
                <router-link to="/orders" class="text-white">주문 내역</router-link>
              </li>
              <!-- orders이동 주문내역링크 -->

              <li>
                <router-link to='/login' class="text-white" v-if='!$store.state.account.id'>로그인</router-link>
                <!-- 만약store에 id값이 없으면 로그인 -->
                <a to='/login' class="text-white" @click="logout()" v-else>로그아웃</a>
                <!-- v-else 반대 store에 id값이 있으면 로그아웃으로 바뀜 -->
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="navbar navbar-dark bg-dark shadow-sm">
    <div class="container">
      <router-link to='/' class="navbar-brand d-flex align-items-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2" viewBox="0 0 24 24"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/><circle cx="12" cy="13" r="4"/></svg>
        <strong>jeenykim</strong>
      </router-link>

      <router-link to= "/cart" class="cart">
      <i class="fa fa-shopping-cart" aria-hidden="true"></i>
    </router-link>

      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
    </div>
  </div>
</header>
</template>

<script>
import router from '@/router'
import store from '@/store'
import axios from 'axios'

export default {
  name: 'Header',
  setup() {
    const logout = () => {
      axios.post('/api/account/logout').then(() => {
        store.commit('setAccount', 0)
        router.push({ path: '/' })
      })
    }
    return { logout }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  header ul li a {
  cursor: pointer;
}
header .navbar .cart {
  margin-right: auto;
  color: #fff;
  font-size: 16pt;
}

header .navbar .cart:hover {
  outline-color: transparent;
}

</style>
