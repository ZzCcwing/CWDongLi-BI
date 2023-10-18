export default [
  {
    name: '登录',
    path: '/user',
    layout: false,
    routes: [{ path: '/user/login', component: './User/Login' }],
  },
  {
    name: '登录',
    path: '/user',
    layout: false,
    routes: [{ path: '/user/register', component: './User/Register' }],
  },
  { path: '/', redirect: 'welcome' },
  { name: '功能介绍', path: '/welcome', icon: 'pieChart', component: 'Welcome' },
  {
    name: '图表分析',
    path: '/chart_analysis',
    icon: 'fund',
    routes: [
      { name: '同步分析图表', path: '/chart_analysis/add_chart', component: './AddChart' },
      { name: '异步分析图表', path: '/chart_analysis/add_chart_async', component: './AddChartAsync' },

    ],
  },
  { name: '图表库', path: '/my_chart', icon: 'pieChart', component: './MyChart' },
  { name: '我的信息', path: '/user/Edit', icon: 'pieChart', component: './User/Edit' },




  {
    path: '/admin',
    icon: 'crown',
    access: 'canAdmin',
    name: '管理员页面',
    routes: [
      { path: '/admin', redirect: '/admin/sub-page' },
      { path: '/admin/sub-page', component: './Admin' },
    ],
  },
  { path: '*', layout: false, component: './404' },
];
