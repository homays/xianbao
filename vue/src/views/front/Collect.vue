<template>
  <div style="width: 70%; margin: 10px auto">

<!--    <div style="margin-bottom: 10px">
      <el-button type="danger" plain @click="delBatch">批量取消收藏</el-button>
    </div>-->

    <div class="card" style="margin-top: 20px">
      <el-table :data="tableData" stripe>
<!--        <el-table-column type="selection" width="55" align="center"></el-table-column>-->
        <el-table-column prop="goodsName" label="商品名称" show-overflow-tooltip style="width: 300px"></el-table-column>
        <el-table-column label="商品图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px;" v-if="scope.row.goodsImg"
                        :src="scope.row.goodsImg" :preview-src-list="[scope.row.goodsImg]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="商品链接">
          <template v-slot="scope">
            <a :href="'/front/goodsDetail?id=' + scope.row.goodsId" target="_blank">点击打开</a>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="100" align="center">
          <template v-slot="scope">
            <el-button plain type="danger" size="mini" @click="del(scope.row.goodsId)">取消收藏</el-button>
          </template>
        </el-table-column>
      </el-table>

<!--      <div style="margin-top: 10px">-->
<!--        <el-pagination-->
<!--            background-->
<!--            @current-change="handleCurrentChange"-->
<!--            :current-page="pageNum"-->
<!--            :page-sizes="[5, 10, 20]"-->
<!--            :page-size="pageSize"-->
<!--            layout="total, prev, pager, next"-->
<!--            :total="total">-->
<!--        </el-pagination>-->
<!--      </div>-->
    </div>

  </div>
</template>

<script>
export default {
  name: "Collect",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      title: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'},
        ]
      },
      ids: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/collect/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    // handleSelectionChange(rows) {   // 当前选中的所有的行数据
    //   this.ids = rows.map(v => v.id)   //  [1,2]
    // },
    // delBatch() {   // 批量删除
    //   if (!this.ids.length) {
    //     this.$message.warning('请选择数据')
    //     return
    //   }
    //   this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
    //     this.$request.delete('/collect/delete/batch', {data: this.ids}).then(res => {
    //       if (res.code === '200') {   // 表示操作成功
    //         this.$message.success('操作成功')
    //         this.load(1)
    //       } else {
    //         this.$message.error(res.msg)  // 弹出错误的信息
    //       }
    //     })
    //   }).catch(() => {
    //   })
    // },
    // load(pageNum) {  // 分页查询
    //   if (pageNum) this.pageNum = pageNum
    //   this.$request.get('/collect/selectPage', {
    //     params: {
    //       pageNum: this.pageNum,
    //       pageSize: this.pageSize,
    //       title: this.title,
    //     }
    //   }).then(res => {
    //     this.tableData = res.data?.list
    //     this.total = res.data?.total
    //   })
    // },
    load() {
      this.$request.get('/collect/selectAll').then(res => {
        this.tableData = res.data
      })
    },
    reset() {
      this.title = null
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>