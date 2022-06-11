<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="日期" prop="date">
        <el-date-picker clearable
          v-model="queryParams.date"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="类型: 1工作日, 2节假日, 3加班, 4调休" prop="calendartype">
        <el-select v-model="queryParams.calendartype" placeholder="请选择类型: 1工作日, 2节假日, 3加班, 4调休" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更新时间" prop="updatetime">
        <el-date-picker clearable
          v-model="queryParams.updatetime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

  <!--添加日历控件-->
  <el-calendar >
    <template  slot="dateCell" slot-scope="{date, data}">
      <div  @click="changeDayType(data)" class="calendarCell" style="width: 100%; height: 100%; margin-top: -20px;">
        <p v-if="data.type == 'current-month'"> {{ data.day.split('-').slice(1).join('-') }} </p>
        
        <!--solution 1, no work-->
        <!-- <p v-if="monthValues[parseInt(data.day.substr(-2))] >0">{{ dayEnum[monthValues[parseInt(data.day.substr(-2))]] }}</p> -->

        <!--solution 2, work-->
        <div v-for="item in specialDays">
          <p v-if="item.day == data.day.split('-').slice(1).join('-')">{{dayEnum[item.type]}}</p>
        </div>
      </div>
    </template>
  </el-calendar>



    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:calendar:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:calendar:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:calendar:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:calendar:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="calendarList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="日期" align="center" prop="date" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="类型: 1工作日, 2节假日, 3加班, 4调休" align="center" prop="calendartype">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.calendartype"/>
        </template>
      </el-table-column>
      <el-table-column label="是否删除" align="center" prop="isdelete" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:calendar:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:calendar:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改日历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日期" prop="date">
          <el-date-picker clearable
            v-model="form.date"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="类型: 1工作日, 2节假日, 3加班, 4调休" prop="calendartype">
          <el-select v-model="form.calendartype" placeholder="请选择类型: 1工作日, 2节假日, 3加班, 4调休">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否删除" prop="isdelete">
          <el-input v-model="form.isdelete" placeholder="请输入是否删除" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCalendar, getCalendar, delCalendar, addCalendar, updateCalendar } from "@/api/system/calendar";

export default {
  name: "Calendar",
  dicts: ['sys_user_sex'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 日历表格数据
      calendarList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        date: null,
        calendartype: null,
        updatetime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        date: [
          { required: true, message: "日期不能为空", trigger: "blur" }
        ],
        calendartype: [
          { required: true, message: "类型: 1工作日, 2节假日, 3加班, 4调休不能为空", trigger: "change" }
        ],
        createtime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      },
      dayEnum:["","工作日","放假"],
      //calendar 控件值      
      monthValues: ['0','1','2','0','1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'],
      specialDays:[
        {"day":"06-01","type":1},
        {"day":"06-02","type":1},
        {"day":"06-03","type":2}
      ]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //调整日期类型
    changeDayType(data){
      //console.log(data);
      // //solution 1
      // let day = parseInt(data.day.substr(-2));
      // let t = this.monthValues[day];
      // // console.log(this.monthValues[day]);
      // this.monthValues[day] = (t+1)%3;
      // // console.log(this.monthValues[day]);
      
      // solution 2
      let md = data.day.split('-').slice(1).join('-');
      //console.log(md)

      let index = -1;
      for(let i = 0; i<this.specialDays.length;i++){
        if(this.specialDays[i].day == md)
        {
          index = i;
          let dtype = this.specialDays[i].type;
          console.log(dtype);
          this.specialDays[i].type = (dtype+1)%3;
          console.log(this.specialDays[i].type);
          break;
        }
      }

      //新增
      if(index== -1){
        let newday =  {"day":md,"type":1};
        this.specialDays.push(newday);
      }
      
    },

    /** 查询日历列表 */
    getList() {
      this.loading = true;
      listCalendar(this.queryParams).then(response => {
        this.calendarList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        date: null,
        calendartype: null,
        isdelete: null,
        createtime: null,
        updatetime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加日历";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCalendar(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改日历";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCalendar(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCalendar(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除日历编号为"' + ids + '"的数据项？').then(function() {
        return delCalendar(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/calendar/export', {
        ...this.queryParams
      }, `calendar_${new Date().getTime()}.xlsx`)
    }
  }
};


</script>
