<template>
  <div class="app-container">
    添加讲师
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="首席讲师"/>
          <el-option :value="2" label="高级讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!--        image属性直接显示头像-->
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
    v-show：是否显示上传组件
    :key：类似于id，如果一个页面多个图片上传控件，可以做区分
    :url：后台上传的url地址, ajax方式上传文件,将头像上传到阿里云
    filed就是对应标签的name值, 对应后端multipart 的形参名
    @close：关闭上传组件
    @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>

      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  /**
   * 添加讲师列表
   * 修改讲师列表
   */
  import teacher from "@/api/edu/teacher";
  //引入头像上传组件
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'

  export default {
    name: "save",
    data() {
      return {
        //用于封装添加讲师的值
        teacher: {},

        //默认为false当点击后修改为true, 为了防止重复提交
        saveBtnDisabled: false,
        imagecropperShow: false, //是否弹框
        imagecropperKey: 0, //唯一标识符
        BASE_API: "http://localhost:9001"
      }
    },
    //生命上传组件
    components: {ImageCropper, PanThumb},
    //如果多次路由跳转到同一个页面, created方法只会执行一次
    created() {
      this.init()
    },
    watch: {//监听
      //固定写法
      $route(to, from) {//路由发生改变,该方法就会执行
        this.init()
      }
    },
    methods: {
      init() {
        //注意区分router和route, router是所有的route对象的集合,
        //在页面渲染之前判断是否有id, 需要回显数据
        //如果path参数中带有id就需要调用getInfoById将通过api查询出来的结果绑定给teacher
        //通过路由携带参数
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
          this.getInfoById(id)
        } else {
          //如果没有id,就清空teacher对象
          this.teacher = {
            //添加初始化头像
            avatar: 'https://gulied-program.oss-cn-beijing.aliyuncs.com/default-tea-img.gif'
          };
        }
      },
      //修改和添加为同一个表单
      saveOrUpdate() {
        //将按钮提交设置为false
        this.saveBtnDisabled = true
        //如果teacher有id就调用updateTeacher, 如果没有就save
        if (this.teacher.id) {
          this.updateTeacher()
        } else {
          this.saveTeacher();
        }
      },
      //根据id获取讲师信息
      getInfoById(id) {
        return teacher.getTeacherInfo(id)
          .then(response => {
            //将查询出来的结果给teacher,teacher双向绑定
            this.teacher = response.data.teacher
          })
      },
      //添加讲师
      saveTeacher() {
        return teacher.saveTeacher(this.teacher)
          .then(response => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            });
          })
          .then(() => {
            //讲师添加成功后路由重定向到/teacher/list
            this.$router.push({path: '/teacher'})
          })
      },
      //更新讲师
      updateTeacher() {
        return teacher.updateTeacherInfo(this.teacher)
          .then(response => {
            //提示信息
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            //修改成功后路由回讲师列表
            this.$router.push({path: '/teacher'})
          })

      },
      //关闭上传弹窗方法
      close() {
        this.imagecropperShow = false;
        //上传组件初始化
        this.imagecropperKey++;
      },
      //上传成功调用方法
      cropSuccess(data) {
        //调用后端api上传成功后返回结果的url,给teacher avatar
        this.teacher.avatar = data.url
        //上传陈成功后关闭弹窗
        this.imagecropperShow = false;
        this.imagecropperKey++;
      }
    }
  }
</script>

<style scoped>

</style>
