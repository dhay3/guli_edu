<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-button type="text" @click="dialogChapterFormVisible =true">添加章节</el-button>
    <ul class="chapterList">
      <li
        v-for="chapter in chapterList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}

          <span class="acts">
<!--            将章节id回传-->
                <el-button type="text" @click="openSaveVideoEdit(chapter.id)">添加小节</el-button>
            <!--            将章节id传到后端api-->
                <el-button style="" type="text" @click="openChapterEdit(chapter.id)">编辑</el-button>
                <el-button type="text" @click="deleteChapterById(chapter.id)">删除</el-button>
            </span>
        </p>

        <!-- 视频 -->
        <ul class="chapterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>
              {{ video.title }}

              <span class="acts">
                    <el-button type="text" @click="openUpdateVideoEdit(video.id)">编辑</el-button>
                    <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改小节 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加小节">
      <el-form :model="video" label-width="120px">
        <el-form-item label="小节标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="小节排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <!--        on-remove上传取消后调用方法
                    limit 允许上传的数量
        -->
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import chapter from "@/api/edu/chapter";
  import video from "@/api/edu/video";

  export default {
    name: 'chapter',
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        //用于接收上一步路由过来的id值
        courseId: '',
        //接收后端返回的数据
        chapterList: [],
        //用于封装章节数据
        chapter: {
          title: '',
          sort: 0,
          //先初始化data,然后调用created方法,所以不能这样调用
          // courseId: this.courseId
          courseId: ''
        },
        video: {
          title: '',
          sort: 0,
          free: 0,
          videoSourceId: '',
          videoOriginalName: ''
        },
        //是否显示章节弹框
        dialogChapterFormVisible: false,
        //是否显示小节弹框
        dialogVideoFormVisible: false,
        BASE_API: "http://localhost:9001",
        fileList: []
      }
    },

    created() {
      //在渲染之后调用
      //js中只要是没值的都是false(包括NaN,undefined,0),反之true
      if (this.$route.params && this.$route.params.id) {
        //根据路由获取id参数
        this.courseId = this.$route.params.id;
        this.getChapterList()
      }
    },

    methods: {
      /*============================视频操作===================================*/
      //成功回调
      handleVodUploadSuccess(response, file, fileList) {
        //调用后端api将api放回的videoId赋值给video对象用于更新
        this.video.videoSourceId = response.data.videoId
        //将参数的filename传入到后端
        this.video.videoOriginalName = file.name
      },
      //视图上传多于一个视频
      handleUploadExceed(files, fileList) {
        this.$message.warning('想要重新上传视频，请先删除已上传的视频')
      },
      beforeVodRemove(file, fileList) {
        return this.$confirm(`是否删除${file.name}?`)
      },
      handleVodRemove() {
        return video.deleteVoDByVideoId(this.video.videoSourceId)
          .then(() => {
            this.$message({
              type: 'success',
              message: '删除视频成功'
            })
            this.filList = {}
            this.video.videoSourceId = ''
            this.video.videoOriginalName= ''
          })
      },
      /*============================小节操作===================================*/
      saveOrUpdateVideo() {
        if (this.video.id) {
          this.updateVideo()
        } else {
          this.saveVideo()
        }
      },
      saveVideo() {
        return video.saveVideo(this.video)
          .then(response => {
            //关闭弹框, 提示信息, 刷新页面
            this.dialogVideoFormVisible = false;
            this.$message({
              type: 'success',
              message: '添加小节成功'
            })
            this.getChapterList()
            this.video.title = ''
            this.video.free = 0
            this.video.sort = 0
          })
      },
      updateVideo() {
        return video.updateVideo(this.video)
          .then(response => {
            //关闭弹框, 提示信息, 刷新页面
            this.dialogVideoFormVisible = false;
            this.$message({
              type: 'success',
              message: '修改小节成功'
            })
            this.getChapterList();
            this.video.title = ''
            this.video.free = 0
            this.video.sort = 0
          })
      },
      //删除小节
      deleteVideo(videoId) {
        //删除之前确认
        this.$confirm('是否删除小节?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => video.deleteVideoById(videoId)
          .then(() => {
            this.$message({
              type: 'success',
              message: '删除小节成功'
            })
            this.getChapterList()
            this.filList = {}
            this.video.title = ''
            this.video.free = 0
            this.video.sort = 0
            //需要将id清空否则会在saveOrUpdate调用时出错
            this.video.id = ''
          }))
      },
      //章节添加小节页面
      openSaveVideoEdit(chapterId) {
        this.dialogVideoFormVisible = true
        //设置课程id和小节id
        this.video.courseId = this.courseId;
        this.video.chapterId = chapterId
      },
      //编辑小节页面
      openUpdateVideoEdit(videoId) {
        //弹出弹框
        this.dialogVideoFormVisible = true;
        //更具videoId回显数据
        return video.getVideoById(videoId)
          .then(response => {
            this.video = response.data.video
          })
      },
      /*============================章节操作===================================*/
      //添加和修改章节
      saveOrUpdate() {
        //如果this.chapter.id不为null调用updateChapter
        //由于edit方法调用api查询将chapter返回带有id所以通过id来判断
        if (!this.chapter.id) {
          this.saveChapter()
        } else {
          this.updateChapter()
        }
      },
      //添加章节
      saveChapter() {
        this.chapter.courseId = this.courseId;
        return chapter.saveChapter(this.chapter)
          .then(response => {
            //关闭弹框, 提示信息, 刷新页面
            this.dialogChapterFormVisible = false;
            this.$message({
              type: 'success',
              message: '添加章节成功'
            })
            //重新展示列表内容
            this.getChapterList()
            //由于是双向绑定所以需要将chapter清空
            this.chapter.title = ''
          })
      },
      updateChapter() {
        return chapter.updateChapter(this.chapter)
          .then(response => {
            //关闭弹框, 提示信息, 刷新页面
            this.dialogChapterFormVisible = false;
            this.$message({
              type: 'success',
              message: '添加章节成功'
            })
            //重新展示列表内容
            this.getChapterList()
            this.chapter.title = ''
          })
      },
      //会获取到info中传过来的id值
      getChapterList() {
        return chapter.listSubject(this.courseId)
          .then(response => {
            this.chapterList = response.data.chapters
          })
      },
      //弹出修改窗口, 用于修改信息
      openChapterEdit(id) {
        //弹框
        this.dialogChapterFormVisible = true
        // 数据回显
        chapter.getChapterInfoById(id)
          .then(response => {
            this.chapter = response.data.chapter
          })
      },
      deleteChapterById(id) {
        //删除之前确认
        this.$confirm('是否删除章节?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => chapter.deleteChapterById(id)
          .then(() => {
            this.$message({
              type: 'success',
              message: '删除章节成功'
            })
            this.getChapterList()
            this.filList = {}
            this.chapter.title = ''
            //需要将id清空否则会在saveOrUpdate调用时出错
            this.chapter.id = ''
          }))
      },
      previous() {
        //路由到前一个组件,将id通过路由传到前一个模块
        this.$router.push({path: '/course/info/' + this.courseId})
      },

      next() {
        //路由到后一个组件
        this.$router.push({path: '/course/publish/' + this.courseId})
      }
    }
  }
</script>

<style scoped>
  .chapterList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }

  .chapterList li {
    position: relative;
  }

  .chapterList p {
    /*float: left;*/
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }

  .chapterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
  }

  .videoList p {
    /*float: left;*/
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }

</style>
