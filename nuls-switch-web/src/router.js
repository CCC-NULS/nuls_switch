import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home'
import NewAddress from './views/user/NewAddress'
import ImportAddress from './views/user/ImportAddress'
import BackupsAddress from './views/user/BackupsAddress'
import SwitchHall from './views/tx/SwitchHall'
import AssetList from './views/asset/AssetList'
import HisOrder from './views/order/HisOrder'

Vue.use(Router);

export default new Router({
  routes: [
    // {
    //   path: '/',
    //   name: 'home',
    //   component: Home
    // },
    {
      path: '/',
      name: 'home',
      component: SwitchHall
    },
    {
      path: '/NewAddress',
      name: 'newAddress',
      component: NewAddress
    },
    {
      path: '/ImportAddress',
      name: 'importAddress',
      component: ImportAddress
    },
    {
      path: '/BackupsAddress',
      name: 'backupsAddress',
      component: BackupsAddress
    },
    {
      path: '/SwitchHall',
      name: 'switchHall',
      component: SwitchHall
    },
    {
      path: '/myAsset',
      name: 'myAsset',
      component: AssetList
    },
    {
      path: '/myOrder',
      name: 'myOrder',
      component: HisOrder
    }
  ]
})
