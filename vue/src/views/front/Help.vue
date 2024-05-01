<template>
  <div style="width: 50%; margin: 10px auto">
    <div style="margin-bottom: 10px">
      <el-input v-model="title" placeholder="请输入标题关键字查询" style="width: 300px; margin-right: 10px" clearable></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div class="card" v-for="item in tableData" :key="item.id" style="margin-bottom: 5px; display: flex; grid-gap: 10px">
      <img :src="item.avatar" alt="" style="width: 50px; height: 50px; display: block; border-radius: 50%">
      <div style="flex: 1">
        <div style="margin-bottom: 5px">
          <strong style="margin-right: 5px">{{ item.title }}</strong>
          <el-tag type="danger" v-if="item.solved === '未解决'">未解决</el-tag>
          <el-tag type="success" v-if="item.solved === '已解决'">已解决</el-tag>
        </div>
        <div style="color: #888;"><span style="margin-right: 20px">{{ item.userName }}</span>  <span>{{ item.time }}</span></div>

        <div style="margin: 15px 0">
          <el-image :preview-src-list="[item.img]" :src="item.img" alt="" style="width: 200px"></el-image>
        </div>
        <div v-html="item.content" style="margin: 15px 0"></div>
        <div style="margin-bottom: 15px">
<!--          <el-button type="primary" @click="handleCommentShow(item)">展开 / 折叠 求购评论</el-button>-->
          <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">
            展开 / 折叠 求购评论
          </el-button>
        </div>
        <div v-if="item.showComment">
          <Comment :fid="item.id" module="help" />
        </div>
      </div>

      <el-drawer
          title="评论"
          :visible.sync="drawer"
          :direction="direction">
        <div class="card">
          <Comment :fid="item.id" module="help" />
        </div>
      </el-drawer>
    </div>

    <div style="margin: 15px auto" v-if="total > 0">
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
</template>

<script>
import Comment from "@/components/Comment";
export default {
  name: "Help",
  components: {Comment},
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0,
      title: '',
      drawer: false,
      direction: 'rtl',
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleCommentShow(help) {
      this.$set(help, 'showComment', !help.showComment)
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.post('/help/selectFrontPage', {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.title
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.records
          this.total = res.data.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    reset() {
      this.title = ''
      this.load(1)
    }
  }
}
</script>

<style scoped>
.card {
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}
</style>