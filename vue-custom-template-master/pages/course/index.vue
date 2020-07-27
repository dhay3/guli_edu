<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="searchAll" :class="{active:oneIndex === -1}">全部</a>
                </li>
                <li v-for="(subject,index) in subjectList" :key="index" :class="{active:oneIndex === index}">
                  <a :title="subject.title" href="#" @click="searchOne(subject.id,index)">{{subject.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(subject,index) in subSubjectNestedList" :key="index" :class="{active:twoIndex === index}">
                  <a :title="subject.title" href="#" @click="searchTwo(subject.id,index)">{{subject.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量
                  <span :class="{hide:buyCountSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
                  <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="data.total > 0">
            <ul class="of" id="bna">
              <!--              遍历课程-->
              <li v-for="course in data.records" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" :alt="course.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" v-if="course.price !== 0">
                      <i class="c-fff fsize12 f-fA">￥{{course.price}}</i>
                    </span>
                    <span class="fr jgTag bg-green" v-if="course.price === 0">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{course.buyCount}}人学习</i>
                      |
                      <i class="c-999 f-fA">{{course.viewCount}}浏览</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首</a>
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current-1)">&lt;</a>
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current+1)">&gt;</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
  import course from "../../api/course";

  export default {
    data() {
      return {
        page: 1,//当前页
        data: {},//返回的map
        subjectList: [], // 一级分类列表
        subSubjectNestedList: [], // 二级分类列表
        searchObj: {}, // 查询表单对象
        oneIndex: -1,
        twoIndex: -1,
        buyCountSort: "",
        gmtCreateSort: "",
        priceSort: ""
      }
    },
    created() {
      this.initCourseIndex()
      this.listSubject()
    },
    methods: {
      //查询第一页数据
      initCourseIndex() {
        return course.getFrontCoursesByCondition(1, 8, this.searchObj)
          .then(response => {
            //data是返回的map
            this.data = response.data.data;
            console.log("data", this.data)
          })
      },
      listSubject() {
        return course.listSubject()
          .then(response => {
            this.subjectList = response.data.data.list
          })
      },
      //分页切换
      gotoPage(cur) {
        return course.getFrontCoursesByCondition(cur, 8, this.searchObj)
          .then(response => {
            this.data = response.data.data
          })
      },
      //点击一级分类,查询二级分类
      searchOne(SubjectParentId, index) {
        //将传递过来的index等于OneIndex,为了使样式生效
        this.oneIndex = index;
        this.twoIndex = -1;
        this.subSubjectNestedList = []
        this.searchObj.subjectId = ''
        //点击时查询数据库
        this.searchObj.subjectParentId = SubjectParentId;
        this.gotoPage(1)
        //显示
        for (let i = 0; i < this.subjectList.length; i++) {
          //如果点击的一级id与查询出来的相同
          if (SubjectParentId === this.subjectList[i].id) {
            this.subSubjectNestedList = this.subjectList[i].children;
          }
        }
      },
      //点击二级分类查询
      searchTwo(SubjectId, index) {
        this.twoIndex = index
        this.searchObj.subjectId = SubjectId
        this.gotoPage(1)
      },
      searchAll() {
        this.oneIndex = -1
        //清空查询条件
        this.searchObj = {}
        this.gotoPage(1)
      },
      //按照购买数排序
      searchBuyCount() {
        //设置一个值,让样式生效
        this.buyCountSort = '1'
        this.gmtCreateSort = ''
        this.priceSort = ''
        this.searchObj.buyCountSort = this.buyCountSort
        this.searchObj.gmtCreateSort = this.gmtCreateSort
        this.searchObj.priceSort = this.priceSort
        this.gotoPage(1)
      },
      searchGmtCreate() {
        //设置一个值,让样式生效
        this.buyCountSort = ''
        this.gmtCreateSort = '1'
        this.priceSort = ''
        this.searchObj.buyCountSort = this.buyCountSort
        this.searchObj.gmtCreateSort = this.gmtCreateSort
        this.searchObj.priceSort = this.priceSort
        this.gotoPage(1)
      },
      searchPrice() {
//设置一个值,让样式生效
        this.buyCountSort = ''
        this.gmtCreateSort = ''
        this.priceSort = '1'
        this.searchObj.buyCountSort = this.buyCountSort
        this.searchObj.gmtCreateSort = this.gmtCreateSort
        this.searchObj.priceSort = this.priceSort
        this.gotoPage(1)
      }
    }
  };
</script>
<style scoped>
  .active {
    background: #bdbdbd;
  }

  .hide {
    display: none;
  }

  .show {
    display: block;
  }
</style>

