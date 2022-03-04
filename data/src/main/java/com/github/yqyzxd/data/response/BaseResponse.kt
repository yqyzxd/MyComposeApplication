package com.github.yqyzxd.data.response

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: BaseResponse
 * Author: wind
 * Date: 2022/3/4 11:33 上午
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
abstract class BaseResponse<T> {
    var err: Int = -1
    var msg: String = ""
    var items: T? = null
}
