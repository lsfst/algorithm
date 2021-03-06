/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190424    lsf     初始版本
 */
package com.algorithm.longpoll.deferredResult;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @brief TODO 类功能作用及实现逻辑说明
 * @see
 */
//@Api( description = "长轮询" )
@RestController
@RequestMapping( value = "/ecc" )
public class ApolloController {

    @Autowired
    LongPoolingContainer container;


    //长轮询
//    @ApiOperation( value = "轮询接口" )
//    @ApiImplicitParams( {
//    @ApiImplicitParam( name = "vid", value = "设备ID", required = true, dataType = "String" )
//    } )
    @GetMapping( value = "/watch/{vid}" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public DeferredResult< Map > watch( @PathVariable( "vid" ) String vid ) {
        return container.watch( vid );
    }

    //模拟发布message
//    @ApiOperation( value = "模拟发布message" )
//    @ApiImplicitParams( {
//            @ApiImplicitParam( name = "vid", value = "设备ID", required = true, dataType = "String" ),
//            @ApiImplicitParam( name = "message", value = "设备消息", required = true, dataType = "String" )
//    } )
    @GetMapping( value = "/publish/{type}/{vid}" )
    public void publish( @PathVariable( "type" ) String type,@PathVariable( "vid" ) String vid, @RequestParam(value="message",defaultValue = "") String message ) {
        container.setMsg( vid, type,message );
    }

}