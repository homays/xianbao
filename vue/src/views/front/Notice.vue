<template>
  <div style="width: 50%; margin: 10px auto">
    <div style="border-left: 4px solid #2656b5; margin-bottom: 20px; padding-left: 20px; font-size: 24px">系统公告</div>
    <div class="card">
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item :name="index" v-for="(item, index) in noticeList" :key="item.id">
          <template slot="title">
            <strong style="font-size: 18px">{{ item.title }}</strong>  <span style="margin-left: 20px; font-size: 13px">{{ item.time }}</span>
          </template>
          <div v-html="item.content"></div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "Notice",
  data() {
    return {
      activeName: 0,
      noticeList: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.$request.get('/notice/selectAll').then(res => {
        this.noticeList = res.data || []
      })
    }
  }
}
</script>

<style scoped>

</style>