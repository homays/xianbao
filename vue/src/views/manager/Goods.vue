<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入关键字查询" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="content" label="详情" width="100">
          <template v-slot="scope">
            <el-button @click="preview(scope.row.content)">显示详情</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="发货地址"></el-table-column>
        <el-table-column prop="img" label="图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px;" v-if="scope.row.img"
                        :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="上架日期"></el-table-column>
        <el-table-column prop="status" label="审核状态">
          <template v-slot="scope">
            <el-tag type="info" v-if="scope.row.status === '待审核'">待审核</el-tag>
            <el-tag type="success" v-if="scope.row.status === '通过'">通过</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '拒绝'">拒绝</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="category" label="分类"></el-table-column>
        <el-table-column prop="userId" label="所属用户ID"></el-table-column>
        <el-table-column prop="userName" label="所属用户"></el-table-column>
        <el-table-column prop="saleStatus" label="上架状态"></el-table-column>
        <el-table-column prop="readCount" label="浏览量"></el-table-column>
        <el-table-column label="操作" align="center" width="240">
          <template v-slot="scope">
            <el-button size="mini" type="success" plain @click="changeStatus(scope.row, '通过')">通过</el-button>
            <el-button size="mini" type="danger" plain @click="changeStatus(scope.row, '拒绝')">拒绝</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
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

    <el-dialog title="内容" :visible.sync="fromVisible1" width="60%" :close-on-click-modal="false" destroy-on-close>
      <div v-html="content"></div>
    </el-dialog>

    <el-dialog title="二手商品" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="价格"></el-input>
        </el-form-item>
        <el-form-item label="详情" prop="content">
          <el-input v-model="form.content" placeholder="详情"></el-input>
        </el-form-item>
        <el-form-item label="发货地址" prop="address">
          <el-input v-model="form.address" placeholder="发货地址"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="img">
          <el-input v-model="form.img" placeholder="图片">
          </el-input>
        </el-form-item>
        <el-form-item label="上架日期" prop="date">
          <el-input v-model="form.date" placeholder="上架日期"></el-input>
        </el-form-item>
        <el-form-item label="审核状态" prop="status">
          <el-input v-model="form.status" placeholder="审核状态"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-input v-model="form.category" placeholder="分类"></el-input>
        </el-form-item>
        <el-form-item label="所属用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="所属用户ID"></el-input>
        </el-form-item>
        <el-form-item label="上架状态" prop="saleStatus">
          <el-input v-model="form.saleStatus" placeholder="上架状态"></el-input>
        </el-form-item>
        <el-form-item label="浏览量" prop="readCount">
          <el-input v-model="form.readCount" placeholder="浏览量"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>
<script>
export default {
  name: "Goods",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
      },
      ids: [],
      content: '',
      fromVisible1: false
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    preview(content) {
      this.content = content
      this.fromVisible1 = true
    },
    changeStatus(row, status) {
      this.$confirm('您确定操作吗？', '确认操作', {type: "warning"}).then(response => {
        this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
        this.form.status = status
        this.$request.put('/goods/update', this.form).then(res => {
          if (res.code === '200') {  // 表示成功保存
            this.$message.success('保存成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(err => {})
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/goods/update' : '/goods/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/goods/delete/' + id).then(res => {
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
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/goods/delete/batch', {data: this.ids}).then(res => {
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
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/goods/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.records
          this.total = res.data.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>