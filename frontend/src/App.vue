<template>
   <Header/>
   <!-- <Home/> -->
   <RouterView/>

<Footer/>

</template>

<script>
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer'
import store from './store'
import axios from 'axios'
import { useRoute } from 'vue-router'
import { watch } from 'vue'

export default {
  name: 'App',
  components: {
    Header,
    Footer
    // Home
  },
  setup() {
    // const id = sessionStorage.getItem('id')

    // if (id) {
    //   store.commit('setAccount', id)
    const check = () => {
      axios.get('/api/account/check').then(({ data }) => {
        console.log(data)
        store.commit('setAccount', data || 0)
        // data있으면 data없으면 0
      })
    }
    const route = useRoute()
    // 현재 브라우저 url정보가져오고 상단 vue-router에서 가져오고

    watch(route, () => {
      // 경로 바뀔때마다 watch하고 check메서드 실행해
      check()
    })
  }
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
h4{
  text-align: left;}

  ul li{
    text-align: left;
  }
/*
nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #ffffff;
}

nav a.router-link-exact-active {
  color: #42b983;
} */

/* img{
  width:100%;
  } */

.bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }

      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }

      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }

      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }

      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }

</style>
