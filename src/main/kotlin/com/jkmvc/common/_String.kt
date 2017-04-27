package com.jkmvc.common

import java.text.ParseException
import java.util.*

/****************************** 字符串扩展 *******************************/
/**
 * StringBuilder扩展
 * 清空
 */
public fun StringBuilder.clear(): StringBuilder {
    return this.delete(0, this.length - 1)
}

/**
 * StringBuilder扩展
 *  删除最后的一段子字符串
 */
public fun StringBuilder.deleteSuffix(str:String):StringBuilder {
    if(this.endsWith(str)) {
        val start = length - str.length;
        delete(start, length);
    }
    return this;
}

/**
 * 首字母大写
 */
public fun String.ucFirst(): String {
    val cs = this.toCharArray()
    if(cs[0] in 'a'..'z')
        cs[0] = cs[0] - 32
    return String(cs)
}

/**
 * 首字母小写
 */
public fun String.lcFirst(): String {
    val cs = this.toCharArray()
    if(cs[0] in 'A'..'Z')
        cs[0] = cs[0] + 32
    return String(cs)
}

/**
 * 转换为日期类型
 */
public fun String.toDate(): Date {
    return java.text.SimpleDateFormat("yyyy-MM-dd").parse(this)
}

/**
 * 去掉两头的字符
 */
public fun String.trim(str:String): String {
    var start = 0;
    var end = length
    if(this.startsWith(str))
        start = str.length;
    if(this.endsWith(str))
        end = length - str.length;
    if(start == 0 && end == length)
        return this;
    return this.substring(start, end);
}

/**
 * 去掉两头的字符
 */
public fun String.trim(preffix:String, suffix:String): String {
    var start = 0;
    var end = length
    if(this.startsWith(preffix))
        start = preffix.length;
    if(this.endsWith(suffix))
        end = length - suffix.length;
    if(start == 0 && end == length)
        return this;
    return this.substring(start, end);
}

/**
 * 替换字符串
 *
 * @param string str 字符串模板
 * @param array params 参数
 * @return string
 */
public fun String.replaces(params:List<String>):String
{
    return this.replace(":([\\w\\d]+)".toRegex()){ matches:MatchResult ->
        val i = matches.groupValues[1]
        val value = params.get(i.toInt());
        if(value == null)
            ""
        else
            value.toString()
    };
}