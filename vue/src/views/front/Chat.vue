<template>
  <div style="width: 60%; margin: 10px auto">

    <div style="display: flex; grid-gap: 20px">

      <!-- 聊天组开始 -->
      <div class="chat-card" style="width: 200px; min-height: 600px">
        <div v-if="chatGroupList.length > 0" @click="loadChatList(item.chatUserId)" v-for="item in chatGroupList" :key="item.id">
          <div style="position: relative" v-if="item.userId === user.id" class="chat-group" :class="{'chat-group-active' : item.chatUserId === activeChatUserId}">
            <img :src="item.chatUserAvatar" style="width: 50px; height: 50px; border-radius: 50%" />
            <el-badge :value="item.chatNum" :max="99" v-if="item.chatNum > 0 && !activeChatUserId">
              <span style="color: #333">{{ item.chatUserName }}</span>
            </el-badge>
            <span v-else style="color: #333">{{ item.chatUserName }}</span>

            <i class="el-icon-close" style="position: absolute; top: 10px; right: 10px" @click.stop="closeGroup(item.id)"></i>
          </div>
        </div>
        <div v-if="chatGroupList.length === 0" style="color: #666; padding: 20px 0; text-align: center">暂无聊天对象..</div>
      </div>
      <!-- 聊天组结束 -->

      <!-- 聊天消息开始 -->
      <div class="chat-card" style="flex: 1; height: 600px">
        <div class="chat-text" style="height: 400px; border-bottom: 1px solid #ddd; overflow-y: auto;">
          <div v-for="item in chatList" :key="item.id" style="margin-bottom: 20px">

            <!-- 左边的聊天消息开始 -->
            <!-- item.chatUserId === user.id 表示对方在跟当前登录的人聊天 user表示对方  显示对方的名称和头像        -->
            <div v-if="item.chatUserId === user.id" style="display: flex; grid-gap: 10px">
              <img style="width: 50px; height: 50px; border-radius: 5px" :src="item.userAvatar" alt="">
              <div style="max-width: 50%; width: fit-content">
                <div style="color: #666; margin-bottom: 5px">{{ item.userName }}</div>
                <div style="background-color: #d1e7ff; padding: 10px; border-radius: 5px;" v-text="item.text"></div>
              </div>
            </div>
            <!-- 左边的聊天消息结束 -->

            <!-- 右边的聊天消息开始 -->
            <div v-if="item.userId === user.id" style="display: flex; justify-content: right; grid-gap: 10px">
              <div style="max-width: 50%; width: fit-content">
                <div style="color: #666; margin-bottom: 5px; text-align: right">{{ item.userName }}</div>
                <div style="background-color: #a3eecb; padding: 10px; border-radius: 5px;" v-text="item.text"></div>
              </div>
              <img style="width: 50px; height: 50px; border-radius: 5px" :src="item.userAvatar" alt="">
            </div>
            <!-- 右边的聊天消息结束 -->

          </div>
          <div style="height: 0"><a id="chat-text-end" name="1" href="#1" style="outline: none">&nbsp</a></div>
        </div>
        <!-- 聊天消息结束 -->

        <!-- 聊天输入框开始 -->
        <div style="height: 200px">
          <textarea v-model="form.text" style="height: 160px; width: 100%; padding: 20px; border: none;
             border-bottom: 1px solid #ddd; outline: none"></textarea>
          <div style="text-align: right; padding-right: 10px">
            <el-button type="primary" size="mini" @click="send">发送</el-button>
          </div>
        </div>
        <!-- 聊天输入框结束 -->

      </div>

    </div>
  </div>
</template>

<script>
let socket

export default {
  name: "Chat",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      chatGroupList: [],
      activeChatUserId: 0,
      form: {},
      chatList: []
    }
  },
  created() {
    this.init()
    this.loadChatGroup()
  },
  methods: {
    closeGroup(id) {
      this.$request.delete('/chatGroup/delete/' + id).then(res => {
        this.loadChatGroup()
        this.chatList = []  // 清空聊天消息
      })
    },
    send() {
      if (!this.activeChatUserId) {
        this.$message.warning('请选择聊天对象')
        return
      }
      if (!this.form.text) {
        this.$message.warning('请输入消息内容')
        return
      }
      this.form.chatUserId = this.activeChatUserId
      this.form.userId = this.user.id
      this.$request.post('/chatInfo/add', this.form).then(res => {
        this.form = {}
        this.loadChatList(this.activeChatUserId)

        // 发送socket消息
        socket.send('聊天消息')
      })
    },
    loadChatList(chatUserId) {
      this.form = {}
      this.activeChatUserId = chatUserId

      // 更新消息读取状态
      this.$request.put('/chatInfo/updateRead/' + chatUserId).then(res => {

        this.loadChatGroup()
      })
      this.$request.get('/chatInfo/selectUserChat/' + chatUserId).then(res => {
        this.chatList = res.data || []
        document.getElementById('chat-text-end').click()
      })
    },
    loadChatGroup() {
      this.$request.get('/chatGroup/selectUserGroup').then(res => {
        this.chatGroupList = res.data || []
      })
    },
    init() {
      let _this = this
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket")
      } else {
        console.log("您的浏览器支持WebSocket")
        let socketUrl = "ws://localhost:9090/chatServer/" + this.user.id
        if (socket != null) {
          socket.close()
          socket = null
        }
        // 开启一个websocket服务
        socket = new WebSocket(socketUrl)
        //打开事件
        socket.onopen = function () {
          console.log("websocket已打开")
        }
        //  浏览器端收消息，获得从服务端数据库里的最新的聊天消息
        socket.onmessage = function (msg) {
          _this.loadChatGroup()
          _this.loadChatList(_this.activeChatUserId)
        }
        //关闭事件
        socket.onclose = function () {
          console.log("websocket已关闭")
        }
        //发生了错误事件
        socket.onerror = function () {
          console.log("websocket发生了错误")
        }
      }
    }
  }
}
</script>

<style scoped>
.chat-card {
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}
.chat-group {
  padding: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  grid-gap: 10px;
  border-bottom: 1px solid #ddd
}
.chat-group-active {
  background-color: #d1e7ff;
}
.chat-text {
  padding: 20px;
}
.chat-text::-webkit-scrollbar {
  width: 10px;
}
.chat-text::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #d1e7ff;
}
/deep/.el-badge__content.is-fixed {
  right: 2px;
}
</style>