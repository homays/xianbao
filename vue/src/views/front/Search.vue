<template>
  <div style="width: 70%; margin: 10px auto">
    <div style="margin-bottom: 10px">
      <el-input v-model="name" size="medium" style="width: 300px; margin-right: 10px"></el-input>
      <el-button type="primary" @click="loadGoods(1)">搜 索</el-button>
      <el-button type="warning" @click="reset">重 置</el-button>
    </div>
    <div class="card">
      <el-row :gutter="15">
        <el-col :span="6" v-for="item in goodsList" :key="item.id">
          <div style="margin-bottom: 20px" class="goods-item" @click="$router.push('/front/goodsDetail?id=' + item.id)">
            <img :src="item.img" alt=""
                 style="width: 100%; display: block; height: 260px; margin-bottom: 10px; border-radius: 5px">
            <div style="font-size: 16px; height: 40px; color: #555; margin-bottom: 10px" class="line2 goods-name">
              {{ item.name }}
            </div>
            <div style="display: flex; align-items: baseline">
              <strong style="color: red; font-size: 24px">￥{{ item.price }}</strong>
              <span style="margin-left: 20px; color: #666">{{ item.readCount }}人浏览</span>
              <span style="margin-left: 20px; color: #666">{{ item.likesCount }}人点赞</span>
            </div>
          </div>
        </el-col>
      </el-row>

      <div style="margin: 15px auto">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      goodsList: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 8,  // 每页显示的个数
      total: 0,
      name: this.$route.query.name
    }
  },
  created() {
    this.loadGoods(1)
  },
  methods: {
    loadGoods(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.post('/goods/selectFrontPage', {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.name
      }).then(res => {
        if (res.code === '200') {
          this.goodsList = res.data.records
          this.total = res.data.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleCurrentChange(pageNum) {
      this.loadGoods(pageNum)
    },
    reset() {
      this.name = null
      this.loadGoods(1)
    }
  }
}
</script>

<style scoped>

</style>