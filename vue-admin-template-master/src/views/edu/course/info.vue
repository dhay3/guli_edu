<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <!--    表单-->
    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <!--        先显示一级分类,不显示二级分类, 选择了一级分类才显示二级分类-->
        <!--        subjectParentId是一级分类的id,subjectId是二级分类的id-->
        <!--        一级分类绑定事件, 改变value时,显示对应的二级分类-->
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类"
          @change="getNestedSubject">

          <el-option
            v-for="subject in subjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>

        <el-select
          v-model="courseInfo.subjectId"
          placeholder="二级分类">

          <el-option
            v-for="subject in NestedSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>
      <!-- 课程讲师 TODO -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">

          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介 TODO 使用富文本编辑器 -->
      <el-form-item label="课程简介">
        <tinymce :heigth="300" v-model="courseInfo.description" placeholder=""/>
      </el-form-item>
      <!-- 课程封面 TODO -->
      <el-form-item label="课程封面">
        <!--        通过action发送请求, 这里使用img直接显示-->
        <el-upload
          :show-file-list="false"
          :on-success="coverUploadSuccess"
          :before-upload="beforeCoverUpload"
          :action="BASE_API+'/eduoss/fileoss'"
          class="avatar-uploader">
          <img width="200px" height="200px" :src="courseInfo.cover">
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/>
        元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>


</template>
<script>
  import course from "@/api/edu/course";
  import subject from "@/api/edu/subject";
  import Tinymce from '@/components/Tinymce'

  export default {
    name: 'info',
    components: {Tinymce},
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        //定义一个对象用于双向绑定对象
        courseInfo: {
          title: '',
          subjectId: '', //二级分类id
          subjectParentId: '', //一级分类id
          teacherId: '',
          lessonNum: 0,
          description: '',
          //设置默认封面
          cover: 'https://gulied-program.oss-cn-beijing.aliyuncs.com/v-play-bg.jpg',
          price: 0
        },
        teacherList: [],
        //课程一级分类
        subjectList: [],
        //课程二级分类
        NestedSubjectList: [],
        BASE_API: 'http://localhost:9001',
        courseId: ''
      }
    },
    created() {
      //这里虽然路由的路径不同, 但是组件相同
      //如果有id值就回显, 如果没有id值就做添加
      //切记这是定义在router中的形参
      if (this.$route.params && this.$route.params.id) {
        //为courseId赋值
        this.courseId = this.$route.params.id
        //调用回显方法
        this.getCourseInfoById()
      } else {
        //初始化讲师下拉列表
        this.getTeacherList()
        //初始化课程下拉列表
        this.getSubjectList()
      }

      /**
       由于二级菜单是onchange事件绑定的,所以在chapter返回上一级时会直接显示二级id
       但是集合中没有值,就会直接显示id值
       */
    },
    methods: {
      //添加课程
      saveCourse() {
        return course.saveCourse(this.courseInfo)
          .then(response => {
            //提示
            this.$message({
              type: 'success',
              message: '添加课程信息成功'
            })
            //跳转到第二步, 将返回的id传入
            this.$router.push({path: '/course/chapter/' + response.data.courseId})
          })
      },
      //修改课程
      updateCourse() {
        course.updateCourseInfo(this.courseInfo)
          .then(response => {
            //提示
            this.$message({
              type: 'success',
              message: '修改课程信息成功'
            })
            //跳转到第二步, 将返回的id传入
            this.$router.push({path: '/course/chapter/' + this.courseId})
          })
      },
      saveOrUpdate() {
        //根据是否有id值判断是添加还是修改操作
        if (!this.courseId) {
          this.saveCourse()
        } else {
          this.updateCourse();
        }
      },
      //查询所有讲师, 用于下拉列表显示
      getTeacherList() {
        return course.getTeacherList()
          .then(response => {
            this.teacherList = response.data.teachers;
          })
      },
      //查询所有的一级分类, 由于只显示title和id所以可以直接调用现有的接口
      getSubjectList() {
        return subject.listSubject()
          .then(response => {
            this.subjectList = response.data.list
          })
      },
      //点击一级分类除法onChange事件,框架封装, value就是一级分类的id
      getNestedSubject(value) {
        for (let i = 0; i < this.subjectList.length; i++) {
          //如果当前选中的id与后端返回的id相同
          if (this.subjectList[i].id === value) {
            //从一级分类中取到二级分类并赋值
            this.NestedSubjectList = this.subjectList[i].children
            //重新选择一级分类时没有清空二级分类id会造成二级分类显示错误
            this.courseInfo.subjectId = ''
          }
        }
      },
      //上传成功调用方法
      coverUploadSuccess(response, file) {
        this.courseInfo.cover = response.data.url
      },
      //上传封面之前,确定文件格式
      beforeCoverUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2 //2M

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      //根据路由回来的id封装对象
      getCourseInfoById() {
        return course.getCourseInfoById(this.courseId)
          .then(response => {
            this.courseInfo = response.data.courseInfoVo;
            //调用查询所有一级二级分类的接口
            subject.listSubject()
              .then(response => {
                //获取所有一级分类,内嵌二级分类
                this.subjectList = response.data.list
                //把所有的一级分类进行遍历, 比较当前courseInfoVo里面一级分类id和所有的一级分类id
                for (let i = 0; i < this.subjectList.length; i++) {
                  //一级分类
                  let subject = this.subjectList[i];
                  //比较当前courseInfo中的一级分类id和所有的一级分类id
                  if (this.courseInfo.subjectParentId === subject.id) {
                    //获取一级分类中的所有二级分类
                    this.NestedSubjectList = subject.children;
                  }
                }
              })
            this.getTeacherList()
          })
      }
    }
  }
</script>
<style scoped>

</style>
