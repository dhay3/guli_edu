<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'https://gulied-program.oss-cn-beijing.aliyuncs.com/template.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>
      <!--
      表单提交
      ref: 唯一标识符
      auto-upload: 自动上传
      on-success: 上传成功回调函数
      on-error: 上传失败回调函数
      disable: 按钮是否能被点击二次
      limit: 限制每次上传文件数
      action: 对应后端的api
      name: 相当于input type =file name = file
      accept: 上传文件类型
      -->
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/eduservice/subject'"
          name="file"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{ fileUploadBtnText }}
          </el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "save",
    data() {
      return {
        //直接调用后端api,无需引入js文件
        BASE_API: "http://localhost:9001", // 接口API地址
        fileUploadBtnText: '上传到服务器', // 按钮文字, 可以将值直接写死
        importBtnDisabled: false, // 按钮是否禁用,
        loading: false
      }
    },
    created() {
    },
    methods: {
      //点击按钮上传到后端api
      submitUpload() {
        this.fileUploadBtnText = '正在上传'
        this.importBtnDisabled = true
        this.loading = true
        //相当于原生js中document.getElementById("upload").submit()
        //upload对应表单的ref属性
        this.$refs.upload.submit()
      },
      //上传成功,提示并放回列表
      fileUploadSuccess() {
        this.fileUploadBtnText = '导入成功'
        this.loading = false
        //提示信息
        this.$message({
          type: 'success',
          message: '添加课程成功'
        })
        //重新路由到subject
        this.$router.push({path: '/subject'})
      }
      ,
      //上传失败
      fileUploadError() {
        this.loading = false
        //提示信息
        this.$message({
          type: 'error',
          message: '添加课程失败'
        })
      }
    }
  }
</script>

<style scoped>

</style>
