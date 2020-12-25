<template>
  <el-form
    :model="ruleForm"
    status-icon
    ref="ruleForm"
    label-width="100px"
    class="demo-ruleForm"
  >
    <el-form-item label="用户名" prop="userName">
      <el-input
        type="text"
        v-model="ruleForm.userName"
        autocomplete="off"
      ></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input
        type="password"
        v-model="ruleForm.password"
        autocomplete="off"
      ></el-input>
    </el-form-item>
    <el-form-item label="角色" prop="role">
      <el-input v-model.number="ruleForm.role"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
 
<script>
import axios from "axios";

export default {
  data() {
    return {
      ruleForm: {
        userName: "",
        password: "",
        role: "",
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios({
            method: "post",
            url: `http://localhost:8080/auth/registry`,
            data: this.ruleForm,
          }).then((res) => {
            if (res.status === "200") {
              alert("注册成功！");
              this.$router.push({ path: "/login" });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
<style scoped>
.demo-ruleForm {
  width: 500px;
  margin: 0px auto;
  text-align: center;
}
</style>