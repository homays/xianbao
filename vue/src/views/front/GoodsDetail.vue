<template>
  <div style="margin: 0 auto; padding: 10px 0; width: 50%">
    <div style="display: flex; grid-gap: 20px; margin-bottom: 40px">
      <img :src="goods.img" alt="" style="width: 50%; height: 400px; display: block">
      <div>
        <div style="font-weight: bold; font-size: 26px; margin: 20px 0">{{ goods.name }}</div>
        <div style="color: #666; font-size: 16px;">
          <span>浏览 {{ goods.readCount }}</span>
          <span style="margin-left: 20px">点赞 {{ goods.likesCount }}</span>
          <span style="margin-left: 20px">收藏 {{ goods.collectCount }}</span>
        </div>
        <div style="color: red; font-size: 24px; margin: 40px 0">￥{{ goods.price }}</div>
        <div style="margin-bottom: 20px"><span style="color: #666">发货地：</span> {{ goods.address }}</div>
        <div style="margin-bottom: 20px"><span style="color: #666">卖家：</span> {{ goods.userName }}</div>
        <div style="margin-bottom: 40px"><span style="color: #666">发布日期：</span> {{ goods.date }}</div>
        <div>
          <el-button size="medium" style="background-color: orangered; color: #eee; border-color: orangered">点赞</el-button>
          <el-button size="medium" type="warning">收藏</el-button>
          <el-button size="medium" type="danger">立即购买</el-button>
        </div>
      </div>
    </div>

    <div>
      <div style="display: flex; border-bottom: 1px solid orangered; margin-bottom: 10px">
        <div style="padding: 10px 20px; cursor: pointer" :class="{ 'active' : current === '商品详情' }" @click="changeItem('商品详情')">商品详情</div>
        <div style="padding: 10px 20px; cursor: pointer" :class="{ 'active' : current === '商品评论' }" @click="changeItem('商品评论')">商品评论</div>
      </div>

      <div v-if="current === '商品详情'">
        <div v-html="goods.content"></div>
      </div>

      <div v-if="current === '商品评论'">

      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "GoodsDetail",
  data() {
    return {
      id: this.$route.query.id,
      goods: {},
      current: '商品详情'
    }
  },
  created() {
    this.load()
  },
  methods: {
    changeItem(current) {
      this.current = current
    },
    load() {
      this.$request.get('/goods/selectById/' + this.id).then(res => {
        this.goods = res.data || {}
      })
    }
  }
}
</script>

<style scoped>
.active {
  background-color: orangered;
  color: #eee;
}
</style>