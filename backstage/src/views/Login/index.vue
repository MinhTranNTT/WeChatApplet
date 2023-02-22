<template>
    <div class="mainWrap">
        <!-- 登录区域 -->
        <el-card class="login-card">
            <div slot="header" class="login-name">
                <span>信游研学管理后台</span>
            </div> 
            <el-form :model="loginForm" :rules="loginRules" ref="loginF" label-width="80px" class="login-form" size="default">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
import { reactive,ref } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

export default {
    setup (){
        // 路由
        const router = useRouter();
        // 登录的表单数据
        const loginForm = reactive({
            username: '',
            password: ''
        })
        // 新增表单的ref
        const loginF = ref(null);
        // 用户名校验规则
        const validateName = (rule,value,callback)=>{
            // 请输入4-10位用户名
            let reg = /^[a-zA-Z0-9_-]{4,10}$/;
            if(value == ''){
                callback(new Error('请输入用户名'));
            }else if(!reg.test(value)){
                callback(new Error('请输入4-10位用户名'));
            }else{
                callback();
            }
        }
        // 密码校验规则
        const validatePass = (rule,value,callback)=>{
            // 6-12位密码需要包含大小写字母和数字以及特殊符号
            let pass = /^\S*(?=\S{6,12})(?=\S*\d)(?=\S*[A-Z])(?=\S*[a-z])(?=\S*[!@#$%^&*?])\S*$/;
            if(value == ''){
                callback(new Error('请输入密码'));
            }else if(!pass.test(value)){
                callback(new Error('6-12位密码需要包含大小写字母和数字以及特殊符号'));
            }else{
                callback();
            }
        }
        // 登录的表单验证规则
        const loginRules = reactive({
                username: [{required: true, validator: validateName, trigger: 'blur'}],
                password: [{required: true, validator: validatePass, trigger: 'blur'}]
        })
        // 登录
        const login = () => {
            // 表单验证
            loginF.value.validate((valid)=>{
                if(!valid){
                    ElMessage.error('请检查表单信息');
                    return;
                }
                // 验证通过,发送请求
                axios.post('/login',loginForm).then(res=>{
                    if(res.data.state==1){
                        ElMessage.error('用户名不存在');
                        return
                    }
                    if(res.data.state==2){
                        ElMessage.error('密码错误');
                        return
                    }
                    ElMessage.success('登录成功');
                    // 将token存储到本地
                    localStorage.setItem('token',res.data.data);   
                    // 将用户信息存储到本地
                    localStorage.setItem('userName',loginForm.username);                 
                    // 跳转到首页
                    router.push({path: '/home'})
                })
                return true;
            });
        }


        return {loginForm, loginRules, loginF,login}
    }
    
}
</script>

<style lang="scss" scoped>
.mainWrap{
    background-image: url(../../../static/backgroundImage.jpg);
    background-attachment: fixed;
    background-repeat: no-repeat;
    background-size: cover;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    .login-card{
        width: 500px;
        height: 300px;
        background-color: rgba($color: #fff, $alpha: 0.6);
        border-radius: 20px;
        .login-name{
            margin-top: 20px;
            text-align: center;
            font-size: 27px;
            font-weight: bold;
            border-bottom: 2px #fff solid;
        }
        .login-form{
            margin-top: 40px;
            width: 400px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            .el-form-item{
                margin-bottom: 20px;
                .el-input{
                    width: 300px;
                    // height: 40px;
                }

                .el-button{
                    margin-top: 20px;
                    width: 150px;
                    height: 40px;
                    font-size: 18px;
                }
            }
        }
    }

}

</style>