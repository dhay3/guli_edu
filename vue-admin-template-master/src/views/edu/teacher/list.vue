<template>
  <!--  参考其他vue组件-->
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="首席讲师"/>
          <el-option :value="2" label="高级讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <!--        时间选择框-->
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!--    data vue单向绑定v-bind-->
    <!-- 表格 -->
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

      <el-table-column prop="name" label="名称" width="80"/>

      <el-table-column label="头衔" width="80">
        <!--        插槽-->
        <template slot-scope="scope">
          {{ scope.row.level===1?'首席讲师':'高级讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历"/>

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60"/>

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!--          路由到/teacher/save/:id-->
          <router-link :to="'/teacher/save/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <!--          scope.row.id根据id值删除讲师-->
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

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
  import teacher from "@/api/edu/teacher";

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

        //可以不用定义参数, 当使用teacherQuery.想要添加的属性名 时, 就会将该属性添加到teacherQuery对象中
        teacherQuery: {}//封装的条件对象
      }
    },
    //在页面渲染之前执行
    created() {
      //调用方法
      this.getList()
    },
    methods: {
      //current初始值为1,如果不传current就是1 ,如果current传2就是2
      getList(current = 1) {
        this.current = current;
        //调用引入的js方法
        return teacher.getTeacherListPage(this.current, this.size, this.teacherQuery)
          //axios请求成功调用该方法
          //response是接口返回的数据
          .then(response => {
            // console.log(response);
            //这里获取的是后端返回的json对象的key
            this.list = response.data.teachers
            this.total = response.data.total
            // console.log(response)
          })
          //axios请求失败调用该方法
          .catch(error => {
            console.log(error)
          })
      },
      //清空查询条件
      resetData() {
        //表单输入项的数据清空, 因为是双向绑定将teacherQuery清空即可
        this.teacherQuery = {} //这里不能使用null
        //查询所有讲师
        this.getList()
      },

      //删除讲师
      removeDataById(id) {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            teacher.deleteTeacherById(id)
              //then()方法只有在前一个方法执行完成后才能执行, 相当于一个阻塞队列
              .then(() => {
                //删除成功提示
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                this.getList();
              })
            //vue-template中封装了catch方法可以不用声明
          })

      }
    }
  }
</script>
<style scoped>

</style>
