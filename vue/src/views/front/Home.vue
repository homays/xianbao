<template>
  <div style="min-height: 100vh">
    <div class="bg">
      <div style="width: 600px">
        <div style="text-align: center; color: #eee; font-size: 30px; margin-bottom: 20px">好看、好用、好玩，都在这里</div>
        <div style="display: flex">
          <el-input v-model="name" size="medium" prefix-icon="el-icon-search"
                    placeholder="请输入商品名称关键字搜索"></el-input>
          <el-button @click="$router.push({ path: '/front/search', query: { name: name }})" size="medium" type="primary"
                     style="margin-left: 5px; background-color: #409EFF"><i class="el-icon-search"></i></el-button>
        </div>
      </div>
    </div>

    <div
        style="width: 70%; background-color: #fff; margin: 10px auto; padding: 20px; border-radius: 5px; position: relative">

      <el-button @click="$router.push('/front/addGoods')" style="position: absolute; top: 20px; right: -150px"
                 size="medium" type="primary" plain>发布商品
      </el-button>
      <el-button style="position: absolute; top: 70px; right: -150px" size="medium" type="primary" plain>发布求购
      </el-button>

      <div style="margin-bottom: 20px">
        <el-select v-model="category" size="medium" style="width: 200px" @change="loadGoods(1)">
          <el-option value="全部"></el-option>
          <el-option v-for="item in categoryList" :key="item.id" :value="item.name"></el-option>
        </el-select>

        <el-select v-model="sort" size="medium" style="width: 200px; margin-left: 10px" @change="loadGoods(1)">
          <el-option value="最新"></el-option>
          <el-option value="最热"></el-option>
        </el-select>
      </div>

      <div>
        <el-row :gutter="15">
          <el-col :span="6" v-for="item in goodsList" :key="item.id">
            <div style="margin-bottom: 20px" class="goods-item"
                 @click="$router.push('/front/goodsDetail?id=' + item.id)">
              <img :src="item.img" alt=""
                   style="width: 100%; display: block; height: 260px; margin-bottom: 10px; border-radius: 5px">
              <div style="font-size: 16px; height: 40px; color: #555; margin-bottom: 10px" class="line2 goods-name">
                {{ item.name }}
              </div>
              <div style="display: flex; align-items: baseline">
                <strong style="color: red; font-size: 24px">￥{{ item.price }}</strong>
                <span style="margin-left: 20px; color: #666">{{ item.readCount }}人浏览</span>
                <span style="margin-left: 20px; color: #666">{{ item.likeCount }}人点赞</span>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>

      <div style="margin: 15px 0">
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

  data() {
    return {
      categoryList: [],
      goodsList: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 12,  // 每页显示的个数
      total: 0,
      category: '全部',
      sort: '最新',
      name: ''
    }
  },
  mounted() {
    this.loadCategory()
    this.loadGoods(1)
  },
  methods: {
    handleCurrentChange(pageNum) {
      this.loadGoods(pageNum)
    },
    loadCategory() {
      this.$request.get('/category/selectAll').then(res => {
        this.categoryList = res.data || []
      })
    },
    loadGoods(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum;
      this.$request.post('/goods/selectFrontPage', {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        category: this.category === '全部' ? null : this.category,
        sort: this.sort
      }).then(res => {
        if (res.code === '200') {
          this.goodsList = res.data.records;
          this.total = res.data?.total;
        } else {
          this.$message.error(res.msg);
        }
      });
    }
  }
}
</script>

<style scoped>
.bg {
  height: 400px;
  background-image: url("@/assets/imgs/home.jpg");
  background-size: 100%;
  background-position-y: center;
  display: flex;
  align-items: center;
  justify-content: center;
}

.goods-item {
  cursor: pointer;
}

.goods-item img, .goods-name {
  transition: all .2s;
}

.goods-item:hover img {
  scale: 1.03;
}

.goods-item:hover .goods-name {
  color: #ec3d3d !important;
}
</style>