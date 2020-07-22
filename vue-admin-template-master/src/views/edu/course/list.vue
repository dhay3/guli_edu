<template>
  <!--  参考其他vue组件-->
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="courseQuery.teacherId"
          placeholder="课程讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="courseQuery.beginPrice" clearable placeholder="最低价格">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="courseQuery.endPrice" clearable placeholder="最高价格">
        </el-input>
      </el-form-item>
      <el-form-item>


        <el-select
          v-model="courseQuery.subjectParentId"
          placeholder="一级分类"
          @change="getNestedSubject">

          <el-option
            v-for="subject in subjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>

        <el-select
          v-model="courseQuery.subjectId"
          placeholder="二级分类">

          <el-option
            v-for="subject in NestedSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>

        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option :value="'Normal'" label="已发布"/>
          <el-option :value="'Draft'" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()" style="">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!--    data vue单向绑定v-bind-->
    <!-- 表格 -->
    <!--    这里绑定list-->
    <el-table
      :data="list"
      border
      fit
      highlight-current-list>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (current - 1) * size + scope.$index + 1 }}
        </template>
      </el-table-column>
      <!--      显示的具体内容,对应后端api返回的vo-->
      <el-table-column prop="title" label="课程名称" width="200"/>
      <el-table-column prop="price" label="价格" width="100"/>
      <el-table-column prop="lessonNum" label="课程时间" width="80"/>
      <el-table-column prop="viewCount" label="浏览数" width="80"/>
      <el-table-column prop="buyCount" label="购买数" width="80"/>
      <el-table-column prop="teacherName" label="讲师" width="100"/>
      <el-table-column prop="subjectLevelTwo" label="二级分类" width="100"/>
      <el-table-column label="状态" width="100">
        <!--        插槽-->
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="创建时间" width="160"/>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <!--          路由到/info/:id-->
          <router-link :to="'info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <!--          scope.row.id根据id值删除讲师-->
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table
    >

    <!-- 分页 -->
    <el-pagination
      background
      :current-page="current"
      :page-size="size"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
    <!--    getList封装了查询只需要修改方法参数即可-->
    <!--    <el-button type="text" @click="open">点击打开 Message Box</el-button>-->
  </div>
</template>

<script>
  //引入teacher.js文件

  import course from "@/api/edu/course";
  import subject from "@/api/edu/subject";

  /**
   * 显示讲师列表
   * 核心代码部分
   */
  export default {
    name: "list",
    data() {
      //使用return包裹的数据只在当前组件中有效,不会影响其他组件
      //如果不用return包裹数据会在项目中全局可见,会造成变量污染
      return {
        //这里的参数要与api接口中定义的参数相同
        list: null,//查询后返回的结果
        current: 1, //显示第几页
        size: 5, //每页显示的行数
        total: 0, //总记录数

        //可以不用定义参数, 当使用courseQuery.想要添加的属性名 时, 就会将该属性添加到courseQuery对象中
        courseQuery: {
          subjectId: null
        },//封装的条件对象
        teacherList: [],
        subjectList: [],
        NestedSubjectList: []
      }
    },
    //在页面渲染之前执行
    created() {
      //调用方法
      this.getList()
      this.getTeacherList();
      this.getSubjectList();
    },
    methods: {
      //current初始值为1,如果不传current就是1 ,如果current传2就是2
      getList(current = 1) {
        this.current = current;
        //调用引入的js方法,初始courseQuery为空,查询所有
        return course.pageCourseOnCondition(this.current, this.size, this.courseQuery)
          //axios请求成功调用该方法
          //response是接口返回的数据
          .then(response => {
            // console.log(response);
            //这里获取的是后端返回的json对象的key
            this.list = response.data.info.courses
            this.total = response.data.info.total
            // console.log(response)
          })
      },
      getSubjectList() {
        return subject.listSubject()
          .then(response => {
            this.subjectList = response.data.list
          })
      },
      getNestedSubject(value) {
        for (let i = 0; i < this.subjectList.length; i++) {
          //如果当前选中的id与后端返回的id相同
          if (this.subjectList[i].id === value) {
            //从一级分类中取到二级分类并赋值
            this.NestedSubjectList = this.subjectList[i].children
            //重新选择一级分类时没有清空上一次二级分类id会造成二级分类显示错误
            this.courseQuery.subjectId = null
            //第一次调用时就会给与courseQuery, subjectId和类型
            //
          }
        }
      },
      //查询所有讲师, 用于下拉列表显示
      getTeacherList() {
        return course.getTeacherList()
          .then(response => {
            this.teacherList = response.data.teachers;
          })
      },
      removeDataById(id) {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(
          () => {
            course.removeCourseInfoByCourseId(id)
              .then(() => {
                this.getList()
              })
          }
        )


      },
      //清空查询条件
      resetData() {
        //表单输入项的数据清空, 因为是双向绑定将courseQuery清空即可
        this.courseQuery = {} //这里不能使用null
        //查询所有讲师
        this.getList()
      },
    }
  }
</script>
<style scoped>

</style>
