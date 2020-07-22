<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;"/>
    <!--
    ref: 相当于id
    data: 数据 (通过接口返回数据)
    filter-node-method: 检索功能
    -->
    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
  import subject from "@/api/edu/subject";

  export default {
    name: "list",
    data() {
      return {
        filterText: '',
        //表示返回的所有的分类数据
        data2: [],
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val)
      }
    },
    created() {
      this.getAllSubject();
    },
    methods: {
      getAllSubject() {
        //调用api接口
        return subject.listSubject()
          .then(response => {
            //将list的值赋值给data2
            this.data2 = response.data.list;
          })
      },
      filterNode(value, data) {
        if (!value) return true
        //将值变为小写比较
        return data.title.toLowerCase().indexOf(value) !== -1
      }
    }
  }
</script>

