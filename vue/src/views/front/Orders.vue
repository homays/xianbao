<template>
  <div style="width: 85%; margin: 10px auto" class="card">

    <div style="margin-bottom: 15px; display: flex">
      <div style="flex: 1">
        <el-radio-group v-model="role">
          <el-radio-button label="buyer">我购买的订单</el-radio-button>
          <el-radio-button label="sale">我出售的订单</el-radio-button>
        </el-radio-group>

      </div>
      <div style="flex: 1">
        <el-input placeholder="请输入订单编号" style="width: 200px; margin-right: 5px" v-model="orderNo"
                  clearable></el-input>
        <el-input placeholder="请输入商品名称" style="width: 200px; margin-right: 5px" v-model="goodsName"
                  clearable></el-input>
        <el-select style="width: 200px; margin-right: 5px" clearable v-model="orderStatus">
          <el-option value="待支付"></el-option>
          <el-option value="待发货"></el-option>
          <el-option value="待收货"></el-option>
          <el-option value="已完成"></el-option>
          <el-option value="已取消"></el-option>
        </el-select>
        <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
        <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
      </div>
    </div>

    <div style="margin: 10px">
      <el-table :data="tableData" strip>
        <el-table-column prop="orderNo" label="订单编号"></el-table-column>
        <el-table-column prop="goodsName" label="商品名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="goodsImg" label="商品图片">
          <template v-slot="scope">
            <el-image v-if="scope.row.goodsImg" style="width: 50px" :src="scope.row.goodsImg"
                      :preview-src-list="[scope.row.goodsImg]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="total" label="总价">
          <template v-slot="scope">
            <span style="color: red">￥{{ scope.row.total }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="下单时间"></el-table-column>
        <el-table-column prop="payNo" label="支付单号"></el-table-column>
        <el-table-column prop="payTime" label="支付时间"></el-table-column>
        <el-table-column prop="userName" label="收货人名称"></el-table-column>
        <el-table-column prop="address" label="收货地址"></el-table-column>
        <el-table-column prop="phone" label="联系方式"></el-table-column>
        <el-table-column prop="status" label="订单状态">
          <template v-slot="scope">
            <el-tag type="danger" v-if="scope.row.status === '已取消'">已取消</el-tag>
            <el-tag type="warning" v-if="scope.row.status === '待支付'">待支付</el-tag>
            <el-tag type="primary" v-if="scope.row.status === '待发货'">待发货</el-tag>
            <el-tag type="info" v-if="scope.row.status === '待收货'">待收货</el-tag>
            <el-tag type="success" v-if="scope.row.status === '已完成'">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="saleName" label="卖家名称"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button v-if="scope.row.status === '待支付'" size="mini" type="primary" plain
                       @click="pay(scope.row.orderNo)">支付
            </el-button>
            <el-button v-if="scope.row.status === '待发货'" size="mini" type="primary" plain
                       @click="changeStatus(scope.row, '待收货')">发货
            </el-button>
            <el-button v-if="scope.row.status === '待收货'" size="mini" type="primary" plain
                       @click="changeStatus(scope.row, '已完成')">收货
            </el-button>
            <el-button v-if="scope.row.status === '待支付'" size="mini" type="danger" plain
                       @click="changeStatus(scope.row, '已取消')">取消
            </el-button>
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

    <el-dialog title="订单信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="商品名称"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="goodsImg">
          <el-input v-model="form.goodsImg" placeholder="商品图片"></el-input>
        </el-form-item>
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="订单编号"></el-input>
        </el-form-item>
        <el-form-item label="总价" prop="total">
          <el-input v-model="form.total" placeholder="总价"></el-input>
        </el-form-item>
        <el-form-item label="下单时间" prop="time">
          <el-input v-model="form.time" placeholder="下单时间"></el-input>
        </el-form-item>
        <el-form-item label="支付单号" prop="payNo">
          <el-input v-model="form.payNo" placeholder="支付单号"></el-input>
        </el-form-item>
        <el-form-item label="支付时间" prop="payTime">
          <el-input v-model="form.payTime" placeholder="支付时间"></el-input>
        </el-form-item>
        <el-form-item label="下单人ID" prop="userId">
          <el-input v-model="form.userId" placeholder="下单人ID"></el-input>
        </el-form-item>
        <el-form-item label="收货地址" prop="address">
          <el-input v-model="form.address" placeholder="收货地址"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone" placeholder="联系方式"></el-input>
        </el-form-item>
        <el-form-item label="收货人名称" prop="userName">
          <el-input v-model="form.userName" placeholder="收货人名称"></el-input>
        </el-form-item>
        <el-form-item label="订单状态" prop="status">
          <el-input v-model="form.status" placeholder="订单状态"></el-input>
        </el-form-item>
        <el-form-item label="卖家ID" prop="saleId">
          <el-input v-model="form.saleId" placeholder="卖家ID"></el-input>
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
  name: "Orders",
  data() {
    return {
      role: 'buyer',
      orderNo: '',
      goodsName: '',
      orderStatus: '',
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {},
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    changeStatus(row, status) {
      this.$confirm('您确认操作吗？', '确认操作', {type: "warning"}).then(response => {
        this.form = JSON.parse(JSON.stringify(row))
        this.form.status = status
        this.$request.put('/orders/update', this.form).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(e => {})
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/orders/update' : '/orders/add',
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
        this.$request.delete('/orders/delete/' + id).then(res => {
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
        this.$request.delete('/orders/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.post('/orders/selectPage', {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        orderNo: this.orderNo,
        goodsName: this.goodsName,
        orderStatus: this.orderStatus
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
      this.orderNo = null
      this.goodsName = null
      this.orderStatus = null
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