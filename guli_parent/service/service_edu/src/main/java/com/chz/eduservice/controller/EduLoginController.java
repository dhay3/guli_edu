package com.chz.eduservice.controller;

import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Api("登入接口")
@RestController
//@CrossOrigin(origins = "http://localhost:9528")
@RequestMapping("/eduservice/user")
public class EduLoginController {
    /**
     * 协议(http和https),ip地址,端口号 其中任意一个不同, 就会产生跨域问题
     * 从前端端口9528,到后端端口需要解决跨域请求
     */
    @ApiOperation(value = "登入", notes = "对应前端框架中的login方法")
//    @CrossOrigin(origins = "http://localhost:9528")
    @PostMapping("/login")
    public ResponseBo login() {
        return ResponseBo.ok().data("token", "this is a token");
    }

    @ApiOperation(value = "获取登入用户的信息", notes = "对应前端框架中的getInfo方法")
//    @CrossOrigin(origins = "http://localhost:9528")
    @GetMapping("/info")
    public ResponseBo info() {
        Map<String, Object> info = new HashMap<>();
        info.put("roles", "[admin]");
        info.put("name", "chz");
        info.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return ResponseBo.ok().data(info);
    }
}
