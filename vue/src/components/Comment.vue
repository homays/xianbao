<template>
  <div>
    <div style="margin-bottom: 20px; font-size: 22px; font-weight: bold">评论 {{ commentCount }}</div>
    <div style="margin-bottom: 20px">
      <el-input type="textarea" placeholder="请输入评论" v-model="content"></el-input>
      <div style="text-align: right; margin-top: 5px"><el-button type="primary" @click="addComment(null)">评论</el-button></div>
    </div>

    <div>
      <div v-for="item in commentList" :key="item.id" style="margin-bottom: 20px">
        <!-- 一级评论-->
        <div style="display: flex; margin-bottom: 20px">
          <img :src="item.userImg" alt="" style="width: 50px; height: 50px; border-radius: 50%">
          <div style="padding-left: 15px; flex: 1">
            <div style="margin-bottom: 10px; color: #666">{{ item.userName }}</div>
            <div style="margin-bottom: 5px">{{ item.content }}</div>
            <div style="color: #666; font-size: 13px; margin-bottom: 5px">
              <span>{{ item.time }}</span>
              <span @click="handleShowReply(item)" style="margin: 0 20px; cursor: pointer"
                    :class="{'comment-active' : item.showReply}"><i class="el-icon-s-comment"></i>回复</span>
              <span @click="delComment(item.id)" v-if="item.userId === user.id" style="cursor: pointer"><i class="el-icon-delete"></i>删除</span>
            </div>
            <div v-if="item.showReply">
              <el-input type="textarea" placeholder="请输入回复" v-model="item.replyContent"></el-input>
              <div style="text-align: right; margin-top: 5px"><el-button type="primary" @click="addComment(item)">回复</el-button></div>
            </div>
          </div>
        </div>
        <!-- 二级评论-->
        <div v-if="item.chrildrenList" style="padding-left: 65px">
          <div v-for="sub in item.chrildrenList" style="display: flex; margin-bottom: 20px">
            <img :src="sub.userImg" alt="" style="width: 50px; height: 50px; border-radius: 50%">
            <div style="padding-left: 15px; flex: 1">
              <div style="margin-bottom: 10px; color: #666">{{ sub.userName }} <span>回复：{{ sub.parentName }}</span></div>
              <div style="margin-bottom: 5px">{{ sub.content }}</div>
              <div style="color: #666; font-size: 13px; margin-bottom: 5px">
                <span>{{ sub.time }}</span>
                <span @click="handleShowReply(sub)" style="margin: 0 20px; cursor: pointer"
                      :class="{'comment-active' : sub.showReply}"><i class="el-icon-s-comment"></i>回复</span>
                <span @click="delComment(sub.id)" v-if="sub.userId === user.id" style="cursor: pointer"><i class="el-icon-delete"></i>删除</span>
              </div>
              <div v-if="sub.showReply">
                <el-input type="textarea" placeholder="请输入回复" v-model="sub.replyContent"></el-input>
                <div style="text-align: right; margin-top: 5px"><el-button type="primary" @click="addComment(sub)">回复</el-button></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CommentComponent",
  props: {
    fid: null,
    module: ''
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      commentCount: 0,
      content: '',
      commentList: []
    }
  },
  created() {
    this.loadComment()
  },
  methods: {
    handleShowReply(comment) {
      this.$set(comment, 'showReply', !comment.showReply)
    },
    delComment(commentId) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/comment/delete/' + commentId).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.loadComment()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    loadComment() {
      this.$request.get('/comment/selectTree/' + this.fid + '/' + this.module).then(res => {
        this.commentList = res.data || []
      })

      this.$request.get('/comment/selectCount/' + this.fid + '/' + this.module).then(res => {
        this.commentCount = res.data || 0
      })
    },
    addComment(comment) {  //  comment 有值表示这是回复
      let data = { fid: this.fid, userId: this.user.id, module: this.module, content: this.content }
      if (comment) {
        data.content = comment.replyContent
        data.pid = comment.id
      }
      this.$request.post('/comment/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.content = ''
          this.loadComment()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
  }
}
</script>

<style scoped>

</style>